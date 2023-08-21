import type { HostComponent, ViewProps } from 'react-native'
import {
  BubblingEventHandler,
  Double,
  Int32,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes'
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent'

type DateEvent = {
  timestamp: Double
}

export interface NativeProps extends ViewProps {
  locale?: string
  date: Double
  maximumDate?: Double
  minimumDate?: Double
  minuteInterval?: Int32
  androidVariant?: WithDefault<'iosClone' | 'nativeAndroid', 'nativeAndroid'>
  mode?: WithDefault<'date' | 'time' | 'datetime', 'datetime'>
  onChange: BubblingEventHandler<DateEvent>

  // Type has to be string to allow null/undefined as value.
  // For timezoneOffset, undefined and 0 means different things. 0 means GMT and undefined means device timezone.
  timeZoneOffsetInMinutes?: string | null
  fadeToColor?: string
  textColor?: string
  dividerHeight?: Int32
  is24hourSource?: WithDefault<'locale' | 'device', 'device'>
  theme?: WithDefault<'light' | 'dark' | 'auto', 'auto'>

  // Modal props
  modal?: boolean
  open?: boolean
  onConfirm?: BubblingEventHandler<DateEvent>
  onCancel?: BubblingEventHandler<undefined>
  confirmText?: string
  cancelText?: string
  title?: string
}

export default codegenNativeComponent<NativeProps>(
  'RNDatePicker'
) as HostComponent<NativeProps>
