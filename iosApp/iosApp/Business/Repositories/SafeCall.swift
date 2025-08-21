//
//  SafeCall.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation
import Alamofire

// helper function for catching errors
func safeCall<T>(_ operation: @escaping () async throws -> T) async throws -> T {
    do {
        return try await operation()
    } catch let afError as AFError {
        if let status = afError.responseCode {
            throw RepositoryError.serverError(statusCode: status)
        } else {
            throw RepositoryError.networkError(underlying: afError)
        }
    } catch is DecodingError {
        throw RepositoryError.decodingError
    } catch {
        throw RepositoryError.unknown
    }
}
