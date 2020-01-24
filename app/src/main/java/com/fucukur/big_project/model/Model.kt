package com.fucukur.big_project.model

import com.google.gson.annotations.SerializedName

data class DogBreed(
    @SerializedName("id")
    val breedID: String?,
    @SerializedName("name")
    val dogBreed: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("bred_for")
    val breedFor: String?,
    @SerializedName("temperament")
    val temprement: String?,
    @SerializedName("url")
    val imageUrl: String?
)