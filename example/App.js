import React, { Component } from 'react';
import { View, StyleSheet} from 'react-native';

import DatePicker from '@henninghall/react-native-date-picker';

export default class App extends Component {

  state = { chosenDate: new Date() }

  setDate = (newDate) => this.setState({ chosenDate: newDate })

  render() {
    return (
      <View style={styles.container}>
        <DatePicker
          date={this.state.chosenDate}
          onDateChange={this.setDate}
          style={{ width: 300, height: 170 }}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
})