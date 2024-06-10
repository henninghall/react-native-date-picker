//
//  DatePicker+UIPickerViewDataSource.swift
//  react-native-date-picker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

extension DatePicker: UIPickerViewDataSource {
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        DatePicker.numberOfComponents
    }

    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        switch component {
        case 0, 1:
            DatePicker.numberOfInfiniteRows
        case 2:
            years.count
        default:
            0
        }
    }
}
