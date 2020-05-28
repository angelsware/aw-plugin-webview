#ifndef __AW_WEBVIEW_JNI_MESSAGE_H__
#define __AW_WEBVIEW_JNI_MESSAGE_H__

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif
	JNIEXPORT void JNICALL Java_com_angelsware_webview_JsInterface_onWebViewMessage(JNIEnv* env, jclass clazz, jstring data, jlong listener);
	JNIEXPORT void JNICALL Java_com_angelsware_webview_JsInterface_onWebViewError(JNIEnv* env, jclass clazz, jstring data, jlong listener);
#ifdef __cplusplus
}
#endif

#endif
