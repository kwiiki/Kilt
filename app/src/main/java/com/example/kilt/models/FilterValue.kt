package com.example.kilt.models


sealed class FilterValue {
    data class SingleValue(val value: Int) : FilterValue()
    data class ListValue(val values: List<Int>) : FilterValue()
    data class ListValue1(val values: List<String>) : FilterValue()
    data class RangeValue(val from: Long, val to: Long) : FilterValue()
    data class RangeValueForMap(val from: Double, val to: Double) : FilterValue()
}