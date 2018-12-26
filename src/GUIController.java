import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIController extends JFrame {

    //default inputs
    final String HOST = "pop.aol.com";
    final String EMAIL = "dysturm99@aol.com";
    final String PASSWORD = "money9999";
    final String TARGET_MESSAGE_SUBJECT = "c";

    //other inputs
    final String ROOT_FOLDER = "C:\\Users\\dystu\\Documents\\";
    final String HTML_HEADER = "<!DOCTYPE html [\n" +
            "    <!ENTITY nbsp \"&#160;\"> \n" +
            "]>";

    //variables
    Message[] messages;
    ArrayList<String> filesWithErrors = new ArrayList<>();


    //fields for first screen

    //Fields for input + labels
    JTextField email = new JTextField(20);
    JTextField password = new JTextField(20);
    JTextField host = new JTextField(20);
    JTextField targetMessageSubject = new JTextField(20);

    JLabel emailLabel = new JLabel("Email Adress: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JLabel hostLabel = new JLabel("Host: ");
    JLabel targetMessageSubjectLabel = new JLabel("Target Message Subject: ");

    //other fields
    JLabel status = new JLabel("");
    JButton downloadEmailsBtn = new JButton("Download Emails");


    //fields for second screen

    JLabel emailMessagesStatus = new JLabel();
    JButton convertToDocx = new JButton("Convert To MS Word");


    public static void main (String[]args) {
        new GUIController();
    }

    public GUIController () {
        super("Medical Note Email Downloader");

        initDesign();
    }

    private void initDesign() {

        Container c = getContentPane();
        c.setBackground(Color.gray);
        setSize(800, 800);
        setLayout(new FlowLayout());

        Font font = new Font("Times New Roman", Font.BOLD, 40);

        email.setText(EMAIL);
        email.setFont(font);
        email.setHorizontalAlignment(JTextField.CENTER);
        emailLabel.setFont(font);
        password.setText(PASSWORD);
        password.setFont(font);
        password.setHorizontalAlignment(JTextField.CENTER);
        passwordLabel.setFont(font);
        host.setText(HOST);
        host.setFont(font);
        host.setHorizontalAlignment(JTextField.CENTER);
        hostLabel.setFont(font);
        targetMessageSubject.setText(TARGET_MESSAGE_SUBJECT);
        targetMessageSubject.setFont(font);
        targetMessageSubject.setHorizontalAlignment(JTextField.CENTER);
        targetMessageSubjectLabel.setFont(font);
        status.setFont(font);
        status.setForeground(Color.red);
        downloadEmailsBtn.setFont(font);

        emailMessagesStatus.setFont(font);
        convertToDocx.setFont(font);


        add(emailLabel);
        add(email);
        add(passwordLabel);
        add(password);
        add(hostLabel);
        add(host);
        add(targetMessageSubjectLabel);
        add(targetMessageSubject);
        add(downloadEmailsBtn);
        add(status);



        downloadEmailsBtn.addActionListener(actionEvent -> {
            boolean serverConnected;

            //open server connection
            System.out.println("Connecting to server...");
            status.setText("Connecting to server...");
            serverConnected = PopEmailClient.openServerConnection(host.getText(), email.getText(), password.getText());

            if (serverConnected) {

                System.out.println("Connected to server.  \nDownloading Messages...");
                status.setText("<html><body>Connected to server.<br/>Downloading Messages...</body></html>");
                repaint();
                //download messages and count msgs + msgs that match target subject
                try {
                    messages = PopEmailClient.getMessages(Folder.READ_WRITE);
                    System.out.println("Downloaded Messages.");
                    int messageWithTargetSubjectCount = 0;
                    for (Message message : messages){
                        if (message.getSubject().equalsIgnoreCase(targetMessageSubject.getText()))
                            messageWithTargetSubjectCount++;
                    }
                    emailMessagesStatus.setText("<html><body style='text-align:center;'>" +
                            messages.length + " emails were downloaded.<br/>" +
                            "<span style='color:green;'>" + messageWithTargetSubjectCount +
                            "</span> email(s) match target subject '" +
                            "<span style='color:green;'>" + targetMessageSubject.getText() +
                            "</span>'</body></html>");
                } catch (Exception ex){
                    System.out.println("exception caught by calling method");
                    emailMessagesStatus.setText(ex.getMessage());
                }

                //remove jframe content and background layout
                getContentPane().removeAll();
                getContentPane().revalidate();

                //add fields for new screen
                add(emailMessagesStatus);
                add(convertToDocx);

                getContentPane().repaint();

            }
            //if server connection fails to open
            else {
                status.setText("Unable to connect to server");
            }

        });

        convertToDocx.addActionListener(actionEvent -> {
            boolean conversionSuccessful = true;

            for (int i=0; i < messages.length; i++) {
                try {
                    if (messages[i].getSubject().equalsIgnoreCase(targetMessageSubject.getText())) {
                        emailMessagesStatus.setText("Found match at message " + i);

                        //get email content
                        String plainText = PopEmailClient.getPlainTextFromMessage(messages[i]);
                        System.out.println("TEXT: " + plainText);
                        String htmlText = HTML_HEADER + PopEmailClient.getHTMLFromMessage(messages[i]);
                        System.out.println("HTML: " + htmlText);

                        //determine correct file name
                        emailMessagesStatus.setText("Determining file name...");
                        String fileName = DocumentHandler.determineFileName(plainText, htmlText, ROOT_FOLDER);
                        emailMessagesStatus.setText(fileName);

                        //check if file name was successfully created
                        if (fileName.contains("PATIENT NAME NOT FOUND"))
                            filesWithErrors.add(fileName);


                        //replace &nbsp; with regular space chars
                        htmlText = htmlText.replace("&nbsp;", " ");
                        htmlText = htmlText.replace("arial", "Times New Roman");
                        htmlText = htmlText.replace(" ", " ");


                        //convert email to MS Word
                        emailMessagesStatus.setText("Converting " + fileName);
                        Docx4jConverter.convertHTMLStringToMSWordDocx(htmlText, ROOT_FOLDER, fileName);

                        //set flag of email to 'seen'
                        emailMessagesStatus.setText("Setting flag...");
                        messages[i].setFlag(Flags.Flag.SEEN, true);
                    }
                } catch (MessagingException e) {
                    System.out.println("exception caught id #1");
                    emailMessagesStatus.setText("<html><p style='color:red;text-align:center;'>Error Getting Message Content</p></html>");
                    e.printStackTrace();
                    conversionSuccessful = false;
                } catch (Exception e) {
                    System.out.println("exception caught id #2");
                    emailMessagesStatus.setText("<html><p style='color:red;text-align:center;'>Conversion Error</p></html>");
                    e.printStackTrace();
                    conversionSuccessful = false;
                }
            }

            //close email server
            try {
                emailMessagesStatus.setText("Closing server connection...");
                PopEmailClient.closeServerConnection(true);
                emailMessagesStatus.setText("Successfully closed connection");
            } catch (MessagingException e) {
                emailMessagesStatus.setText("<html><p style='color:red;text-align:center;'>Error Closing Connection</p></html>");
                e.printStackTrace();
            }

            //print list of files where patient name not found
            if (filesWithErrors.size() > 0) {
                String listOfFiles = "";
                for (String file : filesWithErrors)
                    listOfFiles += file + "<br/>";
                emailMessagesStatus.setText("<html><body style='text-align:center;color:red;width:500px;white-space:normal;word-wrap: break-word;'>" +
                        "The following files need attention:<br/><span style='font-size:20px'>" + listOfFiles + "</span></body></html>");
            }
            else {
                if(conversionSuccessful){
                    emailMessagesStatus.setText("All files successfully converted.");
                }
                else {
                    emailMessagesStatus.setText("Error with conversion.");
                }
            }

            //remove convert button
            getContentPane().remove(convertToDocx);
            repaint();

        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
