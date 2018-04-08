# react-native-date-picker

A cross platform date picker component for React Native. This component uses the default DatePickerIOS on iOS and a custom picker on Android which has similar look and feel. 

## Installation

`$ yarn add @henninghall/react-native-date-picker`

## Usage

```js
import Picker from '@henninghall/react-native-date-picker';

<Picker
  date={new Date()}
  onDateChange={(date) => console.log(date)}
  style={{ width: 300, height: 210 }}
/>

```
