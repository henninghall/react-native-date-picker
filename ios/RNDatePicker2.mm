// #import "RCTComponent.h"
#import "RNDatePicker2.h"

// #import "RCTUtils.h"
// #import "UIView+React.h"
#import <React/RCTConversions.h>
#import "RCTConvert.h"


#import <react/renderer/components/RNDatePicker2Specs/ComponentDescriptors.h>
#import <react/renderer/components/RNDatePicker2Specs/EventEmitters.h>
#import <react/renderer/components/RNDatePicker2Specs/Props.h>
#import <react/renderer/components/RNDatePicker2Specs/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"

using namespace facebook::react;


@interface RNDatePicker2 () <RCTRNDatePicker2ViewProtocol>

@end

@implementation RNDatePicker2 {
  UIDatePicker *_picker;
  UIView *_view;
  UILabel *_label;
  NSInteger _reactMinuteInterval;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
  return concreteComponentDescriptorProvider<RNDatePicker2ComponentDescriptor>();
}


#define UIColorFromRGB(rgbHex) [UIColor colorWithRed:((float)((rgbHex & 0xFF0000) >> 16))/255.0 green:((float)((rgbHex & 0xFF00) >> 8))/255.0 blue:((float)(rgbHex & 0xFF))/255.0 alpha:1.0]

NSDate* unixMillisToNSDate (double unixMillis) {
    double time = unixMillis/1000.0;
    return [NSDate dateWithTimeIntervalSince1970: time];
}

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
        
        _picker = [[UIDatePicker alloc] initWithFrame:_view.bounds];
        
        [_picker addTarget:self action:@selector(didChange:)
       forControlEvents:UIControlEventValueChanged];
        if(@available(iOS 13, *)) {
            _picker.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
        }
        if(@available(iOS 14, *)) {
            _picker.preferredDatePickerStyle = UIDatePickerStyleWheels;
        }
         _reactMinuteInterval = 1;
         
        // only allow gregorian calendar
        _picker.calendar = [NSCalendar calendarWithIdentifier:NSCalendarIdentifierGregorian];
        
        self.contentView = _picker;
    }
    return self;
}

- (void)setContentView:(UIView *)contentView
{
  [super setContentView:_picker];
}

- (void)setColor:(NSString *)hexColor {
    // Hex to int color
    unsigned intColor = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexColor];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&intColor];

    // Setting picker text color
    [_picker setValue:UIColorFromRGB(intColor) forKeyPath:@"textColor"];
}

- (void)removeTodayString {
    #pragma clang diagnostic push
    #pragma clang diagnostic ignored "-Wundeclared-selector"
    [_picker performSelector:@selector(setHighlightsToday:) withObject:[NSNumber numberWithBool:NO]];
    #pragma clang diagnostic pop
}


- (void)setTextColorProp:(NSString *)hexColor
{
    
    if(@available(iOS 13, *)) {

        // black text -> set light mode
        if([hexColor isEqualToString:@"#000000"]){
            _picker.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
        }

        // white text -> set dark mode
        else if([hexColor isEqualToString:@"#FFFFFF"] || [hexColor isEqualToString:@"#ffffff"]){
            _picker.overrideUserInterfaceStyle = UIUserInterfaceStyleDark;
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
  const auto &oldViewProps = *std::static_pointer_cast<RNDatePicker2Props const>(oldProps ? oldProps : _props); //_props equ
  const auto &newViewProps = *std::static_pointer_cast<RNDatePicker2Props const>(props);
    
    //  date
    if(oldViewProps.date != newViewProps.date) {
        [_picker setDate: unixMillisToNSDate(newViewProps.date)];
    }
    
    //  locale
    if(oldViewProps.locale != newViewProps.locale) {
        NSString *convertedLocale = RCTNSStringFromString(newViewProps.locale);
        NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:convertedLocale];
        [_picker setLocale:locale];
    }
 
    // maximumDate
    if(oldViewProps.maximumDate != newViewProps.maximumDate) {
        [_picker setMaximumDate: unixMillisToNSDate(newViewProps.maximumDate)];
    }
    
    //  minimumDate
    if(oldViewProps.minimumDate != newViewProps.minimumDate) {
        [_picker setMinimumDate: unixMillisToNSDate(newViewProps.minimumDate)];
    }
    
    //  setMinuteInterval
    if (oldViewProps.minuteInterval != newViewProps.minuteInterval) {
        [_picker setMinuteInterval:newViewProps.minuteInterval];
        _reactMinuteInterval = newViewProps.minuteInterval;
    }
    
  // mode
  if (oldViewProps.mode != newViewProps.mode) {
      if(newViewProps.mode == RNDatePicker2Mode::Time) [_picker setDatePickerMode:UIDatePickerModeTime];
      if(newViewProps.mode == RNDatePicker2Mode::Date) [_picker setDatePickerMode:UIDatePickerModeDate];
      if(newViewProps.mode == RNDatePicker2Mode::Datetime) [_picker setDatePickerMode:UIDatePickerModeDateAndTime];
    // We need to set minuteInterval after setting datePickerMode, otherwise minuteInterval is invalid in time mode.
    _picker.minuteInterval = _reactMinuteInterval;
  }

    //  timeZoneOffsetInMinutes
    if (oldViewProps.timeZoneOffsetInMinutes != newViewProps.timeZoneOffsetInMinutes) {
        if([RCTNSStringFromString(newViewProps.timeZoneOffsetInMinutes) length] == 0){
            [_picker setTimeZone: nil];
        }
        else {
            NSString *timezoneOffsetString = RCTNSStringFromString(newViewProps.timeZoneOffsetInMinutes);
            NSNumber *timezoneMinutesInt = [NSNumber numberWithInt:[timezoneOffsetString intValue]];
            [_picker setTimeZone:[RCTConvert NSTimeZone: timezoneMinutesInt]];
        }
    }
    
  // text color
    if(oldViewProps.textColor != newViewProps.textColor){
        NSString *textColor = RCTNSStringFromString(newViewProps.textColor);
        [self setTextColorProp:textColor];
    }

  [super updateProps:props oldProps:oldProps];
}

-(void)didChange:(RNDatePicker2 *)sender
{
    std::dynamic_pointer_cast<const RNDatePicker2EventEmitter>(_eventEmitter)
     ->onChange(RNDatePicker2EventEmitter::OnChange{ .timestamp = _picker.date.timeIntervalSince1970 * 1000.0f });
}

- (void)setMinuteInterval:(NSInteger)minuteInterval
{
  [_picker setMinuteInterval:minuteInterval];
  _reactMinuteInterval = minuteInterval;
}

@end

Class<RCTComponentViewProtocol> RNDatePicker2Cls(void)
{
  return RNDatePicker2.class;
}
