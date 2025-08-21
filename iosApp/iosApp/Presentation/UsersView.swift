//
//  UsersView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import SwiftUI

struct UsersView: View {
    @StateObject var viewmodel = UsersViewModel()
    
    var body: some View {
        if viewmodel.isLoading {
            ProgressView()
                .scaleEffect(2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color.black))
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            NavigationStack {
                VStack {
                    List(viewmodel.users) { user in
                        NavigationLink(destination: PostsView(viewmodel: PostsViewModel(userId: user.id))){
                            VStack(alignment: .leading) {
                                Text("Username")
                                    .font(.caption)
                                Text(user.username)
                            }
                        }
                        if viewmodel.errorMessage != nil {
                            Text(viewmodel.errorMessage!)
                                .foregroundColor(.red)
                        }
                    }
                }
                .navigationTitle("Users")
            }
        }
    }
}

#Preview {
    UsersView()
}
