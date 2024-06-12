//
//  DatePicker.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

import UIKit

@objc(DatePicker)
@objcMembers public class DatePicker: UIPickerView {
    private(set) var calendar: Calendar = .init(identifier: .gregorian)
    public var selectedDate: Date = .init()
    public var minimumDate: Date?
    public var maximumDate: Date?
    public var datePickerMode: UIDatePicker.Mode = .date
    public var onChange: (([String: Any]) -> Void)?
    public var onStateChange: (([String: Any]) -> Void)?

    public var locale: Locale = .current {
        didSet {
            dataManager = createDataManager()
        }
    }

    public var minuteInterval: Int = 1 {
        didSet {
            if [.dateAndTime, .countDownTimer, .time].contains(datePickerMode) {
                dataManager = createDataManager()
            }
        }
    }

    var isPickerScrolling = false {
        didSet {
            print("[TEST] \(isPickerScrolling)")
            onStateChange?(["state": isPickerScrolling ? "spinnig" : "idle"])
        }
    }

    private(set) var dataManager: DataManager = .init(collections: []) {
        didSet {
            reloadAllComponents()
            setDate(selectedDate)
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }

    public func setup() {
        overrideUserInterfaceStyle = .light
        calendar.locale = locale
        delegate = self
        dataSource = self
    }

    public func setTextColorProp(_ hexColor: String?) {
        if hexColor == "#000000" {
            overrideUserInterfaceStyle = .light
        } else if hexColor?.lowercased() == "#ffffff" {
            overrideUserInterfaceStyle = .dark
        }
    }

    public func setTimeZoneOffsetInMinutes(_ timeZoneOffsetInMinutes: String) {
        if timeZoneOffsetInMinutes.isEmpty {
            calendar.timeZone = TimeZone.current
        } else if let minutes = Int(timeZoneOffsetInMinutes) {
            calendar.timeZone = TimeZone(secondsFromGMT: minutes * 60) ?? TimeZone.current
        }
        dataManager = createDataManager()
    }

    public func setDate(_ date: Date) {
        let components = calendar.dateComponents(dataManager.components, from: date)
        dataManager.collections.enumerated().map { index, collection in
            var row: Int? = nil
            switch collection.component {
            case .year:
                row = collection.getRowForValue("\(components.year ?? 0)")
            case .month, .day:
                row = collection.middleRow + (components.value(for: collection.component) ?? 0) - 1
            case .hour:
                row = collection.middleRow + (components.value(for: collection.component) ?? 0)
                if let amPmIndex = dataManager.componentIndex(component: .nanosecond),
                   let hourComponent = components.value(for: collection.component)
                {
                    selectRow(hourComponent > 11 ? 1 : 0, inComponent: amPmIndex, animated: false)
                }
            case .minute:
                if let minutes = components.value(for: .minute) {
                    let minutesValue = Int(
                        (Double(minutes) / Double(minuteInterval))
                            .rounded() * Double(minuteInterval)
                    )

                    row = collection.getRowForValue("\(minutesValue)")
                }
            default: break
            }
            if let row {
                selectRow(row, inComponent: index, animated: false)
            }
        }

        selectedDate = date
    }

    func is24HourFormat() -> Bool {
        let dateFormat = DateFormatter.dateFormat(fromTemplate: "j", options: 0, locale: locale) ?? ""
        return dateFormat.contains("H") || dateFormat.contains("k")
    }
}
