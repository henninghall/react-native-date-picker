
# React Native Date Picker [![npm version](https://badge.fury.io/js/react-native-date-picker.svg)](https://npmjs.com/package/react-native-date-picker)<img align="right" width="40" height="40" src="https://camo.githubusercontent.com/9dfa7191b95dd9f03a9b2171fe2d8ae2f9c42fdc/68747470733a2f2f7777772e69636f6e7364622e636f6d2f69636f6e732f707265766965772f736f796c656e742d7265642f6e65772d62616467652d78786c2e706e67">
This is a React Native Date Picker with following main features: 

📱 Supporting iOS and Android <br>
🕑 3 different modes: Time, Date, DateTime <br>
🌍 Multiple languages<br>
🎨 Customizable<br>
<!-- 
[![Monthly download](https://img.shields.io/npm/dm/react-native-date-picker.svg)](https://img.shields.io/npm/dm/react-native-date-picker.svg)
[![Total downloads](https://img.shields.io/npm/dt/react-native-date-picker.svg)](https://img.shields.io/npm/dt/react-native-date- picker.svg) -->




<table>
  <tr>
    <td align="center"><b>iOS</b></td>
    <td align="center"><b>Android</b></td>  
  </tr>
   <tr>
    <td><img src="https://facebook.github.io/react-native/docs/assets/DatePickerIOS/maximumDate.gif" alt="React Native Date Picker IOS" height="150px" />
    </td>
    <td><img src="docs/react-native-date-picker-android.gif" alt="React Native Date Picker Android" height="150px" style="margin-left:10px" />
    </td>  
  </tr>
      <tr>
    <td align="center">A slightly improved DatePickerIOS.</td>
    <td align="center">A custom made native component.</td>  
  </tr>
  
  </table>
  
## Installation

1. `npm install react-native-date-picker --save` or  `yarn add react-native-date-picker`
2. `react-native link `


## Minimal Example

```js
import React, { Component } from 'react';
import DatePicker from 'react-native-date-picker';

export default class App extends Component {

  state = { date: new Date() }

  render = () =>
    <DatePicker
      date={this.state.date}
      onDateChange={date => this.setState({ date })}
    />

}
```

## Properties
Prop | Description | Screenshots iOS | Screenshot Android
------------- | ------------- | ------------- | -------------
date | The currently selected date. |
onDateChange | Date change handler |
fadeToColor | Android picker is fading towords this background color. {color, 'none',} |
maximumDate |  Maximum selectable date. |
minimumDate |  Minimum selectable date |
minuteInterval | The interval at which minutes can be selected. | <img src="docs/minute-interval-ios.png" alt="Date picker minute interval IOS" height="120px" />|<img src="docs/minute-interval-android.png" alt="Date picker minute interval Android" height="120px" />
mode | The date picker mode. {'datetime', 'date', 'time'} | <img src="docs/datetime-mode-ios.png" alt="Datetime mode ios" height="120px" /><img src="docs/date-mode-ios.png" alt="date mode ios" height="120px" /><img src="docs/time-mode-ios.png" alt="time mode ios" height="120px" />|<img src="docs/date-mode-android.png" alt="date mode android" height="120px" /><img src="docs/datetime-mode-android.png" alt="datetime mode android" height="120px" /><img src="docs/time-mode-android.png" alt="time mode android" height="120px" /> |
locale | The locale for the date picker. Changes language, date order and am/pm preferences. Value needs to be a <a title="react native datepicker locale id" href="https://developer.apple.com/library/content/documentation/MacOSX/Conceptual/BPInternational/LanguageandLocaleIDs/LanguageandLocaleIDs.html">Locale ID.</a>| <img src="docs/locale-ios.png" alt="React Native Date picker locale language ios" height="120px" />|<img src="docs/locale-android.png" alt="React Native Date picker locale language android" height="120px" />
textColor | Changes the text color. | <img src="docs/colors-ios.png" alt="text color background color ios" height="120px" />|<img src="docs/colors-android.png" alt="Text color background color android" height="120px" />
timeZoneOffsetInMinutes | Timezone offset in minutes (default: device's timezone)
 | 

## About
📅 &nbsp; React Native Date Picker is a cross platform component working on both iOS and Android. It uses the slightly improved DatePickerIOS on iOS and a custom picker on Android which has similar look and feel. The datetime mode might be particulary interesting if you looking for a way to avoid two different popup pickers on android. 

## Roadmap
- [x] Mode: datetime
- [x] Mode: date
- [x] Mode: time
- [x] Locale support. (AM/PM, 12h/24h toggled and strings translated) 
- [x] Replace todays date with the string "Today" (considering locale)
- [x] Animate to date when state change occur. 
- [x] Switch between AM/PM when scrolling between forenoon and afternoon.
- [x] Support maximumDate/minimumDate.
- [x] Minute interval prop.
- [x] Colored background support.
- [x] Colored text support.

## Why another React Native date picker?
One of the strongest reason to use react native is its cross platform compatibility. Most of the official components are working seemlessly on both platforms but there are some with single platform support only. The react native date picker is one example where both <a href="https://facebook.github.io/react-native/docs/datepickerios">DatePickerIOS</a> and <a href="https://facebook.github.io/react-native/docs/datepickerandroid">DatePickerAndroid</a> are present. The reason for this is that the default date picker is implemented in seperate ways, iOS normally have an integrated view picker wheel where android has different pickers in a dialog format.

If you want to use these pickers you can combile the official ones or a third party module that already done that for you. If you on the other hand want have a more unified design between your android and ios app, this module is for you. The datetime mode can be particular helpful to avoid 2 separate picker dialogs on android. 



<!--
## TODO EXTRA
- [ ] Transparent background support. (Probably need to include transparent gradient).
- [ ] Screen recordings
- [ ] Gray out max/min values. 
- [ ] Align text to right.
-->
