package com.example.easynews

data class Result(
    val stat: String,
    val `data`: List<Data>,
    val page: String,
    val pageSize: String
)
