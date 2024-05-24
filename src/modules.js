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
    // @ts-ignore
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
    // @ts-ignore
    if (global.ignoreDatePickerWarning) return null
    throw Error(getInstallationErrorMessage())
  }
}
