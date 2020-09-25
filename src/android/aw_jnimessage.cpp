#include "aw_jnimessage.h"
#include <webview/aw_webviewlistener.h>
#include <platform/android/aw_jninativestring.h>

JNIEXPORT void JNICALL Java_com_angelsware_webview_JsInterface_onWebViewMessage(JNIEnv* env, jclass clazz, jstring data, jlong listener) {
	if (WebView::IWebViewListener* ptr = reinterpret_cast<WebView::IWebViewListener*>(listener)) {
		Platform::CJniNativeString dataStr(data);
		ptr->onWebViewMessage(dataStr.getText());
	}
}

JNIEXPORT void JNICALL Java_com_angelsware_webview_JsInterface_onWebViewError(JNIEnv* env, jclass clazz, jstring data, jlong listener) {
	if (WebView::IWebViewListener* ptr = reinterpret_cast<WebView::IWebViewListener*>(listener)) {
		Platform::CJniNativeString dataStr(data);
		ptr->onWebViewError(dataStr.getText());
	}
}
