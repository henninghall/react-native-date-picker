//
//  DatePicker+UIPickerViewDataSource.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

import UIKit

extension DatePicker: UIPickerViewDataSource {
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        dataManager.numberOfComponents
    }

    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        dataManager.getNumberOfRowsInComponent(component)
    }
}
