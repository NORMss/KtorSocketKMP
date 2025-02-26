package ru.normno.myktorsocketkmp.data

import kotlinx.serialization.Serializable

@Serializable
data class ConnectMessage(
    val msg: String = "connect",
    val version: String = "1",
    val support: List<String> = listOf("1")
)

@Serializable
data class LoginParams(val resume: String)

@Serializable
data class MethodMessage(
    val msg: String = "method",
    val method: String,
    val id: String = "1",
    val params: List<LoginParams>
)

@Serializable
data class SubscriptionMessage(
    val msg: String = "sub",
    val id: String = "2",
    val name: String,
    val params: List<String>,
)

@Serializable
data class PingMessage(val msg: String = "ping")

@Serializable
data class PongMessage(val msg: String = "pong")