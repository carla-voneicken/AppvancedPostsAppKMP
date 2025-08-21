//
//  UserRepository.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation
import Alamofire

class UserRepository {
    static let shared = UserRepository()
    private init() {}
    
    private let httpService = HttpService.shared
    private let baseURL = "https://jsonplaceholder.typicode.com"
    
    func getUsers() async throws -> [User] {
        let url = "\(baseURL)/users"
        return try await safeCall {
            try await self.httpService.get(url)
        }
    }
}
