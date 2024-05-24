import React, { useCallback } from 'react'
import { StyleSheet } from 'react-native'
import { useModal } from './modal'
import { getNativeComponent } from './modules'

const NativeComponent = getNativeComponent()

/** @type {React.FC<PlatformPickerProps>} */
export const DatePickerIOS = (props) => {
  const onChange = useCallback(
    /** @param {{ nativeEvent: { timestamp: string } }} event */
    (event) => {
      const nativeTimeStamp = event.nativeEvent.timestamp
      if (props.onDateChange) props.onDateChange(new Date(nativeTimeStamp))
    },
    [props]
  )

  /** @type {NativeProps}  */
  const modifiedProps = {
    ...props,
    onChange,
    style: [styles.datePickerIOS, props.style],
    date: props.date ? props.date.getTime() : undefined,
    locale: props.locale ? props.locale : undefined,
    maximumDate: props.maximumDate ? props.maximumDate.getTime() : undefined,
    minimumDate: props.minimumDate ? props.minimumDate.getTime() : undefined,
    theme: props.theme ? props.theme : 'auto',
  }

  useModal({ props: modifiedProps, id: undefined })

  if (props.modal) return null

  return (
    <NativeComponent
      {...modifiedProps}
      onStartShouldSetResponder={() => true}
      onResponderTerminationRequest={() => false}
    />
  )
}

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
