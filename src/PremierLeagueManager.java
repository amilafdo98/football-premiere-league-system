import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.swap;

class PremierLeagueManager implements LeagueManager {

    static ObservableList leagueClubs;
    static ObservableList leagueMatches;

    SimpleDateFormat sdf;
    LeagueLogWriter logWriter;
    int numTeams;

    private static PremierLeagueManager plm=null;

    public static PremierLeagueManager getInstance(){
        if (plm==null){

           plm=new PremierLeagueManager();
        }
        return plm;

    }

    private PremierLeagueManager() {
        this.numTeams = 0;
        this.leagueMatches = FXCollections.observableArrayList();
        this.leagueClubs = FXCollections.observableArrayList();

        sdf = new SimpleDateFormat("yyyy-MM-dd");
       // logWriter = new LeagueLogWriter();
    }

    public void deleteClub(String deleteClubId) {
        FootBallClub deleteClub = null;
        for (Iterator iterator1 = leagueClubs.iterator(); iterator1.hasNext(); ) {
            FootBallClub club = (FootBallClub) iterator1.next();
            if (club.getClubId().equals(deleteClubId)) {
                deleteClub = club;
                //break;
            }
        }
        if (deleteClub != null) {
            List<Match> club_matchList = new ArrayList();
            ListIterator<Match> iterator = leagueMatches.listIterator();
            while (iterator.hasNext()) {
                Match playedMatch = iterator.next();
                if (playedMatch.getTeam1_id().equals(deleteClubId) | playedMatch.getTeam2_id().equals(deleteClubId)) {
                    club_matchList.add(playedMatch);
                }
            }

            for (Match match : club_matchList) {
                deleteMatch(match.getMatchID());
            }
            leagueClubs.remove(deleteClub);
            numTeams--;
            LeagueLogWriter.serializeClubLog();

            //logWriter.updateClubLog();
        }
    }

    // create football teamm and add to this 
    public void addClub(FootBallClub club) {
        if (numTeams < 20) {
            leagueClubs.add(club);
            //logWriter.updateClubLog();

            LeagueLogWriter.serializeClubLog();

            numTeams++;
        }

    }


    @Override
    public int getTeamCount() {
        return leagueClubs.size();
    }



    public ObservableList sortOnPoints() {
        ObservableList clone = FXCollections.observableList(leagueClubs);

        for (int i = clone.size(); i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {

                int club1_points = ((FootBallClub) clone.get(j)).getPoints();
                int club2_points = ((FootBallClub) clone.get(j + 1)).getPoints();

                if (club1_points < club2_points) {
                    swap(clone, j, j + 1);
                } else if (club1_points == club2_points) {
                    int team1_goals = ((FootBallClub) clone.get(j)).getNumGoals();
                    int team2_goals = ((FootBallClub) clone.get(j + 1)).getNumGoals();
                    if (team1_goals < team2_goals) {
                        swap(clone, j, j + 1);
                    }
                }
            }
        }
        return clone;

    }

    public ObservableList sortOnGoalsScored() {

        ObservableList clone = FXCollections.observableList(leagueClubs);

        for (int i = clone.size(); i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {

                int club1_goals = ((FootBallClub) clone.get(j)).getNumGoals();
                int club2_goals = ((FootBallClub) clone.get(j + 1)).getNumGoals();

                if (club1_goals < club2_goals) {
                    swap(clone, j, j + 1);
                } else if (club1_goals == club2_goals) {
                    int team1_points = ((FootBallClub) clone.get(j)).getPoints();
                    int team2_points = ((FootBallClub) clone.get(j + 1)).getPoints();
                    if (team1_points < team2_points) {
                        swap(clone, j, j + 1);
                    }
                }
            }
        }
        return clone;

    }

