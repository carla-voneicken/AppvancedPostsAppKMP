//
//  AppvancedPostsApp.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 17.07.25.
//

import SwiftUI
import Shared

@main
struct iosApp: App {
    init() {
        // Swift automatically renamed the function initKoin() to doInitKoin()
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            UsersView()
        }
    }
}
