#include "aw_webview_ios.h"

extern "C" {
	void* WebView_create();
	void WebView_destroy(void* ptr);
	void WebView_loadUrl(void* ptr);
	void WebView_loadData(void* ptr, const char* data, const char* mimeType, const char* encoding);
	void WebView_loadFile(void* ptr, const char* name, const char* extension, const char* directory);
}

namespace WebView {
	void CWebView_Ios::setVisible(bool visible) {}
	bool CWebView_Ios::isVisible() { return false; }

	void CWebView_Ios::onCreate() {
		mWebView = WebView_create();
	}

	void CWebView_Ios::onDestroy() {
		WebView_destroy(mWebView);
	}

	void CWebView_Ios::addOpenExternally(const char* urlStartsWith) {}
	void CWebView_Ios::goBack() {}
	void CWebView_Ios::goForward() {}
	void CWebView_Ios::evaluateJavascript(const char* data) {}
	void CWebView_Ios::loadData(const char* data, const char* mimeType, const char* encoding) {
		WebView_loadData(mWebView, data, mimeType, encoding);
	}

	void CWebView_Ios::loadUrl(const char* url) {
		WebView_loadUrl(mWebView);
	}

	void CWebView_Ios::loadFile(const char* filename) {
		// TODO: Implement correctly.
		WebView_loadFile(mWebView, "index", "html", "assets/www");
	}

	void CWebView_Ios::reload() {}
	void CWebView_Ios::stopLoading() {}
	void CWebView_Ios::setBackgroundColor(const Rendering::CColor& color) {}
	void CWebView_Ios::addListener(IMessageListener* listener) {}
	void CWebView_Ios::removeListener(IMessageListener* listener) {}
	void CWebView_Ios::clearAllListeners() {}
}
