import React, { Component } from 'react'
import locales from '../locales'
import PropSlider from '../PropSlider'

const data = locales.map(key => ({ name: key }))

const split1 = Math.floor(data.length / 3)
const split2 = Math.floor((2 * data.length) / 3)

const data1 = data.slice(0, split1)
const data2 = data.slice(split1, split2)
const data3 = data.slice(split2)

export default class LocalPicker extends Component {
  render() {
    return (
      <React.Fragment>
        <PropSlider
          selectedProp={this.props.locale}
          onSelect={this.props.onChange}
          data={data1}
          width={50}
        />
        <PropSlider
          selectedProp={this.props.locale}
          onSelect={this.props.onChange}
          data={data2}
          width={50}
        />
        <PropSlider
          selectedProp={this.props.locale}
          onSelect={this.props.onChange}
          data={data3}
          width={50}
        />
      </React.Fragment>
    )
  }
}
