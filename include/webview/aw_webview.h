#ifndef __AW_WEB_VIEW_WEB_VIEW_H__
#define __AW_WEB_VIEW_WEB_VIEW_H__

namespace Rendering {
	class CColor;
}

namespace WebView {
	class IWebViewListener;

	class IWebView
	{
	public:
		virtual ~IWebView() {}

		virtual void setVisible(bool visible) = 0;
		virtual bool isVisible() = 0;
		virtual void onCreate() = 0;
		virtual void onDestroy() = 0;
		virtual void addOpenExternally(const char* urlStartsWith) = 0;
		virtual void goBack() = 0;
		virtual void goForward() = 0;
		virtual void evaluateJavascript(const char* data) = 0;
		virtual void execJavascriptFunction(const char* functionName, const char* b64EncodedParameters) = 0;
		virtual void loadData(const char* data, const char* mimeType, const char* encoding) = 0;
		virtual void loadUrl(const char* url) = 0;
		virtual void loadFile(const char* filename) = 0;
		virtual void reload() = 0;
		virtual void stopLoading() = 0;
		virtual void setBackgroundColor(const Rendering::CColor& color) = 0;
		virtual void addListener(IWebViewListener* listener) = 0;
		virtual void removeListener(IWebViewListener* listener) = 0;
		virtual void clearAllListeners() = 0;
	};
}

#endif
