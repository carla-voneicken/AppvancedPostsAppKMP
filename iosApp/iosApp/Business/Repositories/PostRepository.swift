//
//  PostRepository.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 21.07.25.
//

import Foundation
//import Alamofire

class PostRepository {
    // create instance of the class
    // static -> this property belongs to the class, we don't need to first create an instance of it to access shared
    static let shared = PostRepository()
    // make init private so no other class can create another instance -> Singleton
    private init() {}
    
    private let httpService = HttpService.shared
    private let baseURL = "https://jsonplaceholder.typicode.com"
    
    func getPostsByUserId(userId: Int) async throws -> [Post] {
        let url = "\(baseURL)/posts?userId=\(userId)"
        return try await safeCall {
            try await self.httpService.get(url)
        }
    }
    
    func getPostById(id: Int) async throws -> Post {
        let url = "\(baseURL)/posts/\(id)"
        return try await safeCall {
            try await self.httpService.get(url)
        }
    }
    
    func createPost(post: Post) async throws -> Post {
        let url = "\(baseURL)/posts"
        return try await safeCall {
            try await self.httpService.post(url, data: post)
        }
    }
    
    func updatePost(post: Post) async throws -> Post {
        let url = "\(baseURL)/posts/\(post.id)"
        return try await safeCall {
            try await self.httpService.put(url, data: post)
        }
    }    
}
