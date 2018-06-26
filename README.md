# React Native Date Picker 

📅 React Native Date Picker is a cross platform component working on both iOS and Android. It uses the default DatePickerIOS on iOS and a custom picker on Android which has similar look and feel. The datetime mode might be particulary interesting if you looking for a way to avoid two different popup pickers on android. 


## Installation

`yarn add react-native-date-picker-x`

`react-native link `


## Minimal Example

```js
import React, { Component } from 'react';
import DatePicker from 'react-native-date-picker-x';

export default class App extends Component {

  state = { date: new Date() }

  render = () =>
    <DatePicker
      date={this.state.date}
      onDateChange={date => this.setState({ date })}
    />

}

```

## Extended Example

```js
import React, { Component } from 'react';
import DatePicker from 'react-native-date-picker-x';

const today = new Date();
const tomorrow = new Date(new Date().getTime() + 24 * 60 * 60 * 1000); 

export default class App extends Component {

  state = { date: new Date() }

  render = () =>
     <DatePicker
        date={this.state.date}
        onDateChange={date => this.setState({ date })}
        locale={'en-US'}
        minuteInterval={5}
        minimumDate={today}
        maximumDate={tomorrow}
      />
}

```

## Goal
The goal with React Native Date Picker is to make a cross platform variant of [DatePickerIOS](https://facebook.github.io/react-native/docs/datepickerios.html) by implementing a Android variant with similar look and feel.

![react native date picker](https://facebook.github.io/react-native/docs/assets/DatePickerIOS/maximumDate.gif)



## TODO
- [x] Mode: datetime
- [x] Locale support. (AM/PM, 12h/24h toggled and strings translated) 
- [x] Replace todays date with the string "Today" (considering locale)
- [x] Animate to date when state change occur. 
- [x] Switch between AM/PM when scrolling between forenoon and afternoon.
- [x] Support maximumDate/minimumDate.
- [x] Minute interval prop.
- [x] Mode: time
- [ ] Mode: date
- [ ] Colored background support.
- [ ] Align text to right.

<!--
## TODO EXTRA
- [ ] Transparent background support. (Probably need to include transparent gradient).
- [ ] Screen recordings
- [ ] Gray out max/min values. 
-->
