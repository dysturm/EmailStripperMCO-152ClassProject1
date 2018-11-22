import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;

import java.util.List;


public class Docx4jConverter {

    private static final Logger logger = LogManager.getLogger(Docx4jConverter.class);

    static {
        //turn off console logging of log4j
        Logger.getRootLogger().setLevel(Level.OFF);
        logger.setLevel(Level.OFF);

    }

    public static boolean convertHTMLStringToMSWordDocx (String htmlString,String rootFolder, String fileName) throws Exception {

        Logger.getRootLogger().setLevel(Level.OFF);
        logger.setLevel(Level.OFF);
        String baseURL = "file:///C:/Users/dystu/Documents/";

        //print out original html string
        //System.out.println("HTML: " + htmlString);

        // Setup font mapping
        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
        rfonts.setAscii("Times New Roman");
        rfonts.setCs("Times New Roman");
        XHTMLImporterImpl.addFontMapping("Times New Roman", rfonts);

        // Create an empty docx package
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        // Convert the XHTML, and add it into the empty docx we made
        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

        //add html string into word template
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(htmlString, baseURL) );


        // Get paragraphs
        final String XPATH_TO_SELECT_TEXT_NODES = "//w:p";
        final List<Object> jaxbNodes = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath(XPATH_TO_SELECT_TEXT_NODES, true);

        //remove all empty paragraphs
        for (int i = 0; i < jaxbNodes.size(); i++) {

            //to determine class type of node
            //System.out.println(jaxbNodes.get(i).getClass());

            if (jaxbNodes.get(i).toString().trim().isEmpty()){
                org.docx4j.wml.P p = (org.docx4j.wml.P) jaxbNodes.get(i);

                org.docx4j.wml.Body parent = (org.docx4j.wml.Body) p.getParent();

                parent.getContent().remove(p);
            }
        }

        //print rendered xhtml in word-html-format
        //System.out.println(
        //        XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true));

        //create new word docx
        wordMLPackage.save(new java.io.File(rootFolder + fileName) );

        return true;
    }

}


