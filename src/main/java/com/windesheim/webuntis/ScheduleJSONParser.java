package com.windesheim.webuntis;

import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import com.windesheim.webuntis.calendar.CalendarItem;
import com.windesheim.webuntis.subject.Subject;
import com.windesheim.webuntis.teacher.Teacher;
import com.windesheim.webuntis.team.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * JSON parser for a very specific purpose
 *
 * @author Lucas Ouwens
 */
public class ScheduleJSONParser {

    private static ScheduleJSONParser jsonParser = null;

    private ArrayList<CalendarItem> calendarItems = null;

    private ScheduleJSONParser() {
        calendarItems = new ArrayList<>();
    }

    /**
     * ScheduleJSONParser instance
     *
     * @return ScheduleJSONParser
     */
    public static ScheduleJSONParser getParser() {
        if (jsonParser == null) {
            jsonParser = new ScheduleJSONParser();
        }

        return jsonParser;
    }

    /**
     * Parse the CalendarItem JSON retrieved from untis
     *
     * @param arr JSONArray the retrieved data
     * @return ScheduleJSONParser
     */
    public ScheduleJSONParser parse(JSONArray arr) {
        calendarItems.clear(); // clear before getting new data
        Logger.log("Retrieving new calendar items...", MessageType.INFO);
        // loop through the json objects
        for (int i = 0; i < arr.length(); i++) {
            JSONObject calendarJSONItem = arr.getJSONObject(i);
            // make sure that the calendar json item isn't null and build a calendar item from it.
            if (calendarJSONItem != null) {
                CalendarItem calendarItem = new CalendarItem(calendarJSONItem.getString("id"));

                // Only add the calendar item to the arraylist when the class occurs today, it is otherwise unnecessary data.
                if (Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() <= (calendarJSONItem.getLong("starttijd") - 7200000)
                        && Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay()).getTime() >= (calendarJSONItem.getLong("starttijd") - 7200000)) {
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

                    // add the calendar item to the arraylist.
                    calendarItems.add(calendarItem);
                }

            }
        }
        Logger.log("Calendar items have been retrieved.", MessageType.INFO);
        return this;
    }

    /**
     * The calendar items
     *
     * @return ArrayList
     */
    public ArrayList<CalendarItem> retrieveCalendarItems() {
        return calendarItems;
    }

}
