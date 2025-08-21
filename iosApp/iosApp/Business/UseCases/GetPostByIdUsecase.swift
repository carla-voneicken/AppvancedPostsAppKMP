//
//  GetPostByIdUsecase.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

class GetPostByIdUsecase {
    private let repository = PostRepository.shared
    
    func execute(id: Int) async throws -> Post {
        try await repository.getPostById(id: id)
    }
}
