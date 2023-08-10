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

#import "DatePicker.h"

using namespace facebook::react;


@interface RNDatePicker2 () <RCTRNDatePicker2ViewProtocol>

@end

@implementation RNDatePicker2 {
  DatePicker *_picker;
  UIView *_view;
  UILabel *_label;
  NSInteger _reactMinuteInterval;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
  return concreteComponentDescriptorProvider<RNDatePicker2ComponentDescriptor>();
}

NSDate* unixMillisToNSDate (double unixMillis) {
    double time = unixMillis/1000.0;
    return [NSDate dateWithTimeIntervalSince1970: time];
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame])) {
        static const auto defaultProps = std::make_shared<const RNDatePicker2Props>();
        _props = defaultProps;
        
        _picker = [[DatePicker alloc] initWithFrame:_view.bounds];
        
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
        [_picker setTextColorProp:textColor];
    }

  [super updateProps:props oldProps:oldProps];
}

-(void)didChange:(RNDatePicker2 *)sender
{
    std::dynamic_pointer_cast<const RNDatePicker2EventEmitter>(_eventEmitter)
     ->onChange(RNDatePicker2EventEmitter::OnChange{ .timestamp = _picker.date.timeIntervalSince1970 * 1000.0f });
}

@end

Class<RCTComponentViewProtocol> RNDatePicker2Cls(void)
{
  return RNDatePicker2.class;
}