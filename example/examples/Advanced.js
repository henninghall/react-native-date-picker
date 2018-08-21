import React, { Component } from 'react';
import { Button, View, StyleSheet, ScrollView, Text, TouchableOpacity } from 'react-native';
import SearchInput, { createFilter } from 'react-native-search-filter';
import DeviceInfo from 'react-native-device-info';
import DatePicker from 'react-native-date-picker-x';
import locales from '../locales';

Date.prototype.addHours = function (h) {
  this.setTime(this.getTime() + (h * 60 * 60 * 1000));
  return this;
}

export default class App extends Component {

  state = {
    chosenDate: new Date(),
    searchTerm: '',
    locale: DeviceInfo.getDeviceLocale(),
  }

  render() {
    const result = locales.filter(createFilter(this.state.searchTerm)).slice(0, 5);
    const { backgroundColor } = this.props;
    return (
      <View style={styles.container}>
        <DatePicker
          date={this.state.chosenDate}
          onDateChange={this.setDate}
          locale={this.state.locale}
          minuteInterval={1}
          minimumDate={new Date()}
          maximumDate={(new Date()).addHours(24 * 5)}
          style={this.style()}
        />

        <Text>Current locale: {this.state.locale}</Text>
        <Text>Current date: {this.state.chosenDate.toISOString()}</Text>
        <Text />

        <Button title='Change date'
          onPress={() => this.setState({
            chosenDate: new Date(this.state.chosenDate.getTime() + 86400000)
          })} />

        <SearchInput
          onChangeText={this.searchUpdated}
          style={styles.searchInput}
          placeholder="Change locale"
        />
        <ScrollView>
          {result.map(locale => (
            <TouchableOpacity onPress={() => this.setState({ locale })} key={locale} style={styles.item}>
              <Text>{locale}</Text>
            </TouchableOpacity>
          ))
          }
        </ScrollView>
        <Text />

        <Button title={'Change background color'} onPress={() => this.props.setBackground(randomColor())} />
      </View>
    );
  }
  setDate = (newDate) => this.setState({ chosenDate: newDate })
  searchUpdated = (term) => this.setState({ searchTerm: term })
  style = () => this.props.backgroundColor && ({ backgroundColor: this.props.backgroundColor })

}

const randomColor = () => '#' + Math.floor(Math.random() * 16777215).toString(16);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 15,
  },
  item: {
    borderWidth: 1,
    marginTop: -1,
    borderColor: 'rgba(0,0,0,1)',
    padding: 3,
    width: 100,
    alignItems: 'center',
  },
  emailSubject: {
    color: 'rgba(0,0,0,0.5)'
  },
  searchInput: {
    padding: 5,
    borderColor: '#CCC',
    borderWidth: 1,
    width: 100,
  }
})
