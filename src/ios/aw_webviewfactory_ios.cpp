#include <platform/aw_webviewfactory.h>
#include "aw_webview_ios.h"

namespace Platform {
	IWebView* CWebViewFactory::create() {
		return new CWebView_Ios();
	}

	void CWebViewFactory::destroy(IWebView* webView) {
		delete webView;
	}
}
