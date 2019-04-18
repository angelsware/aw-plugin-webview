#ifndef __AW_WEB_VIEW_WEB_VIEW_FACTORY_H__
#define __AW_WEB_VIEW_WEB_VIEW_FACTORY_H__

namespace WebView {
	class IWebView;

	class CWebViewFactory {
	public:
		static IWebView* create();
		static void destroy(IWebView* webView);
	};
}

#endif
