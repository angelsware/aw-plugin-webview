#ifndef __AW_WEB_VIEW_MESSAGE_LISTENER_H__
#define __AW_WEB_VIEW_MESSAGE_LISTENER_H__

namespace WebView {
	class IMessageListener
	{
	public:
		virtual void onWebViewMessage(const char* data) = 0;
		virtual void onWebViewError(const char* data) = 0;

	protected:
		virtual ~IMessageListener() {}
	};
}

#endif
