package com.rmagent.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.rmagent.app.databinding.FragmentBrowserBinding

class BrowserFragment : Fragment() {

    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    private var webView: WebView? = null
    private var webViewClient: CustomWebViewClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = binding.webView
        webViewClient = CustomWebViewClient()
        webView?.webViewClient = webViewClient!!
        webView?.webChromeClient = WebChromeClient()
        val settings = webView?.settings
        settings?.javaScriptEnabled = true
        settings?.domStorageEnabled = true

        binding.btnGo.setOnClickListener {
            val url = binding.urlInput.text.toString().trim()
            val fullUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
                url
            } else if (url.contains(".") && !url.contains(" ")) {
                "https://$url"
            } else {
                "https://www.google.com/search?q=${android.net.Uri.encode(url)}"
            }
            webView?.loadUrl(fullUrl)
        }

        if (savedInstanceState == null) {
            webView?.loadUrl("https://www.google.com")
            binding.urlInput.setText("https://www.google.com")
        }
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            activity?.runOnUiThread {
                binding.urlInput.setText(url)
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?, request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }

    fun getWebView(): WebView? = webView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
