import React from 'react'
import { Platform } from 'react-native'
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

export default props => {
  const { textColor, fadeToColor, ...rest } = props
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      textColor={colorToHex(textColor)}
      fadeToColor={colorToHex(fadeToColor)}
      {...rest}
    />
  )
}
