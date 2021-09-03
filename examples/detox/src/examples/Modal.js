import React from 'react'
import { Button, View, Text } from 'react-native'
import DatePicker from 'react-native-date-picker'

export default class ModalExample extends React.Component {
  state = { date: new Date(), open: false }

  render = () => (
    <View style={{ alignItems: 'center' }}>
      <Button
        testID="openModal"
        title="Select date"
        onPress={() => this.setState({ open: true })}
      />
      <DatePicker
        modal
        open={this.state.open}
        date={this.state.date}
        onConfirm={(date) => this.setState({ date, open: false })}
        onCancel={() => this.setState({ open: false })}
        androidVariant="nativeAndroid"
      />
      <Text style={{ marginTop: 20, fontSize: 26 }}>
        {this.state.date.toISOString().substr(0, 10)}
      </Text>
      <Text style={{ marginTop: 20, fontSize: 26 }}>
        {this.state.date.toLocaleTimeString()}
      </Text>
    </View>
  )
}
