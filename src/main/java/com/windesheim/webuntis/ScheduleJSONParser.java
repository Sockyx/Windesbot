package com.windesheim.webuntis;

import com.windesheim.webuntis.calendar.CalendarItem;
import com.windesheim.webuntis.subject.Subject;
import com.windesheim.webuntis.teacher.Teacher;
import com.windesheim.webuntis.team.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleJSONParser {

    private static ScheduleJSONParser jsonParser = null;

    private ArrayList<CalendarItem> calendarItems = null;

    private ScheduleJSONParser() {
        calendarItems = new ArrayList<>();
    }

    public static ScheduleJSONParser getParser() {
        if(jsonParser == null) {
            jsonParser = new ScheduleJSONParser();
        }

        return jsonParser;
    }

    public ScheduleJSONParser parse(JSONArray arr) {
        calendarItems.clear(); // clear before getting new data
        for(int i = 0; i < arr.length(); i++) {
            JSONObject calendarJSONItem = arr.getJSONObject(i);
            if(calendarJSONItem != null) {
                CalendarItem calendarItem = new CalendarItem(calendarJSONItem.getString("id"));

                // very long builder method :)
                calendarItem
                        .setSubject(
                                Subject._new(
                                        calendarJSONItem.getString("vakcode"),
                                        calendarJSONItem.getString("vaknaam")
                                )
                        )
                        .setChanged(calendarJSONItem.getBoolean("changed"))
                        .setStartTime(calendarJSONItem.getLong("starttijd") - 7200000) // remove 2 hours for accuracy
                        .setEndTime(calendarJSONItem.getLong("eindtijd") - 7200000)
                        .setNote(calendarJSONItem.getString("commentaar"))
                        .setRoom(calendarJSONItem.getString("lokaal"))
                        .setScheduleDate(calendarJSONItem.getString("roosterdatum"))
                        .setStatus(calendarJSONItem.getBoolean("status"))
                        .setTeams(Team.multipleFromArray(calendarJSONItem.getString("groepcode").split(", ")))
                        .setTeachers(Teacher.multipleFromJSONArray(calendarJSONItem.getJSONArray("docentnamen")));

                calendarItems.add(calendarItem);

            }
        }
        return this;
    }

    public ArrayList<CalendarItem> retrieveCalendarItems() {
        return calendarItems;
    }

}
