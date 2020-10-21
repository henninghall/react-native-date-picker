module.exports = {
  dependency: {
    platforms: {
      android: {
        "sourceDir": "./android",
        "packageImportPath": "import com.henninghall.date_picker.DatePickerPackage;",
        "packageInstance": "new DatePickerPackage()"
      }
    }
  }
};
