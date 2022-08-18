import React, { useState } from 'react'
import { Button, TextInput, Text, View, TouchableOpacity } from 'react-native'

export default function CustomPropValue(props) {
    const [propName, setPropName] = useState("")
    const [propValue, setPropValue] = useState("")

    const getPropValue = () => {
        if (propValue === "undefined") return undefined
        if (propName === "minuteInterval") return parseInt(propValue)
        if (propName === "timeZoneOffsetInMinutes") return parseInt(propValue)
        if (["date", "maximumDate", "minimumDate"].includes(propName)) return new Date(propValue)
        return propValue
    }

    return (
        <View style={{ marginBottom: 15, alignItems: "center" }}>
            <View style={{ flexDirection: 'row' }}>
                <Text>Prop name</Text>
                <TextInput
                    testID="propName"
                    style={input}
                    onChangeText={setPropName}
                    value={propName}
                />
                <Text>Prop value</Text>
                <TextInput
                    testID="propValue"
                    style={input}
                    onChangeText={setPropValue}
                    value={propValue}
                />
            </View>
            <TouchableOpacity
                testID={"changeProp"}
                onPress={() => props.changeProp({ propName, propValue: getPropValue() })}
            ><Text style={{ color: "blue" }}>Change</Text></TouchableOpacity>
        </View>
    )
}

const input = {
    height: 30,
    borderColor: 'gray',
    borderWidth: 0.5,
    margin: 2,
    padding: 0,
    alignItems: "center",
    textAlign: "center"
}