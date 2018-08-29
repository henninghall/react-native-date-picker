import React, { Component } from 'react';
import DatePicker from 'react-native-date-picker-x';

export default class DateMode extends Component {

  state = { date: new Date() }

  render = () =>
    <DatePicker
      date={this.state.date}
      onDateChange={date => this.setState({ date })}
      mode={'date'}
      style={{borderWidth: 1}}
      locale={'sv-SE'}
    />

}
