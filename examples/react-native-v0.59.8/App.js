import React, { Component } from 'react'
import { StyleSheet, View } from 'react-native'
import DatePicker from 'react-native-date-picker'

export default class App extends Component {
  state = { date: new Date() }

  render = () => (
    <View style={styles.container}>
      <DatePicker
        date={this.state.date}
        onDateChange={date => this.setState({ date })}
      />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
})
