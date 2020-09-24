#include "aw_webview_android.h"
#include <platform/android/aw_jni.h>
#include <platform/android/aw_jnifunction.h>
#include <platform/android/aw_jnistring.h>
#include <rendering/aw_color.h>
#include <cstdio>

namespace WebView {
	void CWebView_Android::setVisible(bool visible) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "setVisible", "(Z)V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, visible);
			}
		}
	}

	bool CWebView_Android::isVisible() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "isVisible", "()Z")) {
				return Platform::CJni::getEnv()->CallStaticBooleanMethod(clazz, method);
			}
		}
		return false;
	}

	void CWebView_Android::onCreate() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "onCreate", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::onDestroy() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "onDestroy", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::addOpenExternally(const char* urlStartsWith) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "addOpenExternally", "(Ljava/lang/String;)V")) {
				Platform::CJniString jniUrlStartsWith(urlStartsWith);
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, jniUrlStartsWith.getText());
			}
		}
	}

	void CWebView_Android::goBack() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "goBack", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::goForward() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "goForward", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::evaluateJavascript(const char* data) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "evaluateJavascript", "(Ljava/lang/String;)V")) {
				Platform::CJniString jniData(data);
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, jniData.getText());
			}
		}
	}

	void CWebView_Android::execJavascriptFunction(const char* functionName, const char* b64EncodedParameters) {
		char buffer[1024] = {0};
		sprintf(buffer, "%s('%s')", functionName, b64EncodedParameters);
		evaluateJavascript(buffer);
	}

	void CWebView_Android::loadData(const char* data, const char* mimeType, const char* encoding) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "loadData", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
				Platform::CJniString jniData(data);
				Platform::CJniString jniMimeType(mimeType);
				Platform::CJniString jniEncoding(encoding);
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, jniData.getText(), jniMimeType.getText(), jniEncoding.getText());
			}
		}
	}

	void CWebView_Android::loadUrl(const char* url) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "loadUrl", "(Ljava/lang/String;)V")) {
				Platform::CJniString jniUrl(url);
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, jniUrl.getText());
			}
		}
	}

	void CWebView_Android::reload() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "reload", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::stopLoading() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "stopLoading", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}

	void CWebView_Android::setBackgroundColor(const Rendering::CColor& color) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/WebView")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "setBackgroundColor", "(I)V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, color.toInt());
			}
		}
	}

	void CWebView_Android::addListener(IMessageListener* listener) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/JsInterface")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "addListener", "(J)V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, reinterpret_cast<jlong>(listener));
			}
		}
	}

	void CWebView_Android::removeListener(IMessageListener* listener) {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/JsInterface")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "removeListener", "(J)V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method, reinterpret_cast<jlong>(listener));
			}
		}
	}

	void CWebView_Android::clearAllListeners() {
		if (jclass clazz = Platform::CJniFunction::getClass("com/angelsware/webview/JsInterface")) {
			if (jmethodID method = Platform::CJniFunction::getMethod(clazz, "clearAllListeners", "()V")) {
				Platform::CJni::getEnv()->CallStaticVoidMethod(clazz, method);
			}
		}
	}
}
