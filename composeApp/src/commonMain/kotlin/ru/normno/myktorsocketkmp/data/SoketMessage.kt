package ru.normno.myktorsocketkmp.data

import kotlinx.serialization.Serializable

@Serializable
data class ConnectMessage(
    val msg: String = "connect",
    val version: String = "1",
    val support: List<String> = listOf("1")
)

@Serializable
data class LoginParams(val user: String, val password: String)

@Serializable
data class LoginMessage(
    val msg: String = "method",
    val method: String = "login",
    val id: String,
    val params: LoginParams
)