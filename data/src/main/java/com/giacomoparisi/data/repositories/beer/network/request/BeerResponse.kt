package com.giacomoparisi.data.repositories.beer.network.request

import com.giacomoparisi.entities.beer.Beer
import com.squareup.moshi.Json

data class BeerResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "image_url") val imageUrl: String? = null
) {

    fun toBeer(): Beer? =
        when (null) {
            id, name, tagline, description, imageUrl -> null
            else ->
                Beer(
                    id = id,
                    name = name,
                    shortDescription = tagline,
                    description = description,
                    imageUrl = imageUrl
                )
        }

}
