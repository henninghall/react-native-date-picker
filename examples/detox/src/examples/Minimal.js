import React, { Component } from 'react'
import { Button, NativeModules, Text } from 'react-native'
import DatePicker from 'react-native-date-picker'

export default class MinimalExample extends Component {
  state = { date: new Date(), open: false }

  render = () => (
    <DatePicker
      date={this.state.date}
      onDateChange={(date) => this.setState({ date })}
    />
  )
}
