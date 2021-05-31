import java.io.Serializable;

public class SportsClub implements Serializable {

    private String clubName;
    private String clubId;
    private String location;

    public SportsClub(String clubName, String clubId, String location) {
        this.clubName = clubName;
        this.clubId = clubId;
        this.location = location;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "|" + clubId + "|" + clubName + "|";                 //To change body of generated methods, choose Tools | Templates.
    }

    public String getStoreFormat() {
        return clubId + ":" + clubName + ":" + location ;
    }

}
