#import <UIKit/UIKit.h>
#import <React/RCTConvert.h>

@interface DatePicker : UIDatePicker

- (void)setup;
- (void)setTextColorProp:(NSString *)hexColor;
- (void)setTimeZoneOffsetInMinutes:(NSString *)offset;

@end

