package de.haw_hamburg.dailymanager;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {

    private Calendar startTime;
    private String eventName;
    private String location;
    private String note;
    private String remindOption;

    public Event(Calendar startTime, String eventName) {
        this.startTime = startTime;
        this.eventName = eventName;
    }

    public Event(int year, int month, int day, int hour, int min, String eventName) {
        startTime = Calendar.getInstance();
        startTime.set(year, month, day, hour, min);
        this.eventName = eventName;
    }

    public void setStartTime(int year, int month, int day, int hour, int min) {
        startTime = Calendar.getInstance();
        startTime.set(year, month, day, hour, min);
    }

    public int getYear() {
        return startTime.get(Calendar.YEAR);
    }

    public int getMonth() {
        return startTime.get(Calendar.MONTH);
    }

    public int getDay() {
        return startTime.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return startTime.get(Calendar.HOUR);
    }

    public int getMin() {
        return startTime.get(Calendar.MINUTE);
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRemindOption() {
        return remindOption;
    }

    public void setRemindOption(String remindOption) {
        this.remindOption = remindOption;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Jahr=" + startTime.get(startTime.YEAR) +
                ", Monat=" + startTime.get(startTime.MONTH) +
                ", Tag=" + startTime.get(startTime.DAY_OF_MONTH) +
                ", Stunde=" + startTime.get(startTime.HOUR) +
                ", Minute=" + startTime.get(startTime.MINUTE) +
                ", eventName='" + eventName + '\'' +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                ", remindOption=" + remindOption +
                '}';
    }
}