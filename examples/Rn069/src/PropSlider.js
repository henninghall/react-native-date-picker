import React, { Component } from 'react'
import {
  Dimensions,
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
} from 'react-native'

const { width, height } = Dimensions.get('screen')

export default class PropSlider extends Component {
  render() {
    return (
      <FlatList
        horizontal
        style={styles.list}
        data={this.data()}
        renderItem={this.renderItem}
        contentContainerStyle={styles.content}
        keyExtractor={item => item.name}
      />
    )
  }

  data = () =>
    this.props.data.map(
      prop => ((prop.selected = this.props.selectedProp === prop.name), prop)
    )

  onPress = index => () => this.props.onSelect(this.props.data[index].name)

  renderItem = ({ item, index }) => (
    <TouchableOpacity
      testID={`${this.props.testID}/${item.name}`}
      key={item.name}
      style={[styles.item, this.getStyle(item)]}
      onPress={this.onPress(index)}
    >
      <Text key={item.name}>{item.name}</Text>
    </TouchableOpacity>
  )

  getStyle = item => ({
    backgroundColor: item.selected ? '#82e584' : '#abcdef',
    width: this.props.width ? this.props.width : 100,
    height: 30,
  })
}

const styles = StyleSheet.create({
  list: {
    width,
  },
  content: {
    padding: 20,
  },
  item: {
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 5,
    borderRadius: 10,
  },
})
