import React, { Component } from 'react'
import { Button, NativeModules, Text } from 'react-native'
import DatePicker, { openPicker } from 'react-native-date-picker'

export default class MinimalExample extends Component {
  state = { date: new Date(), open: false }

  render() {
    return (
      <>
        <Button
          onPress={() => this.setState({ open: true })}
          title="Open picker"
        />
        <DatePicker
          modal
          open={this.state.open}
          date={this.state.date}
          onConfirm={(date) => {
            console.log({ confirm: date })
            this.setState({ open: false })
          }}
          onCancel={() => this.setState({ open: false })}
          textColor={'white'}
          onDateChange={(date) => {
            console.log('onDateChange', date)
            this.setState({ date })
          }}
          androidVariant="nativeAndroid"
          // fadeToColor={'none'}
        />
        <Text>Open: {this.state.open.toString()}</Text>
      </>
    )
  }
}
