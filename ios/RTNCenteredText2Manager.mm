#import <React/RCTLog.h>
#import <React/RCTUIManager.h>
#import <React/RCTViewManager.h>

@interface RTNCenteredText2Manager : RCTViewManager
@end

@implementation RTNCenteredText2Manager

RCT_EXPORT_MODULE(RTNCenteredText2)

RCT_EXPORT_VIEW_PROPERTY(text, NSString)

@end