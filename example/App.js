import React, { Component } from 'react';
import { View, StyleSheet, ScrollView, Text, TouchableOpacity } from 'react-native';
import SearchInput, { createFilter } from 'react-native-search-filter';
import DeviceInfo from 'react-native-device-info';
import DatePicker from 'react-native-date-picker-x';
import locales from './locales';

export default class App extends Component {

  searchUpdated(term) {
    this.setState({ searchTerm: term })
  }

  state = {
    chosenDate: new Date(),
    searchTerm: '',
    locale: DeviceInfo.getDeviceLocale(),
  }

  setDate = (newDate) => this.setState({ chosenDate: newDate })

  render() {
    const result = locales.filter(createFilter(this.state.searchTerm)).slice(0, 5);
    console.log({result});
    
    return (
      <View style={styles.container}>
        <DatePicker
          date={this.state.chosenDate}
          onDateChange={this.setDate}
          locale={this.state.locale}
          style={{ width: 300, height: 170 }}
        />
        <Text>Current locale: {this.state.locale}</Text>
        <SearchInput
          onChangeText={(term) => { this.searchUpdated(term) }}
          style={styles.searchInput}
          placeholder="Change locale"
        />
        <ScrollView>
          {result.map(locale => (
            <TouchableOpacity onPress={() => this.setState({locale})} key={locale} style={styles.item}>
              <Text>{locale}</Text>
            </TouchableOpacity>
          ))
          }
        </ScrollView>

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
