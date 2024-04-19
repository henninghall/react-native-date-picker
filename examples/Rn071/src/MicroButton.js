import {Text, TouchableOpacity} from 'react-native';

export const MicroButton = ({onPress, text}) => {
  return (
    <TouchableOpacity title={'MicroButton'} onPress={onPress}>
      <Text style={{fontSize: 10}}>{text}</Text>
    </TouchableOpacity>
  );
};
