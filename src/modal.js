/**
 * @typedef {import("../index").DatePickerProps} Props
 * @typedef {Omit<Props, "timeZoneOffsetInMinutes"> & { timeZoneOffsetInMinutes: string, textColor: string | undefined }} PlatformPickerProps
 * @typedef {import('./modules').NativeProps & {
 * onConfirm?:PlatformPickerProps['onConfirm'],
 * onCancel?:PlatformPickerProps['onCancel'],
 * modal?: boolean,
 * open?: boolean,
 * }} ModalProps
 */

import { useCallback, useEffect, useRef } from 'react'
import { NativeEventEmitter, Platform } from 'react-native'
import { getNativeModule } from './modules'

const NativeModule = getNativeModule()

/**
 * @param {ModalProps} props
 * @param {ModalProps | undefined} prevProps
 */
const shouldOpenModal = (props, prevProps) => {
  if (!props.modal) return false
  if (!props.open) return false
  const currentlyOpen = prevProps?.open
  if (currentlyOpen) return false
  return true
}

/**
 * @param {ModalProps} props
 * @param {ModalProps | undefined} prevProps
 * @param {boolean} isClosing
 */
const shouldCloseModal = (props, prevProps, isClosing) => {
  if (!props.modal) return false
  if (props.open) return false
  const currentlyOpen = prevProps?.open
  if (!currentlyOpen) return false
  if (isClosing) return false
  return true
}

/**
 *
 * @template T
 * @param {T} value
 * @returns {T | undefined}
 */
const usePrevious = (value) => {
  /** @type {React.MutableRefObject<T | undefined>} */
  const ref = useRef()
  useEffect(() => {
    ref.current = value
  })
  return ref.current
}

/** @param {{props: ModalProps, id: string | undefined }} props */
export const useModal = ({ props, id }) => {
  const closing = useRef(false)
  const previousProps = usePrevious(props)

  const onConfirm = useCallback(
    (params) => {
      if (params.id !== id) return
      closing.current = true
      Platform.select({
        ios: () => {
          if (props.onConfirm) props.onConfirm(new Date(params.timestamp))
        },
        android: () => {
          if (props.onConfirm) props.onConfirm(new Date(params.date))
        },
      })
    },
    [id, props]
  )

  const onCancel = useCallback(
    (params) => {
      if (params.id !== id) return
      closing.current = true
      if (props.onCancel) props.onCancel()
    },
    [id, props]
  )

  // open
  useEffect(() => {
    console.log('should open', shouldOpenModal(props, previousProps))
    if (shouldOpenModal(props, previousProps)) {
      closing.current = false
      Platform.select({
        ios: () => NativeModule.openPicker(props, onConfirm, onCancel),
        android: NativeModule.openPicker(props),
      })
    }
  }, [onCancel, onConfirm, previousProps, props])

  // close
  useEffect(() => {
    if (shouldCloseModal(props, previousProps, closing.current)) {
      closing.current = true
      NativeModule.closePicker()
    }
  }, [previousProps, props])

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModule)
    eventEmitter.addListener('onConfirm', onConfirm)
    eventEmitter.addListener('onCancel', onCancel)
    return () => {
      eventEmitter.removeAllListeners('onConfirm')
      eventEmitter.removeAllListeners('onCancel')
    }
  }, [onCancel, onConfirm])
}
