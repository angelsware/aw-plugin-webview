package com.angelsware.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.view.ViewGroup;
import android.view.View;
import android.app.Activity;

import com.angelsware.engine.AppActivity;

public class WebView {
	private static android.webkit.WebView sWebView;
	private static JsInterface sJsInterface;

	private class WebViewClient extends android.webkit.WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String url) {
			webView.loadUrl(url);
			return true;
		}
	}

	public static void onCreate() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView = new android.webkit.WebView(AppActivity.getActivity());
				sJsInterface = new JsInterface();
				sWebView.setWebChromeClient(new WebChromeClient());
				sWebView.setWebViewClient(new android.webkit.WebViewClient());
				sWebView.addJavascriptInterface(sJsInterface, "native");
				sWebView.setBackgroundColor(0x00000000);
				WebSettings webSettings = sWebView.getSettings();
				webSettings.setJavaScriptEnabled(true);
				webSettings.setDomStorageEnabled(true);
				webSettings.setDatabaseEnabled(true);
				webSettings.setAllowFileAccess(true);
				webSettings.setAllowFileAccessFromFileURLs(true);
				webSettings.setAllowContentAccess(true);
				webSettings.setAllowUniversalAccessFromFileURLs(true);
				AppActivity.getActivity().addContentView(sWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			}
		});
	}

	public static void setVisible(boolean visible) {
		sWebView.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public static boolean isVisible() {
		return sWebView.getVisibility() == View.VISIBLE;
	}

	public static void goBack() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.goBack();
			}
		});
	}

	public static void goForward() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.goForward();
			}
		});
	}

	public static void evaluateJavascript(final String data) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.evaluateJavascript(data, null);
			}
		});
	}

	public static void loadData(final String data, final String mimeType, final String encoding) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.loadDataWithBaseURL("file:///android_asset/www/", data, mimeType, encoding, null);
			}
		});
	}

	public static void loadUrl(final String url) {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.loadUrl(url);
			}
		});
	}

	public static void reload() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.reload();
			}
		});
	}

	public static void stopLoading() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.stopLoading();
			}
		});
	}

	public static void clearHistory() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView.clearHistory();
			}
		});
	}

	public static void setBackgroundColor() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
			}
		});
	}
}
