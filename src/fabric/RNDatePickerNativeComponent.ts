import type { HostComponent, ViewProps } from 'react-native'
import { Int32, WithDefault } from 'react-native/Libraries/Types/CodegenTypes'
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent'

export interface NativeProps extends ViewProps {
  mode?: WithDefault<'date' | 'time' | 'datetime', 'datetime'>
  minuteInterval?: Int32
  // add other props here
}

export default codegenNativeComponent<NativeProps>(
  'RNDatePicker2'
) as HostComponent<NativeProps>
