import React, {Component} from 'react';
import {Text} from 'react-native';
import DatePicker from 'react-native-date-picker';

export default class MinimalExample extends Component {
  state = {date: new Date(), state: ''};

  render = () => (
    <>
      <Text>{this.state.state}</Text>
      <DatePicker
        date={this.state.date}
        onDateChange={date => this.setState({date})}
        onStateChange={state => this.setState({state})}
      />
    </>
  );
}
