package se.johanandersson.weeklogger;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTimer {

    @Test
    public void testTick1() {
        Time time = new Time(0, 0, 0);
        Clock c = new Clock(time);
        c.start();

        for (int i = 0; i < 60; i++) { //1 minute
            c.tick();
        }

        String result = c.getTime().toString();
        String expected = "00:01:00";

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTick2() {
        Time time = new Time(0, 0, 0);
        Clock c = new Clock(time);
        c.start();

        for (int i = 0; i < 3600; i++) { //1 minute
            c.tick();
        }


        String result = c.getTime().toString();
        String expected = "01:00:00";

        Assert.assertEquals(expected, result);


    }

    @Test
    public void testTick3() {
        Time time = new Time(0, 0, 0);
        Clock c = new Clock(time);
        c.start();

        for (int i = 0; i < 86401; i++) { //24 hours + 1 second
            c.tick();
        }

        String result = c.getTime().toString();
        String expected = "24:00:01";

        Assert.assertEquals(expected, result);

    }

    @Test
    public void testStop() {
        Time time = new Time(0, 0, 0);
        Clock c = new Clock(time);
        c.start();

        for (int i = 0; i < 10; i++) { //1 minute
            c.tick();
        }

        c.stop();

        c.tick();
        c.tick();
        c.tick();

        Assert.assertEquals(c.getTime().toString(), "00:00:10");

    }

    @Test
    public void testConstructor() {
        Clock c = new Clock(new Time(0, 61, 120));

        String expected = "01:03:00";
        String result = c.getTime().toString();

        Assert.assertEquals(expected, result);


    }

    @Test
    public void testGetCurrentTime() {
        Time currentTime = DateTimeUtils.getCurrentTime();
        Assert.assertNotNull(currentTime);
    }

    @Test
    public void testSetToZero() {
        Clock c = new Clock(new Time(12, 12, 12));
        c.setToZero();
        String actualTime = c.getTime().toString();

        Assert.assertEquals(actualTime, "00:00:00");
    }

    @Test
    public void testAddZeroToTimeUnit() {
        Time t = new Time(0, 9, 0);
        Clock c = new Clock(t);

        String actualTime = c.getTime().toString();
        Assert.assertNotEquals(actualTime, "00:9:00");

        Time t2 = new Time(10, 10, 10);
        Clock c2 = new Clock(t2);

        String actualTime2 = c2.getTime().toString();
        Assert.assertEquals(actualTime2, "10:10:10");


    }

    @Test(expectedExceptions = LogEntryValidationException.class)
    public void testSetLogEntryWrongWeek() throws LogEntryValidationException {
        LogEntry ts = new LogEntry();
        ts.setWeek(100); //should throw TimeException

    }

    @Test
    public void testTimeSpanSetLogDate() throws LogEntryValidationException {
        LogEntry ts = new LogEntry();
        ts.setLogDate("1983-12-12");
    }

    @Test
    public void testAddUppTime() {
        List<Time> timeList = new ArrayList<Time>();

        Time t1 = new Time(0, 34, 12);
        Time t2 = new Time(13, 12, 12);
        Time t3 = new Time(1, 1, 10);

        timeList.add(t1);
        timeList.add(t2);
        timeList.add(t3);

        Time actual = Clock.calculateTotalTime(timeList);
        Time expected = new Time(14, 47, 34);
        Assert.assertEquals(actual, expected);

    }

    @Test
    public void testMoreThan24hours() {
        Time t1 = new Time(24, 7, 2);
        Time t2 = new Time(34, 1, 2);

        List<Time> times = new ArrayList<Time>();
        times.add(t1);
        times.add(t2);

        int totalHours = 0;
        for (Time t : times) {
            totalHours += t.getHours();
        }


        Assert.assertEquals(totalHours, 58);

    }

    @Test
    public void testClockMoreThan24hours() {
        Time actual = new Time(54, 12, 0);
        Clock c = new Clock(actual);
        Assert.assertEquals(c.getTime().getHours(), actual.getHours());

    }

    @Test
    public void tickOver24hours() {
        Clock c = new Clock(new Time(23, 59, 59));
        c.start();

        c.tick();
        c.tick();
        Assert.assertEquals(c.getTime().getHours(), 24);
    }

    @Test
    public void testClockIsTicking() {
        Clock c = new Clock(new Time(0, 0, 0));
        Assert.assertFalse(c.isTicking());
    }
}
