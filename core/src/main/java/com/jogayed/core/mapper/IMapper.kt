package com.jogayed.core.mapper

interface IMapper<From, To> {
    fun map(inputFormat: From): To
    fun reverseMap(inputFormat: To): From
}