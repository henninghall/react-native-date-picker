#import "RNDatePicker.h"

#ifdef RCT_NEW_ARCH_ENABLED
#import "RCTConvert.h"
#import <React/RCTConversions.h>
#import <react/renderer/components/RNDatePickerSpecs/ComponentDescriptors.h>
#import <react/renderer/components/RNDatePickerSpecs/EventEmitters.h>
#import <react/renderer/components/RNDatePickerSpecs/Props.h>
#import <react/renderer/components/RNDatePickerSpecs/RCTComponentViewHelpers.h>
#import "RCTFabricComponentsPlugins.h"

using namespace facebook::react;

#else
#import "RCTUtils.h"
#import "UIView+React.h"
#import "RCTComponent.h"
#endif


#import "DatePicker.h"


#ifdef RCT_NEW_ARCH_ENABLED
@interface RNDatePicker () <RCTRNDatePickerViewProtocol>
@end
#else
@interface RNDatePicker ()

@property (nonatomic, copy) RCTBubblingEventBlock onChange;
@property (nonatomic, assign) NSInteger reactMinuteInterval;

@end

#endif

@implementation RNDatePicker {
    DatePicker *_picker;
    UIView *_view;
    UILabel *_label;
    NSInteger _reactMinuteInterval;
}

#ifdef RCT_NEW_ARCH_ENABLED
+ (ComponentDescriptorProvider)componentDescriptorProvider
{
    return concreteComponentDescriptorProvider<RNDatePickerComponentDescriptor>();
}
#endif

NSDate* unixMillisToNSDate (double unixMillis) {
    double time = unixMillis/1000.0;
    return [NSDate dateWithTimeIntervalSince1970: time];
}

#ifdef RCT_NEW_ARCH_ENABLED
- (instancetype)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame])) {
        static const auto defaultProps = std::make_shared<const RNDatePickerProps>();
        _props = defaultProps;
        
        _picker = [[DatePicker alloc] initWithFrame:_view.bounds];
        [_picker setup];
        
        [_picker addTarget:self action:@selector(didChange:)
          forControlEvents:UIControlEventValueChanged];
        
        _reactMinuteInterval = 1;
        
        self.contentView = _picker;
    }
    return self;
}
#else
- (instancetype)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame])) {
        [self setup];
        [self addTarget:self action:@selector(didChange)
       forControlEvents:UIControlEventValueChanged];
        _reactMinuteInterval = 1;
    }
    return self;
}
#endif


RCT_NOT_IMPLEMENTED(- (instancetype)initWithCoder:(NSCoder *)aDecoder)

#ifdef RCT_NEW_ARCH_ENABLED
- (void)setContentView:(UIView *)contentView
{
    [super setContentView:_picker];
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<RNDatePickerProps const>(oldProps ? oldProps : _props); //_props equ
    const auto &newViewProps = *std::static_pointer_cast<RNDatePickerProps const>(props);
    
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
        if(newViewProps.mode == RNDatePickerMode::Time) [_picker setDatePickerMode:UIDatePickerModeTime];
        if(newViewProps.mode == RNDatePickerMode::Date) [_picker setDatePickerMode:UIDatePickerModeDate];
        if(newViewProps.mode == RNDatePickerMode::Datetime) [_picker setDatePickerMode:UIDatePickerModeDateAndTime];
        // We need to set minuteInterval after setting datePickerMode, otherwise minuteInterval is invalid in time mode.
        _picker.minuteInterval = _reactMinuteInterval;
    }
    
    //  timeZoneOffsetInMinutes
    if (oldViewProps.timeZoneOffsetInMinutes != newViewProps.timeZoneOffsetInMinutes) {
        NSString *newString = RCTNSStringFromString(newViewProps.timeZoneOffsetInMinutes);
        [_picker setTimeZoneOffsetInMinutes:newString];
    }
    
    // text color
    if(oldViewProps.textColor != newViewProps.textColor){
        NSString *textColor = RCTNSStringFromString(newViewProps.textColor);
        [_picker setTextColorProp:textColor];
    }
    
    [super updateProps:props oldProps:oldProps];
}

-(void)didChange:(RNDatePicker *)sender
{
    std::dynamic_pointer_cast<const RNDatePickerEventEmitter>(_eventEmitter)
    ->onChange(RNDatePickerEventEmitter::OnChange{ .timestamp = _picker.date.timeIntervalSince1970 * 1000.0f });
}


Class<RCTComponentViewProtocol> RNDatePickerCls(void)
{
    return RNDatePicker.class;
}

#else
- (void)didChange
{
    if (_onChange) {
        _onChange(@{ @"timestamp": @(self.date.timeIntervalSince1970 * 1000.0) });
    }
}

- (void)setDatePickerMode:(UIDatePickerMode)datePickerMode
{
  [super setDatePickerMode:datePickerMode];
  // We need to set minuteInterval after setting datePickerMode, otherwise minuteInterval is invalid in time mode.
  self.minuteInterval = _reactMinuteInterval;
}

- (void)setMinuteInterval:(NSInteger)minuteInterval
{
  [super setMinuteInterval:minuteInterval];
  _reactMinuteInterval = minuteInterval;
}

#endif


@end
