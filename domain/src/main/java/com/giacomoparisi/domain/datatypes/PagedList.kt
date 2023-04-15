package com.giacomoparisi.domain.datatypes

class PagedList<out T>(
    val data: List<T>,
    val page: Int,
    val isCompleted: Boolean
)

fun <T> List<T>.toPagedList(page: Int, isCompleted: Boolean) =
    PagedList(this, page, isCompleted)