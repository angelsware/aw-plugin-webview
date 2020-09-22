import WebKit

class WebViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        print(message.body) // TODO: Pass to listeners.
        listeners.forEach { listener in
            
        }
    }
    
	@IBOutlet var webView: WKWebView!
    let listeners: Set<Int64> = Set()
	
	override func viewDidLoad() {
		super.viewDidLoad()
		let webConfig = WKWebViewConfiguration()
        let userContentController = WKUserContentController()
        userContentController.add(self, name: "native")
        webConfig.userContentController = userContentController
		webView = WKWebView(frame: .zero, configuration: webConfig)
		webView.navigationDelegate = self
		webView.uiDelegate = self
		view = webView
	}

	func loadUrl(url: String) {
		webView.load(URLRequest(url: URL(string: url)!))
	}

	func loadData(data: String, mimeType: String, encoding: String) {
		webView.loadHTMLString(data, baseURL: nil)
	}

	func loadFile(name: String, ext: String, directory: String) {
		if let indexURL = Bundle.main.url(forResource: name, withExtension: ext, subdirectory: directory) {
			webView.loadFileURL(indexURL, allowingReadAccessTo: indexURL)
		}
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
