package Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ResourceBundle;

public class ParserManager {

    private ResourceBundle texts = ResourceBundle.getBundle("Texts");

    public Elements getParserElements(String url, String query) {
        String getRealUrl = getUrl(url);
        if (getRealUrl.isEmpty()) {
            return null;
        }
        else {
            try {
                Document doc = Jsoup.connect(getRealUrl).get();
                Elements data = doc.select(query);
                return data;
            }
            catch (IOException e) {
                return null;
            }
        }
    }

    public String getUrl(String url) {
        if (!url.trim().isEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {
            return url.trim();
        }
        else if (!url.trim().isEmpty()) {
            return "http://" + url.trim();
        }
        else {
            return "";
        }
    }

    public String getLinks(String url) {
        String result = "";
        if (getParserElements(url, "a[href]") != null) {
            for (Element link : getParserElements(url, "a[href]")) {
                result += link.absUrl("href") + "\n" +
                        (link.text().length() == 0 ? link.html() : link.text()) + "\n\n";
            }
        }
        else {
            result = texts.getString("noData");
        }
        return result;
    }

    public String getImages(String url) {
        String result = "";
        if (getParserElements(url, "img[src]") != null) {
            for (Element img : getParserElements(url, "img[src]")) {
                result += img.absUrl("src") + "\n\n";
            }
        }
        else {
            result = texts.getString("noData");
        }
        return result;
    }

    public Set<String> getImagesList(String url) {
        Set<String> imgList = new HashSet<>();
        if (getParserElements(url, "img[src]") != null) {
            for (Element img : getParserElements(url, "img[src]")) {
                if (!img.absUrl("src").isEmpty()) {
                    imgList.add(img.absUrl("src"));
                }
            }
        }
        return imgList;
    }

    public String getHtml(String url) {
        String result = "";
        if (getParserElements(url, "html") != null) {
            for (Element html : getParserElements(url, "html")) {
                result = "<html>" + html.html() + "</html>";
            }
        }
        else {
            result = texts.getString("noData");
        }
        return result;
    }

    public String getHtml(String url, String tag) {
        String result = "";
        if (getParserElements(url, tag) != null) {
            for (Element html : getParserElements(url, tag)) {
                result += html.text() + "\n\n";
            }
        }
        else {
            result = texts.getString("noData");
        }
        return result;
    }

}