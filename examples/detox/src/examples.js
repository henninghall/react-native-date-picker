import Minimal from './examples/Minimal'
import Modal from './examples/Modal'
import Advanced from './examples/Advanced'
import TimeMode from './examples/TimeMode'
import { EXAMPLE_KEYS } from './exampleKeys'
import DateMode from './examples/DateMode'

export default {
  [EXAMPLE_KEYS.MINIMAL]: {
    buttonTitle: 'Minimal',
    component: Minimal,
  },
  [EXAMPLE_KEYS.ADVANCED]: {
    buttonTitle: 'Advanced',
    component: Advanced,
  },
  [EXAMPLE_KEYS.TIME_MODE]: {
    buttonTitle: 'Time mode',
    component: TimeMode,
  },
  [EXAMPLE_KEYS.DATE_MODE]: {
    buttonTitle: 'Date mode',
    component: DateMode,
  },
  [EXAMPLE_KEYS.MODAL]: {
    buttonTitle: 'Modal',
    component: Modal,
  },
}
