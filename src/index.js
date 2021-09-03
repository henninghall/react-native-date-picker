import React from 'react'
import { Platform } from 'react-native'
import DatePickerIOS from './DatePickerIOS'
import DatePickerAndroid from './DatePickerAndroid'
import propTypes from './propTypes'
import { colorToHex } from './colorToHex'
import { throwIfInvalidProps } from './propChecker'

const DatePicker = Platform.select({
  android: DatePickerAndroid,
  ios: DatePickerIOS,
})

DatePicker.propTypes = propTypes

const DatePickerWrapper = (props) => {
  const {
    textColor,
    fadeToColor,
    confirmText = 'Confirm',
    cancelText = 'Cancel',
    is24hourSource = 'device',
    minuteInterval = 1,
    mode = 'datetime',
    androidVariant = props.modal ? 'nativeAndroid' : 'iosClone',
    innerRef,
    ...rest
  } = props
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      ref={innerRef}
      textColor={colorToHex(textColor)}
      fadeToColor={colorToHex(fadeToColor)}
      title={getTitle(props)}
      confirmText={confirmText}
      cancelText={cancelText}
      {...rest}
    />
  )
}

const getTitle = (props) => {
  const { title, mode } = props
  if (title === null) return ''
  if (title) return title
  if (mode === 'time') return 'Select time'
  return 'Select date'
}

export default React.memo(DatePickerWrapper)
