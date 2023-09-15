import { TurboModuleRegistry } from 'react-native'
import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport'
import { Double, UnsafeObject } from 'react-native/Libraries/Types/CodegenTypes'

export interface Spec extends TurboModule {
  readonly getConstants: () => {}
  openPicker(props: UnsafeObject): void
  removeListeners(type: Double): void
  addListener(eventName: string): void
}

export default TurboModuleRegistry.get<Spec>('RNDatePicker')
