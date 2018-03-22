import java.sql.*;
import java.util.ArrayList;

public class FantasyDraft {

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public static void main(String[] args) throws Exception {
        FantasyDraft fantasy = new FantasyDraft();
        fantasy.readDatabase("teams");
        fantasy.readDatabase("players");

        // Optional add/delete methods

        // team draft
    }


    public void readDatabase(String dbName) throws Exception {
        Connection connection = createConnection();
        statement = connection.createStatement();

        resultSet = statement.executeQuery("select * from " + dbName + ";");

        if (dbName == "teams") {
            ArrayList<Teams> teams = mapTeamsToObjects(resultSet);

            System.out.println("\nOriginal List:");
            for (Teams t : teams) {
                System.out.println(t.toString());
            }
        } else {
            ArrayList<Players> players = mapPlayersToObjects(resultSet);

            System.out.println("\nOriginal List:");
            for (Players p : players) {
                System.out.println(p.toString());
            }
        }
        connection.close();
    }

    private ArrayList<Teams> mapTeamsToObjects(ResultSet resultSet) throws SQLException {

        ArrayList<Teams> retList = new ArrayList();

        while (resultSet.next()) {
            Teams t = new Teams();
            t.setTeamID(resultSet.getInt("id"));
            t.setLocation(resultSet.getString("location"));
            t.setTeamName(resultSet.getString("name"));

            retList.add(t);
        }
        return retList;
    }

    private ArrayList<Players> mapPlayersToObjects(ResultSet resultSet) throws SQLException {

        ArrayList<Players> retList = new ArrayList();

        while (resultSet.next()) {
            Players p = new Players();
            p.setPlayerID(resultSet.getInt("id"));
            p.setFirstName(resultSet.getString("f_name"));
            p.setLastName(resultSet.getString("l_name"));
            p.setPosition(resultSet.getString("position"));
            retList.add(p);
        }
        return retList;
    }

    private ArrayList<PlayersTeams> mapPlayersTeamsToObjects(ResultSet resultSet) throws SQLException {

        ArrayList<PlayersTeams> retList = new ArrayList();

        while (resultSet.next()) {
            PlayersTeams pt = new PlayersTeams();
            pt.setId(resultSet.getInt("id"));
            pt.setFirstName(resultSet.getString("f_name"));
            pt.setLastName(resultSet.getString("l_name"));
            pt.setPosition(resultSet.getString("position"));
            pt.setLocation(resultSet.getString("location"));
            pt.setTeamName(resultSet.getString("name"));
            retList.add(pt);
        }
        return retList;
    }







    public Connection createConnection() throws Exception {
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost/FantasyDraft?" +
                    "user=root&password=z039@DzzthmT&useSSL=false");
            return connection;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
