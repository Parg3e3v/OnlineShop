package com.parg3v.domain.serializer

import androidx.datastore.core.Serializer
import com.parg3v.domain.model.LoginInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object LoginSerializer:Serializer<LoginInfo>  {
    override val defaultValue: LoginInfo
        get() = LoginInfo()

    override suspend fun readFrom(input: InputStream): LoginInfo {
        return try {
            Json.decodeFromString(
                deserializer = LoginInfo.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: LoginInfo, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = LoginInfo.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}