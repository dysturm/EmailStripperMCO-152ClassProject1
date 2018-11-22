import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;

public class PopEmailClient {

    static Store store;
    static Folder emailFolder;

    public static boolean openServerConnection (String host, String user,
                                             String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.ssl.trust", "");
            //properties.put("mail.debug", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            return true;
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Message[] getMessages (int folder) throws MessagingException {
        emailFolder = store.getFolder("INBOX");
        emailFolder.open(folder);
        return emailFolder.getMessages();
    }

    public static void closeServerConnection (boolean useExpunge) throws MessagingException {
        emailFolder.close(useExpunge);
        store.close();
    }



    public static void check(String host, String storeType, String user,
                             String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.ssl.trust", "");
            //properties.put("mail.debug", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            System.out.println("about to connect to host...");

            store.connect(host, user, password);

            System.out.println("successfully connected to host");

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                //System.out.println("Text: " + message.getContent().toString());
                System.out.println("Type: " + message.getContentType());

                //MimeMultipart mmp = (MimeMultipart)message.getContent();

                System.out.println("Content: " + getTextFromMessage(message));


            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPlainTextFromMessage (Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = mimeMultipart.getBodyPart(0).getContent().toString();
        }
        return result;
    }

    public static String getBodyPartTextFromMessage (Message message, String typeCID) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = (String) mimeMultipart.getBodyPart(typeCID).getContent();
        }
        return result;
    }


    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            //System.out.println("Content is text/plain");
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            //System.out.println("Content is multipart");
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/html")) {
                //System.out.println("Body Part #"+ (i + 1) + " is type text/html");
                String html = (String) bodyPart.getContent();
                result = html;
                //result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                //System.out.println("Body Part #"+ (i + 1) + " is instance of MimeMultipart");
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    public static String getEmailTextForSpecificMessage(int emailNum) {
        String host = "pop.aol.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "dysturm99@aol.com";// change accordingly
        String password = "**********";// change accordingly

        return getEmailText(host, mailStoreType, username, password, emailNum);
    }

    public static String getNextEmail(){
        return "";
    }

    public static String getEmailText(String host, String storeType, String user,
                             String password, int emailNum)
    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.ssl.trust", "");
            //properties.put("mail.debug", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            Message message = messages[emailNum-1];
            String text = getTextFromMessage(message);


            //close the store and folder objects
            emailFolder.close(false);
            store.close();

            return text;

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {

        String host = "pop.aol.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "dysturm99@aol.com";// change accordingly
        String password = "************";// change accordingly

        check(host, mailStoreType, username, password);

    }

}