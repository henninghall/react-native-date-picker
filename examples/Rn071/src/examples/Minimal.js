import React, {Component} from 'react';
import {Text} from 'react-native';
import DatePicker from 'react-native-date-picker';

export default class MinimalExample extends Component {
  state = {date: new Date()};

  render = () => (
    <>
      <Text>{this.state.date.toISOString()}</Text>
      <DatePicker
        date={this.state.date}
        onDateChange={date => this.setState({date})}
        onStateChange={state => {
          console.log('onStateChange, state:', state);
        }}
        theme="light"
      />
    </>
  );
}
