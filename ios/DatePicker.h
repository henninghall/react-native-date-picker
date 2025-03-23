#import <UIKit/UIKit.h>
#import <React/RCTConvert.h>

@interface DatePicker : UIDatePicker

- (void)setup;
- (void)setTextColorProp:(NSString *)hexColor;
- (void)setTimeZoneOffsetInMinutes:(NSString *)offset;
- (void)setDate:(NSDate *)date;
- (void)setMinimumDate:(NSDate *)minimumDate;
- (void)setMaximumDate:(NSDate *)maximumDate;

@end

