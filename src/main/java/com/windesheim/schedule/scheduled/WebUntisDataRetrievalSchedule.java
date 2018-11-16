package com.windesheim.schedule.scheduled;

import com.windesheim.database.Database;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import com.windesheim.main.Windesbot;
import com.windesheim.schedule.Schedulable;
import com.windesheim.schedule.ScheduleManager;
import com.windesheim.webuntis.ScheduleJSONParser;
import com.windesheim.webuntis.ScheduleRetriever;
import net.dv8tion.jda.core.entities.Guild;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Data retrieval at the beginning of the day and on startup of the bot.
 *
 * @author Lucas Ouwens
 */
public class WebUntisDataRetrievalSchedule implements Schedulable {

    @Override
    public void schedule() {
        ScheduleManager.scheduler.scheduleWithFixedDelay(() -> {
            // Fetch data we need for the role bindings
            try (Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT server_id, role, untis_group FROM role_bindings")) {
                // loop through the resultset
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String role = rs.getString("role");
                    String untisGroup = rs.getString("untis_group");
                    Guild mentionGuild = Windesbot.getBotInstance().getWindesBot().getGuildById(rs.getLong("server_id"));
                    // check if the guild isn't null and there's a match for the role in the role list
                    if (mentionGuild != null) {
                        if (mentionGuild.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                            // retrieve the schedules
                            StringBuilder sb = new StringBuilder();
                            ScheduleRetriever retriever = ScheduleRetriever.getInstance();
                            try (BufferedReader reader = new BufferedReader(retriever.getScheduleByClass(untisGroup))) {

                                // write read json data to stringbuilder
                                String readLine;
                                while ((readLine = reader.readLine()) != null) {
                                    sb.append(readLine);
                                }

                                // make it look like proper json and convert it to objects
                                sb.append("}");
                                String JSON = "{ \"data\":" + sb.toString().trim();
                                JSONTokener tokener = new JSONTokener(JSON.trim());
                                JSONObject object = new JSONObject(tokener);

                                // parse the json (It will create the calendar items)
                                ScheduleJSONParser.getParser().parse(object.getJSONArray("data"));

                                // initialise the first scheduler at the start of the day or at execution
                                NextClassNotifySchedule notifySchedule = new NextClassNotifySchedule();
                                notifySchedule.schedule();

                                // Add the scheduled action to the manager
                                ScheduleManager.getManagerInstance().getScheduled().add(notifySchedule);
                                Logger.log("Calender item data has been retrieved.", MessageType.INFO);
                            } catch (IOException e) {
                                Logger.log(e.getMessage(), MessageType.ERROR);
                            }
                        }
                    }
                }
            } catch (SQLException | Windesbot.NoBotInstanceException e) {
                Logger.log(e.getMessage(), MessageType.ERROR);
            }
        }, 0, LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES), TimeUnit.MINUTES);
    }

}
