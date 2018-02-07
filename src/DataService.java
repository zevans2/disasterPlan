import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class DataService {
    private static Connection conn;
    private static ResultSet rs;
    private static Statement st;
    private ArrayList<Place> initPlaces = new ArrayList<>();
    private ArrayList<Place> toPrint = new ArrayList<>();


    public DataService() throws FileNotFoundException {
    }

    private PrintWriter logger = new PrintWriter(new File("disaster.log.txt"));

    public void connectToDb() {
        String host = "jdbc:mysql://turing.cs.missouriwestern.edu:3306/misc";
        String user = "csc254";
        String password = "age126";
        String queryString = "SELECT * from zips2 where " +
                "country like 'US' and " +
                "estimatedpopulation > 0 " +
                "and locationtype like 'PRIMARY' OR " +
                "locationtype like 'ACCEPTABLE'";

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
                //System.out.println("Number of Columns: " + numberOfColumns);
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("Column %2d: %s (%s) \n", i,
                            rsMetaData.getColumnName(i),
                            rsMetaData.getColumnTypeName(i));
                }//end for

                while (rs.next()) {
                    String country = rs.getString("country");
                    String name = rs.getString("city");
                    String state = rs.getString("state");
                    float latitude = rs.getFloat("lat");
                    float longitude = rs.getFloat("long");
                    int population = rs.getInt("estimatedpopulation");
                    String zipcode = rs.getString("zipcode");
                    String zipType = rs.getString("zipcodetype");
                    Place place = new Place(name, state, country, latitude, longitude, population, zipcode, zipType);
                    //add place to place arrayList
                    initPlaces.add(place);
                    System.out.println(place);
                }
                System.out.println(initPlaces.toString());
                System.out.println(initPlaces.size());
            }
            conn.close();//close connection
        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Failed to connect to " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        }//end catch
    }//end ConnectToDB

    public void sanitizeCities(/*ArrayList<Place>initPlaces, PrintWriter logger*/) {
        for (int i = 1; i < initPlaces.size(); i++) {
            Place prev = initPlaces.get(i - 1);
            Place current = initPlaces.get(i);

            if (current.zipcode == prev.zipcode) {
                logger.print("**Duplicate Zipcode found**\nSanitizing Database: Removing ");
                if (current.zipType == "ACCEPTABLE") {
                    logger.print(prev.toString() + " and replacing with " + current.toString());
                    initPlaces.remove(prev);
                } else {
                    initPlaces.remove(current);
                    logger.print(current.toString() + " and replacing with " + prev.toString());
                }
            }//end matching zipcodes
        }//end for
    }//end sanitize


    public void processRequest(String zipIn, double distance) {
        //haversine formula
        int index = 0;
        Place comparator; /*= new Place("", "", "",.00, .00, 0, "", "");
        *///search for zip location
        for (int i = 0; i < initPlaces.size(); i++) {
            if (initPlaces.get(i).zipcode == zipIn) {
                index = i;
                break;
            }//exit loop
        }//end for
        comparator = initPlaces.get(index);

        //get Places that are within the requested distance.
        for(int i = 0; i< initPlaces.size(); i++){
            Place temp = initPlaces.get(i);
            if(haversine(comparator.latitude,temp.latitude, comparator.longitude, temp.longitude) <= milesToKm(distance))
                toPrint.add(initPlaces.get(i));
        }//end for
    }//end process Request


    public void printResults(){
        System.out.println("City\t\t\tState\t\t\tTotal Population");
        for(int i = 0; i<toPrint.size(); i++){
            Place temp = toPrint.get(i);
            System.out.println(temp.name+"\t\t" + temp.region +"\t\t" +temp.population);
        }
    }


    public double haversine(float lat1, float lat2, float lon1, float lon2) {
        double R = 6371e3; // in metres
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaGamma = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaGamma / 2) * Math.sin(deltaGamma / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    public double milesToKm(double distanceMiles) {
        double distanceKm = distanceMiles * 1.60934;
        return distanceKm;
    }

    public double kmToMiles(double distanceKm) {
        double distanceMiles = distanceKm / 1.60934;
        return distanceMiles;
    }

}//end data service