/**
 * Should be using fabric props directly when api is aligned between platforms.
 * {import("./fabric/RNDatePickerNativeComponent").NativeProps} NativeProps
 * @typedef {{
 * timeZoneOffsetInMinutes: string,
 * date: string | number | undefined,
 * id?: string ,
 * minimumDate?: string | number | undefined,
 * maximumDate?: string | number | undefined,
 * timezoneOffsetInMinutes?: string | undefined,
 * style: import('react-native').StyleProp<import('react-native').ViewStyle>,
 * onChange: (e: *) => void,
 * onStateChange?: (e?: *) => void,
 * onStartShouldSetResponder?: (e?: *) => void,
 * onResponderTerminationRequest?: (e?: *) => void,
 * locale?: string | undefined,
 * theme?: string | undefined,
 * }} NativeProps
 */
import {
  NativeModules,
  Platform,
  TurboModuleRegistry,
  requireNativeComponent,
} from 'react-native'
import { getInstallationErrorMessage } from './installationError'

/** @returns {import('react-native').HostComponent<NativeProps>} */
export const getNativeComponent = () => {
  try {
    switch (Platform.OS) {
      case 'android':
      case 'ios':
        return requireNativeComponent('RNDatePicker')
      default:
        throw Error(
          'react-native-date-picker is not supported on this platform'
        )
    }
  } catch (e) {
    if (global.ignoreDatePickerWarning) return null
    throw Error(getInstallationErrorMessage())
  }
}

export const getNativeModule = () => {
  try {
    switch (Platform.OS) {
      case 'ios':
        return NativeModules.RNDatePicker
      case 'android':
        return TurboModuleRegistry
          ? TurboModuleRegistry.get('RNDatePicker')
          : NativeModules.RNDatePicker
      default:
        throw Error(
          'react-native-date-picker is not supported on this platform'
        )
    }
  } catch (e) {
    if (global.ignoreDatePickerWarning) return null
    throw Error(getInstallationErrorMessage())
  }
}
