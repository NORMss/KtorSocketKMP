package ru.normno.myktorsocketkmp.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngine(): HttpClientEngine = Darwin.create()