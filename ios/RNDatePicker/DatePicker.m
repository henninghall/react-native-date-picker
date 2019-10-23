/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "DatePicker.h"

#import "RCTUtils.h"
#import "UIView+React.h"

@interface DatePicker ()

@property (nonatomic, copy) RCTBubblingEventBlock onChange;

@end

@implementation DatePicker


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

- (instancetype)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame])) {
        [self addTarget:self action:@selector(didChange)
       forControlEvents:UIControlEventValueChanged];
    }
    return self;
}

- (void)setTextColorProp:(NSString *)hexColor
{
    // Hex to int color
    unsigned intColor = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexColor];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&intColor];

    // Setting picker text color
    [self setValue:UIColorFromRGB(intColor) forKeyPath:@"textColor"];
}

- (void)setHighlightsTodayProp:(BOOL)highlightsToday
{
    if (highlightsToday == NO) {
        [self performSelector:@selector(setHighlightsToday:) withObject:NO];
    }
}

RCT_NOT_IMPLEMENTED(- (instancetype)initWithCoder:(NSCoder *)aDecoder)

- (void)didChange
{
    if (_onChange) {
        _onChange(@{ @"timestamp": @(self.date.timeIntervalSince1970 * 1000.0) });
    }
}

@end

