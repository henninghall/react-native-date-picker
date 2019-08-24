import React, { Component } from 'react'
import PropSlider from '../PropSlider'

const data = [{ name: 'datetime' }, { name: 'date' }, { name: 'time' }]

export default class extends Component {
  render() {
    return (
      <PropSlider
        selectedProp={this.props.selected}
        onSelect={this.props.onChange}
        data={data}
        width={100}
      />
    )
  }
}
