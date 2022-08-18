import React, { Component } from 'react'
import { PropButton } from '../PropButton'

export default class extends Component {
  render() {
    const { onChange } = this.props
    return (
      <React.Fragment>
        <PropButton
          title="Set undefined"
          value={undefined}
          onChange={onChange}
        />
        <PropButton title="Set 0" value={0} onChange={onChange} />
        <PropButton title="Set 180" value={180} onChange={onChange} />
        <PropButton title="Set -180" value={-180} onChange={onChange} />
      </React.Fragment>
    )
  }
}
