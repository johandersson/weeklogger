package se.johanandersson.weeklogger;

import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.uispec4j.utils.Log;

import java.awt.event.WindowEvent;

import static org.assertj.swing.core.matcher.JLabelMatcher.withName;

public class WeekloggerWindowTest {
    private FrameFixture window;
    WeekLoggerWindow frame;
    @BeforeSuite
    public void setUp() {
        frame = GuiActionRunner.execute(() ->  WeekLoggerWindow.getInstance());
        window = new FrameFixture(frame);
    }

    @Test
    public void testWindowGainedFocus() throws LogEntryValidationException {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_LOST_FOCUS));
        LogEntry l = new LogEntry();
        l.setLogDate("1902-12-12");
        l.setWeek(10);
        frame.setCurrentLogEntry(l);
        frame.updateWeekLabel(l);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_GAINED_FOCUS));
        var weekLabelString = DateTimeUtils.getCurrentDate() + " vecka: " + DateTimeUtils.getCurrentWeek();
        JLabelMatcher m = withName("weekLabel").andText(weekLabelString).andShowing();
        window.label(m);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_GAINED_FOCUS));
        JLabelMatcher m2 = withName("weekLabel").andText(weekLabelString).andShowing();
        window.label(m2);
        Assert.assertTrue(frame.getCurrentLogEntry().getLogDate().equals(DateTimeUtils.getCurrentDate()));
    }

}
