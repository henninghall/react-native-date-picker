#import <React/RCTLog.h>
#import <React/RCTUIManager.h>
#import <React/RCTViewManager.h>

@interface RNDatePicker2Manager : RCTViewManager
@end

@implementation RNDatePicker2Manager

RCT_EXPORT_MODULE(RNDatePicker2)

RCT_EXPORT_VIEW_PROPERTY(text, NSString)

@end