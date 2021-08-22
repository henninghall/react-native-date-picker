/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "RNDatePickerManager.h"
#import <React/RCTLog.h>

#import "RCTConvert.h"

#import "DatePicker.h"

@implementation RCTConvert(UIDatePicker)

RCT_ENUM_CONVERTER(UIDatePickerMode, (@{
  @"time": @(UIDatePickerModeTime),
  @"date": @(UIDatePickerModeDate),
  @"datetime": @(UIDatePickerModeDateAndTime),
  @"countdown": @(UIDatePickerModeCountDownTimer), // not supported yet
}), UIDatePickerModeTime, integerValue)

@end

@implementation RNDatePickerManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  return [DatePicker new];
}

RCT_EXPORT_VIEW_PROPERTY(date, NSDate)
RCT_EXPORT_VIEW_PROPERTY(locale, NSLocale)
RCT_EXPORT_VIEW_PROPERTY(minimumDate, NSDate)
RCT_EXPORT_VIEW_PROPERTY(maximumDate, NSDate)
RCT_EXPORT_VIEW_PROPERTY(minuteInterval, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(onChange, RCTBubblingEventBlock)
RCT_REMAP_VIEW_PROPERTY(mode, datePickerMode, UIDatePickerMode)
RCT_REMAP_VIEW_PROPERTY(timeZoneOffsetInMinutes, timeZone, NSTimeZone)


RCT_CUSTOM_VIEW_PROPERTY(textColor, NSString, DatePicker)
{
    [view setTextColorProp:[RCTConvert NSString:json]];
}

RCT_CUSTOM_VIEW_PROPERTY(open, BOOL, DatePicker)
{
//    bool open = [RCTConvert BOOL:json];
//    if(!open) return

}

RCT_EXPORT_METHOD(openPicker:(NSDictionary *) props)
{
    
    dispatch_async(dispatch_get_main_queue(), ^{
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"\n\n\n\n\n\n" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
            
        NSLayoutConstraint *heigth = [NSLayoutConstraint constraintWithItem:alertController.view attribute:NSLayoutAttributeHeight relatedBy:NSLayoutRelationEqual toItem:nil attribute:NSLayoutAttributeNotAnAttribute multiplier:1 constant:400];

        [alertController.view addConstraint:heigth];
                    
        DatePicker* picker = [[DatePicker alloc] init];
        
        NSDate * _Nonnull date = [RCTConvert NSDate:[props objectForKey:@"date"]];
        [picker setDate:date];

        NSDate * minimumDate = [RCTConvert NSDate:[props objectForKey:@"minimumDate"]];
        if(minimumDate) [picker setMinimumDate:minimumDate];
        
        NSDate * maximumDate = [RCTConvert NSDate:[props objectForKey:@"maximumDate"]];
        if(maximumDate) [picker setMaximumDate:maximumDate];
        
        NSString * textColor = [RCTConvert NSString:[props objectForKey:@"textColor"] ];
        if(textColor) [picker setTextColorProp:textColor];
        
        UIDatePickerMode mode = [RCTConvert UIDatePickerMode:[props objectForKey:@"mode"]];
        [picker setDatePickerMode:mode];
        
        NSLocale * locale = [RCTConvert NSLocale:[props objectForKey:@"locale"]];
        if(locale) [picker setLocale:locale];

        int minuteInterval = [RCTConvert int:[props objectForKey:@"minuteInterval"]];
        [picker setMinuteInterval:minuteInterval];

        NSTimeZone* timezone = [RCTConvert NSTimeZone:[props valueForKey:@"timeZoneOffsetInMinutes"]];
        [picker setTimeZone:timezone];
        
        [alertController.view addSubview:picker];

        UIAlertAction *somethingAction = [UIAlertAction actionWithTitle:@"Confirm" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {}];
        UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:^(UIAlertAction *action) {}];
            
        [alertController addAction:somethingAction];
        [alertController addAction:cancelAction];
        UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
        [rootViewController presentViewController:alertController animated:YES completion:^{}];
    });

}

@end
