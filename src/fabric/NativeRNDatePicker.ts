import { TurboModuleRegistry } from 'react-native'
import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport'
import {
  BubblingEventHandler,
  Double,
  Int32,
  UnsafeObject,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes'

type DateEvent = {
  timestamp?: Double // ios
  date?: string // android
  dateString?: string // android
  id?: string // android
}

export interface ModuleProps extends TurboModule {
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

  // turbo module only props
  closePicker(): void
  openPicker(props: UnsafeObject): void
  onConfirm: BubblingEventHandler<DateEvent>
  onCancel: BubblingEventHandler<undefined>
  confirmText?: string
  cancelText?: string
  title?: string
  buttonColor?: string
}

export interface Spec extends ModuleProps {
  readonly getConstants: () => {}
}

export default TurboModuleRegistry.get<Spec>('RNDatePicker')
