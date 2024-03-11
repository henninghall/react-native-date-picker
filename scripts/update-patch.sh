DIR="android/src/main/java/com/henninghall/date_picker/generated"
PATCH="scripts/NumberPicker.patch"

curl "https://android.googlesource.com/platform/frameworks/base/+/main/core/java/android/widget/NumberPicker.java?format=text" | base64 -d > $DIR/NumberPickerOriginal.java

cp $DIR/NumberPicker.java $DIR/NumberPickerChanged.java
git diff --no-index $DIR/NumberPickerOriginal.java $DIR/NumberPickerChanged.java  > $PATCH
rm $DIR/NumberPickerOriginal.java
rm $DIR/NumberPickerChanged.java
sed -i.bak "s/NumberPickerOriginal/NumberPicker/g" $PATCH
sed -i.bak "s/NumberPickerChanged/NumberPicker/g" $PATCH
rm $PATCH.bak
