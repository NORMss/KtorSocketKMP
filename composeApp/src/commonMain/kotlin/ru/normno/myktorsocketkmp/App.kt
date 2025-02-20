package ru.normno.myktorsocketkmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.normno.myktorsocketkmp.data.remote.RealtimeMessagingClient
import ru.normno.myktorsocketkmp.data.remote.clientBuilder
import ru.normno.myktorsocketkmp.data.remote.getHttpEngine

@Composable
@Preview
fun App() {
    MaterialTheme {
        val httpClientEngine = getHttpEngine()
        val httpClient = clientBuilder(httpClientEngine)
        val realtimeMessagingClient = RealtimeMessagingClient(httpClient)

        runBlocking {
            realtimeMessagingClient.connect().collect { frame ->
                println("Новое сообщение: ${frame.readText()}")
            }
        }
    }
}