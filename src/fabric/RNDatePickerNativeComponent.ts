import type { HostComponent, ViewProps } from 'react-native'
import {
  BubblingEventHandler,
  Double,
  Int32,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes'
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent'

type DateEvent = {
  timestamp?: Double // ios
  date?: string // android
  dateString?: string // android
  id?: string // android
}

type SpinnerStateEvent = {
  spinnerState: 'spinning' | 'idle'
  id: string
}

export interface NativeProps extends ViewProps {
  removeListeners(type: Double): void
  addListener(eventName: string): void
  locale?: string
  date: string
  maximumDate?: string
  minimumDate?: string
  minuteInterval?: Int32
  mode?: WithDefault<'date' | 'time' | 'datetime', 'datetime'>
  // Type has to be string to allow null/undefined as value.
  // For timezoneOffset, undefined and 0 means different things. 0 means GMT and undefined means device timezone.
  timeZoneOffsetInMinutes?: string | null
  textColor?: string
  dividerColor?: string
  is24hourSource?: WithDefault<'locale' | 'device', 'device'>
  theme?: WithDefault<'light' | 'dark' | 'auto', 'auto'>

  // fabric only props
  onChange: BubblingEventHandler<DateEvent>
  onStateChange: BubblingEventHandler<SpinnerStateEvent>
}

export default codegenNativeComponent<NativeProps>(
  'RNDatePicker'
) as HostComponent<NativeProps>
