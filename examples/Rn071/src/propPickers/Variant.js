import React, { Component } from 'react'
import PropSlider from '../PropSlider'

const data = [{ name: 'iosClone' }, { name: 'nativeAndroid' }]

export default class extends Component {
  render() {
    return (
      <PropSlider
        testID={'variant'}
        selectedProp={this.props.selected}
        onSelect={this.props.onChange}
        data={data}
        width={100}
      />
    )
  }
}
