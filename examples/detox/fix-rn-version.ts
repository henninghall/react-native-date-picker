import fs from 'fs'

const file = '../../android/build.gradle'
const replace = /implementation 'com.facebook.react:react-native:\+'/g
const replacement = "implementation 'com.facebook.react:react-native:0.59.9'"
const encoding = 'utf8'

const data = fs.readFileSync(file, encoding)
const result = data.replace(replace, replacement)
fs.writeFileSync(file, result, encoding)
