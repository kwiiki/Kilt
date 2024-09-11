package com.example.kilt.data


//@Serializable
sealed class FilterValue {
//    @Serializable
    data class SingleValue(val value: Int) : FilterValue()

//    @Serializable
    data class ListValue(val values: List<Int>) : FilterValue()

//    @Serializable
    data class RangeValue(val from: Int, val to: Int) : FilterValue()
}