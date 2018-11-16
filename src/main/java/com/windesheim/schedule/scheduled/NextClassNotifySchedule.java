package com.windesheim.schedule.scheduled;

import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import com.windesheim.main.Windesbot;
import com.windesheim.schedule.Schedulable;
import com.windesheim.schedule.ScheduleManager;
import com.windesheim.webuntis.ScheduleJSONParser;
import com.windesheim.webuntis.calendar.CalendarItem;
import com.windesheim.webuntis.teacher.Teacher;
import com.windesheim.webuntis.team.Team;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;

import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Scheduled action for sending an embed once a class starts in 10 minutes.
 *
 * @author Lucas Ouwens
 */
public class NextClassNotifySchedule implements Schedulable {

    public static AtomicInteger todaysClassCount = new AtomicInteger(0);

    @Override
    public void schedule() {
        // make sure we're not overflowing the data in the arraylist
        if (todaysClassCount.get() < ScheduleJSONParser.getParser().retrieveCalendarItems().size()) {
            // schedule the action
            ScheduleManager.scheduler.schedule(() -> {
                // remove this from the scheduled data and get a specific calendar item
                ScheduleManager.getManagerInstance().getScheduled().remove(this);
                CalendarItem item = ScheduleJSONParser.getParser().retrieveCalendarItems().get(todaysClassCount.get());
                // make sure the item is not null
                if (item != null) {
                    // Get the teams for the class
                    StringBuilder teams = new StringBuilder();
                    for (Team schoolTeam : item.getTeams()) {
                        teams.append(schoolTeam.getName()).append(" ");
                    }

                    // Get the teachers for the class
                    StringBuilder docenten = new StringBuilder();
                    for (Teacher classTeacher : item.getTeachers()) {
                        docenten.append(classTeacher.getName()).append(" ");
                    }

                    // Get the start and end time of the lesson
                    String start = new Timestamp(item.getStartTime()).toLocalDateTime().format(DateTimeFormatter.ofPattern("H:m"));
                    String end = new Timestamp(item.getEndTime()).toLocalDateTime().format(DateTimeFormatter.ofPattern("H:m"));

                    // build the embed to show.
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(item.getSubject().getName(), "https://liveadminwindesheim.sharepoint.com/sites/wip/pages/activities.aspx");
                    embed.setDescription((item.getNote().length() > 0 ? item.getNote() : ""));
//                    embed.addBlankField(false);
                    embed.addField("Klas:", teams.toString(), true);
                    embed.addField("Docent(en):", (item.getTeachers().length > 0) ? docenten.toString() : "Geen.", true);
                    embed.addField("Van - tot:", start + " - " + end, true);
                    embed.addField("Lokaal: ", (item.getRoom().length() > 0 ? item.getRoom() : "Geen."), true);
                    embed.setThumbnail("https://www.captise.nl/Portals/1/EasyDNNNews/151/600600p509EDNmain151windesheim_300x300.png");
                    embed.setColor(Color.ORANGE);

                    // get the roles-to-ping for class and send the message to those whom need to receive it
                    try (Connection c = Database.getInstance().getConnection();
                         PreparedStatement ps = c.prepareStatement("SELECT server_id, role, untis_group FROM role_bindings"); ResultSet rs = ps.executeQuery()) {

                        while (rs.next()) {
                            // retrieve data from database for message use
                            String role = rs.getString("role");
                            String untisGroup = rs.getString("untis_group");
                            long serverId = rs.getLong("server_id");

                            // Send messages to the ones that are in the correct team and role.
                            Arrays.stream(item.getTeams()).filter((team -> team.getName().equalsIgnoreCase(untisGroup))).forEach((team -> {
                                try {
                                    Guild targetGuild = Windesbot.getBotInstance().getWindesBot().getGuildById(serverId);
                                    targetGuild.getMembersWithRoles(targetGuild.getRolesByName(role, false)).forEach((member) -> {
                                        if (!(member.getUser().isBot() || member.getUser().isFake())) {
                                            member.getUser().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(embed.build()).queue());
                                        }
                                    });
                                } catch (Windesbot.NoBotInstanceException e) {
                                    Logger.log(e.getMessage(), MessageType.ERROR);
                                }
                            }));

                        }
                    } catch (SQLException e) {
                        Logger.log(e.getMessage(), MessageType.ERROR);
                    }


                    // Create a new instance of this scheduler if we haven't reached the last lesson yet, otherwise we reset the count for the next day.
                    if (ScheduleJSONParser.getParser().retrieveCalendarItems().size() > todaysClassCount.get()) {
                        ScheduleManager.getManagerInstance().getScheduled().add(new NextClassNotifySchedule());
                        Logger.log("Notifications for the next class have been sent.", MessageType.INFO);
                    } else {
                        todaysClassCount.set(0);
                        Logger.log("Notifications for todays lessons have been sent, shutting down scheduler.", MessageType.INFO);
                    }

                }
            }, 1000, TimeUnit.MILLISECONDS);
        }
    }
}
