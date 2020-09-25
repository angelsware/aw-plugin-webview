#import <Foundation/Foundation.h>

@interface WebViewDelegate : NSObject

+(void) onWebViewMessage:(int64_t) id message:(NSString*) message;
+(void) onWebViewFinishedLoading:(int64_t) id;

@end
