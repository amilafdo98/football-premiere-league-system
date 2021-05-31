


import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class LeagueLogWriter {





    public static void serializeClubLog() {
        ObservableList clubList = PremierLeagueManager.getClubslist();

        ArrayList<FootBallClub> footBallClubs = new ArrayList<FootBallClub>();
        Iterator iterator = clubList.iterator();
        while (iterator.hasNext()) {
            FootBallClub fc = (FootBallClub) iterator.next();
            footBallClubs.add(fc);
        }


        FileOutputStream fop = null;
        ObjectOutputStream oos;
        try {
            fop = new FileOutputStream("clubSerialize.txt");

            oos = new ObjectOutputStream(fop);

            oos.writeObject(footBallClubs);

            fop.close();
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList deserializeClublogs() {
        ArrayList<FootBallClub> clublist = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("clubSerialize.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clublist = (ArrayList<FootBallClub>) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("club Info file not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clublist;

    }


    public static void serializeMatchLog() {
        ObservableList matchObservableList = PremierLeagueManager.getMatcheslist();
        ArrayList<Match> matchArrayList = new ArrayList<Match>();
        Iterator iterator = matchObservableList.iterator();
        while (iterator.hasNext()) {
            Match match = (Match) iterator.next();
            matchArrayList.add(match);
        }
        FileOutputStream fop = null;
        ObjectOutputStream oos;
        try {
            fop = new FileOutputStream("matchSerialize.txt");
            oos = new ObjectOutputStream(fop);

            oos.writeObject(matchArrayList);

            fop.close();
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList deserializeMatchlogs() {
        ArrayList<Match>  matchArraylist = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("matchSerialize.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            matchArraylist = (ArrayList<Match>) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("club Info file not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return matchArraylist;

    }


}
