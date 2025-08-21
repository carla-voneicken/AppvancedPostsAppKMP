//
//  UpdatePostUsecase.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

class UpdatePostUsecase {
    private let repository = PostRepository.shared
    
    func execute(post: Post) async throws -> Post {
        return try await repository.updatePost(post: post)
    }
}
