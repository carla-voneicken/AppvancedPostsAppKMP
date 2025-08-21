//
//  PostRepositoryError.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

enum RepositoryError: Error, LocalizedError {
    case networkError(underlying: Error)
    case decodingError
    case serverError(statusCode: Int)
    case unknown

    var errorDescription: String? {
        switch self {
        case .networkError(let err):
            return "Netzwerkfehler: \(err.localizedDescription)"
        case .decodingError:
            return "Fehler beim Verarbeiten der Antwort"
        case .serverError(let code):
            return "Serverfehler mit Statuscode \(code)"
        case .unknown:
            return "Ein unbekannter Fehler ist aufgetreten"
        }
    }
}
