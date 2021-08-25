import React, { useEffect } from 'react'
import { Platform, NativeModules, NativeEventEmitter } from 'react-native'
import DatePickerIOS from './DatePickerIOS'
import DatePickerAndroid from './DatePickerAndroid'
import propTypes from './propTypes'
import defaultProps from './defaultProps'
import { colorToHex } from './colorToHex'
import { throwIfInvalidProps } from './propChecker'

const DatePicker = Platform.select({
  android: DatePickerAndroid,
  ios: DatePickerIOS,
})

DatePicker.defaultProps = defaultProps
DatePicker.propTypes = propTypes

class DatePickerWrapper extends React.PureComponent {
  render() {
    const { textColor, fadeToColor, innerRef, ...rest } = this.props

    if (__DEV__) throwIfInvalidProps(this.props)

    return (
      <DatePicker
        ref={innerRef}
        textColor={colorToHex(textColor)}
        fadeToColor={colorToHex(fadeToColor)}
        {...rest}
      />
    )
  }
}

export default React.memo(DatePickerWrapper)
