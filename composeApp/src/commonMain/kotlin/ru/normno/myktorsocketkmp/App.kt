package ru.normno.myktorsocketkmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.normno.myktorsocketkmp.data.ConnectMessage
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
            realtimeMessagingClient.connection()
        }
    }
}