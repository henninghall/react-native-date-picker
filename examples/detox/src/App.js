import React, { Component } from 'react'
import {
  ScrollView,
  AppRegistry,
  StyleSheet,
  Text,
  TouchableOpacity,
} from 'react-native'
import examples from './examples'

class App extends Component {
  state = {
    picker: undefined,
  }

  render() {
    return (
      <ScrollView
        style={[
          styles.container,
          { backgroundColor: this.state.backgroundColor },
        ]}
        contentContainerStyle={styles.content}
      >
        <Text style={styles.header}>{!this.state.picker && 'Examples'}</Text>
        {!this.state.picker && this.renderButtons()}
        {!!this.state.picker && this.renderBackButton()}
        {!!this.state.picker && this.renderPicker()}
      </ScrollView>
    )
  }

  setBackgroundColor = (backgroundColor) => this.setState({ backgroundColor })

  renderPicker = () => {
    const Picker = examples[this.state.picker].component
    return (
      <Picker
        backgroundColor={this.state.backgroundColor}
        setBackground={this.setBackgroundColor}
      />
    )
  }

  renderButtons = () =>
    Object.keys(examples)
      .filter((key) => key !== this.state.picker)
      .map(this.renderButton)

  renderButton = (key) => (
    <TouchableOpacity
      key={key}
      onPress={() => this.setState({ picker: key })}
      style={{ margin: 10 }}
    >
      <Text style={styles.text}>{examples[key].buttonTitle}</Text>
    </TouchableOpacity>
  )

  renderBackButton = (key) => (
    <TouchableOpacity
      onPress={() => this.setState({ picker: undefined })}
      style={{ margin: 10, position: 'absolute', top: 0, left: 10 }}
    >
      <Text style={styles.text}>Back</Text>
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  container: {
    paddingTop: 15,
    borderWidth: 1,
  },
  content: {
    alignItems: 'center',
  },
  text: {
    color: 'dodgerblue',
    fontSize: 16,
  },
  header: {
    color: 'black',
    fontSize: 22,
    margin: 20,
  },
})

AppRegistry.registerComponent('example', () => App)
