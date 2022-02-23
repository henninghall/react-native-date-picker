import React, { useEffect } from 'react'
import {
  StyleSheet,
  View,
  requireNativeComponent,
  NativeModules,
} from 'react-native'

const RCTDatePickerIOS = requireNativeComponent('RNDatePicker')

export default class DatePickerIOS extends React.Component {
  _picker = null

  componentDidUpdate() {
    if (this.props.date) {
      const propsTimeStamp = this.props.date.getTime()
      if (this._picker) {
        this._picker.setNativeProps({
          date: propsTimeStamp,
        })
      }
    }
  }

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
    this.props.onConfirm(new Date(timestamp))
  }

  render() {
    const props = this._toIosProps(this.props)
    const isClosed = this._isCurrentlyClosed();

    this.previousProps = props; 
    if (props.modal) {
      if (props.open && isClosed) {
        NativeModules.RNDatePickerManager.openPicker(
          props,
          this._onConfirm,
          props.onCancel
        )
      }
      return null
    }

    return (
      <RCTDatePickerIOS
        key={props.textColor} // preventing "Today" string keep old text color when text color changes
        ref={(picker) => {
          this._picker = picker
        }}
        onChange={this._onChange}
        onStartShouldSetResponder={() => true}
        onResponderTerminationRequest={() => false}
        {...props}
      />
    )
  }
  
  _isCurrentlyClosed = () => !this.previousProps || !this.previousProps.open
}

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
