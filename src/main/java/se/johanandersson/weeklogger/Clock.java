package se.johanandersson.weeklogger;

import java.util.List;

/**
 * @author Johan Andersson
 */
public class Clock {

    private Time time;
    private boolean ticking;

    public Clock(Time t) {
        this.time = t;
    }

    public void tick() { // step 1 second forward every tick

        if (this.isTicking()) {

            time.setSeconds(time.getSeconds() + 1);

            if (time.getSeconds() == 60) { // if s is 60
                time.setSeconds(0); // set s to 0
                time.setMinutes(time.getMinutes() + 1); // add 1 to minutes
            }

            if (time.getMinutes() == 60) { // if m is 60
                time.setMinutes(0); // set m to 0
                time.setHours(time.getHours() + 1); // add 1 to hours
            }
        }
    }

    public void stop() {
        setTicking(false);
    }

    public void start() {
        setTicking(true);
    }

    public Time getTime() {
        return time;
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public void setToZero() {
        time.setHours(0);
        time.setMinutes(0);
        time.setSeconds(0);

    }

    public boolean isZero() {
        return time.getHours() == 0 && time.getMinutes() == 0
                && time.getSeconds() == 0;
    }

    public static Time calculateTotalTime(List<Time> timeList) {
        int sumOfHours = 0;
        int sumOfMinutes = 0;
        int sumOfSeconds = 0;

        for (Time t : timeList) {
            int hours = t.getHours();
            int minutes = t.getMinutes();
            int seconds = t.getSeconds();

            sumOfHours += hours;
            sumOfMinutes += minutes;
            sumOfSeconds += seconds;

        }

        return new Time(sumOfHours, sumOfMinutes, sumOfSeconds);

    }
}