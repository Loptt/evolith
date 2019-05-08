package evolith.helpers;

import evolith.helpers.Commons;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Time implements Commons {

    private long ticker; // counter that represents the frames in a second
    private double hour; // time represented in hours
    private double minutes; // time represented in minutes
    private double seconds; // time represented in seconds
    private double milliseconds; // time represented in milliseconds

    /**
     * Initialization of all variables in 0
     */
    public Time() {
        this.ticker = 0;
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    /**
     * To get the ticker
     *
     * @return ticker
     */
    public long getTicker() {
        return ticker;
    }

    /**
     * To set the ticker
     *
     * @param ticker
     */
    public void setTicker(long ticker) {
        this.ticker = ticker;
    }

    /**
     * To get the hour
     *
     * @return hour
     */
    public double getHour() {
        return hour;
    }

    /**
     * To get the minutes
     *
     * @return minutes
     */
    public double getMinutes() {
        return minutes;
    }

    /**
     * To get the seconds
     *
     * @return seconds
     */
    public double getSeconds() {
        return seconds;
    }

    /**
     * To get the milliseconds
     *
     * @return milliseconds
     */
    public double getMilliseconds() {
        return milliseconds;
    }

    /**
     * To tick the ticker per number of frames
     */
    public void tick() {
        ticker++; // increments the ticker per frame
        seconds = ticker / FRAME_RATE; // converts the framerate to seconds
        minutes = seconds / FRAME_RATE; // converts the seconds to minutes
        hour = minutes / FRAME_RATE; // converts the minutes to hours
        milliseconds = seconds * 1000; // converts the seconds to milliseconds
    }

    /**
     * To reset to 0 all time values
     */
    public void reset() {
        this.ticker = 0;
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    /**
     * Returns string of the time
     *
     * @return string of the time
     */
    @Override
    public String toString() {
        return "Time{" + "ticker=" + ticker + ", hour=" + hour + ", minutes=" + minutes + ", seconds=" + seconds + ", milliseconds=" + milliseconds + '}';
    }

}
