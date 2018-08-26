import React, { Component } from 'react';
import { Dimensions, Button, View, StyleSheet, ScrollView, Text, TouchableOpacity } from 'react-native';
import SearchInput, { createFilter } from 'react-native-search-filter';
import locales from '../locales';
import PropSlider from '../PropSlider';

const data = [
    { name: 'datetime' },
    { name: 'date' },
    { name: 'time' },
]

export default class extends Component {

    render() {
        return (
            <PropSlider
                selectedProp={this.props.selected}
                onSelect={this.props.onChange}
                data={data}
                width={100}
            />
        );
    }
}