    public ObservableList sortOn_NumberWins() {

        ObservableList clone = FXCollections.observableList(leagueClubs);

        for (int i = clone.size(); i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {

                int club1_wins = ((FootBallClub) clone.get(j)).getNumWins();
                int club2_wins = ((FootBallClub) clone.get(j + 1)).getNumWins();

                if (club1_wins < club2_wins) {
                    swap(clone, j, j + 1);
                } else if (club1_wins == club2_wins) {
                    int team1_points = ((FootBallClub) clone.get(j)).getPoints();
                    int team2_points = ((FootBallClub) clone.get(j + 1)).getPoints();
                    if (team1_points < team2_points) {
                        swap(clone, j, j + 1);
                    }
                }
            }
        }
        return clone;

    }

    @Override
    public void addMatch(Match match) {

        leagueMatches.add(match);

        Iterator iterator = leagueClubs.iterator();

        int updatedCount = 0;
        int teamIndex=0;
        while (iterator.hasNext() & updatedCount < 2) {


            FootBallClub team = (FootBallClub) iterator.next();


            if (team.getClubId().equals(match.getTeam1_id())) {
                int team1Score = match.getTeam1_score();

                switch (team1Score) {
                    case 0:
                        team.incrementDefeats();
                        break;
                    case 1:
                        team.incrementDraws();
                        team.incrementPoints(team1Score);
                        break;
                    case 3:
                        team.incrementWins();
                        team.incrementPoints(team1Score);
                        break;
                }

                team.incrementGoals(match.getTeam1_goals());
                team.incrementPlayedMatches();
                updatedCount++;
                leagueClubs.set(teamIndex,team);

            }

            if (team.getClubId().equals(match.getTeam2_id())) {
                int team2Score = match.getTeam2_score();

                switch (team2Score) {
                    case 0:
                        team.incrementDefeats();
                        break;
                    case 1:
                        team.incrementDraws();
                        team.incrementPoints(team2Score);
                        break;
                    case 3:
                        team.incrementWins();
                        team.incrementPoints(team2Score);
                        break;
                }

                team.incrementGoals(match.getTeam2_goals());
                team.incrementPlayedMatches();
                updatedCount++;


                leagueClubs.set(teamIndex,team);
            }
            teamIndex++;
        }


        LeagueLogWriter.serializeClubLog();
        LeagueLogWriter.serializeMatchLog();
    }

    public void updateTeamScore() {

    }

    public static ObservableList getMatcheslist() {

        return leagueMatches;
    }

    public static ObservableList getClubslist() {

        return leagueClubs;
    }

    @Override
    public FootBallClub getClubStatics(String clubId) throws Exception {

        FootBallClub fbc = null;
        Iterator iterator = leagueClubs.iterator();

        while (iterator.hasNext()) {
            FootBallClub club = (FootBallClub) iterator.next();

            if (club.getClubId().equals(clubId)) {
                fbc = club;
            }
        }

        if (fbc == null) {
            throw new Exception("Club ID  is invalid ");
        }

        return fbc;

    }


    public Match getMatch(String matchID) throws Exception {
        Match match = null;

        Iterator iterator = leagueMatches.iterator();
        while (iterator.hasNext()) {
            Match m = (Match) iterator.next();

            if (m.getMatchID().equals(matchID)) {
                match = m;
            }

        }
        if (match==null) {
            throw new Exception("Match ID is invalid");

        }
        return match;

    }


    public void restoreMatchRecord() {

       // System.out.println("Restore Match Records");

        ArrayList<Match> matchList = LeagueLogWriter.deserializeMatchlogs();

        Iterator iterator = matchList.iterator();
        while (iterator.hasNext()){
            Match match = (Match) iterator.next();
            leagueMatches.add(match);
        }



    }

    public void restoreClubRecord() {

     //  System.out.println("Restore Club Records");


        ArrayList<FootBallClub> clubList = LeagueLogWriter.deserializeClublogs();
        Iterator iterator = clubList.iterator();

        while (iterator.hasNext()){
            FootBallClub footBallClub = (FootBallClub) iterator.next();
            leagueClubs.add(footBallClub);
        }




    }




