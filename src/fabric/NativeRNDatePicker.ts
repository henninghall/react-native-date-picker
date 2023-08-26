import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport'
import { TurboModuleRegistry } from 'react-native'
import { UnsafeObject } from 'react-native/Libraries/Types/CodegenTypes'

export interface Spec extends TurboModule {
  readonly getConstants: () => {}
  openPicker(props: UnsafeObject): void
}

export default TurboModuleRegistry.get<Spec>('RNDatePicker')
