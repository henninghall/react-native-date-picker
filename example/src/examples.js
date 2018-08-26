import React, { Component } from 'react'
import Minimal from './examples/Minimal'
import Advanced from './examples/Advanced'
import TimeMode from './examples/TimeMode'
import { EXAMPLE_KEYS } from './exampleKeys'

export default {
    [EXAMPLE_KEYS.MINIMAL]: {
        buttonTitle: 'Minimal',
        component: Minimal
    },
    [EXAMPLE_KEYS.ADVANCED]: {
        buttonTitle: 'Advanced',
        component: Advanced
    },
    [EXAMPLE_KEYS.TIME_MODE]: {
        buttonTitle: 'Time mode',
        component: TimeMode
    }
}
