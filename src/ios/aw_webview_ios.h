#ifndef __AW_WEB_VIEW_WEB_VIEW_IOS_H__
#define __WEB_VIEW_AW_WEB_VIEW_IOS_H__

#include <webview/aw_webview.h>

namespace WebView {
	class IMessageListener;

	class CWebView_Ios
		: public IWebView
	{
	private:
		void setVisible(bool visible) override {}
		bool isVisible() override { return false; }
		void onCreate() override {}
		void goBack() override {}
		void goForward() override {}
		void evaluateJavascript(const char* data) override {}
		void loadData(const char* data, const char* mimeType, const char* encoding) override {}
		void loadUrl(const char* url) override {}
		void reload() override {}
		void stopLoading() override {}
		void setBackgroundColor(const Rendering::CColor& color) override {}
		void addListener(IMessageListener* listener) override {}
		void removeListener(IMessageListener* listener) override {}
		void clearAllListeners() override {}
	};
}

#endif
