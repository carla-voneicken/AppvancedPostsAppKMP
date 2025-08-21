//
//  PostDetailViewModel.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

@MainActor
class PostDetailViewModel: ObservableObject {
    @Published var post: Post?
    @Published var userId: Int
    @Published var title: String
    @Published var body: String
    @Published var errorMessage: String?
    @Published var showError: Bool = false
    @Published var isSaved = false

    
    init(post: Post?, userId: Int) {
        if let post {
            self.post = post
            self.userId = userId
            self.title = post.title
            self.body = post.body
        } else {
            self.userId = userId
            self.title = ""
            self.body = ""
        }
    }
    
    func savePost() async {
        let newPost = Post(
            userId: userId,
            id: post?.id ?? 0, // 0 if it's a new post
            title: title,
            body: body)
        do {
            if post != nil {
                _ = try await UpdatePostUsecase().execute(post: newPost)
            } else {
                _ = try await CreatePostUsecase().execute(post: newPost)
            }
        } catch let error as RepositoryError {
            self.errorMessage = error.errorDescription
            showError = true
        } catch {
            self.errorMessage = "Ein unbekannter Fehler ist aufgetreten"
            showError = true
        }
        self.isSaved = true
    }
}
