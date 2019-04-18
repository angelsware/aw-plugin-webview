#include <platform/aw_webviewfactory.h>
#include "aw_webview_android.h"

namespace Platform {
	IWebView* CWebViewFactory::create() {
		return new CWebView_Android();
	}

	void CWebViewFactory::destroy(IWebView* webView) {
		delete webView;
	}
}
