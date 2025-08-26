package de.carlavoneicken.appvancedpostsappkmp.business.utils

interface Error

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}

fun mapNetworkErrorToMessage(error: NetworkError): String {
    return when (error) {
        NetworkError.NO_INTERNET -> "Keine Internetverbindung"
        NetworkError.SERVER_ERROR -> "Server-Fehler"
        NetworkError.UNAUTHORIZED -> "Nicht autorisiert"
        NetworkError.REQUEST_TIMEOUT -> "Anfrage-Timeout"
        NetworkError.SERIALIZATION -> "Daten-Fehler"
        else -> "Ein unbekannter Fehler ist aufgetreten"
    }
}