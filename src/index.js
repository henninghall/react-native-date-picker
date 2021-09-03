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
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      ref={props.innerRef}
      {...props}
      textColor={colorToHex(props.textColor)}
      fadeToColor={colorToHex(props.fadeToColor)}
      title={getTitle(props)}
      confirmText={props.confirmText ? props.confirmText : 'Confirm'}
      cancelText={props.cancelText ? props.cancelText : 'Cancel'}
      androidVariant={getAndroidVariant(props)}
      minuteInterval={props.minuteInterval ? props.minuteInterval : 1}
      mode={props.mode ? props.mode : 'datetime'}
    />
  )
}

const getAndroidVariant = (props) => {
  const { modal, androidVariant } = props
  if (androidVariant) return androidVariant
  return modal ? 'nativeAndroid' : 'iosClone'
}

const getTitle = (props) => {
  const { title, mode } = props
  if (title === null) return ''
  if (title) return title
  if (mode === 'time') return 'Select time'
  return 'Select date'
}

export default React.memo(DatePickerWrapper)
