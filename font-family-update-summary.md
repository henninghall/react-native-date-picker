This pull request adds custom font-family prop support for the Android version of the React Native Date Picker library. The implementation improves styling flexibility by allowing developers to specify custom fonts for the picker's UI elements, without introducing any breaking changes.

All changes are isolated to the Android implementation and were made with reliability, backward compatibility, and clarity in mind.


---

✨ Highlights

Adds support for a font-family prop (Android-only)

Implements safe font loading with fallbacks and error logging

Improves reflection handling to reduce crash risk

Updates documentation with clear usage guidance



---

🔧 Technical Breakdown

1. Improved Error Handling

Files: FontFamily.java, DatePickerModuleImpl.java

Added try-catch blocks with fallback to default font when loading fails

Log errors to assist in debugging

Ensures app doesn’t crash due to misconfigured or missing fonts


2. Reduced Reflection Dependency

File: FontFamily.java

Wrapped reflection code with error handling

Skips applying fonts if expected fields or views aren’t found

Prevents runtime exceptions across different Android versions


3. Documentation Enhancements

Files: FontFamily.java, README.md

Added JavaDoc comments explaining the new logic

Updated README.md with usage instructions for the new prop



---

✅ Verification

All changes tested on Android devices/emulators

Validated against edge cases (missing fonts, invalid input, etc.)

Confirmed no breaking behavior on devices without the font-family prop



---

📝 Usage Example

<DatePicker
  mode="date"
  value={new Date()}
  onChange={handleChange}
  fontFamily="CustomFont-Regular" // Font file placed in android/app/src/main/assets/fonts/
 />


---

📂 Assets

Ensure your custom font file is located at:
android/app/src/main/assets/fonts/CustomFont-Regular.ttf


---

🧠 Rationale

This feature was implemented after reviewing internal customization needs and frequent community requests for improved styling support. All logic was built to be safe, fallback-friendly, and easy to extend in the future.


---

🙌 Final Notes

This PR is ready for review. I’d be happy to make adjustments based on your feedback.

Thanks for maintaining this great library!

