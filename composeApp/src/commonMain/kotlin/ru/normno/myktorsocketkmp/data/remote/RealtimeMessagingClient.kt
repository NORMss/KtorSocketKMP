package ru.normno.myktorsocketkmp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import ru.normno.myktorsocketkmp.data.ConnectMessage
import ru.normno.myktorsocketkmp.data.LoginParams
import ru.normno.myktorsocketkmp.data.MethodMessage
import ru.normno.myktorsocketkmp.data.PongMessage
import ru.normno.myktorsocketkmp.data.SubscriptionMessage

class RealtimeMessagingClient(
    private val httpClient: HttpClient,
    private val authToken: String,
    private val roomId: String
) {
    private val baseUrl = "wss://server/websocket"
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    fun connect(): Flow<Frame.Text> = flow {
        println("Connecting to WebSocket at $baseUrl...")
        httpClient.webSocket(urlString = baseUrl) {
            println("WebSocket connection established.")

            // Отправка ConnectMessage
            println("Sending ConnectMessage...")
            sendSerialized(ConnectMessage())

            // Обработка входящих сообщений
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val responseText = frame.readText()
                        println("Received: $responseText")

                        // Отправка данных в поток
                        emit(frame)

                        // Обработка ответа на ConnectMessage
                        if (responseText.contains("connected")) {
                            println("Server is connected. Sending login message...")
                            val loginMessage = MethodMessage(
                                method = "login",
                                params = listOf(LoginParams(authToken))
                            )
                            sendSerialized(loginMessage)
                        }

                        if (responseText.contains("\"msg\":\"ping\"")) {
                            println("Received ping. Sending pong...")
                            sendSerialized(PongMessage())
                        }

                        // Обработка ответа на MethodMessage (логин)
                        if (responseText.contains("result")) {
                            println("Login successful. Sending subscription message...")
                            val subMessage = SubscriptionMessage(
                                name = "stream-room-messages",
                                params = listOf(roomId, "false")
                            )
                            sendSerialized(subMessage)
                        }

                        // Обработка ошибок
                        if (responseText.contains("error")) {
                            println("Error received from server: $responseText")
                        }
                    }

                    is Frame.Close -> {
                        println("WebSocket connection closed: ${frame.readReason()}")
                    }

                    else -> {
                        println("Received non-text frame: $frame")
                    }
                }
            }
        }
    }.catch { e ->
        println("WebSocket connection failed: ${e.message}")
    }

    private suspend inline fun <reified T> WebSocketSession.sendSerialized(data: T) {
        val jsonString = json.encodeToString(data)
        println("Sending JSON: $jsonString")
        send(Frame.Text(jsonString))
    }
}
