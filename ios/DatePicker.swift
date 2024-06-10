//
//  DatePicker.swift
//  react-native-date-picker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

import UIKit

@objc(DatePicker)
@objcMembers public class DatePicker: UIPickerView {
    private(set) var days: [String] = []
    private(set) var months: [String] = []
    private(set) var years: [String] = []
    private(set) var calendar: Calendar = .init(identifier: .gregorian)
    private var pickerViewMiddleDays: Int = 0
    private var pickerViewMiddleMonth: Int = 0
    private var lastSelectedRows: (Int, Int, Int) = (0, 0, 0)
    public var selectedDate: Date?
    public var minimumDate: Date?
    public var maximumDate: Date?
    public var onChange: (([String: Any]) -> Void)?
    public var onStateChange: (([String: Any]) -> Void)?

    public var locale: Locale? {
        didSet {
            updateMonthNames()
            reloadAllComponents()
        }
    }

    var isPickerScrolling = false {
        didSet {
            onStateChange?(["state": isPickerScrolling ? "spinnig" : "idle"])
        }
    }

    static let numberOfComponents = 3
    static let numberOfInfiniteRows = 10000

    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }

    public func setup() {
        if #available(iOS 13, *) {
            overrideUserInterfaceStyle = .light
        }
        locale = Locale.current
        initializeDateArrays()
        delegate = self
        dataSource = self
    }

    public func setTextColorProp(_ hexColor: String?) {
        if #available(iOS 13, *) {
            if hexColor == "#000000" {
                overrideUserInterfaceStyle = .light
            } else if hexColor?.lowercased() == "#ffffff" {
                overrideUserInterfaceStyle = .dark
            }
        }
    }

    public func setTimeZoneOffsetInMinutes(_ timeZoneOffsetInMinutes: String) {
        if timeZoneOffsetInMinutes.isEmpty {
            calendar.timeZone = TimeZone.current
        } else if let minutes = Int(timeZoneOffsetInMinutes) {
            calendar.timeZone = TimeZone(secondsFromGMT: minutes * 60) ?? TimeZone.current
        }
    }

    public func setDate(_ date: Date) {
        let components = calendar.dateComponents([.day, .month, .year], from: date)
        guard let day = components.day, let month = components.month, let year = components.year else { return }

        let newDayRow = pickerViewMiddleDays + (day - 1) % days.count
        let newMonthRow = pickerViewMiddleMonth + (month - 1) % months.count
        let yearIndex = years.firstIndex(of: "\(year)") ?? 0

        selectRow(newMonthRow, inComponent: 0, animated: false)
        selectRow(newDayRow, inComponent: 1, animated: false)
        selectRow(yearIndex, inComponent: 2, animated: false)

        lastSelectedRows = (newMonthRow, newDayRow, yearIndex)
        selectedDate = date
    }

    private func initializeDateArrays() {
        days = Array(1 ... 31).map { "\($0)" }
        years = Array(1900 ... 2100).map { "\($0)" }
        updateMonthNames()
        pickerViewMiddleDays = ((DatePicker.numberOfInfiniteRows / days.count) / 2) * days.count
        pickerViewMiddleMonth = ((DatePicker.numberOfInfiniteRows / months.count) / 2) * months.count
    }

    private func updateMonthNames() {
        let dateFormatter = DateFormatter()
        dateFormatter.locale = locale
        months = dateFormatter.monthSymbols
    }
}
