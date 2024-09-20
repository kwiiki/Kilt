package com.example.kilt.data.config

import com.google.gson.annotations.SerializedName

data class Advanced(
    val ad_frequency_1: String,
    val ad_frequency_2: String,
    val bonus_enabled: String,
    val partners_show: String,
    @SerializedName("quick-filters-1-1-1")
    val quick_filters_1_1_1: String,
    @SerializedName("quick-filters-1-1-2")
    val quick_filters_1_1_2: String,
    @SerializedName("quick-filters-2-2-6")
    val quick_filters_2_2_6: String,
    @SerializedName("quick-filters-1-2-6")
    val quick_filters_1_2_6: String,
    @SerializedName("quick-filters-2-1-2")
    val quick_filters_2_1_2: String,
    @SerializedName("quick-filters-2-1-1")
    val quick_filters_2_1_1: String,
    val initial_bonus: String,
    val bonus_to_referred_user: String,
    val bonus_to_referer: String,
    val published_at_enabled: String,
    val agency_verification_items: String
)