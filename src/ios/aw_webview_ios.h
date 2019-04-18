#ifndef __AW_WEB_VIEW_IOS_H__
#define __AW_WEB_VIEW_IOS_H__

#include <webview/aw_webview.h>

namespace Platform {
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
	};
}

#endif
