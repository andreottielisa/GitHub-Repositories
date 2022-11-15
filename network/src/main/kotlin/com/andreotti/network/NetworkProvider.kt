package com.andreotti.network

interface NetworkProvider {

    fun <T> create(service: Class<T>): T
}