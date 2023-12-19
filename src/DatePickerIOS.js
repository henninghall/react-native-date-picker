import React from 'react'
import { StyleSheet } from 'react-native'
import { shouldCloseModal, shouldOpenModal } from './modal'
import { getNativeComponent, getNativeModule } from './modules'

const NativeComponent = getNativeComponent()
const NativeModule = getNativeModule()

const DatePickerIOS = (props) => {
  const {
    style,
    date,
    maximumDate,
    minimumDate,
    theme = 'auto',
    onDateChange,
    onCancel,
    onConfirm,
  } = props
  const isClosing = useRef(false)
  const previousProps = useRef()

  const onNativeConfirm = ({ timestamp }) => {
    isClosing.current = true
    onConfirm(new Date(timestamp))
  }

  const onNativeCancel = () => {
    isClosing.current = true
    onCancel()
  }

  if (shouldOpenModal(props, previousProps.current)) {
    isClosing.current = false
    NativeModule.openPicker(props, onNativeConfirm, onNativeCancel)
  }
  if (shouldCloseModal(props, previousProps.current, isClosing.current)) {
    isClosing.current = true
    NativeModule.closePicker()
  }

  previousProps.current = props

  if (props.modal) return null

  return (
    <NativeComponent
      key={props.textColor} // preventing "Today" string keep old text color when text color changes
      onChange={(event) => {
        const nativeTimeStamp = event.nativeEvent.timestamp
        onDateChange && onDateChange(new Date(nativeTimeStamp))
      }}
      onStartShouldSetResponder={() => true}
      onResponderTerminationRequest={() => false}
      {...props}
      maximumDate={maximumDate ? maximumDate.getTime() : undefined}
      minimumDate={minimumDate ? minimumDate.getTime() : undefined}
      date={date ? date.getTime() : undefined}
      style={[styles.datePickerIOS, style]}
      theme={theme}
    />
  )
}

export default DatePickerIOS

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
