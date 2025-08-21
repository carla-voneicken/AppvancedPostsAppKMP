//
//  PostsViewModel.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 21.07.25.
//

import Foundation

@MainActor
class PostsViewModel: ObservableObject {
    let userId: Int
    @Published var posts: [Post] = []
    @Published var isLoading = true
    @Published var errorMessage: String?
    
    
    init(userId: Int) {
        self.userId = userId
        
        Task {
            await loadPosts()
        }
    }
    
    func loadPosts() async {
        isLoading = true
        do {
            let loadedPosts = try await GetPostByUserIdUsecase().execute(userId: userId)
            self.posts = loadedPosts
        } catch let error as RepositoryError {
            self.errorMessage = error.errorDescription
        } catch {
            self.errorMessage = "Ein unbekannter Fehler ist aufgetreten"
        }
        isLoading = false
    }
}
