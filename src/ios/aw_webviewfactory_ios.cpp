#include <webview/aw_webviewfactory.h>
#include "aw_webview_ios.h"

namespace WebView {
	IWebView* CWebViewFactory::create() {
		return new CWebView_Ios();
	}

	void CWebViewFactory::destroy(IWebView* webView) {
		delete webView;
	}
}
