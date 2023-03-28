import React from 'react'
import { Platform, Appearance, Text } from 'react-native'
import DatePickerAndroid from './DatePickerAndroid'
import propTypes from './propTypes'
import { colorToHex } from './colorToHex'
import { throwIfInvalidProps } from './propChecker'
import DatePickerIOS from './DatePickerIOS'

const DatePicker = Platform.select({
  android: DatePickerAndroid,
  ios: DatePickerIOS,
  default: () => <Text>DatePicker is not supported on this platform.</Text>,
})

DatePicker.propTypes = propTypes

const DatePickerWrapper = (props) => {
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      ref={props.innerRef}
      {...props}
      textColor={colorToHex(getTextColor(props))}
      theme={getTheme(props)}
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

const getTheme = (props) => {
  if (props.theme) return props.theme
  if (!Appearance) return 'auto'
  return Appearance.getColorScheme()
}

const getTextColor = (props) => {
  if (props.textColor) return props.textColor
  const darkTheme = getTheme(props) === 'dark'
  if (darkTheme) return 'white'
  return undefined
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
