import { Component } from 'react'
import { ViewProps } from 'react-native'

export interface DatePickerProps extends ViewProps {
  /**
   * The currently selected date.
   */
  date?: Date

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
  onDateChange: (date: Date) => void

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
   * Custom date format for android
   */
  dateFormat?: string

  /**
   * Changes the text color.
   */
  textColor?: string
}

export default class DatePicker extends Component<DatePickerProps> {}