    public ObservableList sortMatchesOnDate() {
        ObservableList clone= FXCollections.observableList(leagueMatches);

        for (int i = clone.size(); i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {

                Date match1_date = ((Match) clone.get(j)).getDate();
                Date match2_date = ((Match) clone.get(j + 1)).getDate();

                if (match1_date.compareTo(match2_date) > 0) {
                    swap(clone, j, j + 1);
                }
            }
        }
        return clone;

    }

    public ObservableList searchMatches(Date searchDate) {
        ObservableList clone= FXCollections.observableList(leagueMatches);
        ObservableList filtered= FXCollections.observableArrayList();

        String str_searchDate = sdf.format(searchDate);

        for (Iterator iterator = clone.iterator(); iterator.hasNext(); ) {
            Match match = (Match) iterator.next();
            String str_matchDate = sdf.format(match.getDate());

            if (str_matchDate.equals(str_searchDate)) {
                filtered.add(match);
            }
        }
        return filtered;
    }

    @Override
    public void deleteMatch(String deleteMatchId) {

        Match deleteMatch = null;

        for (Iterator iterator = leagueMatches.iterator(); iterator.hasNext(); ) {
            Match match = (Match) iterator.next();
            if (match.getMatchID().equals(deleteMatchId)) {
                deleteMatch = match;
                //System.out.println("match deleting ..." + match.getStoreFormat());
                break;
            }
        }
        if (deleteMatch != null) {
            FootBallClub club1 = null;
            FootBallClub club2 = null;
            int club2_index = -1;
            int club1_index = -1;
            int club1_matchGoals = deleteMatch.getTeam1_goals();
            int club2_matchGoals = deleteMatch.getTeam2_goals();

            for (Iterator iterator = leagueClubs.iterator(); iterator.hasNext(); ) {
                FootBallClub fbc = (FootBallClub) iterator.next();

                if (deleteMatch.getTeam1_id().equals(fbc.getClubId())) {
                    club1 = fbc;
                    club1_index = leagueClubs.indexOf(fbc);
                }
                if (deleteMatch.getTeam2_id().equals(fbc.getClubId())) {
                    club2 = fbc;
                    club2_index = leagueClubs.indexOf(fbc);
                }
            }
            if (club1_index != -1 && club2_index != -1) {
                club1.decrementPlayedMatches();
                club2.decrementPlayedMatches();
                club1.decrementGoals(club1_matchGoals);
                club2.decrementGoals(club2_matchGoals);

                if (club1_matchGoals == club2_matchGoals) {
                    club1.decrementDraws();
                    club2.decrementDraws();
                    club1.decrementPoints(1);
                    club2.decrementPoints(1);

                } else if (club1_matchGoals > club2_matchGoals) {

                    club1.decrementWins();
                    club2.decrementDefeats();
                    club1.decrementPoints(3);

                } else if (club1_matchGoals < club2_matchGoals) {
                    club2.decrementWins();
                    club1.decrementDefeats();
                    club2.decrementPoints(3);
                }
                leagueClubs.set(club1_index, club1);
               // System.out.println(club1.getStoreFormat() + "-" + club1_index);
                leagueClubs.set(club2_index, club2);
            }
            leagueMatches.remove(deleteMatch);
        }


        LeagueLogWriter.serializeClubLog();
        LeagueLogWriter.serializeMatchLog();
    }

    @Override
    public void updatePremierLeagueLog() {
        System.out.println("info saved... ");
        LeagueLogWriter.serializeClubLog();
        LeagueLogWriter.serializeMatchLog();
    }

    @Override
    public void setClub(String clubId, SportsClub modifiedClub) throws Exception {
        FootBallClub initialClub = getClubStatics(clubId);
        int index= (int) leagueClubs.indexOf(initialClub);
        leagueClubs.set(index,modifiedClub);

        LeagueLogWriter.serializeClubLog();
        LeagueLogWriter.serializeMatchLog();
    }
}
