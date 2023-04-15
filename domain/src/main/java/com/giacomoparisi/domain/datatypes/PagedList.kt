package com.giacomoparisi.domain.datatypes

class PagedList<out T>(
    val data: List<T>,
    val page: Int,
    val isCompleted: Boolean
) {

    val size get() = data.size
    companion object {

        fun <T> empty(): PagedList<T> =
            PagedList(data = emptyList(), page = 0, isCompleted = false)

    }

}

fun <T> List<T>.toPagedList(page: Int = 0, isCompleted: Boolean = false): PagedList<T> =
    PagedList(this, page, isCompleted)

fun <T> PagedList<T>.addPage(other: PagedList<T>): PagedList<T> =
    PagedList(data.plus(other.data), other.page, other.isCompleted)