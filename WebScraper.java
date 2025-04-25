
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    public static void main(String[] args) {
        try {
           final String url = "https://bbc.com";  
            Document doc = Jsoup.connect(url).get();
            
            
            List<NewsArticle> articles = new ArrayList<>();
            
            
            Elements newsElements = doc.select("div.gs-c-promo");

            for (Element news : newsElements) {
                String headline = news.select("h3").text();
                String date = news.select("time").attr("datetime");
                String author = news.select(".gs-c-byline__name").text();

               
            }

            // Print articles
            for (NewsArticle article : articles) {
                System.out.println(article);
            }            
            
            // Title
            System.out.println("Title: " + doc.title());
            
            
        

            // Headings
            for (int i = 1; i <= 6; i++) {
                Elements headings = doc.select("h" + i);
                for (org.jsoup.nodes.Element heading : headings) {
                    System.out.println("H" + i + ": " + heading.text());
                }
            }

            // Links
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println("Link: " + link.attr("abs:href"));
            }
			
           

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public class NewsArticle {
        private String headline;
        private String date;
        private String author;

        public NewsArticle(String headline, String date, String author) {
            this.headline = headline;
            this.date = date;
            this.author = author;
        }

        @Override
        public String toString() {
            return "Headline: " + headline + "\nDate: " + date + "\nAuthor: " + author + "\n";
        }
    }
}
