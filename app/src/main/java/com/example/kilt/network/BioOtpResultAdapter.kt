package com.example.kilt.network

import com.example.kilt.models.authentification.BioOtpResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class BioOtpResultAdapter : JsonDeserializer<BioOtpResult> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BioOtpResult {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("JsonElement is null")

        return when {
            jsonObject.has("id") && jsonObject.has("phone") -> {
                BioOtpResult.RegisteredUser(
                    id = jsonObject.get("id").asLong,
                    phone = jsonObject.get("phone").asString,
                    type = jsonObject.get("type").asString,
                    createdAt = jsonObject.get("createdAt").asString,
                    success = jsonObject.get("success").asBoolean,
                    register = jsonObject.get("register").asBoolean
                )
            }
            jsonObject.has("register") && jsonObject.has("success") && jsonObject.has("message") -> {
                BioOtpResult.Success(
                    register = jsonObject.get("register").asBoolean,
                    success = jsonObject.get("success").asBoolean,
                    message = jsonObject.get("message").asString
                )
            }
            jsonObject.has("success") && jsonObject.has("message") -> {
                BioOtpResult.Failure(
                    success = jsonObject.get("success").asBoolean,
                    message = jsonObject.get("message").asString
                )
            }
            else -> throw JsonParseException("Неизвестный тип BioOtpResult")
        }
    }
}