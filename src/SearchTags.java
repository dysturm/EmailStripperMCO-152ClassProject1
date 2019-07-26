import java.util.ArrayList;

public class SearchTags {
    protected static final ArrayList<String> PATIENT_NAME_START_TAGS = new ArrayList<>();
    protected static final ArrayList<String> PATIENT_NAME_END_TAGS = new ArrayList<>();
    protected static final ArrayList<String> DOC_NAME_START_TAGS = new ArrayList<>();
    protected static final ArrayList<String> DOC_NAME_END_TAGS = new ArrayList<>();


    static {
        setPatientStartTags();
        setPatientEndTags();
    }

    private static void setPatientStartTags() {
        PATIENT_NAME_START_TAGS.add("Re:");
        PATIENT_NAME_START_TAGS.add("Re;");
        PATIENT_NAME_START_TAGS.add("Patient:");
        PATIENT_NAME_START_TAGS.add("Re;");
    }

    private static void setPatientEndTags() {

    }

}
