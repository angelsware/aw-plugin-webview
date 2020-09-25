#import "WebViewDelegate.h"
#import <webview/aw_webviewlistener.h>

@implementation WebViewDelegate

+(void) onWebViewMessage:(int64_t) id message:(NSString*) message {
    reinterpret_cast<WebView::IWebViewListener*>(id)->onWebViewMessage([message UTF8String]);
}

+(void) onWebViewFinishedLoading:(int64_t) id {
    reinterpret_cast<WebView::IWebViewListener*>(id)->onWebViewFinishedLoading();
}

@end
