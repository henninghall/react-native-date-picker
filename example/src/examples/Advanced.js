import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import DatePicker from 'react-native-date-picker-x';
import DeviceInfo from 'react-native-device-info';
import DateChange from '../propPickers/DateChange';
import FadeToColor from '../propPickers/FadeToColor';
import LocalePicker from '../propPickers/LocalePicker';
import ModePicker from '../propPickers/ModePicker';
import TextColor from '../propPickers/TextColor';
import PropSlider from '../PropSlider';

Date.prototype.addHours = function (h) {
  this.setTime(this.getTime() + (h * 60 * 60 * 1000));
  return this;
}


export default class Advanced extends Component {

  state = {
    chosenDate: new Date(),
    searchTerm: '',
    textColor: '#000000',
    selectedProp: 'mode',
    locale: DeviceInfo.getDeviceLocale(),
    mode: 'datetime',
  }

  render() {
    return (
      <View style={styles.container}>
        <DatePicker
          date={this.state.chosenDate}
          onDateChange={this.setDate}
          locale={this.state.locale}
          minuteInterval={1}
          minimumDate={new Date()}
          maximumDate={(new Date()).addHours(24 * 5 * 1000)}
          fadeToColor={this.props.backgroundColor}
          textColor={this.state.textColor}
          mode={this.state.mode}
        />
        <Text>Picker date: {this.state.chosenDate.toISOString()}</Text>
        <Text />
        <Text>Change prop: </Text>
        <Text />
        <PropSlider
          selectedProp={this.state.selectedProp}
          onSelect={this.onSelect}
          data={this.propertyList()}
        />
        <Text>Prop value:</Text>
        {this.selectedPropData().component}
      </View>
    )
  }

  propertyList = () => [
    {
      name: 'mode', component:
        <ModePicker selected={this.state.mode} onChange={mode => this.setState({ mode })} />
    },
    {
      name: 'locale', component:
        <LocalePicker locale={this.state.locale} onChange={locale => this.setState({ locale })} />
    },
    {
      name: 'date', component:
        <DateChange value={this.state.chosenDate} onChange={chosenDate => this.setState({ chosenDate })} />
    },
    { name: 'minuteInterval' },
    { name: 'minDate' },
    { name: 'maxDate' },
    {
      name: 'fadeToColor', component:
        <FadeToColor onChange={() => this.props.setBackground(randomColor())} />
    },
    {
      name: 'textColor', component:
        <TextColor onChange={() => this.setState({ textColor: randomColor() })} />
    },
  ]

  selectedPropData = () => this.propertyList().find(p => p.name === this.state.selectedProp)
  onSelect = selectedProp => this.setState({ selectedProp })
  setDate = newDate => this.setState({ chosenDate: newDate })

}

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 15,
    backgroundColor: 'transparent',
    flex: 1,
  },
  item: {
    borderWidth: 1,
    marginTop: -1,
    borderColor: 'rgba(0,0,0,1)',
    padding: 3,
    width: 100,
    alignItems: 'center',
  },
})

const randomColor = () => '#' + pad(Math.floor(Math.random() * 16777215).toString(16), 6);

function pad(n, width) {
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}