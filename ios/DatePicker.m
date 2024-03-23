/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
#import "RCTComponent.h"
#import "DatePicker.h"

#import "RCTUtils.h"
#import "UIView+React.h"
#import "RCTConvert.h"
#import "RCTView.h"

@interface DatePicker ()

@property (nonatomic, copy) RCTBubblingEventBlock onChange;
@property (nonatomic, assign) NSInteger reactMinuteInterval;

@end

@implementation DatePicker

-(void)setup {
    if(@available(iOS 13, *)) {
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
    }
    if(@available(iOS 14, *)) {
        self.preferredDatePickerStyle = UIDatePickerStyleWheels;
    }
    self.calendar = [NSCalendar calendarWithIdentifier:NSCalendarIdentifierGregorian];
}

#define UIColorFromRGB(rgbHex) [UIColor colorWithRed:((float)((rgbHex & 0xFF0000) >> 16))/255.0 green:((float)((rgbHex & 0xFF00) >> 8))/255.0 blue:((float)(rgbHex & 0xFF))/255.0 alpha:1.0]


- (UIColor *) colorFromHexCode:(NSString *)hexString {
    NSString *cleanString = [hexString stringByReplacingOccurrencesOfString:@"#" withString:@""];
    if([cleanString length] == 3) {
        cleanString = [NSString stringWithFormat:@"%@%@%@%@%@%@",
                       [cleanString substringWithRange:NSMakeRange(0, 1)],[cleanString substringWithRange:NSMakeRange(0, 1)],
                       [cleanString substringWithRange:NSMakeRange(1, 1)],[cleanString substringWithRange:NSMakeRange(1, 1)],
                       [cleanString substringWithRange:NSMakeRange(2, 1)],[cleanString substringWithRange:NSMakeRange(2, 1)]];
    }
    if([cleanString length] == 6) {
        cleanString = [cleanString stringByAppendingString:@"ff"];
    }
    
    unsigned int baseValue;
    [[NSScanner scannerWithString:cleanString] scanHexInt:&baseValue];
    
    float red = ((baseValue >> 24) & 0xFF)/255.0f;
    float green = ((baseValue >> 16) & 0xFF)/255.0f;
    float blue = ((baseValue >> 8) & 0xFF)/255.0f;
    float alpha = ((baseValue >> 0) & 0xFF)/255.0f;
    
    return [UIColor colorWithRed:red green:green blue:blue alpha:alpha];
}

- (void)removeTodayString {
    #pragma clang diagnostic push
    #pragma clang diagnostic ignored "-Wundeclared-selector"
    [self performSelector:@selector(setHighlightsToday:) withObject:[NSNumber numberWithBool:NO]];
    #pragma clang diagnostic pop
}

- (void)setTextColorProp:(NSString *)hexColor
{
    if(@available(iOS 13, *)) {

        // black text -> set light mode
        if([hexColor isEqualToString:@"#000000"]){
            self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
        }

        // white text -> set dark mode
        else if([hexColor isEqualToString:@"#FFFFFF"] || [hexColor isEqualToString:@"#ffffff"]){
            self.overrideUserInterfaceStyle = UIUserInterfaceStyleDark;
        }
    }
}

- (void)setTimeZoneOffsetInMinutes:(NSString *)timeZoneOffsetInMinutes
{
    if([timeZoneOffsetInMinutes length] == 0){
        [self setTimeZone: nil];
    }
    else {
        NSNumber *timezoneMinutesInt = [NSNumber numberWithInt:[timeZoneOffsetInMinutes intValue]];
        [self setTimeZone:[RCTConvert NSTimeZone: timezoneMinutesInt]];
    }
}

@end

