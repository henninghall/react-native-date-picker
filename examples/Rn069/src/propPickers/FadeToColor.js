import React, { Component } from 'react'
import { Button } from 'react-native'

export default class extends Component {
  render() {
    return <>
    <Button title={'Change color'} onPress={this.props.onChange} />
    <Button title={'Set "none"'} onPress={this.props.setNone} />
    </>
  }
}
