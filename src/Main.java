

import org.docx4j.org.xhtmlrenderer.util.XRLog;

import javax.mail.*;
import java.io.File;
import java.util.ArrayList;

public class Main {

    final static String HOST = "pop.aol.com";
    final static String USERNAME = "dysturm99@aol.com";
    final static String PASSWORD = "***********";
    final static String TARGET_MESSAGE_SUBJECT = "c";
    final static String ROOT_FOLDER = "C:\\Users\\dystu\\Documents\\";
    final static String HTML_HEADER = "<!DOCTYPE html [\n" +
            "    <!ENTITY nbsp \"&#160;\"> \n" +
            "]>";
    static ArrayList<String> filesWithErrors = new ArrayList<>();

    public static void main (String[]args) throws Exception {

        //actual production code

        new GUIController();

//        PopEmailClient.openServerConnection(HOST, USERNAME, PASSWORD);
//
//        Message[] messages = PopEmailClient.getMessages(Folder.READ_WRITE);
//
//        for (int i = 0; i < messages.length; i++) {
//            if (messages[i].getSubject().equalsIgnoreCase(TARGET_MESSAGE_SUBJECT)) {
//                String plainText = PopEmailClient.getHTMLFromMessage(messages[i], "text/plain");
//                String htmlText = HTML_HEADER + PopEmailClient.getHTMLFromMessage(messages[i], "text/html");
//
//                String fileName = DocumentHandler.determineFileName(plainText, htmlText, ROOT_FOLDER);
//
//                //check if file name was successfully created
//                if (fileName.contains("PATIENT NAME NOT FOUND"))
//                    filesWithErrors.add(fileName);
//
//
//                //replace &nbsp; with regular space chars
//                htmlText = htmlText.replace("&nbsp;", " ");
//                Docx4jConverter.convertHTMLStringToMSWordDocx(htmlText, ROOT_FOLDER, fileName);
//
//                messages[i].setFlag(Flags.Flag.SEEN, true);
//            }
//        }
//
//        PopEmailClient.closeServerConnection(true);
//
//
//        System.out.println("The following files need attention:");
//        for (String file : filesWithErrors)
//            System.out.println(file);
//


        //end production code



        //test code

        //delete old test file
        File mFile = new File(ROOT_FOLDER + "OUT_from_XHTML3.docx");
        if (mFile.exists())
            mFile.delete();

        //String text = PopEmailClient.getEmailTextForSpecificMessage(7);
        String text = HTMLString.htmlString;
        //text = HTMLStringCleaner.cleanHTML(text);
        //MSWordDocumentCreator.CreateNewDoc("testDoc", text);

        //text = HTML_HEADER + text;

        text = text.replace("&nbsp;", " ");
        //text = text.replace("arial", "Times New Roman");

        Docx4jConverter.convertHTMLStringToMSWordDocx(text, ROOT_FOLDER, "OUT_from_XHTML3.docx");

    }
}
