package de.carlavoneicken.appvancedpostsappkmp.business.utils

sealed interface NetworkResult<out D, out E: Error> {
    data class Success<out D>(val data: D): NetworkResult<D, Nothing>
    data class Failure<out E: Error>(val error: E): NetworkResult<Nothing, E>
}