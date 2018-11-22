import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.TagNode;

public class HTMLStringCleaner {
    public static String cleanHTML (String html) {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        TagNode node = cleaner.clean(html);

        StringBuilder stringBuilder = (StringBuilder)node.getText();

        return stringBuilder.toString();
    }
}
