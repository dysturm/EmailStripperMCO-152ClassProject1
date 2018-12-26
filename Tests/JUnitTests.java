import org.junit.jupiter.api.Test;

import javax.mail.Folder;
import javax.mail.Message;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


public class JUnitTests {

    //test if email connection can be successfully opened and content retrieved
    @Test
    void PopEmailClientTest() throws Exception {

        //test if connection can be opened
        assertTrue(PopEmailClient.openServerConnection("pop.aol.com", "dysturm99@aol.com", "money9999"));
        System.out.println("connected to server");

        //get messages
        Message[] messages = PopEmailClient.getMessages(Folder.READ_ONLY);
        int messgeNum = 5;

        //get msg text+html
        String messageHTML = PopEmailClient.getHTMLFromMessage(messages[messgeNum]);
        String messageText = PopEmailClient.getPlainTextFromMessage(messages[messgeNum]);

        //print info of specific msg
        System.out.println("subject: " + messages[messgeNum].getSubject());
        System.out.println("Length: " + messageText.length() + "\nMessage: " + messageText);

        assertNotNull(messageText);
        assertNotNull(messageHTML);
    }

    //this test just confirms that converter successfully created MS Word doc
    @Test
    void Docx4JConverterTest() throws Exception {
        String root = "C:\\Users\\dystu\\Documents\\";
        String fileName = "javaTestDoc.docx";

        File file = new File(root + fileName);

        if (file.exists())
            file.delete();

        Docx4jConverter.convertHTMLStringToMSWordDocx("<html><div style=\"text-align:center;color:red\"><b>hello, world</b></div></html>", root, fileName);

        File newdoc = new File(root + fileName);

        assertTrue(newdoc.exists());

    }

    //test if doctor's name can be found in text
    @Test
    void docNameTest() {
        String docText = "Patient: Joe Smith\n" +
                "Dear Dr Kingasia,\n" +
                "blah blah  NP Baron-kole klk";

        String docName = DocumentHandler.getDoctor(docText);

        assertEquals("KINGASIA", docName);

        docText = "Patient: Joe Smith\n" +
                "Dear MR. Kingasia,\n" +
                "blah blah  NP Baron-kole klk";

        docName = DocumentHandler.getDoctor(docText);

        assertEquals("BARON-KOLE", docName);
    }

    //test if patient's name can be found in text
    @Test
    void patientNameTest() {
        String docText = "Patient: Joe Smith\n" +
                "Dear Dr. Kingasia,\n" +
                "blah blah  NP Baron-kole klk";

        String patientName = DocumentHandler.getPatientName(docText);

        assertEquals("Joe Smith", patientName);

        String expectedName = "Smith JR., Joe";

        docText = "hskdfj" +
                "Re:    "+ expectedName + "\n" +
                "dsflkjsd , dsff";

        patientName = DocumentHandler.getPatientName(docText);

        assertEquals(expectedName, patientName);


        expectedName = "JORGE L TORRES";

        docText = "Patient:  JORGE L TORRES Mod/Unit/Pin:   F/113/3738          DOB: 6/18/79 Order #: A190178                                                               Joseph Sturm, MD                                                                                                     161Atlantic Ave                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         Brooklyn, New York 11201                                                                                                 (718)237-5600   DATE:  ";

        docText = docText.replace(" ", " ");

        patientName = DocumentHandler.getPatientName(docText);

        assertEquals(expectedName, patientName);

    }

    //test if patient's name can be found in html
    @Test
    void patientNameFromHTMLTest () {
        String patientName;

        patientName = DocumentHandler.findPatientNameFromHTML(HTMLString.htmlString);

        assertEquals("Smith, Greg", patientName);
    }

    @Test
    void spaceCharTest() {
        System.out.println((int)" ".charAt(0));

        System.out.println((int)" ".charAt(0));

        assertEquals("  ", "  ");
    }
}
