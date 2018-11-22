public class DocumentHandler {

    public static String determineFileName (String documentText) {
        String fileName;

        fileName = getHeader(documentText);

        fileName += getPatientName(documentText);



        return fileName;
    }

    private static String getPatientName(String documentText) {
        return "";
    }

    private static String getHeader(String documentText) {
        String fileName = "";
        if (documentText.contains("medical note"))
            fileName = FileNameHeaders.MEDICAL_NOTE;
        else if (documentText.contains("dr.")) {
            fileName = FileNameHeaders.LETTER;
            fileName += getDoctor(documentText);
        }
        else if (documentText.contains("bill"))
            fileName = FileNameHeaders.BILL;
        else if (documentText.contains("garden care"))
            fileName = FileNameHeaders.GARDENCARE;
        else if (documentText.contains("disability"))
            fileName = FileNameHeaders.DISABILITY;
        else
            fileName = FileNameHeaders.MEDICAL_NOTE;

        return fileName;
    }

    private static String getDoctor(String documentText) {
        return "";
    }
}
