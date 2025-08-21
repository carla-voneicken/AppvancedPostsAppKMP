//
//  PostDetailView.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 23.07.25.
//

import SwiftUI

struct PostDetailView: View {
    @StateObject var viewmodel: PostDetailViewModel
    @State private var showSuccessText = false
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
                    Task {
                        await viewmodel.savePost()
                    }
                }
                .buttonStyle(.borderedProminent)
            }
            Text("Titel")
                .font(.headline)
            
            TextField("Titel eingeben", text: $viewmodel.title)
                .padding(10)
                .background(RoundedRectangle(cornerRadius: 8).stroke(Color.gray.opacity(0.5)))
                .font(.body)
            
            Text("Inhalt")
                .font(.headline)
            
            TextEditor(text: $viewmodel.body)
                .padding(10)
                .frame(height: 200)
                .background(RoundedRectangle(cornerRadius: 8).stroke(Color.gray.opacity(0.5)))
            
            Spacer()
            // display error message
//            if viewmodel.errorMessage != nil {
//                Text(viewmodel.errorMessage!)
//                    .foregroundColor(.red)
//                    .frame(maxWidth: .infinity, alignment: .center)
//            }
            // display success message
            if showSuccessText {
                Text("🥳 Post gespeichert 🥳")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundStyle(.green)
                    .frame(maxWidth: .infinity, alignment: .center)
            }
        }
        .padding()
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewmodel.isSaved) { saved in
            if saved {
                showSuccessText = true
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                    dismiss()
                }
            }
        }
        .alert("Fehler", isPresented: $viewmodel.showError) {
            Button("OK", role: .cancel) {}
        } message: {
            Text(viewmodel.errorMessage ?? "Ein unbekannter Fehler ist aufgetreten")
        }
    }
}


#Preview {
    PostDetailView(
        viewmodel: PostDetailViewModel(
            post: Post(
                userId: 1,
                id: 2,
                title: "qui est esse",
                body: "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
            ), userId: 1
        )
    )
}
