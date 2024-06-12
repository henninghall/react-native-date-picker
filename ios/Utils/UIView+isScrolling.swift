//
//  UIView+isScrolling.swift
//  FlipDatePicker
//
//  Created by Halina Smolskaya on 07/06/2024.
//

import UIKit

public extension UIView {
    func isScrolling() -> Bool {
        if let scrollView = self as? UIScrollView {
            if scrollView.isDragging || scrollView.isDecelerating {
                return true
            }
        }

        for subview in subviews {
            if subview.isScrolling() {
                return true
            }
        }
        return false
    }
}
