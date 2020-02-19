import React, { Component } from 'react'
import { Text, Button, View, StyleSheet } from 'react-native'
import { PropButton } from '../PropButton'
import { readableDate } from '../utils'

export default class extends Component {
  render() {
    const { onChange, defaultDate } = this.props
    return (
      <React.Fragment>
        <Text>{readableDate(this.props.value)}</Text>
        <View style={styles.row}>
          {this.renderButton('-1 hour', -1)}
          {this.renderButton('+1 hour', 1)}
        </View>
        <View style={styles.row}>
          {this.renderButton('-1 day', -24)}
          {this.renderButton('+1 day', 24)}
        </View>
        <View style={styles.row}>
          <PropButton
            title="Set undefined"
            value={undefined}
            onChange={onChange}
          />
          <PropButton
            title="Set default"
            value={defaultDate}
            onChange={onChange}
          />
        </View>
      </React.Fragment>
    )
  }

  renderButton = (title, hourDiff) => (
    <PropButton
      title={title}
      onChange={this.props.onChange}
      value={
        this.props.value &&
        new Date(this.props.value.getTime()).addHours(hourDiff)
      }
    />
  )
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    width: 300,
    justifyContent: 'space-between',
    margin: 5,
  },
})
