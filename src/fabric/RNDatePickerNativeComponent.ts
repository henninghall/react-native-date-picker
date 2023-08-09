import type { HostComponent, ViewProps } from 'react-native'
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent'

export interface NativeProps extends ViewProps {
  text2?: string
  mode?: string
  // add other props here
}

export default codegenNativeComponent<NativeProps>(
  'RNDatePicker2'
) as HostComponent<NativeProps>
