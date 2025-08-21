//
//  Post.swift
//  AppvancedUebung
//
//  Created by Carla von Eicken on 21.07.25.
//

import Foundation

struct Post: Codable, Identifiable {
    let userId: Int
    let id: Int
    let title: String
    let body: String
}
