#import <UIKit/UIKit.h>

@interface DatePicker : UIDatePicker

- (void)setup;
- (void)setTextColorProp:(NSString *)hexColor;
- (void)setTimeZoneOffsetInMinutes:(NSString *)offset;

@end

