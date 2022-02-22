import { Platform, ViewPropTypes } from 'react-native'
import PropTypes from 'prop-types'

const androidPropTypes = {
  fadeToColor: PropTypes.string,
  androidVariant: PropTypes.oneOf(['iosClone', 'nativeAndroid']),
  dividerHeight: PropTypes.number,
  is24hourSource: PropTypes.oneOf(['locale', 'device']),
}

const iOSPropTypes = {
  theme: PropTypes.oneOf(['light', 'dark', 'auto']),
}

const modalPropTypes = {
  modal: PropTypes.bool,
  open: PropTypes.bool,
  onConfirm: PropTypes.func,
  onCancel: PropTypes.func,
  confirmText: PropTypes.string,
  cancelText: PropTypes.string,
  title: PropTypes.string,
}

const DateType = PropTypes.instanceOf(Date)

export default {
  ...(Platform === 'android' ? androidPropTypes : iOSPropTypes),
  date: DateType.isRequired,
  onChange: PropTypes.func,
  minimumDate: DateType,
  maximumDate: DateType,
  mode: PropTypes.oneOf(['date', 'time', 'datetime']),
  minuteInterval: PropTypes.oneOf([1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30]),
  locale: PropTypes.string,
  textColor: PropTypes.string,
  timeZoneOffsetInMinutes: PropTypes.number,
  testID: ViewPropTypes.testID,
  style: ViewPropTypes.style,
  ...modalPropTypes,
}
