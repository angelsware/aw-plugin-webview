#include <webview/aw_webviewfactory.h>
#include "aw_webview_default.h"

namespace WebView {
	IWebView* CWebViewFactory::create() {
		return new CWebView_Default();
	}

	void CWebViewFactory::destroy(IWebView* webView) {
		delete webView;
	}
}
