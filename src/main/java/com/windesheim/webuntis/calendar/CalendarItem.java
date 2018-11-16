package com.windesheim.webuntis.calendar;

import com.windesheim.webuntis.subject.Subject;
import com.windesheim.webuntis.teacher.Teacher;
import com.windesheim.webuntis.team.Team;

/**
 *
 * @author Lucas Ouwens
 */
public class CalendarItem {

    // item
    private String UUID;

    private boolean changed;
    private String note, room;

    private boolean status;

    private long startTime, endTime;
    private String scheduleDate;

    private Subject subject;
    private Teacher[] teachers;
    private Team[] teams;

    public CalendarItem(String UUID) {
        this.setUUID(UUID).setSubject(subject);
    }

    public CalendarItem setChanged(boolean changed) {
        this.changed = changed;
        return this;
    }

    public CalendarItem setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public CalendarItem setNote(String note) {
        this.note = note;
        return this;
    }

    public CalendarItem setRoom(String room) {
        this.room = room;
        return this;
    }

    public CalendarItem setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public CalendarItem setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public CalendarItem setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public CalendarItem setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public CalendarItem setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
        return this;
    }

    public CalendarItem setTeams(Team[] teams) {
        this.teams = teams;
        return this;
    }

    public CalendarItem setUUID(String UUID) {
        this.UUID = UUID;
        return this;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getNote() {
        return note;
    }

    public String getRoom() {
        return room;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public boolean getStatus() {
        return status;
    }

    public String getUUID() {
        return UUID;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public Team[] getTeams() {
        return teams;
    }
}
