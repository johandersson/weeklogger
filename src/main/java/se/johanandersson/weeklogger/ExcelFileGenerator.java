package se.johanandersson.weeklogger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExcelFileGenerator {
    private String fileName;
    private FileOutputStream out;


    public ExcelFileGenerator(String fileName) {
        this.fileName=fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void createFile() throws FileNotFoundException {
        out = new FileOutputStream(getFileName());
    }

    public OutputStream getFileOutPutStream() {
        return out;
    }
}
