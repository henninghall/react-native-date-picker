//
//  ComponentDataSource.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 10/06/2024.
//

import Foundation

public struct ComponentDataSource: DataSource {
    let data: [String]
    let component: Calendar.Component

    var count: Int {
        data.count
    }

    var middleRow: Int {
        0
    }

    public init(data: [String], component: Calendar.Component) {
        self.data = data
        self.component = component
    }

    func getValueForRow(_ row: Int) -> String? {
        data[safe: row]
    }

    func getRowForValue(_ value: String?) -> Int? {
        guard let value else { return nil }
        return data.firstIndex(of: value)
    }

    func getOriginalRow(_ row: Int) -> Int {
        row
    }
}
