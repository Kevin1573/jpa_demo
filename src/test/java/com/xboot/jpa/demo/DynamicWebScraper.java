package com.xboot.jpa.demo;

import com.google.common.util.concurrent.Service;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.Duration;


public class DynamicWebScraper {
    public static void main(String[] args) {
        // 设置 ChromeDriver 的路径
        System.setProperty("webdriver.chrome.driver", "D:/devtools/chrome-win64/chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("D:/devtools/chrome-win64/chrome.exe"))
                .withTimeout(Duration.ofSeconds(60)) // 设置超时时间为 60 秒
                .build();
        // 创建 WebDriver 实例
//        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        WebDriver driver = new ChromeDriver(service, options);
        try {
            // 访问指定的 URL
            String url = "https://www.toutiao.com/article/7401154467426599433/?app=news_article&use_new_style=1&is_hit_share_recommend=0&wxshare_count=1&tt_from=weixin&utm_source=weixin&utm_medium=toutiao_android&utm_campaign=client_share&share_token=fd3b7f00-5956-440e-8abb-34aa55ff787b&source=m_redirect&wid=1726930905038";
            driver.get(url);

            // 等待页面加载完成
            Thread.sleep(5000); // 等待5秒，可根据实际情况调整

            // 获取页面源码
            String htmlContent = driver.getPageSource();

            // 使用 jsoup 解析页面
            Document doc = Jsoup.parse(htmlContent);

            // 提取页面元数据
            Elements titles = doc.select("title");
            if (!titles.isEmpty()) {
                Element titleElement = titles.first();
                System.out.println("Title: " + titleElement.text());
            }

            Elements metaTags = doc.select("meta[name]");
            for (Element metaTag : metaTags) {
                String name = metaTag.attr("name");
                String content = metaTag.attr("content");
                System.out.println("Meta Tag: " + name + " -> " + content);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭 WebDriver
            driver.quit();
        }
    }
}
