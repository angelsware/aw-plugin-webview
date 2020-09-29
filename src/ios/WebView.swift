import WebKit

class WebViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
	func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
		if(message.name == "native") {
			if let dict = message.body as? Dictionary<String, AnyObject> {
				for (_, value) in dict {
					listeners.forEach { listener in
						WebViewDelegate.onWebViewMessage(listener, message: (value as! String))
					}
				}
			}
		}
	}

	@IBOutlet var webView: WKWebView!
	var listeners: Set<Int64> = Set()
	var openExternally: Set<String> = Set()

	override func viewDidLoad() {
		super.viewDidLoad()
		let webConfig = WKWebViewConfiguration()
		let userContentController = WKUserContentController()
		userContentController.add(self, name: "native")
		webConfig.userContentController = userContentController
		webView = WKWebView(frame: .zero, configuration: webConfig)
		if #available(iOS 11.0, *) {
			webView.scrollView.contentInsetAdjustmentBehavior = .never;
		}
		webView.navigationDelegate = self
		webView.uiDelegate = self
		view = webView
	}

	func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
		listeners.forEach { listener in
			WebViewDelegate.onWebViewFinishedLoading(listener)
		}
	}
	
	func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
		if (isOpenExternally(url: navigationAction.request.url!.absoluteString)) {
			if let url = navigationAction.request.url, UIApplication.shared.canOpenURL(url) {
				UIApplication.shared.open(url)
				decisionHandler(.cancel)
			} else {
				decisionHandler(.allow)
			}
		} else {
			decisionHandler(.allow)
		}
	}

	func loadUrl(url: String) {
		DispatchQueue.main.async {
			self.webView.load(URLRequest(url: URL(string: url)!))
		}
	}

	func loadData(data: String, mimeType: String, encoding: String) {
		DispatchQueue.main.async {
			self.webView.loadHTMLString(data, baseURL: nil)
		}
	}

	func loadFile(name: String, ext: String, directory: String) {
		DispatchQueue.main.async {
			if let indexURL = Bundle.main.url(forResource: name, withExtension: ext, subdirectory: directory) {
				self.webView.loadFileURL(indexURL, allowingReadAccessTo: indexURL)
			}
		}
	}

	func evaluateJavaScript(data: String) {
		DispatchQueue.main.async {
			self.webView.evaluateJavaScript(data) { (_, error) in
				if error != nil {
					print(error!)
				}
			}
		}
	}

	func addListener(listener: Int64) {
		listeners.insert(listener)
	}

	func removeListener(listener: Int64) {
		listeners.remove(listener)
	}

	func clearAllListeners() {
		listeners.removeAll()
	}

	func execJavaScriptFunction(functionName: String, b64EncodedParameters: String) {

	}
	
	func addOpenExternally(urlStartsWith: String) {
		openExternally.insert(urlStartsWith)
	}
	
	private func isOpenExternally(url: String) -> Bool {
		var result = false
		openExternally.forEach { (openExternally) in
			if (url.starts(with: openExternally)) {
				result = true
				return
			}
		}
		return result
	}
}

@_cdecl("WebView_create")
func WebView_create() -> UnsafeMutablePointer<WebViewController> {
	let webViewController = UnsafeMutablePointer<WebViewController>.allocate(capacity: 1)
	webViewController.initialize(to: WebViewController())
	let appDelegate = UIApplication.shared.delegate as! AppDelegate
	appDelegate.window = UIWindow(frame: UIScreen.main.bounds)
	appDelegate.window?.rootViewController = webViewController.pointee
	appDelegate.window?.makeKeyAndVisible()
	return webViewController
}

@_cdecl("WebView_destroy")
func WebView_destroy(ptr: UnsafeRawPointer) {
	ptr.deallocate()
}

@_cdecl("WebView_loadUrl")
func WebView_loadUrl(ptr: UnsafeMutablePointer<WebViewController>, url: UnsafePointer<CChar>) {
	ptr.pointee.loadUrl(url: String.init(cString: url))
}

@_cdecl("WebView_loadData")
func WebView_loadData(ptr: UnsafeMutablePointer<WebViewController>, data: UnsafePointer<CChar>, mimeType: UnsafePointer<CChar>, encoding: UnsafePointer<CChar>) {
	ptr.pointee.loadData(data: String.init(cString: data), mimeType: String.init(cString: mimeType), encoding: String.init(cString: encoding))
}

@_cdecl("WebView_loadFile")
func WebView_loadFile(ptr: UnsafeMutablePointer<WebViewController>, name: UnsafePointer<CChar>, ext: UnsafePointer<CChar>, directory: UnsafePointer<CChar>) {
	ptr.pointee.loadFile(name: String.init(cString: name), ext: String.init(cString: ext), directory: String.init(cString: directory))
}

@_cdecl("WebView_evaluateJavaScript")
func WebView_evaluateJavaScript(ptr: UnsafeMutablePointer<WebViewController>, data: UnsafePointer<CChar>) {
	ptr.pointee.evaluateJavaScript(data: String.init(cString: data))
}

@_cdecl("WebView_addListener")
func WebView_addListener(ptr: UnsafeMutablePointer<WebViewController>, listener: Int64) {
	ptr.pointee.addListener(listener: listener)
}

@_cdecl("WebView_removeListener")
func WebView_removeListener(ptr: UnsafeMutablePointer<WebViewController>, listener: Int64) {
	ptr.pointee.removeListener(listener: listener)
}

@_cdecl("WebView_clearAllListeners")
func WebView_clearAllListeners(ptr: UnsafeMutablePointer<WebViewController>) {
	ptr.pointee.clearAllListeners()
}

@_cdecl("WebView_execJavascriptFunction")
func WebView_execJavascriptFunction(ptr: UnsafeMutablePointer<WebViewController>, functionName: UnsafePointer<CChar>, b64EncodedParameters: UnsafePointer<CChar>) {
	ptr.pointee.execJavaScriptFunction(functionName: String.init(cString: functionName), b64EncodedParameters: String.init(cString: b64EncodedParameters))
}

@_cdecl("WebView_addOpenExternally")
func WebView_addOpenExternally(ptr: UnsafeMutablePointer<WebViewController>, urlStartsWith: UnsafePointer<CChar>) {
	ptr.pointee.addOpenExternally(urlStartsWith: String.init(cString: urlStartsWith))
}
