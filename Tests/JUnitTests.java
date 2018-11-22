import org.docx4j.org.xhtmlrenderer.util.XRLog;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JUnitTests {

    //test if email connection can be successfully opened
    @Test
    void PopEmailClientTest(){

        assertTrue(PopEmailClient.openServerConnection("pop.aol.com", "dysturm99@aol.com", "*******"));


    }

    //this test just confirms that converter successfully created MS Word doc
    @Test
    void Docx4JConverterTest() throws Exception {
        String root = "C:\\Users\\dystu\\Documents\\";
        String fileName = "javaTestDoc.docx";

        File file = new File(root + fileName);

        if (file.exists())
            file.delete();

        Docx4jConverter.convertHTMLStringToMSWordDocx("<html><div style=\"text-align:center;\">hello, world</div></html>", root, fileName);

        File newdoc = new File(root + fileName);

        assertTrue(newdoc.exists());

    }

    @Test
    void fileNameTest() {
        String docText = "Dr. kingasia";

        assertTrue(docText.contains("Dr."));
    }
}
