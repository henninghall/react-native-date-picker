/**
 * @typedef {import("../index").DatePickerProps} Props
 * @typedef {Omit<Props, "timeZoneOffsetInMinutes"> & { timeZoneOffsetInMinutes: string, textColor: string | undefined, onDateStringChange?: (date: string) => void }} PlatformPickerProps
 */
import React, { useCallback, useEffect, useRef } from 'react'
import { NativeEventEmitter } from 'react-native'
import { shouldCloseModal, shouldOpenModal, usePrevious } from './modal'
import { getNativeComponent, getNativeModule } from './modules'

const NativeComponent = getNativeComponent()
const NativeModule = getNativeModule()

const height = 180
const timeModeWidth = 240
const defaultWidth = 310

/**
 * @type {React.FC<PlatformPickerProps>}
 **/
export const DatePickerAndroid = React.memo((props) => {
  const previousProps = usePrevious(props)
  const thisId = useRef(Math.random().toString()).current
  const closing = useRef(false)

  const onChange = useCallback(
    /**
     * @typedef {{date: string, id: string, dateString: string}} Data
     * @param {{ nativeEvent: Data } | Data & { nativeEvent: undefined }} e  */
    (e) => {
      const { date, id, dateString } = e.nativeEvent ?? e
      const newArch = id !== null
      if (newArch && id !== thisId) return
      const jsDate = fromIsoWithTimeZoneOffset(date)
      if (props.onDateChange) props.onDateChange(jsDate)
      if (props.onDateStringChange) props.onDateStringChange(dateString)
    },
    [props, thisId]
  )
  const onSpinnerStateChanged = useCallback(
    /**
     * @typedef {{ spinnerState: Parameters<Props['onStateChange']>[0], id: string }} SpinnerStateData
     * @param {{ nativeEvent: SpinnerStateData } | SpinnerStateData & { nativeEvent: undefined }} e  */
    (e) => {
      const { spinnerState, id } = e.nativeEvent ?? e
      const newArch = id !== null
      if (newArch && id !== thisId) return
      props.onStateChange && props.onStateChange(spinnerState)
    },
    [props, thisId]
  )
  const onConfirm = useCallback(
    /** @param {{date: string,id:string}} e  */
    ({ date, id }) => {
      if (id !== thisId) return
      closing.current = true
      if (props.onConfirm) props.onConfirm(fromIsoWithTimeZoneOffset(date))
    },
    [props, thisId]
  )

  const onCancel = useCallback(
    ({ id }) => {
      /** @param {{date: string,id:string}} e  */
      if (id !== thisId) return
      closing.current = true
      if (props.onCancel) props.onCancel()
    },
    [props, thisId]
  )

  // open picker
  useEffect(() => {
    if (shouldOpenModal(props, previousProps)) {
      closing.current = false
      NativeModule.openPicker(props)
    }
  }, [previousProps, props])

  // close picker
  useEffect(() => {
    if (shouldCloseModal(props, previousProps, closing.current)) {
      closing.current = true
      NativeModule.closePicker()
    }
  }, [previousProps, props])

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModule)
    eventEmitter.addListener('dateChange', onChange)
    eventEmitter.addListener('spinnerStateChange', onSpinnerStateChanged)
    eventEmitter.addListener('onConfirm', onConfirm)
    eventEmitter.addListener('onCancel', onCancel)
    return () => {
      eventEmitter.removeAllListeners('spinnerStateChange')
      eventEmitter.removeAllListeners('dateChange')
      eventEmitter.removeAllListeners('onConfirm')
      eventEmitter.removeAllListeners('onCancel')
    }
  }, [onCancel, onChange, onConfirm, onSpinnerStateChanged])

  if (props.modal) return null

  return (
    <NativeComponent
      {...props}
      date={toIsoWithTimeZoneOffset(props.date)}
      id={thisId}
      minimumDate={toIsoWithTimeZoneOffset(props.minimumDate)}
      maximumDate={toIsoWithTimeZoneOffset(props.maximumDate)}
      timezoneOffsetInMinutes={getTimezoneOffsetInMinutes(props)}
      style={getStyle(props)}
      onChange={onChange}
      onStateChange={onSpinnerStateChanged}
    />
  )
})

/** @param {PlatformPickerProps} props */
const getStyle = (props) => {
  const width = props.mode === 'time' ? timeModeWidth : defaultWidth
  return [{ width, height }, props.style]
}

/** @param {PlatformPickerProps} props */
const getTimezoneOffsetInMinutes = (props) => {
  // eslint-disable-next-line eqeqeq
  if (props.timeZoneOffsetInMinutes == undefined) return undefined
  return props.timeZoneOffsetInMinutes
}

/**
 * @template {Date | undefined} T
 * @param {T} date
 * */
const toIsoWithTimeZoneOffset = (date) => {
  if (!date) return undefined
  return date.toISOString()
}

/** @param {string} timestamp */
const fromIsoWithTimeZoneOffset = (timestamp) => {
  return new Date(timestamp)
}

export default DatePickerAndroid
