#import "DatePickerEventEmitterModule.h"

@implementation DatePickerEventEmitterModule

RCT_EXPORT_MODULE();

- (NSArray<NSString*> *)supportedEvents {
  return @[@"neutralButtonPress"];
}

// Allocate as a singleton to avoid the bridge not being initialized exception
// See https://github.com/facebook/react-native/issues/15421
+ (id)allocWithZone:(NSZone *)zone {
    static DatePickerEventEmitterModule *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}

- (void)sendNeutralButtonPressEvent
{
    [self sendEventWithName:@"neutralButtonPress" body:@""];
}

@end
