package ru.normno.myktorsocketkmp.data

import kotlinx.serialization.Serializable

@Serializable
data class ConnectMessage(
    val msg: String = "connect",
    val version: String = "1",
    val support: List<String> = listOf("1")
)

@Serializable
data class SubscribeMessage(
    val msg: String = "sub",
    val id: String = "2",
    val name: String = "stream-room-messages",
    val params: Params,
)

@Serializable
data class Params(
    val channelId: String,
    val useCollection: Boolean = false,
)

@Serializable
data class LoginParams(val resume: String)

@Serializable
data class Password(val digest: String, val algorithm: String = "sha-256")

@Serializable
data class LoginMessage(
    val msg: String = "method",
    val method: String = "login",
    val id: String = "42",
    val params: List<LoginParams>,
)