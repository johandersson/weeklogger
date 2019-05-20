package se.johanandersson.weeklogger;

/**
 * @author Johan Andersson
 */
public class Time {

    private int hours;
    private int minutes;
    private int seconds;

    public Time(int tim, int min, int sek) {
        setTime(tim, min, sek);
    }

    private String getTimeStringForDisplay() {
        // time to "tt:mm:ss"

        String sec = addZeroToTimeUnit(getSeconds());
        String min = addZeroToTimeUnit(getMinutes());
        String hrs = addZeroToTimeUnit(getHours());

        String time = hrs + ":" + min + ":" + sec;

        return time;
    }

    private void setTime(int hours, int min, int sec) {
        setHours(hours);
        setMinutes(min);
        setSeconds(sec);

        if (min > 60) {
            setHours(hours + min / 60);
            setMinutes(min % 60);
        }

        if (sec > 60) {
            setMinutes(getMinutes() + sec / 60);
            setSeconds(sec % 60);
        }

    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return getTimeStringForDisplay();
    }

    @Override
    public boolean equals(Object aThat) {
        if (!(aThat instanceof Time)) {
            return false;
        }

        Time that = (Time) aThat;

        return this.getHours() == that.getHours()
                && this.getMinutes() == that.getMinutes()
                && this.getSeconds() == that.getSeconds();
    }

    private String addZeroToTimeUnit(int timeUnit) {
        String result;

        if (timeUnit == 0 || timeUnit < 10) {
            result = "0" + String.valueOf(timeUnit);
        } else {
            result = String.valueOf(timeUnit);
        }
        return result;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
}