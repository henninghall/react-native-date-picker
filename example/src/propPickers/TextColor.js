import React, { Component } from 'react';
import { Dimensions, Button, View, StyleSheet, ScrollView, Text, TouchableOpacity } from 'react-native';
import SearchInput, { createFilter } from 'react-native-search-filter';
import locales from '../locales';
import PropSlider from '../PropSlider';

export default class extends Component {

    render() {
        return (
            <Button title={'Change color'} onPress={this.props.onChange} />
        );
    }

}