//
//  DataManager.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 10/06/2024.
//

import Foundation

public struct DataManager {
    let collections: [DataSource]

    public var numberOfComponents: Int {
        collections.count
    }

    public func getValueInComponentForRow(component: Int, row: Int) -> String? {
        collections[safe: component]?.getValueForRow(row)
    }

    public func getNumberOfRowsInComponent(_ component: Int) -> Int {
        collections[safe: component]?.count ?? 0
    }

    public var components: Set<Calendar.Component> {
        Set(collections.compactMap { $0.component == .nanosecond ? nil : $0.component })
    }

    public func componentIndex(component: Calendar.Component) -> Int? {
        collections.firstIndex { $0.component == component }
    }
}
