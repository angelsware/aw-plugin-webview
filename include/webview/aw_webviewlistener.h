#ifndef __AW_WEB_VIEW_LISTENER_H__
#define __AW_WEB_VIEW_LISTENER_H__

namespace WebView {
	class IWebViewListener
	{
	public:
		virtual void onWebViewMessage(const char* data) = 0;
		virtual void onWebViewError(const char* data) = 0;
		virtual void onWebViewFinishedLoading(const char* url) = 0;

	protected:
		virtual ~IWebViewListener() {}
	};
}

#endif
