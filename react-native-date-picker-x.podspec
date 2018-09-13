Pod::Spec.new do |s|
    s.name         = "react-native-date-picker-x"
    s.version      = "1.5.0"
    s.license      = "MIT"
    s.homepage     = "https://github.com/henninghall/react-native-date-picker"
    s.authors      = { 'Henning Hall' => 'henninghall@gmail.com' }
    s.summary      = "A Cross Platform React Native Picker"
    s.source       = { :git => "https://github.com/henninghall/react-native-date-picker.git" }
    s.source_files  = "ios/DatePickerX/*.{h,m}"
    
    s.platform     = :ios, "7.0"
    s.dependency 'React'
  end