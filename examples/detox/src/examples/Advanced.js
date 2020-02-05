import React, { Component } from 'react'
import { StyleSheet, Text, View, UIManager, findNodeHandle } from 'react-native'
import DatePicker from 'react-native-date-picker'
import DateChange from '../propPickers/DateChange'
import FadeToColor from '../propPickers/FadeToColor'
import LocalePicker from '../propPickers/LocalePicker'
import MinMaxDateChange from '../propPickers/MinMaxDateChange'
import ModePicker from '../propPickers/ModePicker'
import TextColor from '../propPickers/TextColor'
import TimeZoneOffsetInMinutes from '../propPickers/TimeZoneOffsetInMinutes'
import PropSlider from '../PropSlider'
import MinuteInterval from '../propPickers/MinuteInterval'
import Scroll from '../propPickers/Scroll'
import CustomPropValue from '../CustomPropValue'

Date.prototype.addHours = function (h) {
  this.setTime(this.getTime() + h * 60 * 60 * 1000)
  return this
}

const getInitialDate = () => new Date(2000, 0, 1, 0, 0);

export const defaultMinDate = getInitialDate().addHours(-24 * 5)
export const defaultMaxDate = getInitialDate().addHours(24 * 5)

export const readableUtcDate = date =>
  date
    ? date
      .toISOString()
      .substr(0, 19)
      .replace('T', ' ')
    : 'undefined'

export const readableDate = (d) => {
  if (!d) return undefined
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())} ${pad2(d.getHours())}:${pad2(d.getMinutes())}:${pad2(d.getSeconds())}`
}


function pad2(string) {
  return (`0${string}`).slice(-2)
}

export default class Advanced extends Component {
  state = {
    date: getInitialDate(),
    searchTerm: '',
    textColor: '#000000',
    selectedProp: 'mode',
    locale: 'en-US',
    mode: 'datetime',
    minimumDate: defaultMinDate,
    maximumDate: defaultMaxDate,
    timeZoneOffsetInMinutes: undefined,
    minuteInterval: 1,
  }

  render() {
    return (
      <View style={styles.container}>
        <CustomPropValue
          changeProp={({ propName, propValue }) => this.setState({ [propName]: propValue })}
        />
        <DatePicker
          ref={ref => this.ref = ref}
          date={this.state.date}
          onDateChange={this.setDate}
          locale={this.state.locale}
          minuteInterval={this.state.minuteInterval}
          minimumDate={this.state.minimumDate}
          maximumDate={this.state.maximumDate}
          fadeToColor={this.props.backgroundColor}
          textColor={this.state.textColor}
          mode={this.state.mode}
          timeZoneOffsetInMinutes={this.state.timeZoneOffsetInMinutes}
        />
        <Text testID={"dateOutput"}>{readableDate(this.state.date)}</Text>
        <Text />
        <Text>Change prop: </Text>
        <Text />
        <PropSlider
          testID={"props"}
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
      name: 'scroll',
      component: (
        <Scroll scroll={this.scroll} reset={() => this.setState({ date: getInitialDate() })} />
      ),
    },
    {
      name: 'mode',
      component: (
        <ModePicker
          selected={this.state.mode}
          onChange={mode => this.setState({ mode })}
        />
      ),
    },
    {
      name: 'locale',
      component: (
        <LocalePicker
          locale={this.state.locale}
          onChange={locale => this.setState({ locale })}
        />
      ),
    },
    {
      name: 'timeZoneOffset',
      component: (
        <TimeZoneOffsetInMinutes
          onChange={timeZoneOffsetInMinutes =>
            this.setState({ timeZoneOffsetInMinutes })
          }
        />
      ),
    },
    {
      name: 'date',
      component: (
        <DateChange
          value={this.state.date}
          onChange={date => this.setState({ date })}
        />
      ),
    },
    {
      name: 'minuteInterval',
      component: (
        <MinuteInterval
          value={this.state.minuteInterval}
          onChange={minuteInterval => this.setState({ minuteInterval })}
        />
      ),
    },
    {
      name: 'minDate',
      component: (
        <MinMaxDateChange
          value={this.state.minimumDate}
          onChange={minDate => this.setState({ minDate })}
          defaultDate={defaultMinDate}
        />
      ),
    },
    {
      name: 'maxDate',
      component: (
        <MinMaxDateChange
          value={this.state.maximumDate}
          onChange={maxDate => this.setState({ maxDate })}
          defaultDate={defaultMaxDate}
        />
      ),
    },
    {
      name: 'fadeToColor',
      component: (
        <FadeToColor onChange={() => this.props.setBackground(randomColor())} />
      ),
    },
    {
      name: 'textColor',
      component: (
        <TextColor
          onChange={() => this.setState({ textColor: randomColor() })}
        />
      ),
    },
  ]

  selectedPropData = () => {
    return this.propertyList().find(p => p.name === this.state.selectedProp)
  }

  onSelect = selectedProp => this.setState({ selectedProp })

  setDate = date => this.setState({ date })

  scroll = ({ wheelIndex, scrollTimes }) => {
    if (!this.ref) return
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.ref),
      UIManager.getViewManagerConfig("DatePickerManager").Commands.scroll,
      [wheelIndex, scrollTimes],
    );
  }
}



const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
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

const randomColor = () =>
  '#' + pad(Math.floor(Math.random() * 16777215).toString(16), 6)

function pad(n, width) {
  n = n + ''
  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n
}
