import React, { Component } from 'react'
import { Button, NativeModules } from 'react-native'
import DatePicker, { openPicker } from 'react-native-date-picker'

export default class MinimalExample extends Component {
  state = { date: new Date(), open: 1 }

  render() {
    console.log(
      Object.keys(NativeModules).filter((k) =>
        k.toLocaleLowerCase().includes('date')
      )
    )
    return (
      <>
        <Button
          onPress={() => this.setState({ open: this.state.open + 1 })}
          title="Open picker"
        />
        <DatePicker
          open={this.state.open}
          date={this.state.date}
          onDateChange={(date) => this.setState({ date })}
          androidVariant="nativeAndroid"
          fadeToColor={'none'}
        />
      </>
    )
  }
}
