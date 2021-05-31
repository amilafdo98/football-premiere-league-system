
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Match implements Serializable {

    private String matchID;
    private String team1_id;
    private String team2_id;
    private String team1_name;
    private String team2_name;

    private int team1_score;
    private int team2_score;
    private int team1_goals;

    public String getTeam1_name() {
        return team1_name;
    }


    public String getTeam2_name() {
        return team2_name;
    }


    private int team2_goals;
    private Date date;
    private String dateStr;

    SimpleDateFormat sdf;



    public Match(String matchID, String team1_id, String team2_id, int team1_goals, int team2_goals, Date date) {
        this(team1_id, team2_id, team1_goals, team2_goals, date);
        this.matchID = matchID;

    }


    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public Date getDate() {
        return date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Match(String team1_id, String team2_id, int team1_goals, int team2_goals, Date date) {
        this.team1_id = team1_id;
        this.team2_id = team2_id;

        this.team1_goals = team1_goals;
        this.team2_goals = team2_goals;
        this.date = date;

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.dateStr = sdf.format(date);
        updateScores();
        updateTeamNames();
    }

    private void updateTeamNames() {
        PremierLeagueManager plm = PremierLeagueManager.getInstance();
        try {
            team1_name = plm.getClubStatics(team1_id).getClubName();
            team2_name = plm.getClubStatics(team2_id).getClubName();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public String getTeam1_id() {
        return team1_id;
    }

    public void setTeam1_id(String team1_id) {
        this.team1_id = team1_id;
    }

    public String getTeam2_id() {
        return team2_id;
    }

    public void setTeam2_id(String team2_id) {
        this.team2_id = team2_id;
    }

    public int getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(int team1_score) {
        this.team1_score = team1_score;
    }

    public int getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(int team2_score) {
        this.team2_score = team2_score;
    }

    public int getTeam1_goals() {
        return team1_goals;
    }

    public void setTeam1_goals(int team1_goals) {
        this.team1_goals = team1_goals;
    }

    public int getTeam2_goals() {
        return team2_goals;
    }

    public void setTeam2_goals(int team2_goals) {
        this.team2_goals = team2_goals;
    }

    public Date getMatchDate() {
        return date;
    }

    public void setMatchDate(Date date) {
        this.date = date;
    }

    private void updateScores() {
        if (team1_goals == team2_goals) {
            team1_score = 1;
            team2_score = 1;
        } else if (team1_goals > team2_goals) {
            team1_score = 3;
            team2_score = 0;
        } else {
            team1_score = 0;
            team2_score = 3;
        }
    }

    @Override
    public String toString() {

        String sdate=sdf.format(date);
        String s_id1=String.valueOf(team1_id);
        String s_id2=String.valueOf(team2_id);

        String s_name1=String.valueOf(team1_name);
        String s_name2=String.valueOf(team2_name);


        String s_goal1=String.valueOf(team1_goals);
        String s_goal2=String.valueOf(team2_goals);




        //formatted
        String fmatchID = matchID + String.format("%0" + (8 - matchID.length() + "d"), 0).replace("0", " ");
        String fDate=sdate+String.format("%0" + (12 -sdate.length() + "d"), 0).replace("0", " ");
        String f_id1=s_id1+String.format("%0" + (10 -s_id1.length() + "d"), 0).replace("0", " ");
        String f_id2=s_id2+String.format("%0" + (10 -s_id2.length() + "d"), 0).replace("0", " ");

        String f_name1=s_name1+String.format("%0" + (10 -s_name1.length() + "d"), 0).replace("0", " ");
        String f_name2=s_name2+String.format("%0" + (10 -s_name2.length() + "d"), 0).replace("0", " ");

        String f_goal1=s_goal1+String.format("%0" + (12 -s_goal1.length() + "d"), 0).replace("0", " ");
        String f_goal2=s_goal2+String.format("%0" + (12 -s_goal2.length() + "d"), 0).replace("0", " ");

        return "|  " + fmatchID + "|   " + fDate+ "|   " + f_id1 + "|   " + f_id2+"|   " + f_name1 + "|   " + f_name2 + "|   " + f_goal1 + "|   " + f_goal2 + "|";

    }

    public String getStoreFormat() {
        return matchID + ":"
                + team1_id + ":"
                + team2_id + ":"
                + team1_score + ":"
                + team2_score + ":"
                + team1_goals + ":"
                + team2_goals + ":"
                + sdf.format(date);
    }
}
