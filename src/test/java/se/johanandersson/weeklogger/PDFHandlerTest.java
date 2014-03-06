/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.johanandersson.weeklogger;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import junit.framework.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.johanandersson.weeklogger.itext.PDFCreator;

/**
 *
 * @author nossrednanahoj
 */
public class PDFHandlerTest {
    
    private PDFCreator pdfHandler;
    


    @Test
    public void testCreateFile() throws FileNotFoundException, DocumentException {
        pdfHandler = new PDFCreator();
        Assert.assertNotNull(pdfHandler.createFile());
    }
    
    @Test
    public void testGetFileName() {
        pdfHandler = new PDFCreator();
        Assert.assertEquals("report.pdf", pdfHandler.getFileName());
    }

   
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}