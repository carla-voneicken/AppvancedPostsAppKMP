//
//  CreatePostUsecase.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

class CreatePostUsecase {
    private let repository = PostRepository.shared
    
    func execute(post: Post) async throws -> Post {
        try await repository.createPost(post: post)
    }
}
