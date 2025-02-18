package ru.normno.myktorsocketkmp.data

import kotlinx.serialization.Serializable

@Serializable
data class ConnectMessage(
    val msg: String = "connect",
    val version: String = "1",
    val support: List<String> = listOf("1")
)

@Serializable
data class LoginParams(val user: User, val password: Password)

@Serializable
data class User(val username: String)

@Serializable
data class Password(val digest: String, val algorithm: String = "sha-256")

@Serializable
data class LoginMessage(
    val msg: String = "method",
    val method: String = "login",
    val id: String,
    val params: LoginParams
)