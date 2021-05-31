import java.io.Serializable;

public class FootBallClub extends SportsClub implements Serializable {

    private int numWins;
    private int numDraws;
    private int numDefeats;
    private int points;
    private int numGoals;
    private int numPlayedMatches;

    public FootBallClub(int numWins, int numdraws, int numDefeats, int points, int numGoals, int numPlayedMatches, String clubName, String clubId, String location) {
        super(clubName, clubId, location);
        this.numWins = numWins;
        this.numDraws = numdraws;
        this.numDefeats = numDefeats;
        this.points = points;
        this.numGoals = numGoals;
        this.numPlayedMatches = numPlayedMatches;
    }


    public FootBallClub(String clubName, String clubId, String location) {
        super(clubName, clubId, location);
    }

    public FootBallClub() {
        super(null, null, null);
    }

    public int getNumWins() {
        return numWins;
    }

    public void setNumWins(int numWins) {
        this.numWins = numWins;
    }

    public int getNumDraws() {
        return numDraws;
    }

    public void setNumDraws(int numDraws) {
        this.numDraws = numDraws;
    }

    public int getNumDefeats() {
        return numDefeats;
    }

    public void setNumDefeats(int numDefeats) {
        this.numDefeats = numDefeats;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumGoals() {
        return numGoals;
    }

    public void setNumGoals(int numGoals) {
        this.numGoals = numGoals;
    }

    public int getNumPlayedMatches() {
        return numPlayedMatches;
    }

    public void setNumPlayedMatches(int numPlayedMatches) {
        this.numPlayedMatches = numPlayedMatches;
    }

    public void incrementWins() {
        numWins++;
    }

    public void incrementDraws() {
        numDraws++;
    }

    public void incrementDefeats() {
        numDefeats++;
    }

    public void incrementPlayedMatches() {
        numPlayedMatches++;
    }

    public void incrementPoints(int incrementPoints) {
        points += incrementPoints;
    }

    public void incrementGoals(int goalCount) {
        numGoals += goalCount;
    }

    public void decrementWins() {
        numWins--;
    }

    public void decrementDraws() {
        numDraws--;
    }

    public void decrementDefeats() {
        numDefeats--;
    }

    public void decrementPlayedMatches() {
        numPlayedMatches--;
    }

    public void decrementPoints(int decrementPoints) {
        points -= decrementPoints;
    }

    public void decrementGoals(int goalCount) {
        numGoals -= goalCount;
    }

    @Override
    public String toString() {
        String name = super.getClubName();
        String formatedName = name + String.format("%0" + (16 - name.length() + "d"), 0).replace("0", " ");
        String str_points = Integer.toString(points);
        String formatedPoints = str_points + String.format("%0" + (6 - str_points.length() + "d"), 0).replace("0", " ");
        return "|   " + super.getClubId() + "    |  " + formatedName + "|    " + formatedPoints + "    |";
    }

    @Override
    public String getStoreFormat() {
        return getClubId() + ":"
                + getClubName() + ":"
                + getLocation() + ":"
                + numWins + ":"
                + numDraws + ":"
                + numDefeats + ":"
                + points + ":"
                + numGoals + ":"
                + numPlayedMatches;
    }

}
