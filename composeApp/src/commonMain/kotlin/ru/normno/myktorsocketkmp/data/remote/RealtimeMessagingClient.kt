package ru.normno.myktorsocketkmp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import ru.normno.myktorsocketkmp.data.ConnectMessage
import ru.normno.myktorsocketkmp.data.LoginMessage
import ru.normno.myktorsocketkmp.data.LoginParams
import kotlin.text.contains

class RealtimeMessagingClient(
    private val httpClient: HttpClient
) {
    private var session: WebSocketSession? = null

    suspend fun connection(): DefaultClientWebSocketSession {
        return httpClient.webSocketSession {
        }
    }

    fun close() {
        httpClient.close()
    }

//    sendSerialized(ConnectMessage())
//    for (frame in incoming) {
//        if (frame is Frame.Text) {
//            val responseText = frame.readText()
//            println("Получено сообщение: $responseText")
//            if (responseText.contains("connected")) {
//                break
//            }
//        }
//    }
//    sendSerialized(
//    LoginMessage(
//    id = "1",
//    params = LoginParams(
//    user = "norm",
//    password = "20332601"
//    )
//    )
//    )
//    for (frame in incoming) {
//        if (frame is Frame.Text) {
//            val responseText = frame.readText()
//            println("Получено сообщение: $responseText")
//            if (responseText.contains("result") && responseText.contains("id\":\"1\"")) {
//                println("Аутентификация успешна")
//                break
//            }
//        }
//    }

    private val baseUrl = "https://eltex2025.rocket.chat"

}