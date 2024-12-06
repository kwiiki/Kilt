package com.example.kilt.core.network

import android.util.Log
import com.example.kilt.models.authentification.CheckOtpResult
import com.example.kilt.models.authentification.ErrorResponse
import com.example.kilt.models.authentification.User
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class CheckOtpResultAdapter : JsonDeserializer<CheckOtpResult> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CheckOtpResult {
        val jsonObject = json?.asJsonObject
            ?: throw JsonParseException("JSON is null or not a JsonObject")
        Log.d("parsing", "deserialize: 1 section")
        return when {
            jsonObject.has("user") || jsonObject.has("token") || jsonObject.has("expired") || jsonObject.has(
                "created"
            ) || jsonObject.has("bonus") -> {
                val userJson = jsonObject.getAsJsonObject("user")
                Log.d("parsing", "deserialize: $userJson")
                val user = context?.deserialize<User>(userJson, User::class.java)
                CheckOtpResult.Success(
                    expired = jsonObject.get("expired").asBoolean,
                    user = user ?: throw JsonParseException("User is null"),
                    token = jsonObject.get("token").asString,
                    created = jsonObject.get("created").asBoolean,
                    bonus = jsonObject.get("bonus").asInt
                )
            }
            jsonObject.has("error") -> {
                // Если ошибка
                val errorMessage = jsonObject.getAsJsonObject("error").get("msg").asString
                CheckOtpResult.Failure(ErrorResponse(errorMessage))
            }

            else -> throw JsonParseException("Unknown CheckOtpResult type")
        }
    }
}