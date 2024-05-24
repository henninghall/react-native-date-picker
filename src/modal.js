/**
 * @typedef {import("../index").DatePickerProps} Props
 * @typedef {Omit<Props, "timeZoneOffsetInMinutes"> & { timeZoneOffsetInMinutes: string, textColor: string | undefined }} PlatformPickerProps
 */

import { useEffect, useRef } from 'react'

/**
 * @param {PlatformPickerProps} props
 * @param {PlatformPickerProps | undefined} prevProps
 */
export const shouldOpenModal = (props, prevProps) => {
  if (!props.modal) return false
  if (!props.open) return false
  const currentlyOpen = prevProps?.open
  if (currentlyOpen) return false
  return true
}

/**
 * @param {PlatformPickerProps} props
 * @param {PlatformPickerProps | undefined} prevProps
 * @param {boolean} isClosing
 */
export const shouldCloseModal = (props, prevProps, isClosing) => {
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
export const usePrevious = (value) => {
  /** @type {React.MutableRefObject<T | undefined>} */
  const ref = useRef()
  useEffect(() => {
    ref.current = value
  })
  return ref.current
}
