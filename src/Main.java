

import javax.mail.*;
import java.io.File;

public class Main {

    static String host = "pop.aol.com";
    static String username = "dysturm99@aol.com";
    static String password = "***********";
    static String targetMessageSubject = "c";
    static String rootFolder = "C:\\Users\\dystu\\Documents\\";

    public static void main (String[]args) throws Exception {

        //actual production code
        String htmlHeader = "<!DOCTYPE html [\n" +
                "    <!ENTITY nbsp \"&#160;\"> \n" +
                "]>";

/*        PopEmailClient.openServerConnection(host, username, password);

        Message[] messages = PopEmailClient.getMessages(Folder.READ_WRITE);

        for (int i = 0; i < messages.length; i++) {
            if (messages[i].getSubject().equalsIgnoreCase(targetMessageSubject)) {
                String plainText = PopEmailClient.getBodyPartTextFromMessage(messages[i], "text/plain");

                String fileName = DocumentHandler.determineFileName(plainText);

                String htmlText = htmlHeader + PopEmailClient.getBodyPartTextFromMessage(messages[i], "text/html");
                htmlText = htmlText.replace("&nbsp;", " ");
                Docx4jConverter.convertHTMLStringToMSWordDocx(htmlText, rootFolder, fileName);

                messages[i].setFlag(Flags.Flag.SEEN, true);
            }
        }

        PopEmailClient.closeServerConnection(true);*/



        //end production code



        //test code

        //delete old test file
        File mFile = new File(rootFolder + "OUT_from_XHTML3.docx");
        if (mFile.exists())
            mFile.delete();

        //String text = PopEmailClient.getEmailTextForSpecificMessage(7);
        String text = HTMLString.htmlString;
        //text = HTMLStringCleaner.cleanHTML(text);
        //MSWordDocumentCreator.CreateNewDoc("testDoc", text);

        //text = htmlHeader + text;

        //text = text.replace("&nbsp;", " ");
        //text = text.replace("arial", "Times New Roman");
        //text = text.replace("span", "div");
        //text = text.replace("\n", "");
        //System.out.println(text.replaceAll("<div style=\"font-family:arial,helvetica;font-size:10pt;color:black\">",
         //       "<div style=\"font-family:Times New Roman;font-size:10pt;color:black\">"));

        Docx4jConverter.convertHTMLStringToMSWordDocx(text, rootFolder, "OUT_from_XHTML3.docx");

/*        MSWordDocumentCreator.removeLineBreaks("C:\\Users\\dystu\\Documents\\OUT_from_XHTML3",
                "C:\\Users\\dystu\\Documents\\OUT_from_XHTML4");*/
    }
}
