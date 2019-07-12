
# How this cocoapods project were created

1. `react native init ExampleCocoaPods`
2. `pod init` (from ./ios)
3. `yarn add react-native-date-picker`
4. `react-native-link`
5.  Edited [Podfile](./ios/Podfile)
    - Removed tvOS lines
    - Added 2 lines in top accordning to [this StackOverflow answer](https://stackoverflow.com/questions/50805753/duplicate-module-name-react-native#51372213)
6. `pod install` (from ./ios)

## Versions

` "react": "16.6.1",` <br>
` "react-native": "0.57.7",`<br>
` "react-native-date-picker": "^2.2.1"`<br>
