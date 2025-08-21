//
//  GetUserUsecase.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

class GetUserUsecase {
    private let repository = UserRepository.shared
    
    func execute() async throws -> [User] {
        return try await repository.getUsers()
    }
}
