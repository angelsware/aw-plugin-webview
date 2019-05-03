# aw-plugin-webview

Web view plugin for Angels' Ware rendering engine.

### Getting Started

Add the **plugins** section to the **config.json** file of your project.

```
{
	...

	"plugins":[
		{
			"repo":"https://github.com/angelsware/aw-plugin-webview.git",
			"tag":"*"
		}
	]
}
```

```
WebView::IWebView* webView = WebView::CWebViewFactory::create();
webView->onCreate();
webView->loadUrl("https://example.com");

```
