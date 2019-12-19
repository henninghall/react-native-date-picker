/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 *
 * This is a controlled component version of RCTDatePickerIOS
 *
 */

'use strict'

import React from 'react'
import { StyleSheet, View, requireNativeComponent } from 'react-native'
import { throwIfInvalidProps } from './propChecker'
const invariant = require('fbjs/lib/invariant')

const RCTDatePickerIOS = requireNativeComponent('RNDatePicker')

/**
 * Use `DatePickerIOS` to render a date/time picker (selector) on iOS.  This is
 * a controlled component, so you must hook in to the `onDateChange` callback
 * and update the `date` prop in order for the component to update, otherwise
 * the user's change will be reverted immediately to reflect `props.date` as the
 * source of truth.
 */
export default class DatePickerIOS extends React.Component {
  static DefaultProps = {
    mode: 'datetime',
  }

  // $FlowFixMe How to type a native component to be able to call setNativeProps
  _picker = null

  componentDidUpdate() {
    if (this.props.date) {
      const propsTimeStamp = this.props.date.getTime()
      if (this._picker) {
        this._picker.setNativeProps({
          date: propsTimeStamp,
        })
      }
    }
  }

  _onChange = event => {
    const nativeTimeStamp = event.nativeEvent.timestamp
    this.props.onDateChange &&
      this.props.onDateChange(new Date(nativeTimeStamp))
    this.props.onChange && this.props.onChange(event)
  }

  render() {
    const props = this.props
    if (__DEV__) throwIfInvalidProps(props)
    return (
      <RCTDatePickerIOS
        testID={this.props.testID}
        key={this.props.textColor} // preventing "Today" string keep old text color when text color changes
        ref={picker => {
          this._picker = picker
        }}
        style={[styles.datePickerIOS, props.style]}
        date={
          props.date
            ? props.date.getTime()
            : props.initialDate
            ? props.initialDate.getTime()
            : undefined
        }
        locale={props.locale ? props.locale : undefined}
        maximumDate={
          props.maximumDate ? props.maximumDate.getTime() : undefined
        }
        minimumDate={
          props.minimumDate ? props.minimumDate.getTime() : undefined
        }
        mode={props.mode}
        minuteInterval={props.minuteInterval}
        timeZoneOffsetInMinutes={props.timeZoneOffsetInMinutes}
        onChange={this._onChange}
        onStartShouldSetResponder={() => true}
        onResponderTerminationRequest={() => false}
        textColor={props.textColor}
      />
    )
  }
}

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
