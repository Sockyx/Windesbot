package com.windesheim.webuntis.team;

/**
 * @author Lucas Ouwens
 */
public class Team {

    private String name;

    private Team(String name) {
        this.name = name;
    }

    public static Team _new(String name) {
        return new Team(name);
    }

    public String getName() {
        return name;
    }

    public static Team[] multipleFromArray(String[] teams) {
        Team[] oTeams = new Team[teams.length];
        for(int i = 0; i < teams.length; i++) {
            oTeams[i] = Team._new(teams[i]);
        }

        return oTeams;
    }
}
