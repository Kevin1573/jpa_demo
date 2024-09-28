package com.xboot.jpa.demo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 注释
 *
 * @author xboot
 **/
 class WebsiteMetaTest {

    @Test
     void getWebsiteMetaInfo() throws IOException {
        Document document = Jsoup.parse(new URL("https://github.com/atlassian/pragmatic-drag-and-drop"), 30000);
        Elements elements = document.head().getElementsByTag("meta");
        WebsiteMeta websiteMeta = new WebsiteMeta.WebsiteMetaBuilder()
                .title(document.title())
                .url(document.location())
                .build();
        for (org.jsoup.nodes.Element element : elements) {
            if ("twitter:description".equals(element.attr("name"))) {
                websiteMeta.setDescription(element.attr("content"));
            }
            if ("og:image".equals(element.attr("property"))) {
                websiteMeta.setOgImage(element.attr("content"));
            }
            if ("og:description".equals(element.attr("property"))) {
                websiteMeta.setOgDescription(element.attr("content"));
            }
        }
        System.out.println(websiteMeta);

        assertEquals(200, document.connection().response().statusCode());
    }

    @Test
    void getWebsiteMetaInfoToTouTiao() throws IOException {
        Document document = Jsoup.parse(new URL("https://www.toutiao.com/article/7401154467426599433/?app=news_article&use_new_style=1&is_hit_share_recommend=0&wxshare_count=1&tt_from=weixin&utm_source=weixin&utm_medium=toutiao_android&utm_campaign=client_share&share_token=fd3b7f00-5956-440e-8abb-34aa55ff787b&source=m_redirect&wid=1726930905038"), 30000);
        System.out.println(document);
        Elements elements = document.head().getElementsByTag("meta");
        WebsiteMeta websiteMeta = new WebsiteMeta.WebsiteMetaBuilder()
                .title(document.title())
                .url(document.location())
                .build();
        for (org.jsoup.nodes.Element element : elements) {
            if ("keywords".equals(element.attr("name"))) {
                websiteMeta.setKeywords(element.attr("content"));
            }
            if ("description".equals(element.attr("name"))) {
                websiteMeta.setDescription(element.attr("content"));
            }

        }
        Elements linkElements = document.head().getElementsByTag("link");
        for (Element linkElement : linkElements) {
            if ("shortcut icon".equals(linkElement.attr("rel"))) {
                websiteMeta.setShortcutIcon(linkElement.attr("href"));
            }
        }

        System.out.println(websiteMeta);

        assertEquals(200, document.connection().response().statusCode());
    }

    @Builder
    @Data
    @ToString
    static class WebsiteMeta {
        private String title;
        private String keywords;
        private String description;
        private String shortcutIcon;
        private String ogImage;
        private String ogDescription;
        private String url;
    }
}
