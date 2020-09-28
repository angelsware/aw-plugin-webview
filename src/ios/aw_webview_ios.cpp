#include "aw_webview_ios.h"

extern "C" {
	void* WebView_create();
	void WebView_destroy(void* ptr);
	void WebView_loadUrl(void* ptr);
	void WebView_loadData(void* ptr, const char* data, const char* mimeType, const char* encoding);
	void WebView_loadFile(void* ptr, const char* name, const char* extension, const char* directory);
	void WebView_evaluateJavaScript(void* ptr, const char* data);
	void WebView_addListener(void* ptr, long long listener);
	void WebView_removeListener(void* ptr, long long listener);
	void WebView_clearAllListeners(void* ptr);
	void WebView_execJavascriptFunction(void* ptr, const char* functionName, const char* b64EncodedParameters);
	void WebView_addOpenExternally(void* ptr, const char* urlStartsWith);
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

	void CWebView_Ios::addOpenExternally(const char* urlStartsWith) {
		WebView_addOpenExternally(mWebView, urlStartsWith);
	}

	void CWebView_Ios::goBack() {}
	void CWebView_Ios::goForward() {}

	void CWebView_Ios::evaluateJavascript(const char* data) {
		WebView_evaluateJavaScript(mWebView, data);
	}

	void CWebView_Ios::execJavascriptFunction(const char* functionName, const char* b64EncodedParameters) {
		WebView_execJavascriptFunction(mWebView, functionName, b64EncodedParameters);
	}

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

	void CWebView_Ios::addListener(IWebViewListener* listener) {
		WebView_addListener(mWebView, reinterpret_cast<long long>(listener));
	}

	void CWebView_Ios::removeListener(IWebViewListener* listener) {
		WebView_removeListener(mWebView, reinterpret_cast<long long>(listener));
	}

	void CWebView_Ios::clearAllListeners() {
		WebView_clearAllListeners(mWebView);
	}
}
