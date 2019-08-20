---
name: Bug report
about: Help us improve react-native-date-picker
title: ''
labels: ''
assignees: ''

---

**Describe the bug**
A clear and concise description of what the bug is. 

**Expected behavior**
A clear and concise description of what you expected to happen.

**To Reproduce**
Add example code that reproduces the behavior. 
```javascript
export default class App extends Component {

  state = { date: new Date() }

  render = () =>
    <DatePicker
      date={this.state.date}
      onDateChange={date => this.setState({ date })}
    />

}
```

**Smartphone (please complete the following information):**
 - OS: [Android or iOS]
 - React Native version [e.g. 0.59.4]
 - react-native-date-picker version [e.g. 2.5.1]
