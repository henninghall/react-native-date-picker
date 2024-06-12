//
//  ComponentInfinityDataSource.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 10/06/2024.
//

import Foundation

let loopingMargin: Int = 1000

public struct ComponentInfinityDataSource: DataSource {
    let data: [String]
    let component: Calendar.Component

    var count: Int {
        data.count * loopingMargin
    }

    var middleRow: Int {
        (loopingMargin / 2) * data.count
    }

    public init(data: [String], component: Calendar.Component) {
        self.data = data
        self.component = component
    }

    func getValueForRow(_ row: Int) -> String? {
        data[safe: row % data.count]
    }

    func getRowForValue(_ value: String?) -> Int? {
        guard let value, let index = data.firstIndex(of: value) else { return nil }
        return middleRow + index
    }

    func getOriginalRow(_ row: Int) -> Int {
        row % data.count
    }
}
