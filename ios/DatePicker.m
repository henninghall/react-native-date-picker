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
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleUnspecified;
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

- (void)refreshAfterInterfaceStyleChange
{
    if (self.window == nil) {
        return;
    }
    if (@available(iOS 14.0, *)) {
        UIDatePickerStyle currentStyle = self.preferredDatePickerStyle;
        NSDate *currentDate = [self.date copy];
        UIDatePickerMode currentMode = self.datePickerMode;
        NSInteger currentMinuteInterval = self.minuteInterval;
        NSDate *minimumDate = self.minimumDate;
        NSDate *maximumDate = self.maximumDate;
        NSLocale *locale = self.locale;
        NSTimeZone *timeZone = self.timeZone;

        NSSet *targets = [[self allTargets] copy];
        NSMutableDictionary<NSValue *, NSArray<NSString *> *> *actionsByTarget = [NSMutableDictionary new];
        for (id target in targets) {
            NSArray<NSString *> *actions = [self actionsForTarget:target forControlEvent:UIControlEventValueChanged];
            if (actions.count == 0) {
                continue;
            }
            actionsByTarget[[NSValue valueWithNonretainedObject:target]] = actions;
            for (NSString *action in actions) {
                [self removeTarget:target action:NSSelectorFromString(action) forControlEvents:UIControlEventValueChanged];
            }
        }

        self.preferredDatePickerStyle = UIDatePickerStyleCompact;
        self.preferredDatePickerStyle = currentStyle;
        self.datePickerMode = currentMode;
        self.minuteInterval = currentMinuteInterval;
        self.minimumDate = minimumDate;
        self.maximumDate = maximumDate;
        self.locale = locale;
        self.timeZone = timeZone;
        [self setDate:currentDate animated:NO];

        for (NSValue *key in actionsByTarget) {
            id target = [key nonretainedObjectValue];
            for (NSString *action in actionsByTarget[key]) {
                [self addTarget:target action:NSSelectorFromString(action) forControlEvents:UIControlEventValueChanged];
            }
        }
    } else {
        [self setNeedsLayout];
        [self layoutIfNeeded];
    }
}

- (void)applyInterfaceStyle:(UIUserInterfaceStyle)style API_AVAILABLE(ios(13.0))
{
    if (self.overrideUserInterfaceStyle == style) {
        return;
    }
    self.overrideUserInterfaceStyle = style;
    [self refreshAfterInterfaceStyleChange];
}

- (void)traitCollectionDidChange:(UITraitCollection *)previousTraitCollection
{
    [super traitCollectionDidChange:previousTraitCollection];
    if (@available(iOS 13, *)) {
        if (previousTraitCollection == nil) {
            return;
        }
        if (self.traitCollection.userInterfaceStyle == previousTraitCollection.userInterfaceStyle) {
            return;
        }
        [self refreshAfterInterfaceStyleChange];
    }
}

- (void)setTheme:(NSString *)theme
{
    if (@available(iOS 13, *)) {
        if ([theme isEqualToString:@"light"]) {
            [self applyInterfaceStyle:UIUserInterfaceStyleLight];
        } else if ([theme isEqualToString:@"dark"]) {
            [self applyInterfaceStyle:UIUserInterfaceStyleDark];
        } else {
            [self applyInterfaceStyle:UIUserInterfaceStyleUnspecified];
        }
    }
}

- (void)setTextColorProp:(NSString *)hexColor
{
    if(@available(iOS 13, *)) {

        // black text -> set light mode
        if([hexColor isEqualToString:@"#000000"]){
            [self applyInterfaceStyle:UIUserInterfaceStyleLight];
        }
        
        // white text -> set dark mode
        else if([hexColor isEqualToString:@"#FFFFFF"] || [hexColor isEqualToString:@"#ffffff"]){
            [self applyInterfaceStyle:UIUserInterfaceStyleDark];
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

