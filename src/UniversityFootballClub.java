public class UniversityFootballClub extends FootBallClub  {

    private String universityName;

    public UniversityFootballClub(String universityName, String clubName, String clubId, String location) {
        super(clubName, clubId, location);
        this.universityName = universityName;
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
                + getNumPlayedMatches()+":"
                +universityName;
    }

}
