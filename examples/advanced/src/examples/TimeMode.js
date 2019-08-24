import React, { Component } from 'react'
import DatePicker from 'react-native-date-picker'

export default class TimeMode extends Component {
  state = { date: new Date() }

  render = () => (
    <DatePicker
      date={this.state.date}
      onDateChange={date => this.setState({ date })}
      mode={'time'}
    />
  )
}
