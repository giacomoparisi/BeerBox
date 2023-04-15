package com.giacomoparisi.entities.beer

import com.giacomoparisi.entities.mock.Mock

data class Beer(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val imageUrl: String
) {

    companion object {

        fun mock(): Beer =
            Beer(
                id = 1,
                name = Mock.name,
                shortDescription = Mock.body,
                description = Mock.bodyLong,
                imageUrl = ""
            )

    }

}