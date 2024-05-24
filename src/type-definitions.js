/**
 * @typedef {import("../index").DatePickerProps} Props
 * @typedef {Omit<Props, "timeZoneOffsetInMinutes"> & { timeZoneOffsetInMinutes: string, textColor: string | undefined, onDateStringChange?: (date: string) => void }} PlatformPickerProps
 *
 * Should be using fabric props directly when api is aligned between platforms.
 * {import("./fabric/RNDatePickerNativeComponent").NativeProps} NativeProps
 * @typedef {{
 * timeZoneOffsetInMinutes: string,
 * date: string | number | undefined,
 * id?: string ,
 * minimumDate?: string | number | undefined,
 * maximumDate?: string | number | undefined,
 * timezoneOffsetInMinutes?: string | undefined,
 * style: import('react-native').StyleProp<import('react-native').ViewStyle>,
 * onChange: (e: *) => void,
 * onStateChange?: (e?: *) => void,
 * onStartShouldSetResponder?: (e?: *) => void,
 * onResponderTerminationRequest?: (e?: *) => void,
 * locale?: string | undefined,
 * theme?: string | undefined,
 * onConfirm?:PlatformPickerProps['onConfirm'],
 * onCancel?:PlatformPickerProps['onCancel'],
 * }} NativeProps
 *
 * @typedef {NativeProps & {
 * modal?: boolean,
 * open?: boolean,
 * }} ModalProps
 *
 */
