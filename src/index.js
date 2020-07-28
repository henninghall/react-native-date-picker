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

const DatePickerWrapper = props => {
  const { textColor, fadeToColor, innerRef, ...rest } = props
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      ref={innerRef}
      textColor={colorToHex(textColor)}
      fadeToColor={colorToHex(fadeToColor)}
      {...rest}
    />
  )
}

export default React.memo(DatePickerWrapper)
