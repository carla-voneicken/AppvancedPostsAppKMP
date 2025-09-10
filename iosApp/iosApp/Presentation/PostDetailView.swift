//
//  PostDetailView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import SwiftUI
import KMPObservableViewModelSwiftUI
import Shared

struct PostDetailView: View {
    @StateViewModel var viewmodel: PostDetailViewModel
    @State private var showSuccessText = false
    @State private var showErrorDialog = false
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Button("Abbrechen") {
                    dismiss()
                }
                .foregroundStyle(.red)
                Spacer()
                Button("Speichern") {
                    // savePost() already does error handling so this function should not really throw any errors, but in case something outside the ViewModel goes wrong we use a do-catch-block here
                    Task {
                        do {
                            try await viewmodel.savePost()
                        } catch {
                            print("Unexpected error: \(error)")
                        }
                    }
                }
                .buttonStyle(.borderedProminent)
            }
            Text("Titel")
                .font(.headline)
            
            TextField("Titel eingeben",
                      text: .init(
                        get: { viewmodel.uiState.title },
                        set: { viewmodel.updateTitle(newTitle: $0) }
                      ))
                .padding(10)
                .background(RoundedRectangle(cornerRadius: 8).stroke(Color.gray.opacity(0.5)))
                .font(.body)
            
            Text("Inhalt")
                .font(.headline)
            
            TextEditor(text: .init(
                get: { viewmodel.uiState.body },
                set: { viewmodel.updateBody(newBody: $0) }
            ))
                .padding(10)
                .frame(height: 200)
                .background(RoundedRectangle(cornerRadius: 8).stroke(Color.gray.opacity(0.5)))
            
            Spacer()
            if showSuccessText {
                Text("Post gespeichert")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundStyle(.primary)
                    .frame(maxWidth: .infinity, alignment: .center)
            }
        }
        .padding()
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewmodel.uiState.isSaved) {
            if viewmodel.uiState.isSaved {
                showSuccessText = true
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    dismiss()
                }
            }
        }
        .onChange(of: viewmodel.uiState.showError) {
            showErrorDialog = viewmodel.uiState.showError
        }
        .alert("Fehler", isPresented: $showErrorDialog) {
            Button("OK") { viewmodel.dismissError() }
        } message: {
            Text(viewmodel.uiState.errorMessage ?? "Ein unbekannter Fehler ist aufgetreten")
        }
    }
}
