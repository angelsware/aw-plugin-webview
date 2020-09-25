#ifndef __AW_WEB_VIEW_WEB_VIEW_IOS_H__
#define __AW_WEB_VIEW_WEB_VIEW_IOS_H__

#include <webview/aw_webview.h>

namespace WebView {
	class IWebViewListener;

	class CWebView_Ios
		: public IWebView
	{
	private:
		void setVisible(bool visible) override;
		bool isVisible() override;
		void onCreate() override;
		void onDestroy() override;
		void addOpenExternally(const char* urlStartsWith) override;
		void goBack() override;
		void goForward() override;
		void evaluateJavascript(const char* data) override;
		void execJavascriptFunction(const char* functionName, const char* b64EncodedParameters) override;
		void loadData(const char* data, const char* mimeType, const char* encoding) override;
		void loadUrl(const char* url) override;
		void loadFile(const char* filename) override;
		void reload() override;
		void stopLoading() override;
		void setBackgroundColor(const Rendering::CColor& color) override;
		void addListener(IWebViewListener* listener) override;
		void removeListener(IWebViewListener* listener) override;
		void clearAllListeners() override;

		void* mWebView;
	};
}

#endif
