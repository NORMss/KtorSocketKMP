package ru.normno.myktorsocketkmp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import ru.normno.myktorsocketkmp.data.ConnectMessage
import ru.normno.myktorsocketkmp.data.LoginMessage
import ru.normno.myktorsocketkmp.data.LoginParams
import ru.normno.myktorsocketkmp.data.Params
import ru.normno.myktorsocketkmp.data.SubscribeMessage

class RealtimeMessagingClient(
    private val httpClient: HttpClient
) {
    private var session: WebSocketSession? = null
    private val baseUrl = "wss://eltex2025.rocket.chat"

    fun connect(): Flow<Frame.Text> = flow {
        try {
            session = httpClient.webSocketSession {
                url("$baseUrl/websocket")
            }
            println("WebSocket-соединение установлено.")

            // Отправка CONNECT-сообщения
            session?.send(Frame.Text(Json.encodeToString(ConnectMessage())))
            println("Сообщение 'connect' отправлено.")

//            // Аутентификация через токен
//            session?.send(
//                Frame.Text(
//                    Json.encodeToString(
//                        LoginMessage(
//                            params = listOf(
//                                LoginParams(
//                                    resume = "W9wSDcc8j6wOD-bgRvQrtkrUUDgNJruurMzhSRHDMeZ"
//                                )
//                            )
//                        )
//                    )
//                )
//            )
//            println("Сообщение 'login' отправлено с токеном.")
//
//            // Подписка на канал
//            val channelId = "eLekPeZQDqt4AWPRYt4f2t2BwyHuQ3GBdM"
//            val subscribeMessage = SubscribeMessage(
//                params = Params(channelId = channelId)
//            )
//            session?.send(Frame.Text(Json.encodeToString(subscribeMessage)))
//            println("Сообщение 'subscribe' отправлено для канала $channelId.")

            // Обработка входящих сообщений
            session?.incoming
                ?.consumeAsFlow()
                ?.filterIsInstance<Frame.Text>()
                ?.collect { frame ->
                    println("Получено сообщение: ${frame.readText()}")
                    emit(frame)
                }
        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
        } finally {
            session?.close()
            println("WebSocket-соединение закрыто.")
        }
    }
}