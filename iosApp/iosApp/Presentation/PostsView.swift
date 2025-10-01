//
//  PostsView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 17.07.25.
//

import SwiftUI
import KMPObservableViewModelSwiftUI
import Shared

struct PostsView: View {
    @StateViewModel var viewmodel: PostsViewModel
    
    
    var body: some View {
        if viewmodel.uiState.isLoading {
            ProgressView()
                .scaleEffect(2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color.black))
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            VStack {
                List(viewmodel.uiState.posts, id: \.id) { post in
                    NavigationLink(
                        destination: createPostDetailView(for: post)
                    ) {
                        PostRow(post: post)
                    }
                }
            }
            .navigationTitle("Posts")
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    NavigationLink(
                        destination: createNewPostView()
                    ) {
                        Text("Neuer Post")
                    }
                }
            }
        }
    
    // Display errorMessage
    if let errorMessage = viewmodel.uiState.errorMessage {
        Text(errorMessage)
            .foregroundColor(.red)
    }
}
    
    
    // Helper Methods
    @ViewBuilder
    private func createPostDetailView(for post: Post) -> some View {
        PostDetailView(
            viewmodel: PostDetailViewModel(
                postId: KotlinLong(value: Int64(post.id)),  // Convert Swift Int to KotlinInt
                userId: Int64(post.userId)
            )
        )
    }
    
    @ViewBuilder
    private func createNewPostView() -> some View {
        PostDetailView(
            viewmodel: PostDetailViewModel(
                postId: nil,
                userId: Int64(viewmodel.uiState.userId)
            )
        )
    }
}


struct PostRow: View {
    let post: Post
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(post.title)
                .font(.headline)
            Text(post.body)
                .font(.system(size: 14))
        }
        .padding(.vertical, 4)
    }
}
