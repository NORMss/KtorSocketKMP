package ru.normno.myktorsocketkmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import io.ktor.websocket.readText
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
        val realtimeMessagingClient = RealtimeMessagingClient(
            httpClient = httpClient,
            authToken = "",
            roomId = "",
        )

        runBlocking {
            realtimeMessagingClient.connect()
                .collect { frame ->
                    println("Collected frame: ${frame.readText()}")
                }
        }
    }
}