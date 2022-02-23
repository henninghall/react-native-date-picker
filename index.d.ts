import { Component } from 'react'
import { ViewProps } from 'react-native'

export interface DatePickerProps extends ViewProps {
  /**
   * The currently selected date.
   */
  date: Date

  /**
   * The date picker locale.
   */
  locale?: string

  /**
   * Maximum date.
   *
   * Restricts the range of possible date/time values.
   */
  maximumDate?: Date

  /**
   * Minimum date.
   *
   * Restricts the range of possible date/time values.
   */
  minimumDate?: Date

  /**
   * The interval at which minutes can be selected.
   */
  minuteInterval?: 1 | 2 | 3 | 4 | 5 | 6 | 10 | 12 | 15 | 20 | 30

  /**
   * The Android style variant.
   */
  androidVariant?: 'iosClone' | 'nativeAndroid'

  /**
   * The date picker mode.
   */
  mode?: 'date' | 'time' | 'datetime'

  /**
   * Date change handler.
   *
   * This is called when the user changes the date or time in the UI.
   * The first and only argument is a Date object representing the new
   * date and time.
   */
  onDateChange?: (date: Date) => void

  /**
   * Timezone offset in minutes.
   *
   * By default, the date picker will use the device's timezone. With this
   * parameter, it is possible to force a certain timezone offset. For
   * instance, to show times in Pacific Standard Time, pass -7 * 60.
   */
  timeZoneOffsetInMinutes?: number

  /**
   * Android picker is fading towards this background color. { color, 'none' }
   */
  fadeToColor?: string

  /**
   * Changes the text color.
   */
  textColor?: string

  /**
   * Changes the divider height of the android variant iosClone
   */
  dividerHeight?: number

  /**
   * Changes if 24/12-hour format should be determined from the locale or device setting.
   * "device" is default on android and "locale" on iOS. On iOS this cannot be changed.
   */
  is24hourSource?: 'locale' | 'device'

  /** Enables the built-in modal */
  modal?: boolean

  /** Modal prop only. Set to true to open the modal */
  open?: boolean

  /** Modal callback invoked when the user presses the confirm button */
  onConfirm?: (date: Date) => void

  /** Modal callback invoked when user presses the cancel button or closes the modal by a press outside  */
  onCancel?: () => void

  /** Modal confirm button text */
  confirmText?: string

  /** Modal cancel button text */
  cancelText?: string

  /** Modal title. Set to null to remove */
  title?: string | null

  /** Modal color theme on iOS. Defaults to 'auto' */
  theme?: 'light' | 'dark' | 'auto'
}

export default class DatePicker extends Component<DatePickerProps> {}
