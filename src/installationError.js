import { Platform } from 'react-native'

const getMessage = (message) => {
  return [
    'react-native-date-picker is not installed correctly. Make sure you: ',
    '',
    ...message,
    '',
    'Please reply in this thread if this solved your issue or not: ',
    'https://github.com/henninghall/react-native-date-picker/issues/404',
    '',
    "To ignore this warning, add 'global.ignoreDatePickerWarning = true' to the top of your index file.",
  ].join('\n')
}

const messages = {
  ios: {
    expo: getMessage([
      "1. Have rebuilt your app (with for instance 'npx expo run:ios')",
      `2. Are not using Expo Go (Expo Go is unsupported). See README for more info: `,
      'https://github.com/henninghall/react-native-date-picker',
    ]),
    nonExpo: getMessage([
      "1. Installed pods (by for instance running 'cd ios && pod install')",
      "2. Rebuilt the app (by for instance 'npx react-native run-ios')",
    ]),
  },
  android: {
    expo: getMessage([
      "1. Have rebuilt your app (with for instance 'npx expo run:android')",
      `2. Are not using Expo Go (Expo Go is unsupported). See README for more info: `,
      'https://github.com/henninghall/react-native-date-picker',
    ]),
    nonExpo: getMessage([
      "1. Rebuilt the app (by for instance 'npx react-native run-ios')",
    ]),
  },
}

export const getInstallationErrorMessage = () => {
  try {
    require('expo-constants').default
    return messages[Platform.OS].expo
  } catch (e) {
    return messages[Platform.OS].nonExpo
  }
}
