import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Menu extends Application {


    public static void main(String[] args) {

        launch(args);
    }


    private Stage stage;

    // random match properties

    private TextField tf_id_1;
    private TextField tf_id_2;
    private TextField tf_name_1;
    private TextField tf_name_2;
    private TextField tf_goals_1;
    private TextField tf_goals_2;

    private TableView leagueTable;
    private TableView matchTable;

    private TextField date_textField;

    private ObservableList<FootBallClub> leagueTableData;
    private ObservableList<Match> matchTableData;

    Scene clubTablescene;
    Scene matchTablescene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            scanner = new Scanner(System.in);
            lm = PremierLeagueManager.getInstance();

            lm.restoreClubRecord();
            lm.restoreMatchRecord();
        }

        stage = primaryStage;
        primaryStage.setTitle("Premier League Management System");

        BorderPane pl_Layout = new BorderPane();
        pl_Layout.setCenter(addLeagueTableLayout());
        pl_Layout.setLeft(addLeftLayout_LeagueTable());

        clubTablescene = new Scene(pl_Layout, 1250, 600);
        primaryStage.setScene(clubTablescene);

        BorderPane matchMain_Layout = new BorderPane();
        matchMain_Layout.setCenter(addMatchTableLayout());
        matchMain_Layout.setLeft(addmatchLeftLayout());

        matchTablescene = new Scene(matchMain_Layout, 1250, 600);
        primaryStage.setScene(matchTablescene);

        matchTableData = FXCollections.observableArrayList();
        leagueTableData = FXCollections.observableArrayList();


        System.out.println("Premier League  System");

        displayCommandLineMenu();

    }

    private Node addmatchLeftLayout() {
        VBox leftLayout = new VBox();

        leftLayout.prefWidth(50);
        leftLayout.setPadding(new Insets(10, 30, 30, 30));
        leftLayout.setSpacing(50);
        Label dateLabel = new Label("Date");


        date_textField = new TextField("");

        Button searchButton = new Button("Search Matches");
        searchButton.setPrefSize(200, 20);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String dateStr = date_textField.getText();
                //check validity if ok --- else ---
                try {
                    Date searchDate = sdf.parse(dateStr);
                    ObservableList searchMatches = (ObservableList) lm.searchMatches(searchDate);
                    refreshMatchTableData(searchMatches);
                    date_textField.setStyle("-fx-text-inner-color: black;");
                } catch (ParseException e) {

                    date_textField.setStyle("-fx-text-inner-color: red;");
                    date_textField.setText("YYYY-MM-dd");
                }

            }
        });


        Button sortMatchesButton = new Button("Sort all matches ");
        sortMatchesButton.setPrefSize(200, 20);
        sortMatchesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                date_textField.setText("");
                refreshMatchTableData(lm.sortMatchesOnDate());

            }
        });


        Button backButton = new Button("Back to Command Line");
        backButton.setPrefSize(200, 20);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                date_textField.setText("");
                stage.close();
                displayCommandLineMenu();

            }
        });

        leftLayout.getChildren().addAll(dateLabel, date_textField, searchButton, sortMatchesButton, backButton);

        return leftLayout;
    }


    private VBox addButtonLayout() {

        VBox buttonLayout = new VBox();
        buttonLayout.setSpacing(50);
        buttonLayout.setPadding(new Insets(20, 20, 20, 20));


        Button generateButton = new Button("Generate Match");
        generateButton.setPrefSize(200, 20);
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateAndAddRandomMatch();

                refreshLeagueTableData(lm.sortOnPoints());


            }
        });


        Button refreshButton = new Button("Refresh Premier League Table");
        refreshButton.setPrefSize(200, 20);
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshLeagueTableData(lm.sortOnPoints());


                displayPremierLeagueTable();

            }
        });

        Button sortWinsButton = new Button("Sort on Wins ");
        sortWinsButton.setPrefSize(200, 20);
        sortWinsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                refreshLeagueTableData(lm.sortOn_NumberWins());


            }
        });

        Button sortOnGoalsButton = new Button("Sort on Goals");
        sortOnGoalsButton.setPrefSize(200, 20);
        sortOnGoalsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                refreshLeagueTableData(lm.sortOnGoalsScored());


            }
        });


        Button backButton = new Button("Back to Command Line");
        backButton.setPrefSize(200, 20);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tf_id_1.setText("");
                tf_id_2.setText("");
                tf_name_1.setText("");
                tf_name_2.setText("");
                tf_goals_1.setText("");
                tf_goals_2.setText("");

                stage.close();
                displayCommandLineMenu();
            }
        });

        buttonLayout.getChildren().addAll(generateButton, refreshButton, sortWinsButton, sortOnGoalsButton, backButton);
        return buttonLayout;

    }

    private void refreshLeagueTableData(ObservableList list) {
        leagueTableData.clear();


        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            FootBallClub club = (FootBallClub) iterator.next();
            leagueTableData.add(club);
        }
        leagueTable.setItems(leagueTableData);

    }

    private void refreshMatchTableData(ObservableList list) {

        matchTableData.clear();

        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Match match = (Match) iterator.next();
            matchTableData.add(match);
        }

        matchTable.setItems(matchTableData);


    }

    private void generateAndAddRandomMatch() {
        final ObservableList clubslist = PremierLeagueManager.getClubslist();


        Random random = new Random();
        int teamCount = lm.getTeamCount();
        if (teamCount < 2) {
            System.out.println("Can't generate a match. Not enough teams");
            return;
        }

        int team1_index = random.nextInt(teamCount);
        int team2_index = -1;

        boolean isTeam2IndexGenerated = false;
        while (!isTeam2IndexGenerated) {
            team2_index = random.nextInt(teamCount);

            if (team1_index != team2_index) {
                isTeam2IndexGenerated = true;
            }
        }

        FootBallClub club1 = (FootBallClub) clubslist.get(team1_index);
        FootBallClub club2 = (FootBallClub) clubslist.get(team2_index);


        int maxGoalsRnd = 10;
        int team1Goals = random.nextInt(maxGoalsRnd);
        int team2Goals = random.nextInt(maxGoalsRnd);
        String club1Id = club1.getClubId();
        String club2Id = club2.getClubId();
        String club1Name = club1.getClubName();
        String club2Name = club2.getClubName();

        tf_id_1.setText(club1Id);
        tf_id_2.setText(club2Id);
        tf_name_1.setText(club1Name);
        tf_name_2.setText(club2Name);
        tf_goals_1.setText(String.valueOf(team1Goals));
        tf_goals_2.setText(String.valueOf(team2Goals));


        String matchId = IDgenerator.getNextMatchId();

        Date d2_now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date d1_past = calendar.getTime();

        Date randomDate = new Date(ThreadLocalRandom.current().nextLong(d1_past.getTime(), d2_now.getTime()));


        Match ranomMatch = new Match(matchId, club1Id, club2Id, team1Goals, team2Goals, randomDate);
        lm.addMatch(ranomMatch);
        System.out.println("match generated");

    }

    private GridPane addLeftLayout_LeagueTable() {
        GridPane grid = new GridPane();
        //grid.prefHeight(50);
        grid.prefWidth(50);
        grid.setPadding(new Insets(10, 30, 30, 30));
        grid.setHgap(10);
        grid.setVgap(20);
        //  grid.setPadding(new Insets(0,10,0,10));

        Label team1Label = new Label("Team 1");
        grid.add(team1Label, 1, 0);

        Label team2Label = new Label("Team 2");
        grid.add(team2Label, 2, 0);

        Label teamIdLabel = new Label("Team ID");
        grid.add(teamIdLabel, 0, 1);

        Label nameLabel = new Label("Name");
        grid.add(nameLabel, 0, 2);

        Label goalsLabel = new Label("Goals");
        grid.add(goalsLabel, 0, 3);


        tf_id_1 = new TextField();
        tf_id_1.setEditable(false);
        grid.add(tf_id_1, 1, 1);

        tf_id_2 = new TextField();
        tf_id_2.setEditable(false);
        grid.add(tf_id_2, 2, 1);

        tf_name_1 = new TextField();
        tf_name_1.setEditable(false);
        grid.add(tf_name_1, 1, 2);

        tf_name_2 = new TextField();
        tf_name_2.setEditable(false);
        grid.add(tf_name_2, 2, 2);

        tf_goals_1 = new TextField();
        tf_goals_1.setEditable(false);
        grid.add(tf_goals_1, 1, 3);

        tf_goals_2 = new TextField();
        tf_goals_2.setEditable(false);
        grid.add(tf_goals_2, 2, 3);

        VBox buttonLayout = addButtonLayout();
        grid.add(buttonLayout, 0, 4, 5, 2);
        return grid;
    }

    private Node addLeagueTableLayout() {


        TableColumn<FootBallClub, String> clubIdColumn = new TableColumn<>("Club ID");
        clubIdColumn.setMinWidth(50);
        clubIdColumn.setCellValueFactory(new PropertyValueFactory<>("clubId"));


        TableColumn<FootBallClub, String> clubNameColumn = new TableColumn<>("Club Name");
        clubNameColumn.setMinWidth(100);
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));

        TableColumn<FootBallClub, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setMinWidth(100);
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<FootBallClub, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(50);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));


        TableColumn<FootBallClub, Integer> goalsColumn = new TableColumn<>("Goals");
        goalsColumn.setMinWidth(50);
        goalsColumn.setCellValueFactory(new PropertyValueFactory<>("numGoals"));

        TableColumn<FootBallClub, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setMinWidth(50);
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("numWins"));


        TableColumn<FootBallClub, Integer> drawsColumn = new TableColumn<>("Draws");
        drawsColumn.setMinWidth(50);
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("numDraws"));

        TableColumn<FootBallClub, Integer> defeatsColumn = new TableColumn<>("Defeats");
        defeatsColumn.setMinWidth(50);
        defeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numDefeats"));

        TableColumn<FootBallClub, Integer> num_matchesColumn = new TableColumn<>("Played Matches");
        num_matchesColumn.setMinWidth(50);
        num_matchesColumn.setCellValueFactory(new PropertyValueFactory<>("numPlayedMatches"));


        leagueTable = new TableView<>();
        leagueTable.setItems(leagueTableData);
        leagueTable.getColumns().addAll(clubIdColumn, clubNameColumn, locationColumn, pointsColumn, goalsColumn, winsColumn, drawsColumn, defeatsColumn, num_matchesColumn);


        HBox tableLayout = new HBox();
        tableLayout.setPadding(new Insets(5, 5, 5, 5));
        tableLayout.getChildren().addAll(leagueTable);

        return tableLayout;
    }

    private Node addMatchTableLayout() {

        TableColumn<Match, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateStr"));

        TableColumn<Match, String> matchIDColumn = new TableColumn<>("Match ID");
        matchIDColumn.setMinWidth(50);
        matchIDColumn.setCellValueFactory(new PropertyValueFactory<>("matchID"));

        TableColumn<Match, String> team1_idColumn = new TableColumn<>("Team1 Id");
        team1_idColumn.setMinWidth(50);
        team1_idColumn.setCellValueFactory(new PropertyValueFactory<>("team1_id"));

        TableColumn<Match, String> team2_idColumn = new TableColumn<>("Team2 Id");
        team2_idColumn.setMinWidth(50);
        team2_idColumn.setCellValueFactory(new PropertyValueFactory<>("team2_id"));

        TableColumn<Match, String> team1_nameColumn = new TableColumn<>("Team1 Name");
        team1_nameColumn.setMinWidth(100);
        team1_nameColumn.setCellValueFactory(new PropertyValueFactory<>("team1_name"));

        TableColumn<Match, String> team2_nameColumn = new TableColumn<>("Team2 Name");
        team2_nameColumn.setMinWidth(100);
        team2_nameColumn.setCellValueFactory(new PropertyValueFactory<>("team2_name"));

        TableColumn<Match, Integer> team1_goalsColumn = new TableColumn<>("Team1 Goals");
        team1_goalsColumn.setMinWidth(50);
        team1_goalsColumn.setCellValueFactory(new PropertyValueFactory<>("team1_goals"));

        TableColumn<Match, Integer> team2_goalsColumn = new TableColumn<>("Team2 Goals");
        team2_goalsColumn.setMinWidth(50);
        team2_goalsColumn.setCellValueFactory(new PropertyValueFactory<>("team2_goals"));

        TableColumn<Match, Integer> team1_scoreColumn = new TableColumn<>("Team1 Score");
        team1_scoreColumn.setMinWidth(50);
        team1_scoreColumn.setCellValueFactory(new PropertyValueFactory<>("team1_score"));

        TableColumn<Match, Integer> team2_scoreColumn = new TableColumn<>("Team2 Score");
        team2_scoreColumn.setMinWidth(50);
        team2_scoreColumn.setCellValueFactory(new PropertyValueFactory<>("team2_score"));

        matchTable = new TableView<>();
        matchTableData = lm.sortMatchesOnDate();
        matchTable.setItems(matchTableData);

        matchTable.getColumns().addAll(dateColumn, matchIDColumn, team1_idColumn, team2_idColumn, team1_nameColumn, team2_nameColumn, team1_goalsColumn, team2_goalsColumn, team1_scoreColumn, team2_scoreColumn);


        HBox tableLayout = new HBox();
        tableLayout.setPadding(new Insets(5, 5, 5, 5));
        tableLayout.getChildren().addAll(matchTable);

        return tableLayout;
    }


    Scanner scanner;
    LeagueManager lm;
    SimpleDateFormat sdf;

    void displayCommandLineMenu() {


        while (true) {
            System.out.println();
            System.out.println("Press 1 to Add A Club: ");
            System.out.println("Press 2 to Delete A Club: ");
            System.out.println("Press 3 To View Statistics Of Selected Club: ");
            System.out.println("Press 4 To Display Premiere League Table: ");
            System.out.println("Press 5 To Add A Match: ");
            System.out.println("Press 6 To Save Information: ");
            System.out.println("Press 7 To View Match Table: ");
            System.out.println("Press 8 To Exit!!!!!");
            System.out.println();


//            System.out.println("Press 8 To Edit A Club: ");
//            System.out.println("Press 9 To Edit A Match: ");
//            System.out.println("Press 10 To Delete A Match: ");


            String command = scanner.next();

            if (command.equals("1")) {

                if (lm.getTeamCount() < 20) {
                    addClub();
                } else {
                    System.out.println("Team count is reached to its max");
                }
            } else if (command.equals("2")) {
                deleteClub();
            } else if (command.equals("3")) {
                FootBallClub club = readValidClub();
                displayClubStatics(club);
            } else if (command.equals("4")) {
                displayPremierLeagueTable();
                stage.setScene(clubTablescene);
                stage.show();
                break;
            } else if (command.equals("5")) {
                addMatch();
            } else if (command.equals("6")) {

                lm.updatePremierLeagueLog();

            } else if (command.equals("7")) {
                displaymatches();

                stage.setScene(matchTablescene);
                stage.show();
                break;
            } else if (command.equals("8")) {

                System.out.println("System Closed");
                System.exit(0);

            }  else {
                System.out.println("Enter a Valid Number");
            }
        }
    }


    void displayPremierLeagueTable() {


        ObservableList clubslist = lm.sortOnPoints();
        refreshLeagueTableData(clubslist);


        if (clubslist.size() == 0) {
            System.out.println("do not have any Club details");
            return;
        }


        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println("|  Club ID |   Club Name     |    Location     | Points |  Goals |  Wins  |  Draws  | Defeats |  Played Matches |");

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Iterator iterator = clubslist.iterator(); iterator.hasNext(); ) {
            FootBallClub fbc = (FootBallClub) iterator.next();
            String[] split = fbc.getStoreFormat().split(":");

            String s_clubId = split[0];
            String s_clubName = split[1];
            String s_location = split[2];
            String s_wins = split[3];
            String s_draws = split[4];
            String s_defeats = split[5];
            String s_points = split[6];
            String s_numGoals = split[7];
            String s_PlayedMatches = split[8];


            String f_clubId =s_clubId+String.format("%0" + (8 -s_clubId.length() + "d"), 0).replace("0", " ");
            String f_clubName = s_clubName+String.format("%0" + (15 -s_clubName.length() + "d"), 0).replace("0", " ");
            String f_location = s_location+String.format("%0" + (15 -s_location.length() + "d"), 0).replace("0", " ");
            String f_points = s_points+String.format("%0" + (6 -s_points.length() + "d"), 0).replace("0", " ");
            String f_wins = s_wins+String.format("%0" + (6 -s_wins.length() + "d"), 0).replace("0", " ");
            String f_draws =s_draws+String.format("%0" + (6 -s_draws.length() + "d"), 0).replace("0", " ");
            String f_defeats = s_defeats+String.format("%0" + (7 -s_defeats.length() + "d"), 0).replace("0", " ");

            String f_numGoals = s_numGoals+String.format("%0" + (7 -s_numGoals.length() + "d"), 0).replace("0", " ");
            String f_PlayedMatches =s_PlayedMatches+String.format("%0" + (15 -s_PlayedMatches.length() + "d"), 0).replace("0", " ");





            System.out.println("|  " + f_clubId + "|  " + f_clubName + "|  " + f_location + "|  "+ f_points +"|  "+ f_numGoals + "|  "+ f_wins + "|  " + f_draws + "|  " + f_defeats + "|  "+ f_PlayedMatches + "|  ");
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");


        tf_id_1.setText("");
        tf_id_2.setText("");
        tf_name_1.setText("");
        tf_name_2.setText("");
        tf_goals_1.setText("");
        tf_goals_2.setText("");

    }

    private void addClub() {
        FootBallClub club = new FootBallClub();

        club.setClubId(IDgenerator.getNextClubId());
        String clubName = readValidClubName();
        club.setClubName(clubName);
        System.out.print("Enter club Location :");
        club.setLocation(scanner.next());
        lm.addClub(club);
        System.out.println("Club added...");
    }

    private void deleteClub() {
        do {
            System.out.println("Cancel:0    Enter Club ID for Deleting:   ");

            String clubId = scanner.next().toUpperCase(Locale.ROOT);
            if (clubId.equals("0")) {
                return;
            }

            try {
                SportsClub club = lm.getClubStatics(clubId);
                if (club != null) {
                    lm.deleteClub(club.getClubId());
                    System.out.println("Club deleted...");
                    return;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }


        } while (true);

    }

    private void editClub() {


        do {
            System.out.println("Cancel:0    Enter Club ID for Editing:   ");
            String clubId = scanner.next().toUpperCase(Locale.ROOT);
            if (clubId.equals("0")) {
                return;
            }

            try {
                SportsClub club = lm.getClubStatics(clubId);
                if (club != null) {
                    editClubStatics(clubId, club);
                    return;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }


        } while (true);

    }

    private void editClubStatics(String clubId, SportsClub club) {
        System.out.println("Enter relevant Number for Editing  ");
        do {

            System.out.println("Club Name:1 club Location:2   Finish:3");
            try {
                String command = scanner.next();
                if (command.equals("1")) {
                    String validClubName = readValidClubName();
                    club.setClubName(validClubName);
                    lm.setClub(clubId, club);
                } else if (command.equals("2")) {
                    String validlocation = scanner.next();
                    club.setLocation(validlocation);
                    lm.setClub(clubId, club);
                } else if (command.equals("3")) {
                    return;
                } else {

                    System.out.println("Enter a Valid Number");
                }
            } catch (Exception e) {
                System.out.println("Enter valid Number ");

            }

        } while (true);

    }

    private void displaymatches() {

        ObservableList matchesListsorted = lm.sortMatchesOnDate();

        refreshMatchTableData(matchesListsorted);

        Iterator iterator = matchesListsorted.iterator();
        if (matchesListsorted.size() == 0) {
            System.out.println("do not have any Match details");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("| Match ID |   Match Date  |  Team 1 Id  |  Team 2 Id  | Team 1 Name | Team 2 Name | Team 1 Goals  | Team 2 Goals  |");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        while (iterator.hasNext()) {
            Match match = (Match) iterator.next();
            System.out.println(match.toString());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }

    private void addMatch() {

        String matchID = IDgenerator.getNextMatchId();
        String team1_ID = readValidClubIdForMatch("Club 1", "");
        String team2_ID = readValidClubIdForMatch("Club 2", team1_ID);
        int team1_goals = readNumericValue("Club 1", 20);
        int team2_goals = readNumericValue("Club 2", 20);
        Date matchDate = readValidDate();

        Match match = new Match(matchID, team1_ID, team2_ID, team1_goals, team2_goals, matchDate);
        lm.addMatch(match);
        System.out.println("Match added...");

    }

    private void deleteMatch() {
        do {
            System.out.println("Cancel:0    Enter Match ID for Deleting:   ");
            String matchId = scanner.next().toUpperCase(Locale.ROOT);
            if (matchId.equals("0")) {
                return;
            }
            try {
                Match match = lm.getMatch(matchId);
                if (match != null) {
                    lm.deleteMatch(match.getMatchID());
                    return;
                }
            } catch (Exception ex) {
                System.out.println("Invalid Match ID");
            }
        } while (true);
    }

    private void editMatch() {


        do {
            System.out.println("Cancel:0    Enter Club ID for Editing:   ");
            String matchId = scanner.next().toUpperCase(Locale.ROOT);
            if (matchId.equals("0")) {
                return;
            }

            try {
                Match match = lm.getMatch(matchId);
                if (match != null) {
                    editMatchStactics(match);
                    return;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        } while (true);
    }

    private void editMatchStactics(Match match) {
        System.out.println("Enter the relevant field no for editing");

        do {
            System.out.println("Date:1 Club1 ID:2 Club2 ID: 3  Club1 goals:4  Club2 goals:5   End:6 ");
            String field_No = scanner.next();
            if (field_No.equals("1")) {
                match.setDate(readValidDate());
            } else if (field_No.equals("2")) {
                String readClubID = readValidClubIdForMatch("Club 1", match.getTeam2_id());
                match.setTeam1_id(readClubID);
            } else if (field_No.equals("3")) {
                String readClubID = readValidClubIdForMatch("Club 2", match.getTeam1_id());
                match.setTeam2_id(readClubID);
            } else if (field_No.equals("4")) {
                match.setTeam1_goals(readNumericValue("Club 1", 20));
            } else if (field_No.equals("5")) {
                match.setTeam2_goals(readNumericValue("club 2", 20));
            } else if (field_No.equals("6")) {
                lm.deleteMatch(match.getMatchID());
                lm.addMatch(match);
                return;
            } else {
                System.out.println("Enter a valid Number");
            }
        } while (true);

    }


    private FootBallClub readValidClub() {
        do {
            System.out.println("Enter Valid Club ID ");
            String clubId = scanner.next().toUpperCase(Locale.ROOT);


            try {
                FootBallClub clubStatics = lm.getClubStatics(clubId);
                return clubStatics;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }


        } while (true);
    }

    private void displayClubStatics(FootBallClub club) {

        System.out.println("--------------------------------");
        System.out.println("Statics of the selected Club ");
        System.out.println("--------------------------------");

        System.out.println("Team ID        : " + club.getClubId());
        System.out.println("Name           : " + club.getClubName());
        System.out.println("Location       : " + club.getLocation());
        System.out.println("Score          : " + club.getPoints());
        System.out.println("Wins           : " + club.getNumWins());
        System.out.println("Draws          : " + club.getNumDraws());
        System.out.println("Defeats        : " + club.getNumDefeats());
        System.out.println("Matches        : " + club.getNumPlayedMatches());
        System.out.println("Goals          : " + club.getNumGoals());
        System.out.println("--------------------------------");


    }

    private Match readValidMatch() {

        do {
            System.out.println("Enter Valid Match ID ");
            String matchId = scanner.next().toUpperCase(Locale.ROOT);
            try {
                Match match = lm.getMatch(matchId);
                return match;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        } while (true);
    }

    private void displayMatchDetails(Match match) {

        System.out.println("--------------Match Details------------");

        System.out.println("Date :" + match.getDate());
        System.out.println("Team 1 ID :" + match.getTeam1_id());
        System.out.println("Team 2 ID :" + match.getTeam2_id());
        System.out.println("Team 1 Goals :" + match.getTeam1_goals());
        System.out.println("Team 2 Goals :" + match.getTeam2_goals());
        System.out.println("Team 1 Score :" + match.getTeam1_score());
        System.out.println("Team 2 Score :" + match.getTeam2_score());

        System.out.println("----------------------------------------");

    }


    private String readValidClubName() {

        String newclubName;
        do {
            System.out.print("Enter club Name:");
            newclubName = scanner.next();
            try {
                Validator.validateClubName(PremierLeagueManager.getClubslist(), newclubName);
                return newclubName;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } while (true);

    }

    private String readValidClubIdForMatch(String team, String selectedID) {
        ObservableList clubs = PremierLeagueManager.getClubslist();
        boolean isCorrect = false;

        do {
            System.out.println("Enter Correct ID for " + team);
            String id = scanner.next().toUpperCase(Locale.ROOT);

            for (Iterator iterator = clubs.iterator(); iterator.hasNext(); ) {
                SportsClub club = (SportsClub) iterator.next();
                if (id.equals(club.getClubId()) && !selectedID.equals(id)) {

                    return id;
                }

            }

        } while (true);

    }

    private Date readValidDate() {
        boolean isValid = false;
        do {
            System.out.println("Enter Match Date (in  YYYY-MM-dd format ) :");
            String dateStr = scanner.next();
            try {
                Date date = sdf.parse(dateStr);

                if (!dateStr.equals(sdf.format(date))) {
                    date = null;

                    System.out.println("invalid Date...");
                } else {
                    return date;
                }

            } catch (ParseException e) {
                System.out.println("Invalid Format....");
                scanner.nextLine();
            }
        } while (true);

    }

    private int readNumericValue(String team, int maxValue) {
        boolean isValid = false;
        do {

            System.out.println("Enter Goals of " + team);

            try {
                int goals = scanner.nextInt();
                if (goals > 20 & goals < 0) {
                    System.out.println("Input value is out of range...make sure to enter correct value ");
                } else {

                    return goals;
                }

            } catch (InputMismatchException e) {
                System.out.println("Input is Invalid.. ");
                scanner.nextLine();
            }

        } while (isValid);
        return 0;

    }


}
