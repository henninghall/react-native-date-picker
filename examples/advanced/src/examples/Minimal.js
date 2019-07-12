import React, { Component } from 'react';
import { DatePickerIOS, View } from 'react-native';
import DatePicker from 'react-native-date-picker';

export default class MinimalExample extends Component {

  state = { date: new Date() }

  render = () =>
    <DatePicker
      date={this.state.date}
      locale={"sv-SE"}
      onDateChange={date => this.setState({ date })} 
      style={{width:240, height: 200}}
    />

}
