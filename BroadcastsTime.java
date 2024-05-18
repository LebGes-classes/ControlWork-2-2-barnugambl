package ControlWork2;

public class BroadcastsTime implements Comparable<BroadcastsTime>{
    private byte hour;

    private byte minute;

    public BroadcastsTime(byte hour, byte minute) {
        this.hour = hour;
        this.minute = minute;
    }


    public boolean after(BroadcastsTime bt){
        if(this.hour < bt.hour){
            return true;
        }
        else if(this.hour == bt.hour && this.minute < bt.minute){
            return true;
        }
        return false;
    }
    public boolean before(BroadcastsTime bt){
        if(this.hour > bt.hour){
            return true;
        }
        else if(this.hour == bt.hour && this.minute > bt.minute){
            return true;
        }
        return false;
    }
    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public int compareTo(BroadcastsTime t) {
        if (this.hour != t.hour) {
            return Byte.compare(this.hour, t.hour);
        } else {
            return Byte.compare(this.minute, t.minute);
        }
    }
}
