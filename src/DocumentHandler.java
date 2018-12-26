import java.io.File;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentHandler {

    final static Pattern DOC_NAME_PATTERN = Pattern.compile("((?<=Dr\\.? )|(?<=NP ))[a-zA-Z-]+");
    final static Pattern PATIENT_NAME_PATTERN =
            Pattern.compile("(?:(Re[;:] *)|(Patient: *)|(PATIENT: *))(?<PatientName>[a-zA-Z-]+( [a-zA-Z]+\\.?)? *,? *[a-zA-Z-]+)");
//    final static Pattern PATIENT_NAME_PATTERN =
//            Pattern.compile("(?:(Re[;:] *)|(Patient: *)|(PATIENT: *))(?<PatientName>[a-zA-Z-]+( [a-zA-Z]+\\.?)? *,? *[a-zA-Z-]+( [a-zA-Z-]+)?)");
    final static Pattern PATIENT_NAME_FROM_HTML_PATTERN =
            Pattern.compile("<b>.*> *(?<PatientName>[a-zA-Z-]+( [a-zA-Z]+\\.?)? *, +[a-zA-Z-]+( [a-zA-Z-]+)?) *<.*<\\/b>");

    public static String determineFileName (String documentText, String htmlText, String rootFolder) {
        String fileName;

        //determine doc type for beg of filename
        fileName = getHeader(documentText);

        //get patient name for filename
        String patientName = getPatientName(documentText);

        //check if patient name successfully found; if not, try to get it from html (look in <b> tag)
        if (patientName.isEmpty())
            patientName = findPatientNameFromHTML(htmlText);

        if (!patientName.isEmpty())
            fileName += patientName;
        else {
            fileName += "PATIENT NAME NOT FOUND";
        }



        //check if filename already exists; if yes, add correct number to filename
        fileName = checkForPrevious(fileName, rootFolder);

        return fileName + ".docx";
    }

    protected static String findPatientNameFromHTML(String htmlText) {
        String patientName = "";

        Matcher matcher = PATIENT_NAME_FROM_HTML_PATTERN.matcher(htmlText);

        if (matcher.find())
            patientName = matcher.group("PatientName");

        return patientName;
    }

    protected static String checkForPrevious(String fileName, String root) {
        int counter = 2;

        File file = new File(root + fileName);

        //check if filename already exists
        if (file.exists()) {

            //first check if subsequent files are saved without the '#' symbol
            file = new File(root + fileName + " " + counter);

            if (file.exists()) {

                //if they are, find the next available number
                do {
                    file = new File(root + fileName + " " + ++counter);
                } while (file.exists());

                fileName = fileName + " " + counter;
            }

            //if not, then proceed to save file with the '#' symbol, at the next available number
            else {
                file = new File(root + fileName + " #" + counter);

                if (file.exists()) {
                    do {
                        file = new File(root + fileName + " #" + ++counter);
                    } while (file.exists());
                }

                fileName = fileName + " #" + counter;
            }
        }

        return fileName;
    }

    protected static String getPatientName(String documentText) {
        String patientName = "";

        Matcher matcher = PATIENT_NAME_PATTERN.matcher(documentText);

        //retrieve patient namegroup from the matcher (since pattern has non-capture groups)
        if (matcher.find())
            patientName = matcher.group("PatientName");

        return patientName;
    }

    protected static String getPatientNameBySplitting(String documentText) {
        String patientName = "";

        String[] parts = documentText.split(" +");

        for (int i = 0; i < parts.length; i++) {
            if (SearchTags.PATIENT_NAME_START_TAGS.contains(parts[i]))
                //max 4 parts for name
                for (int j = i; j < i+4; j++) {
                    patientName += parts[j];
                    //when end tag hit, break out of loops
                    if (SearchTags.PATIENT_NAME_END_TAGS.contains(parts[j+1])) {
                        j = i+4;
                        i = parts.length;
                    }
                }
        }

        return patientName;
    }

    private static String getHeader (String documentText) {
        String fileName = "";

        if (documentText.contains("Room:"))
            fileName = FileNameHeaders.GARDENCARE;
        else if (documentText.contains("DISABILITY"))
            fileName = FileNameHeaders.DISABILITY;
        else if (documentText.contains("PROCEDURE NOTE"))
            fileName = FileNameHeaders.PROCEDURE_NOTE;
        else if (documentText.contains("Medical Note") || documentText.contains("MEDICAL NOTE"))
            fileName = FileNameHeaders.MEDICAL_NOTE;
        else if (documentText.contains("Dr.")) {
            fileName = FileNameHeaders.LETTER;
            String docName = getDoctor(documentText);
            if (!docName.isEmpty())
                fileName += docName + "-";
        }
        else if (documentText.contains("bill"))
            fileName = FileNameHeaders.BILL;
        else
            fileName = FileNameHeaders.MEDICAL_NOTE;

        return fileName;
    }

    protected static String getDoctor(String documentText) {
        String docName = "";

        Matcher matcher = DOC_NAME_PATTERN.matcher(documentText);

        if (matcher.find())
            docName = matcher.group();

        return docName.toUpperCase();
    }
}
