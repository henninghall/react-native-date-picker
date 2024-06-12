//
//  DataSource.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 10/06/2024.
//

import Foundation

protocol DataSource {
    var data: [String] { get }
    var component: Calendar.Component { get }
    var count: Int { get }
    var middleRow: Int { get }

    func getValueForRow(_ row: Int) -> String?
    func getRowForValue(_ value: String?) -> Int?
    func getOriginalRow(_ row: Int) -> Int
}
