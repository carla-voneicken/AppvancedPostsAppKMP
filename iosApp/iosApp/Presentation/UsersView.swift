//
//  UsersView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import SwiftUI
import KMPObservableViewModelSwiftUI
import Shared

struct UsersView: View {
    @StateViewModel var viewmodel = UsersViewModel()
    
    var body: some View {
        if viewmodel.uiState.isLoading {
            ProgressView()
                .scaleEffect(2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color.black))
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            NavigationStack {
                VStack {
                    List(viewmodel.uiState.users.map {
                        IdentifiableUser(id: Int($0.id), username: $0.username) }
                    ) { user in
                        NavigationLink(destination: PostsView(
                            viewmodel: PostsViewModel(
                                userId: Int64(user.id)
                            )
                        )){
                            VStack(alignment: .leading) {
                                Text("Username")
                                    .font(.caption)
                                Text(user.username)
                            }
                        }
                        if viewmodel.uiState.errorMessage != nil {
                            Text(viewmodel.uiState.errorMessage!)
                                .foregroundColor(.red)
                        }
                    }
                }
                .navigationTitle("Users")
            }
        }
    }
}
