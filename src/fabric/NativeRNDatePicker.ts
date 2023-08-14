import type { TurboModule } from 'react-native'
import { TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
  readonly getConstants: () => {}
  openPicker(): void
}

export default TurboModuleRegistry.get<Spec>('RNDatePicker')
