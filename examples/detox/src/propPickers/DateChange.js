import React, { Component } from 'react'
import { Button, Text } from 'react-native'

export default class extends Component {
  render() {
    return (
      <React.Fragment>
        <Button
          title="Add 1 min"
          onPress={() =>
            this.props.onChange(
              new Date(this.props.value.getTime() + 60 * 1000)
            )
          }
        />
        <Button
          title="Add 1 hour"
          onPress={() =>
            this.props.onChange(
              new Date(this.props.value.getTime() + 60 * 60 * 1000)
            )
          }
        />
        <Button
          title="Add 24 hours"
          onPress={() =>
            this.props.onChange(
              new Date(this.props.value.getTime() + 60 * 60 * 24 * 1000)
            )
          }
        />
        <Button
          title="Add 1 year"
          onPress={() =>
            this.props.onChange(
              new Date(this.props.value.getTime() + 60 * 60 * 24 * 1000 * 365)
            )
          }
        />
      </React.Fragment>
    )
  }
}
