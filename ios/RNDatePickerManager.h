/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

#ifdef RCT_NEW_ARCH_ENABLED
#import "RNDatePickerSpecs.h"
#endif

@interface RNDatePickerManager : RCTViewManager
#ifdef RCT_NEW_ARCH_ENABLED
<NativeRNDatePickerSpec>
#endif

@property (strong, nonatomic) UIViewController *topViewController;

@end
