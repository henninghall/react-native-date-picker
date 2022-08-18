import React, { Component } from 'react'
import { Button } from 'react-native'

export default class extends Component {
  render() {
    return (
      <React.Fragment>
        {[1, 5, 10, 15].map(minutes => (
          <Button
            key={minutes}
            title={minutes + ' min'}
            onPress={() => this.props.onChange(minutes)}
          />
        ))}
      </React.Fragment>
    )
  }
}
