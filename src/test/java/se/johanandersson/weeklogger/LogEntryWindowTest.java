package se.johanandersson.weeklogger;

import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.awt.event.WindowEvent;
import java.io.File;

import static org.assertj.swing.core.matcher.JLabelMatcher.withName;

public class LogEntryWindowTest {
    private FrameFixture window;
    LogEntryWindow frame;
    @BeforeSuite
    public void setUp() {
        frame = GuiActionRunner.execute(() ->  LogEntryWindow.getInstance());
        window = new FrameFixture(frame);
    }

    @Test
    public void testWindowGainedFocus() throws LogEntryValidationException {
        var file = new File("weeklogger.json");
        file.delete();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_GAINED_FOCUS));
    }

    @AfterSuite
    public void tearDown() {
       window.close();
    }


}
