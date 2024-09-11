package com.example.kilt.data


//@Serializable
data class Filters(
    val filterMap: MutableMap<String, FilterValue> = mutableMapOf()
)


//object FilterValueSerializer : KSerializer<FilterValue> {
//
//    override val descriptor: SerialDescriptor = FilterValue.serializer().descriptor
//    override fun deserialize(decoder: Decoder): FilterValue {
//        TODO("Not yet implemented")
//    }
//
//    override fun serialize(encoder: Encoder, value: FilterValue) {
//        val jsonEncoder = encoder as? kotlinx.serialization.json.JsonEncoder
//            ?: throw IllegalStateException("Expected JsonEncoder")
//
//        val jsonElement = when (value) {
//            is FilterValue.SingleValue -> JsonPrimitive(value.value)
//            is FilterValue.ListValue -> Json.encodeToJsonElement(FilterValue.ListValue.serializer(), value)
//            is FilterValue.RangeValue -> JsonObject(
//                mapOf(
//                    "from" to JsonPrimitive(value.from),
//                    "to" to JsonPrimitive(value.to)
//                )
//            )
//        }
//
//        jsonEncoder.encodeJsonElement(jsonElement)
//    }
//
//
//}