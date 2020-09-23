#import "WebViewDelegate.h"
#import <webview/aw_messagelistener.h>

@implementation WebViewDelegate

+(void) onWebViewMessage:(int64_t) id message:(NSString*) message {
    reinterpret_cast<WebView::IMessageListener*>(id)->onWebViewMessage([message UTF8String]);
}

@end
