import React from 'react'
import { Appearance, Platform, Text } from 'react-native'
import { colorToHex } from './colorToHex'
import { throwIfInvalidProps } from './propChecker'

/** @type {React.FC<PlatformPickerProps>} */
const DatePicker = Platform.select({
  android: () => require('./DatePickerAndroid').DatePickerAndroid,
  ios: () => require('./DatePickerIOS').DatePickerIOS,
  default: () => () =>
    <Text>DatePicker is not supported on this platform.</Text>,
})()

/** @type {React.FC<Props>} */
const DatePickerWrapper = (props) => {
  if (__DEV__) throwIfInvalidProps(props)
  return (
    <DatePicker
      {...props}
      textColor={colorToHex(getTextColor(props))}
      dividerColor={colorToHex(getDividerColor(props))}
      theme={getTheme(props)}
      title={getTitle(props)}
      confirmText={props.confirmText ? props.confirmText : 'Confirm'}
      cancelText={props.cancelText ? props.cancelText : 'Cancel'}
      minuteInterval={props.minuteInterval ? props.minuteInterval : 1}
      mode={props.mode ? props.mode : 'datetime'}
      timeZoneOffsetInMinutes={
        props.timeZoneOffsetInMinutes == null
          ? ''
          : props.timeZoneOffsetInMinutes.toString()
      }
    />
  )
}

/** @param {Props} props **/
const getTheme = (props) => {
  if (props.theme) return props.theme
  if (!Appearance) return 'auto'
  const scheme = Appearance.getColorScheme()
  if (scheme === null) return undefined
  return scheme
}

/** @param {Props} props **/
const getDividerColor = (props) => {
  if (props.dividerColor) return props.dividerColor
  const theme = getTheme(props)
  if (theme === 'dark') return 'white'
  if (theme === 'light') return 'black'
  return undefined
}

/** @param {Props} props **/
const getTextColor = (props) => {
  const theme = getTheme(props)
  if (theme === 'dark') return 'white'
  if (theme === 'light') return 'black'
  return undefined
}

/** @param {Props} props **/
const getTitle = (props) => {
  const { title, mode } = props
  if (title === null) return ''
  if (title) return title
  if (mode === 'time') return 'Select time'
  return 'Select date'
}

export default React.memo(DatePickerWrapper)
