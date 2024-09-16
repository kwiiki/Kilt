package com.example.kilt.data


sealed class FilterValue {
    data class SingleValue(val value: Int) : FilterValue()
    data class ListValue(val values: List<Int>) : FilterValue()
    data class RangeValue(val from: Int, val to: Int) : FilterValue()
}