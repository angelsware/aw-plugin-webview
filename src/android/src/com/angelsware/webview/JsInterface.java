package com.angelsware.webview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.angelsware.engine.AppActivity;

public class JsInterface {
	private static String TAG = "aw";

	public static native void onWebViewMessage(String data, long listener);
	public static native void onWebViewError(String data, long listener);
	public static native void onWebViewFinishedLoading(String url, long listener);

	private static List sListeners = new ArrayList();

	public static void addListener(long listener) {
		sListeners.add(listener);
	}

	public static void removeListener(long listener) {
		sListeners.remove(listener);
	}

	public static void clearAllListeners() {
		sListeners.clear();
	}

	@JavascriptInterface
	public void log(String str) {
		Log.i(TAG, str);
	}

	@JavascriptInterface
	public void loadUrl(final String url) {
		WebView.loadUrl(url);
	}

	@JavascriptInterface
	public void loadData(final String data, final String mimeType, final String encoding) {
		WebView.loadData(data, mimeType, encoding);
	}

	@JavascriptInterface
	public void publish(final String data) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				for (Iterator<Long> i = sListeners.iterator(); i.hasNext();) {
					Long listener = i.next();
					onWebViewMessage(data, (long)listener);
				}
			}
		});
	}

	@JavascriptInterface
	public void publishError(final String data) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				for (Iterator<Long> i = sListeners.iterator(); i.hasNext();) {
					Long listener = i.next();
					onWebViewError(data, (long)listener);
				}
			}
		});
	}

	public void onPageFinished(final String url) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				for (Iterator<Long> i = sListeners.iterator(); i.hasNext();) {
					Long listener = i.next();
					onWebViewFinishedLoading(url, (long)listener);
				}
			}
		});
	}

	@JavascriptInterface
	public void setBackgroundColor(final String color) {
		WebView.setBackGroundColor(color);
	}
}
