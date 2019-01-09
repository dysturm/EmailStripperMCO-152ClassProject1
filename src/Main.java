

import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    final static String DEFAULT_HOST = "pop.aol.com";
    final static String DEFAULT_USERNAME = "dysturm99@aol.com";
    final static String DEFAULT_PASSWORD = "**********";
    final static String DEFAULT_TARGET_MESSAGE_SUBJECT = "fwd: c";
    final static String DEFAULT_ROOT_FOLDER = "C:\\Users\\dystu\\Documents\\";
    final static String HTML_HEADER = "<!DOCTYPE html [\n" +
            "    <!ENTITY nbsp \"&#160;\"> \n" +
            "]>";
    static ArrayList<String> convertedFiles = new ArrayList<>();
    static ArrayList<String> filesWithErrors = new ArrayList<>();

    public static void main (String[]args) throws Exception {

        Font msgFont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
        UIManager.put("OptionPane.messageFont", msgFont);
        UIManager.put("OptionPane.buttonFont", msgFont);

        //allow user to choose app version
        String calcVersion = (String) JOptionPane.showInputDialog(null,
                "Please select the app version:",
                "Select Version",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Version.DISPLAY_OPTIONS,
                Version.DISPLAY_OPTIONS[1]);

        if (calcVersion != null) {
            if (calcVersion.equals(Version.GUI))
                runGUIVersion();
            else if (calcVersion.equals(Version.CONSOLE)) {
                int useDefaults = JOptionPane.showConfirmDialog(null,
                        "Would you like to use default connection settings?",
                        "Select Preferences",
                        JOptionPane.YES_NO_OPTION);
                runConsoleVersion(useDefaults == JOptionPane.YES_OPTION);
            }
        }
    }

    private static void runGUIVersion() {
        new GUIController();
    }

    private static void runConsoleVersion(boolean useDefaults) throws Exception {
        String host, username, password, rootFolder, targetSubject;
        if (useDefaults) {
            host = DEFAULT_HOST; username = DEFAULT_USERNAME; password = DEFAULT_PASSWORD; rootFolder = DEFAULT_ROOT_FOLDER; targetSubject = DEFAULT_TARGET_MESSAGE_SUBJECT;
        }
        else {
            host = getInput("Enter host url:");
            username = getInput("Enter username:");
            password = getInput("Enter password:");
            rootFolder = getInput("Enter path of containing folder:");
            targetSubject = getInput("Enter Target Message Subject:");
        }
        System.out.println("Establishing Connection...");
        PopEmailClient.openServerConnection(host, username, password);
        System.out.println("Connection Established.");

        Message[] messages = PopEmailClient.getMessages(Folder.READ_WRITE);
        System.out.println(messages.length + " messages downloaded.");

        System.out.println("Converting Messages with subject '" + targetSubject + "'...");

        int count = 0;

        for (int i = 0; i < messages.length; i++) {
            if (messages[i].getSubject().equalsIgnoreCase(targetSubject)) {
                System.out.println("Converting message #" + i);
                String plainText = PopEmailClient.getTextFromMessage(messages[i]);
                String htmlText = HTML_HEADER + PopEmailClient.getHTMLFromMessage(messages[i]);

                String fileName = DocumentHandler.determineFileName(plainText, htmlText, rootFolder);

                htmlText = htmlText.replace("Arial", "Times New Roman");
                //replace &nbsp; with regular space chars
                //htmlText = htmlText.replace("&nbsp;", " ");
                Docx4jConverter.convertHTMLStringToMSWordDocx(htmlText, rootFolder, fileName);

                messages[i].setFlag(Flags.Flag.SEEN, true);

                //check if file name was successfully created
                if (fileName.contains("PATIENT NAME NOT FOUND"))
                    filesWithErrors.add(fileName);
                else
                    convertedFiles.add(fileName);

                count++;
            }
        }

        System.out.println("Finished converting " + count + " message(s).");

        PopEmailClient.closeServerConnection(true);

        System.out.println("Connection Closed.");

        if (convertedFiles.size() > 0) {
            System.out.println("The following files were successfully created:");
            for (String file : convertedFiles)
                System.out.println(rootFolder + file);
        }

        if (filesWithErrors.size() > 0) {
            System.out.println("The following files need attention:");
            for (String file : filesWithErrors)
                System.out.println(rootFolder + file);
        }
    }

    public static String getInput (String msg){
        if (msg != null)
            System.out.println(msg);
        Scanner kby = new Scanner(System.in);
        return kby.nextLine();
    }
}
