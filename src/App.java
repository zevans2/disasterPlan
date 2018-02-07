import java.sql.*;
import java.util.ArrayList;

public class App {
    static Connection conn;
    static ResultSet rs;
    static Statement st;

    public static void main(String[] args) {
        String host = "jdbc:mysql://turing.cs.missouriwestern.edu:3306/misc";
        String user = "csc254";
        String password = "age126";
        String queryString = "SELECT city, region, country, latitude, longitude from cities where zip is like 64501";
        ArrayList<Place> initPlaces = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(host, user, password);
            //testing connection
            if (conn == null)
                System.out.println("Connection Failed");
            else {
                System.out.println("Successfully connected to " + host);
                st = conn.createStatement();
                rs = st.executeQuery(queryString);

                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();
                System.out.println("Number of Columns: " + numberOfColumns);
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("Column %2d: %s (%s) \n", i,
                            rsMetaData.getColumnName(i),
                            rsMetaData.getColumnTypeName(i));
                }//end for

                while(rs.next()){
                    String country = rs.getString("country");
                    String name = rs.getString("city");
                    String region = rs.getString("region");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    Place place = new Place(name, region, country, latitude, longitude);
                    //add place to place arrayList
                    initPlaces.add(place);
                    System.out.println(place);
                }
            }
            conn.close();//close connection
        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Failed to connect to " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        }//end catch


        //Connect to Database todo: log database connection status and connection string
        //read database into place objects.

        //Request Zip Code todo: validate to only real zip codes

        //Request Range in Miles for radius todo:limit distance to width of US

        //Query Place ArrayList for records matching zipcode entered.
        //Places with the same name should be compared based on state, population, and zipcode.
        //Filter results to only return one location name and population for each place.


    }//end main
}//end Program


