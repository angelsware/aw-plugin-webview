package com.angelsware.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsInterface {
	private static String TAG = "aw";
 
	@JavascriptInterface
	public void log(String str) {
		Log.i(TAG, str);
	}
}
