//
//  Array+subscript.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 10/06/2024.
//

extension Array {
    subscript(safe index: Int) -> Element? {
        indices.contains(index) ? self[index] : nil
    }
}
