package com.windesheim.main;

import com.windesheim.command.CommandRegister;
import com.windesheim.command.commands.AuthoriseCommand;
import com.windesheim.command.commands.SudoCommand;
import com.windesheim.command.commands.UnauthoriseCommand;
import com.windesheim.constant.BotConstant;
import com.windesheim.database.Database;
import com.windesheim.webuntis.ScheduleJSONParser;
import com.windesheim.webuntis.ScheduleRetriever;
import com.windesheim.webuntis.calendar.CalendarItem;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Main class for initializing the bot.
 *
 * @author Lucas Ouwens
 */
public class Main {

    private static Windesbot windesbotWrapper = null;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Main method, initialises the application
     * @param args
     */
    public static void main(String[] args) {
        windesbotWrapper = Windesbot.createBotInstance(BotConstant.discordBotToken);
        CommandRegister.getRegister().registerCommandExecutionTemplate("sudo", new SudoCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("authorise", new AuthoriseCommand());
        CommandRegister.getRegister().registerCommandExecutionTemplate("unauthorise", new UnauthoriseCommand());

        Long scheduledTime = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay().plusHours(6), ChronoUnit.MINUTES);
        System.out.println(((double)scheduledTime / 60));


            String role = "dab";
            String untisGroup = "ICTM1s";
            Guild mentionGuild = windesbotWrapper.getWindesBot().getGuildById(498176239429877761L);
            if(mentionGuild != null) {
                if(mentionGuild.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                    StringBuilder sb = new StringBuilder();
                    ScheduleRetriever retriever = ScheduleRetriever.getInstance();
                    try(BufferedReader reader = new BufferedReader(retriever.getScheduleByClass(untisGroup))) {
                        String readLine;
                        while((readLine = reader.readLine()) != null) {
                            sb.append(readLine);
                        }

                        sb.append("}");
                        String JSON = "{ \"data\":" + sb.toString().trim();
                        JSONTokener tokener = new JSONTokener(JSON.trim());
                        JSONObject object = new JSONObject(tokener);

                        ScheduleJSONParser.getParser().parse(object.getJSONArray("data"));

                        CalendarItem firstLesson = ScheduleJSONParser.getParser().retrieveCalendarItems().get(0);

                        if(firstLesson != null) {
                            Date currentTime = new Date(System.currentTimeMillis());
                            Date t = new Date(firstLesson.getStartTime());
                            Date t2 = new Date(firstLesson.getEndTime());

                            if (t.getDate() == currentTime.getDate()) {
                                if (t.getYear() == currentTime.getYear()) {
                                    if (t.getDay() == currentTime.getDay()) {
                                        mentionGuild.getTextChannelById(498176239874342946L)
                                                .sendMessage(new MessageBuilder().append("The first lesson of the day starts at ")
                                                        .append(t.getHours()).append(":").append(t.getMinutes())
                                                        .append(" - ").append(t2.getHours()).append(":").append(t2.getMinutes()).build()).queue();

                                    }
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        /*
        ScheduledFuture<?> mentionSchedule = scheduler.scheduleAtFixedRate(() -> {
            try(Connection con = Database.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT server_id, role, untis_group FROM role_bindings")) {
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    String role = rs.getString("role");
                    String untisGroup = rs.getString("untis_group");
                    Guild mentionGuild = Windesbot.getBotInstance().getWindesBot().getGuildById(rs.getLong("server_id"));
                    if(mentionGuild != null) {
                        if(mentionGuild.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(role))) {
                            StringBuilder sb = new StringBuilder();
                            ScheduleRetriever retriever = ScheduleRetriever.getInstance();
                            try(BufferedReader reader = new BufferedReader(retriever.getScheduleByClass(untisGroup))) {
                                String readLine;
                                while((readLine = reader.readLine()) != null) {
                                    sb.append(readLine);
                                }

                                sb.append("}");
                                String JSON = "{ \"data\":" + sb.toString().trim();
                                JSONTokener tokener = new JSONTokener(JSON.trim());
                                JSONObject object = new JSONObject(tokener);

                                ScheduleJSONParser.getParser().parse(object.getJSONArray("data"));
                                for(CalendarItem item : ScheduleJSONParser.getParser().retrieveCalendarItems()) {
                                    Arrays.stream(item.getTeachers()).forEach((e) -> System.out.println(e.getName()));
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (SQLException | Windesbot.NoBotInstanceException e) {
                e.printStackTrace();
            }
        }, scheduledTime, 1440, TimeUnit.MINUTES);
        */



    }


    /**
     * Instance to the windesbot wrapper
     * @return Windesbot
     */
    public static Windesbot getWindesbotWrapper() {
        return windesbotWrapper;
    }

}
