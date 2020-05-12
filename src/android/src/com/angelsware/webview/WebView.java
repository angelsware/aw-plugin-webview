package com.angelsware.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.DownloadListener;
import android.view.ViewGroup;
import android.view.View;
import android.graphics.Color;
import android.app.DownloadManager;
import android.widget.Toast;
import android.net.Uri;
import android.os.Environment;
import android.content.Context;
import android.content.Intent;

import com.angelsware.engine.AppActivity;

import java.util.ArrayList;
import java.util.List;

public class WebView {
	private static android.webkit.WebView sWebView;
	private static JsInterface sJsInterface;
	private static List<String> sOpenExternally = new ArrayList<>();

	private static class WebViewClient extends android.webkit.WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, WebResourceRequest request) {
			String url = request.getUrl().toString();
			if (isOpenExternally(url)) {
				Intent i = new Intent(Intent.ACTION_VIEW, request.getUrl());
				AppActivity.getActivity().startActivity(i);
			} else {
				webView.loadUrl(url);
			}
			return true;
		}

		private boolean isOpenExternally(String url) {
			for (int i = 0; i < sOpenExternally.size(); ++i) {
				if (url.startsWith(sOpenExternally.get(i))) {
					return true;
				}
			}
			return false;
		}
	}

	public static void onCreate() {
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView = new android.webkit.WebView(AppActivity.getActivity());
				sJsInterface = new JsInterface();
				sWebView.setWebChromeClient(new WebChromeClient());
				sWebView.setWebViewClient(new WebViewClient());
				sWebView.setWebContentsDebuggingEnabled(true);
				sWebView.addJavascriptInterface(sJsInterface, "native");
				sWebView.setBackgroundColor(0x00000000);
				sWebView.setDownloadListener(new DownloadListener() {
					@Override
					public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
						request.allowScanningByMediaScanner();
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "document"); // TODO: Fix proper name.
						DownloadManager dm = (DownloadManager)AppActivity.getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
						dm.enqueue(request);
						Toast.makeText(AppActivity.getActivity(), "Downloading File", Toast.LENGTH_LONG).show();
					}
				});
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

	public static void addOpenExternally(String urlStartsWith) {
		sOpenExternally.add(urlStartsWith);
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

	public static void setBackGroundColor(String color) {
		sWebView.setBackgroundColor(Color.parseColor(color));
	}
}
