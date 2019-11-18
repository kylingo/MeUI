package com.me.ui.sample.library.util;

/**
 * 网页解析
 *
 * @author tangqi
 * @since 2015年8月9日下午08:09:57
 */
@SuppressWarnings("unused")
public class JsoupUtils {

//    private static final String BLOG_URL = "https://blog.csdn.net";
//
//    public static Object getBlogger(String html) {
//        if (TextUtils.isEmpty(html)) {
//            return null;
//        }
//
//        Document doc = Jsoup.parse(html);
//        String imgUrl;
//        try {
//            imgUrl = doc.getElementsByClass("avatar_pic").attr("src");
//        } catch (Exception e) {
//            e.printStackTrace();
//            imgUrl = "";
//        }
//
//        try {
//            Elements headerElement = doc.getElementsByClass("title-box");
//            String title = headerElement.select("h6").select("a").text();
//            String description = headerElement.select("p").text();
//            if (TextUtils.isEmpty(title)) {
//                return getBlogger2(html);
//            }
//
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return getBlogger2(html);
//    }
//
//    /**
//     * 第2种风格博主
//     */
//    private static Object getBlogger2(String html) {
//        Document doc = Jsoup.parse(html);
//        String imgUrl;
//        try {
//            // 头像
//            imgUrl = doc.getElementsByClass("inf_bar").select("a").select("img").attr("src");
//        } catch (Exception e) {
//            e.printStackTrace();
//            imgUrl = "";
//        }
//
//        try {
//            // 博主名字和个性签名
//            Elements headerElement = doc.getElementsByClass("header-left");
//            String title = headerElement.select("h1").text();
//            String description = headerElement.select("div").select("span").text();
//            if (TextUtils.isEmpty(title)) {
//                return null;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static void getBlogList(String category, String html) {
//        Document doc = Jsoup.parse(html);
//
//        // blog item
//        try {
//            int totalPage = getBlogListPage(doc);
//
//            Elements blogList = doc.getElementsByClass("article-item-box");
//            for (Element blogItem : blogList) {
//                // 不需要展示item
//                if ("display: none;".equals(blogItem.attr("style"))) {
//                    continue;
//                }
//
//                String description = blogItem.getElementsByClass("content").select("a").text();
//                String date = blogItem.getElementsByClass("info-box").get(0).text();
//                String link = blogItem.getElementsByClass("content").select("a").attr("href");
//                if (!TextUtils.isEmpty(link) && !link.contains(BLOG_URL)) {
//                    link = BLOG_URL + link;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static int getBlogListPage(Document doc) {
//        int totalPage = 0;
//        Elements elements = doc.getElementsByClass("page-link");
//        if (elements != null && elements.size() > 1) {
//            Element lastElement = elements.get(elements.size() - 2);
//            try {
//                String lastPage = lastElement.text();
//                totalPage = Integer.parseInt(lastPage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return totalPage;
//    }
//
//    private static int getBlogTotalPage(String page) {
//        int totalPage = 0;
//        if (!TextUtils.isEmpty(page)) {
//            int pageStart = page.lastIndexOf("共");
//            int pageEnd = page.lastIndexOf("页");
//            String pageStr = page.substring(pageStart + 1, pageEnd);
//            try {
//                totalPage = Integer.parseInt(pageStr);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return totalPage;
//    }
//
//    public static String getBlogContent(String html) {
//        if (TextUtils.isEmpty(html)) {
//            return null;
//        }
//
//        try {
//            Element detailElement = Jsoup.parse(html).getElementsByClass("article_content").get(0);
//            detailElement.select("script").remove();
//
//            if (detailElement.getElementById("digg") != null) {
//                detailElement.getElementById("digg").remove();
//            }
//
//            if (detailElement.getElementsByClass("tag2box") != null) {
//                detailElement.getElementsByClass("tag2box").remove();
//            }
//
//            if (detailElement.getElementsByClass("category") != null) {
//                detailElement.getElementsByClass("category").remove();
//            }
//
//            if (detailElement.getElementsByClass("bog_copyright") != null) {
//                detailElement.getElementsByClass("bog_copyright").remove();
//            }
//
//            detailElement.getElementsByClass("article_manage").remove();
//            detailElement.getElementsByTag("h1").tagName("h2");
//            for (Element element : detailElement.select("pre[name=code]")) {
//                element.attr("class", "brush: java; gutter: false;");
//            }
//
//            return detailElement.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return getBlogContent2(html);
//    }
//
//    /**
//     * 第2种风格博客
//     */
//    private static String getBlogContent2(String html) {
//        try {
//            Element detailElement = Jsoup.parse(html).getElementsByClass("article_content").get(0);
//            detailElement.getElementsByClass("article_manage").remove();
//            detailElement.getElementsByTag("h1").tagName("h2");
//            for (Element element : detailElement.select("pre[name=code]")) {
//                element.attr("class", "brush: java; gutter: false;");
//            }
//
//            return detailElement.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String getBlogTitle(String html) {
//        if (TextUtils.isEmpty(html)) {
//            return null;
//        }
//
//        try {
//            Element titleElement = Jsoup.parse(html).getElementsByClass("article_title").get(0);
//            return titleElement.select("h1").select("span").select("a").text();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return getBlogTitle2(html);
//    }
//
//    private static String getBlogTitle2(String html) {
//        if (TextUtils.isEmpty(html)) {
//            return null;
//        }
//
//        try {
//            Element titleElement = Jsoup.parse(html).getElementsByClass("csdn_top").get(0);
//            return titleElement.text();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
