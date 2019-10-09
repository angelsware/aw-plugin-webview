#include "aw_jnimessage.h"
#include <webview/aw_messagelistener.h>
#include <platform/android/aw_jninativestring.h>

JNIEXPORT void JNICALL Java_com_angelsware_webview_JsInterface_onWebViewMessage(JNIEnv* env, jclass clazz, jstring data, jlong listener) {
	if (WebView::IMessageListener* ptr = reinterpret_cast<WebView::IMessageListener*>(listener)) {
		Platform::CJniNativeString dataStr(data);
		ptr->onWebViewMessage(dataStr.getText());
	}
}
