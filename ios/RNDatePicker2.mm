// #import "RCTComponent.h"
#import "RNDatePicker2.h"

// #import "RCTUtils.h"
// #import "UIView+React.h"

#import <react/renderer/components/RNDatePicker2Specs/ComponentDescriptors.h>
#import <react/renderer/components/RNDatePicker2Specs/EventEmitters.h>
#import <react/renderer/components/RNDatePicker2Specs/Props.h>
#import <react/renderer/components/RNDatePicker2Specs/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"

using namespace facebook::react;


@interface RNDatePicker2 () <RCTRNDatePicker2ViewProtocol>

@end

@implementation RNDatePicker2 {
  UIView *_view;
  UILabel *_label;
  NSInteger _reactMinuteInterval;
  facebook::react::SharedViewProps _props;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
  return concreteComponentDescriptorProvider<RNDatePicker2ComponentDescriptor>();
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

- (instancetype)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame])) {
        static const auto defaultProps = std::make_shared<const RNDatePicker2Props>();
        _props = defaultProps;
        
        [self addTarget:self action:@selector(didChange)
       forControlEvents:UIControlEventValueChanged];
        if(@available(iOS 13, *)) {
            self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
        }
        if(@available(iOS 14, *)) {
            self.preferredDatePickerStyle = UIDatePickerStyleWheels;
        }
         _reactMinuteInterval = 1;
         
        // only allow gregorian calendar
        self.calendar = [NSCalendar calendarWithIdentifier:NSCalendarIdentifierGregorian];
    }
    return self;
}

- (void)setColor:(NSString *)hexColor {
    // Hex to int color
    unsigned intColor = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexColor];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&intColor];

    // Setting picker text color
    [self setValue:UIColorFromRGB(intColor) forKeyPath:@"textColor"];
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
        // other colors -> remove "Today" string since it cannot be colored from iOS 13.
        else {
            [self removeTodayString];
            [self setColor:hexColor];
        }
    }

    // if ios 12 and earlier -> no need to remove today string since it can be colored.
    else {
        [self setColor:hexColor];
    }
}


RCT_NOT_IMPLEMENTED(- (instancetype)initWithCoder:(NSCoder *)aDecoder)

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
  const auto &oldViewProps = *std::static_pointer_cast<RNDatePicker2Props const>(_props);
  const auto &newViewProps = *std::static_pointer_cast<RNDatePicker2Props const>(props);
    
  if (oldViewProps.mode != newViewProps.mode) {
      if(newViewProps.mode == RNDatePicker2Mode::Time) [super setDatePickerMode:UIDatePickerModeTime];
      if(newViewProps.mode == RNDatePicker2Mode::Date) [super setDatePickerMode:UIDatePickerModeDate];
      if(newViewProps.mode == RNDatePicker2Mode::Datetime) [super setDatePickerMode:UIDatePickerModeDateAndTime];
    // We need to set minuteInterval after setting datePickerMode, otherwise minuteInterval is invalid in time mode.
    self.minuteInterval = _reactMinuteInterval;
  }

    if (oldViewProps.minuteInterval != newViewProps.minuteInterval) {
        [super setMinuteInterval:newViewProps.minuteInterval];
        _reactMinuteInterval = newViewProps.minuteInterval;
    }
    

  [super updateProps:props oldProps:oldProps];
}


- (void)didChange
{
    // if (_onChange) {
    //     _onChange(@{ @"timestamp": @(self.date.timeIntervalSince1970 * 1000.0) });
    // }
}

- (void)setMinuteInterval:(NSInteger)minuteInterval
{
  [super setMinuteInterval:minuteInterval];
  _reactMinuteInterval = minuteInterval;
}

@end

Class<RCTComponentViewProtocol> RNDatePicker2Cls(void)
{
  return RNDatePicker2.class;
}
