

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;


public class IDgenerator {

    public static String getNextClubId() {

        List clubs = PremierLeagueManager.getClubslist();
        int size = clubs.size();
        if (size == 0) {
            return "C001";
        } else {



            ObservableList clone = FXCollections.observableList(PremierLeagueManager.getClubslist());

            for (int i = clone.size(); i > 1; i--) {
                for (int j = 0; j < i - 1; j++) {

                    String  club1_id = ((FootBallClub) clone.get(j)).getClubId();
                    String  club2_id= ((FootBallClub) clone.get(j + 1)).getClubId();


                    int id1_value = Integer.parseInt(club1_id.substring(1, club1_id.length()));
                    int id2_value = Integer.parseInt( club2_id.substring(1, club2_id.length()));


                    if (id1_value > id2_value) {
                        swap(clone, j, j + 1);
                    }
                }
            }


            SportsClub sc = (SportsClub) clone.get(size - 1);

            String clubId = sc.getClubId();
            String substring = clubId.substring(1, clubId.length());

            int newIdValue = Integer.parseInt(substring) + 1;

            String newId_str = Integer.toString(newIdValue);
            int i = 3 - newId_str.length();
            return clubId.substring(0, 1) + String.format("%0" + i + "d", 0) + newId_str;
        }
    }

    public static String getNextMatchId() {

        ObservableList matches = PremierLeagueManager.getMatcheslist();
        int size = matches.size();
        if (size == 0) {
    return "M001";




        } else {

            ObservableList clone = FXCollections.observableList(PremierLeagueManager.getMatcheslist());

            for (int i = clone.size(); i > 1; i--) {
                for (int j = 0; j < i - 1; j++) {

                    String  match1_id = ((Match) clone.get(j)).getMatchID();
                    String  match2_id= ((Match) clone.get(j + 1)).getMatchID();


                    int id1_value = Integer.parseInt(match1_id.substring(1, match1_id.length()));
                    int id2_value = Integer.parseInt( match2_id.substring(1, match2_id.length()));


                    if (id1_value > id2_value) {
                        swap(clone, j, j + 1);
                    }
                }
            }

            Match match = (Match) clone.get(size - 1);

            String maatchId = match.getMatchID();
            String substring = maatchId.substring(1, maatchId.length());

            int newIdValue = Integer.parseInt(substring) + 1;

            String newId_str = Integer.toString(newIdValue);
            if (newIdValue<100){


                int i = 3 - newId_str.length();
                return maatchId.substring(0, 1) + String.format("%0" + i + "d", 0) + newId_str;

            }

            return maatchId.substring(0, 1) +  newId_str;

        }

    }
}
