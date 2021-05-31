
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;

public interface LeagueManager {

    void addClub(FootBallClub club);

    void deleteClub(String clubId);
    void deleteMatch(String matchId);

    int getTeamCount();



    ObservableList sortOnPoints();

    void addMatch(Match match);

    FootBallClub getClubStatics(String clubId) throws Exception;

    public Match getMatch(String matchID) throws Exception;

    void restoreClubRecord();

    void restoreMatchRecord();

    

    ObservableList sortMatchesOnDate();

    ObservableList sortOn_NumberWins();

    ObservableList sortOnGoalsScored();

    ObservableList searchMatches(Date matchDate);
    void updatePremierLeagueLog();

    void setClub(String clubId, SportsClub club) throws Exception ;
}
