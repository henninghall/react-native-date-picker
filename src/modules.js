/**
 * @typedef {Omit<Props, "timeZoneOffsetInMinutes"> & { timeZoneOffsetInMinutes: string, textColor: string | undefined }} PlatformPickerProps
 */
import {
  NativeModules,
  Platform,
  TurboModuleRegistry,
  requireNativeComponent,
} from 'react-native'
import { getInstallationErrorMessage } from './installationError'

/** @returns {import('react-native').HostComponent<PlatformPickerProps>} */
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
    // TODO: uncomment this line
    // if (global.ignoreDatePickerWarning) return null
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
