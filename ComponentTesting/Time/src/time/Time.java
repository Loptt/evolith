/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

/**
 *
 * @author ErickFrank
 */
public class Time {
    
    private int ticker;
    private double hour;
    private double minutes;
    private double seconds;
    private double milliseconds;

    public Time() {
        this.ticker = 0;
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    public int getTicker() {
        return ticker;
    }

    public void setTicker(int ticker) {
        this.ticker = ticker;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public double getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(double milliseconds) {
        this.milliseconds = milliseconds;
    }
    
    public void tick()
    {
        ticker++;
        seconds = ticker / 60;
        minutes = seconds / 60;
        hour = minutes / 60;
        milliseconds = ticker;
    }
    public void reset()
    {
        this.ticker = 0;
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    @Override
    public String toString() {
        return "Time{" + "ticker=" + ticker + ", hour=" + hour + ", minutes=" + minutes + ", seconds=" + seconds + ", milliseconds=" + milliseconds + '}';
    }
    
}
