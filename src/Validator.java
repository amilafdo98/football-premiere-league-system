
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class Validator {

    public static boolean validateMatchEntry(ArrayList<Match> matchList, Match newEntry) throws Exception {

        Iterator<Match> iterator = matchList.iterator();
        boolean isConditionsValid = true;
        String new_c1_id = newEntry.getTeam1_id();
        String new_c2_id = newEntry.getTeam2_id();
        while (iterator.hasNext()) {
            Match match = iterator.next();
            String team1_id = match.getTeam1_id();
            String team2_id = match.getTeam1_id();

            if (newEntry.getMatchID() == match.getMatchID()) {
                isConditionsValid = false;
                throw new Exception("Match id can't be duplicated");
            }
        }
        return isConditionsValid;

    }
    
    public static void validateClubName(List clubList, String newclubName) throws Exception {
        
        Iterator<FootBallClub> iterator = clubList.iterator();
        while (iterator.hasNext()) {
            
            FootBallClub club = iterator.next();
            String clubName = club.getClubName().toUpperCase(Locale.ROOT);

            if (newclubName.toUpperCase(Locale.ROOT).equals(clubName) ){
                throw new Exception("can't use already exist club Name");
            }
            
            
        }

    }


}
