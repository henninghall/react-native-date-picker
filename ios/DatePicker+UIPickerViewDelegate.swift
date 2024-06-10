//
//  DatePicker+UIPickerViewDelegate.swift
//  react-native-date-picker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

extension DatePicker: UIPickerViewDelegate {
    public func pickerView(_ pickerView: UIPickerView, widthForComponent component: Int) -> CGFloat {
        switch component {
        case 0:
            pickerView.bounds.size.width * 0.35
        case 1:
            pickerView.bounds.size.width * 0.1
        case 2:
            pickerView.bounds.size.width * 0.25
        default:
            0
        }
    }

    public func pickerView(
        _ pickerView: UIPickerView,
        viewForRow row: Int,
        forComponent component: Int,
        reusing view: UIView?
    )
        -> UIView
    {
        let label: UILabel
        if let reusedLabel = view as? UILabel {
            label = reusedLabel
        } else {
            label = UILabel()
            let rowSize = pickerView.rowSize(forComponent: component)
            label.frame = CGRect(x: 0, y: 0, width: rowSize.width, height: rowSize.height)
            label.textAlignment = .center
            label.font = UIFont.systemFont(ofSize: 20)
            label.backgroundColor = .clear
        }

        switch component {
        case 0:
            label.text = value(forRow: row, pickerViewData: months)
            label.textAlignment = .left
        case 1:
            label.text = value(forRow: row, pickerViewData: days)
        case 2:
            label.text = years[row]
        default:
            break
        }

        if !isPickerScrolling, isScrolling() { isPickerScrolling = true }

        return label
    }

    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        isPickerScrolling = false
        let month = (selectedRow(inComponent: 0) % months.count) + 1
        let day = Int(value(forRow: selectedRow(inComponent: 1), pickerViewData: days)) ?? 1
        let year = Int(years[selectedRow(inComponent: 2)]) ?? 1900

        var components = DateComponents()
        components.day = day
        components.month = month
        components.year = year

        guard var date = calendar.date(from: adjustDateComponents(components)) else { return }

        if let minimumDate, date < minimumDate {
            date = minimumDate
        }
        if let maximumDate, date > maximumDate {
            date = maximumDate
        }

        setDate(date)

        onChange?(["timestamp": date.timeIntervalSince1970 * 1000])
    }

    private func value(forRow row: Int, pickerViewData: [String]) -> String {
        pickerViewData[row % pickerViewData.count]
    }

    private func adjustDateComponents(_ components: DateComponents) -> DateComponents {
        var newComponents = components
        newComponents.day = 1

        guard let date = calendar.date(from: newComponents),
              let range = calendar.range(of: .day, in: .month, for: date)
        else { return components }
        newComponents.day = components.day

        if let day = components.day {
            if day > range.count {
                newComponents.day = range.count
            } else if day < 1 {
                newComponents.day = 1
            }
        }
        return newComponents
    }
}
