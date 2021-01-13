package com.rrr.shakemon.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Model class to hold "Shakespeare text" from fun translations api
 */
data class Translation(
    @SerializedName("success")
    val success: Success,
    @SerializedName("contents")
    val contents: Contents
) {
    inner class Success(
        @SerializedName("total")
        val total: Int
    )

    inner class Contents(
        @SerializedName("translated")
        val translated: String,
        @SerializedName("text")
        val text: String,
        @SerializedName("translation")
        val translation: String
    )
}