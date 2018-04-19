package io.backbase.backbasetestassignment.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.backbase.backbasetestassignment.R;

public class HelpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);
        WebView webView = view.findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/html.html");
        webView.setWebViewClient(new WebViewClient());

        return view;
    }
}
