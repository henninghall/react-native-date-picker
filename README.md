# 📅 React Native Date Picker 📅

A date picker component for React Native working on iOS and Android. It uses the default DatePickerIOS on iOS and a custom picker on Android which has similar look and feel.


## Installation

`yarn add @henninghall/react-native-date-picker`

`react-native link `


## Usage

```js
import React, { Component } from 'react';
import { View, StyleSheet} from 'react-native';

import DatePicker from '@henninghall/react-native-date-picker';

export default class App extends Component {

  state = { chosenDate: new Date()}
  
  setDate = (newDate) => this.setState({ chosenDate: newDate })

  render() {
    return (
      <View style={styles.container}>
        <DatePicker
          date={this.state.chosenDate}
          onDateChange={this.setDate}
          style={styles.picker}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
  picker: { 
    width: 300, 
    height: 170,
  },
})

```

