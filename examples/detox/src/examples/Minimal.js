import React, { Component } from 'react'
import { Button, NativeModules, Text } from 'react-native'
import DatePicker from 'react-native-date-picker'

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
            this.setState({ open: false, date })
          }}
          onCancel={() => this.setState({ open: false })}
          confirmText="Okej då"
          cancelText="Nej tack"
          title="Välj tid är du gullig!"
          androidVariant="nativeAndroid"
          onDateChange={(date) => {
            this.setState({ date })
          }}
        />
        <Text />
        <Text>Open: {this.state.open.toString()}</Text>
        <Text />
        <Text>Confirmed date: {this.state.date?.toISOString()}</Text>
        <Text />
      </>
    )
  }
}
