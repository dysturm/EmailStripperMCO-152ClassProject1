import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

public class MSWordDocumentCreator {
    public static void CreateNewDoc(String fileName, String docText)throws Exception  {

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream( new File(fileName + ".docx"));

        //create Paragraph
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(docText);

        document.write(out);
        out.close();
        System.out.println(fileName + ".docx written successully");
    }

    public static void removeLineBreaks (String oldFilePath, String newFilePath) throws Exception {
        File oldDoc = new File(oldFilePath);
        FileInputStream inFile = new FileInputStream(oldDoc);

        XWPFDocument doc = new XWPFDocument(OPCPackage.open(inFile));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(" ")) {
                        text = text.replace(" ", "");
                        r.setText(text, 0);
                    }
                }
            }
        }
/*        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains("needle")) {
                                text = text.replace("needle", "haystack");
                                r.setText(text,0);
                            }
                        }
                    }
                }
            }
        }*/
        doc.write(new FileOutputStream("output.docx"));
    }
}
