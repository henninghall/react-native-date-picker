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
      const date = Platform.select({
        ios: params.timestamp,
        android: params.date,
      })
      if (props.onConfirm) props.onConfirm(new Date(date))
    },
    [id, props]
  )

  const onCancel = useCallback(
    (params) => {
      if (params?.id !== id) return
      closing.current = true
      if (props.onCancel) props.onCancel()
    },
    [id, props]
  )

  // open
  useEffect(() => {
    if (shouldOpenModal(props, previousProps)) {
      closing.current = false
      const params = Platform.select({
        android: [props],
        ios: [props, onConfirm, onCancel],
      })
      if (!params) throw Error('Unsupported platform')
      NativeModule.openPicker(...params)
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
