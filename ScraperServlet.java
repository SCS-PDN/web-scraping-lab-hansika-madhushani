import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/ScrapeServlet")
public class ScrapeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        String[] options = request.getParameterValues("scrapeOption");

        List<Map<String, String>> results = new ArrayList<>();

        if (url != null && options != null) {
            Document doc = Jsoup.connect(url).get();

            for (String option : options) {
                switch (option) {
                    case "title":
                        Map<String, String> titleMap = new HashMap<>();
                        titleMap.put("Type", "Title");
                        titleMap.put("Content", doc.title());
                        results.add(titleMap);
                        break;
                    case "links":
                        Elements links = doc.select("a[href]");
                        for (Element link : links) {
                            Map<String, String> linkMap = new HashMap<>();
                            linkMap.put("Type", "Link");
                            linkMap.put("Content", link.absUrl("href"));
                            results.add(linkMap);
                        }
                        break;
                    case "images":
                        Elements images = doc.select("img[src]");
                        for (Element img : images) {
                            Map<String, String> imgMap = new HashMap<>();
                            imgMap.put("Type", "Image");
                            imgMap.put("Content", img.absUrl("src"));
                            results.add(imgMap);
                        }
                        break;
                }
            }
        }

        // Convert to JSON
        Gson gson = new Gson();
        String json = gson.toJson(results);
        request.setAttribute("jsonData", json);
        request.setAttribute("scrapedData", results);
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }
}
