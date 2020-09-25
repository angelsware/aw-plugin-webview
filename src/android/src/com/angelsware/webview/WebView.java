package com.angelsware.webview;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Pair;
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
import com.angelsware.engine.RequestPermissionResultListener;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class WebView {
	private static android.webkit.WebView sWebView;
	private static JsInterface sJsInterface;
	private static List<String> sOpenExternally = new ArrayList<>();
	private static WebViewClient.WebViewDownloadListener mDownloadListener;

	private static class WebViewClient extends android.webkit.WebViewClient {
		static class WebViewDownloadListener implements RequestPermissionResultListener, DownloadListener {
			private String mUrl;
			private List<Pair<Long, String>> mDownloadIds = new ArrayList<>();

			private BroadcastReceiver mOnDownloadComplete = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
					for (Pair<Long, String> element : mDownloadIds) {
						if (element.first == id) {
							if (element.second != null) {
								Toast.makeText(AppActivity.getActivity(), element.second, Toast.LENGTH_LONG).show();
							}
							mDownloadIds.remove(element);
							break;
						}
					}
				}
			};

			WebViewDownloadListener() {
				AppActivity appActivity = (AppActivity)AppActivity.getActivity();
				appActivity.addRequestPermissionResultListener(this);
				AppActivity.getActivity().registerReceiver(mOnDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			}

			public void destroy() {
				AppActivity.getActivity().unregisterReceiver(mOnDownloadComplete);
				AppActivity appActivity = (AppActivity)AppActivity.getActivity();
				appActivity.removeRequestPermissionResultListener(this);
			}

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (ContextCompat.checkSelfPermission(AppActivity.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
					download(url);
				} else {
					this.mUrl = url;
					AppActivity.getActivity().requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 84262);
				}
			}

			private void download(String url) {
				Uri uri = Uri.parse(url);
				String filename = uri.getQueryParameter("filename");
				if (filename == null || filename.equals("")) {
					filename = "document";
				}
				String toast = uri.getQueryParameter("toast");

				DownloadManager.Request request = new DownloadManager.Request(uri);
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
				DownloadManager dm = (DownloadManager)AppActivity.getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
				mDownloadIds.add(new Pair<>(dm.enqueue(request), uri.getQueryParameter("complete_toast")));
				if (toast != null) {
					Toast.makeText(AppActivity.getActivity(), toast, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
				if (requestCode == 84262 && permissions.length > 0) {
					for (int grantResult : grantResults) {
						if (grantResult != PackageManager.PERMISSION_GRANTED) {
							return;
						}
					}
					download(this.mUrl);
				}
				this.mUrl = "";
			}
		}

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

		@Override
		public void onReceivedError(android.webkit.WebView webView, int errorCode, String description, String failingUrl) {
			try {
				webView.stopLoading();
			} catch (Exception ignored) {
			}

			webView.loadUrl("about:blank");
			sJsInterface.publishError("{\"description\":\"" + description + "\",\"failing-url\":\"" + failingUrl + "\"}");

			super.onReceivedError(webView, errorCode, description, failingUrl);
		}

		@Override
		public void onPageFinished(android.webkit.WebView webView, String url) {
			sJsInterface.onPageFinished();
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
		mDownloadListener = new WebViewClient.WebViewDownloadListener();
		AppActivity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sWebView = new android.webkit.WebView(AppActivity.getActivity());
				sJsInterface = new JsInterface();
				sWebView.setWebChromeClient(new WebChromeClient());
				sWebView.setWebViewClient(new WebViewClient());
				sWebView.addJavascriptInterface(sJsInterface, "native");
				sWebView.setBackgroundColor(0x00000000);
				sWebView.setVerticalScrollBarEnabled(false);
				sWebView.setHorizontalScrollBarEnabled(false);
				sWebView.setDownloadListener(mDownloadListener);
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

	public static void onDestroy() {
		mDownloadListener.destroy();
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
