package com.example.kilt.network

import com.example.kilt.data.ErrorResponse
import com.example.kilt.data.OtpResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class OtpResultAdapter : JsonDeserializer<OtpResult> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OtpResult {
        val jsonObject = json!!.asJsonObject
        return if (jsonObject.has("id") && jsonObject.has("phone")) {
            // Deserialization for Success
            OtpResult.Success(
                id = jsonObject.get("id").asInt,
                phone = jsonObject.get("phone").asString,
                type = jsonObject.get("type").asString,
                createdAt = jsonObject.get("createdAt").asString
            )
        } else if (jsonObject.has("error")) {
            // Deserialization for Failure
            val errorMessage = jsonObject.getAsJsonObject("error").get("msg").asString
            OtpResult.Failure(ErrorResponse(errorMessage))
        } else {
            throw JsonParseException("Unknown OtpResult type")
        }
    }
}