
public class SchoolFootBallClubs extends FootBallClub {

    private String schoolName;

    public SchoolFootBallClubs(String schoolName, String clubName, String clubId, String location) {
        super(clubName, clubId, location);
        this.schoolName = schoolName;
    }

    @Override
    public String getStoreFormat() {
        return getClubId() + ":"
                + getClubName() + ":"
                + getLocation() + ":"
                + getNumWins() + ":"
                + getNumDraws() + ":"
                + getNumDefeats() + ":"
                + getPoints() + ":"
                + getNumGoals() + ":"
                + getNumPlayedMatches() + ":"
                + schoolName;
    }

}
