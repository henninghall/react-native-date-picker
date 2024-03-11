curl "https://android.googlesource.com/platform/frameworks/base/+/main/core/java/android/widget/NumberPicker.java?format=text" | base64 -d > android/src/main/java/com/henninghall/date_picker/generated/NumberPicker.java
git apply scripts/NumberPicker.patch
