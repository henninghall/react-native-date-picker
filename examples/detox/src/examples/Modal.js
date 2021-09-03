import React from 'react'
import { Button, View, Text } from 'react-native'
import DatePicker from 'react-native-date-picker'

export default class ModalExample extends React.Component {
  state = { date: new Date(), open: false }

  render = () => (
    <View>
      <Button title="Open" onPress={() => this.setState({ open: true })} />
      <DatePicker
        modal
        open={this.state.open}
        date={this.state.date}
        onConfirm={(date) => this.setState({ date, open: false })}
        onCancel={() => this.setState({ open: false })}
        androidVariant="nativeAndroid"
      />
      <Text>{this.state.date.toISOString()}</Text>
    </View>
  )
}
