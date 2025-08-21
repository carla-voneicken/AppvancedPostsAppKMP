//
//  GetPostByUserIdUsecase.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

class GetPostByUserIdUsecase {
    private let repository = PostRepository.shared
    
    func execute(userId: Int) async throws -> [Post] {
        try await repository.getPostsByUserId(userId: userId)
    }
}
