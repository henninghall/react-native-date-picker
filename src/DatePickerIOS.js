import React from 'react'
import { StyleSheet } from 'react-native'
import { shouldCloseModal, shouldOpenModal } from './modal'
import { getNativeComponent, getNativeModule } from './modules'

const NativeComponent = getNativeComponent()
const NativeModule = getNativeModule()

export default class DatePickerIOS extends React.Component {
  _onChange = (event) => {
    const nativeTimeStamp = event.nativeEvent.timestamp
    this.props.onDateChange &&
      this.props.onDateChange(new Date(nativeTimeStamp))
  }

  _toIosProps = (props) => {
    return {
      ...props,
      style: [styles.datePickerIOS, props.style],
      date: props.date ? props.date.getTime() : undefined,
      locale: props.locale ? props.locale : undefined,
      maximumDate: props.maximumDate ? props.maximumDate.getTime() : undefined,
      minimumDate: props.minimumDate ? props.minimumDate.getTime() : undefined,
      theme: props.theme ? props.theme : 'auto',
    }
  }

  _onConfirm = ({ timestamp }) => {
    this.isClosing = true
    this.props.onConfirm(new Date(timestamp))
  }

  _onCancel = () => {
    this.isClosing = true
    this.props.onCancel()
  }

  render() {
    const props = this._toIosProps(this.props)

    if (shouldOpenModal(props, this.previousProps)) {
      this.isClosing = false
      NativeModule.openPicker(props, this._onConfirm, this._onCancel)
    }
    if (shouldCloseModal(props, this.previousProps, this.isClosing)) {
      this.isClosing = true
      NativeModule.closePicker()
    }

    this.previousProps = props

    if (props.modal) return null

    return (
      <NativeComponent
        key={props.textColor} // preventing "Today" string keep old text color when text color changes
        onChange={this._onChange}
        onStartShouldSetResponder={() => true}
        onResponderTerminationRequest={() => false}
        {...props}
      />
    )
  }
}

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
