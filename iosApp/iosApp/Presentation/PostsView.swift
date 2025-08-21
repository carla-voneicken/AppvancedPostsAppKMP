//
//  PostsView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 17.07.25.
//

import SwiftUI

struct PostsView: View {
    @StateObject var viewmodel: PostsViewModel
    @State private var isShowingNewPost = false
    
    var body: some View {
        if viewmodel.isLoading {
            ProgressView()
                .scaleEffect(2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color.black))
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            VStack {
                List(viewmodel.posts) { post in
                    NavigationLink(destination: PostDetailView(viewmodel: PostDetailViewModel(post: post, userId: viewmodel.userId))){
                        VStack(alignment: .leading) {
                            Text(post.title)
                                .font(.headline)
                            Text(post.body)
                                .font(.system(size: 14))
                        }
                    }
                }
                if viewmodel.errorMessage != nil {
                    Text(viewmodel.errorMessage!)
                        .foregroundColor(.red)
                }
            }
            .navigationTitle("Posts")
            // if isShowingNewPost is true (when the New Post button is pressed), go to the PostDetailView
            .navigationDestination(isPresented: $isShowingNewPost) {
                PostDetailView(viewmodel: PostDetailViewModel(post: nil, userId: viewmodel.userId))
            }

            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Neuer Post") {
                        isShowingNewPost = true
                    }
                }
            }
        }
    }
}


#Preview {
    NavigationStack {
        PostsView(viewmodel: PostsViewModel(userId: 1))
    }
}
