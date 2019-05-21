package se.johanandersson.weeklogger;

import org.apache.poi.xwpf.usermodel.*;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestGenerateWordDocument {

    @Test
    public void testGenerateDiaryFromLogEntries() throws LogEntryValidationException, IOException {
        List<LogEntry> logEntries = genTestLogEntries();
        XWPFDocument document = new XWPFDocument();
        createTitle(document);
        Collections.sort(logEntries);

        for(LogEntry l: logEntries){
            createParagraphFromLogEntry(l,document);
        }

        FileOutputStream out = new FileOutputStream("veckologgaren.docx");
        document.write(out);
        out.close();
        document.close();

    }

    private void createParagraphFromLogEntry(LogEntry l,XWPFDocument document) {
        createSubTitle(document, l.getLogDate()+ " " +l.getStartTime(), false);
        createParagraph(document,l.getComment(), true);
    }

    private void createParagraph(XWPFDocument document, String text, boolean carriageReturn) {
        XWPFParagraph para = document.createParagraph();
        para.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun para1Run = para.createRun();
        para1Run.setFontFamily("Arial");
        para1Run.setFontSize(10);
        para1Run.setText(text);
        if(carriageReturn){
            para1Run.addCarriageReturn();
        }

    }

    private void createSubTitle(XWPFDocument document, String text, boolean carriageReturn){
        XWPFParagraph subTitle = document.createParagraph();
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText(text);
        subTitleRun.setFontFamily("Arial");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        if(carriageReturn){
            subTitleRun.addCarriageReturn();
        }

    }

    private void createTitle(XWPFDocument document) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Veckologgaren");
        titleRun.setBold(true);
        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(20);
    }

    private List<LogEntry> genTestLogEntries()
            throws LogEntryValidationException {
        LogEntry test1 = getTestLogEntry(2, 34, 22, 2012, 12);
        LogEntry test2 = getTestLogEntry(1, 34, 22, 2012, 12);
        LogEntry test3 = getTestLogEntry(2, 34, 22, 2013, 1);
        LogEntry test4 = getTestLogEntry(1, 1, 2, 2014, 1);
        LogEntry test5 = getTestLogEntry(1, 1, 22, 2014, 12);
        List<LogEntry> logEntryList = new ArrayList<LogEntry>();
        logEntryList.add(test1);
        logEntryList.add(test2);
        logEntryList.add(test3);
        logEntryList.add(test4);
        logEntryList.add(test5);
        return logEntryList;
    }

    private LogEntry getTestLogEntry(int h, int m, int s, int year, int week)
            throws LogEntryValidationException {
        LogEntry le1 = new LogEntry();
        le1.setComment("");
        le1.setLogDate(DateTimeUtils.getRandomDateString());
        le1.setYear(year);
        le1.setWeek(week);

        // Not used for calculation
        le1.setStartTime("14:01:33");
        le1.setStopTime("15:02:00");
        le1.setTotalTime(new Time(h, m, s));
        le1.setComment("Idag var en bra dag. Jag gick upp klockan halv 5.");
        return le1;
    }
}
