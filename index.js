import { Platform } from 'react-native';
import DatePickerIOS from './DatePickerIOS';
import DatePickerAndroid from './DatePickerAndroid';


export default Platform.OS === 'ios' ? DatePickerIOS : DatePickerAndroid
