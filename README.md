# React Native Date Picker &nbsp;&nbsp; ![npm package](https://badge.fury.io/js/react-native-date-picker-x.svg)<img src="https://www.iconsdb.com/icons/preview/soylent-red/new-badge-xxl.png" alt="React Native Date Picker" width="40dp" align="right"/>


<!-- 
[![Monthly download](https://img.shields.io/npm/dm/react-native-date-picker-x.svg)](https://img.shields.io/npm/dm/react-native-date-picker-x.svg)
[![Total downloads](https://img.shields.io/npm/dt/react-native-date-picker-x.svg)](https://img.shields.io/npm/dt/react-native-date- picker-x.svg) -->

📱 Supporting iOS and Android <br>
🌍 Multiple languages<br>
🎨 Customizable<br>


<table>
  <tr>
    <td align="center"><b>iOS</b></td>
    <td align="center"><b>Android</b></td>  
  </tr>
   <tr>
    <td><img src="https://facebook.github.io/react-native/docs/assets/DatePickerIOS/maximumDate.gif" alt="React Native Date Picker IOS" width="338px" />
    </td>
    <td><img src="docs/react-native-date-picker-android.gif" alt="React Native Date Picker Android" width="338px" style="margin-left:10px" />
    </td>  
  </tr>
      <tr>
    <td align="center">A slightly improved DatePickerIOS.</td>
    <td align="center">A custom made native component. (Smoother in reality)</td>  
  </tr>
  
  </table>



</div>

## Installation

`npm install react-native-date-picker-x ` 
<br>
or <br>
`yarn add react-native-date-picker-x`
<br>
<br>
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
        textColor={'#ffffff'}
        fadeToColor={'#000000'}
      />
}

```


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


<!--
## TODO EXTRA
- [ ] Transparent background support. (Probably need to include transparent gradient).
- [ ] Screen recordings
- [ ] Gray out max/min values. 
- [ ] Align text to right.
-->
