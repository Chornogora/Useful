import java.sql.*;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

//An example of JDBC connection
public class JDBCConnector {
    private String userName = "root";
    private String password = "";
    private String connectionURL = "jdbc:postgresql://localhost:5432/test";
    private String tableName = "test0.mytable";
    private volatile AtomicInteger id;

    public JDBCConnector(String username, String pass, String connURL, String tName){
        userName = username;
        password = pass;
        connectionURL = connURL;
        tableName = tName;

        id = getMaxId();
    }

    private AtomicInteger getMaxId(){
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password)){
            System.out.println("Connection successful\n");
            Statement st = connection.createStatement();

            ResultSet set = st.executeQuery("SELECT MAX(id) FROM " + tableName);
            set.next();
            return new AtomicInteger(set.getInt(1));
        }catch(SQLException e){
            System.out.println("Cannot get maximal id");
            e.printStackTrace();
        }
        return null;
    }

    public void insert(String name, String text) throws IllegalArgumentException {
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password)){
            System.out.println("Connection successful\n");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?)");

            statement.setInt(1, id.get()+1);
            statement.setString(2, name);
            statement.setString(3, text);
            statement.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));

            statement.execute();
            id.incrementAndGet();

        }catch(SQLException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Wrong arguments");
        }
    }

    public ResultSet select(){
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password)){
            System.out.println("Connection successful\n");
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName);
            return set;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void printContent(){
        String format = "%|10s|%20s|%80s|%30s|";
        ResultSet set = select();

        try {
            while (set.next()) {
                System.out.println(String.format(format, set.getInt(1), set.getString(2), set.getString(3), set.getTimestamp(4)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
