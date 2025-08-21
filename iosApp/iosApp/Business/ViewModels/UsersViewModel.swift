//
//  UsersViewModel.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import Foundation

@MainActor
class UsersViewModel: ObservableObject {
    @Published var users: [User] = []
    @Published var isLoading: Bool = true
    @Published var errorMessage: String?
    
    init() {
        Task {
            await loadUsers()
        }
    }
    
    func loadUsers() async {
        isLoading = true
        do {
            let loadedUsers = try await GetUserUsecase().execute()
            self.users = loadedUsers
        } catch let error as RepositoryError {
            self.errorMessage = error.errorDescription
        } catch {
            self.errorMessage = "Ein unbekannter Fehler ist aufgetreten"
        }
        isLoading = false
    }
}
