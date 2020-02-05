import React, { Component } from 'react'
import { Button } from 'react-native'

export const PropButton = ({ title, value, onChange }) => (
  <Button title={title} onPress={() => onChange(value)} />
)
