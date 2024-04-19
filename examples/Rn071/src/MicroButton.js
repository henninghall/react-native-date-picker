import {Text, TouchableOpacity} from 'react-native';

export const MicroButton = ({onPress, text}) => {
  return (
    <TouchableOpacity title={'MicroButton'} onPress={onPress}>
      <MicroText text={text} style={{color: '#008'}} />
    </TouchableOpacity>
  );
};
export const MicroText = ({text, ...props}) => (
  <Text style={[props.style, {fontSize: 10}]}>{text}</Text>
);
