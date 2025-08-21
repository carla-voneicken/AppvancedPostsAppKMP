//
//  HttpService.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 21.07.25.
//

import Foundation
import Alamofire

class HttpService {
    // create instance of the class
    // static -> this property belongs to the class, we don't need to first create an instance of it to access shared
    static let shared = HttpService()
    // make init private so no other class can create another instance -> Singleton
    private init() {}
    
    func get<T: Decodable>(_ url: String) async throws -> T{
        let data = try await AF.request(url)
            .validate() // checks if the status code is 200-299
            .serializingDecodable(T.self) // returns a DataResponse<T, AFError> -> so the decoded data or an error
            .value // async property that either returns the decoded object or throws the error
        return data
    }
    
    func post<T: Encodable, U: Decodable>(_ url: String, data: T) async throws -> U {
        let response = try await AF.request(
            url,
            method: .post,
            parameters: data,
            encoder: JSONParameterEncoder.default
        )
            .validate()
            .serializingDecodable(U.self)
            .value
        return response
    }
    
    func put<T: Encodable, U: Decodable>(_ url: String, data: T) async throws -> U {
        let response = try await AF.request(
            url,
            method: .put,
            parameters: data,
            encoder: JSONParameterEncoder.default
        )
            .validate()
            .serializingDecodable(U.self)
            .value
        return response
    }
    
    func delete(_ url: String) async throws {
        _ = try await AF.request(url, method: .delete)
            .validate()
            .serializingData() // returns raw data without decoding -> used when API doesn't return a body or we just want to see if the call was successful
            .value
    }
}
