#include <webview/aw_webviewfactory.h>
#include "aw_webview_android.h"

namespace WebView {
	IWebView* CWebViewFactory::create() {
		return new CWebView_Android();
	}

	void CWebViewFactory::destroy(IWebView* webView) {
		delete webView;
	}
}
