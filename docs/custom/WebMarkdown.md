# Android Markdown解析
Android平台上的MarkDown有解析库，比较出名的有：pegdown、txtmark、markdown4j(fork自txtmark)。
不过实测发现，pegdown的效果虽好，但依赖其他的包，且导入容易遇到问题。txtmark和markdown4j会出现解析错误的情况。

# 开始
这里我选用了chjj/[marked](https://github.com/chjj/marked)作为解析器，这是一个使用javascript开发的解析库，你懂的。

```
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.wv_main);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        loadDefaultUrl();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String data = getMdData();
                if (data != null) {
                    String call = "javascript:marked(\'" + data + "\')";
                    loadUrl(call);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void loadDefaultUrl() {
        loadUrl("file:///android_asset/index.html");
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    private String getMdData() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            InputStreamReader inputReader = new InputStreamReader(getAssets().open("README.md"));
            br = new BufferedReader(inputReader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\\n");//注意这一行，通常应该是sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
```