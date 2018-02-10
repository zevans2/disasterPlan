import java.sql.*;
import java.util.ArrayList;

public class DataService {
    private ArrayList<Place> initPlaces = new ArrayList<>();
    private ArrayList<Place> toPrint = new ArrayList<>();


    DataService(){
    }

    public void connectToDb() {
        String host = "jdbc:mysql://turing.cs.missouriwestern.edu:3306/misc";
        String user = "csc254";
        String password = "age126";
        String queryString = "SELECT * from zips2 where " +
                "country like 'US' and " +
                "estimatedpopulation > 0 " +
                "and locationtype like 'PRIMARY'";

        try {
            Connection conn = DriverManager.getConnection(host, user, password);
            //testing connection
            if (conn == null)
                System.out.println("Connection Failed");
            else {
                System.out.println("Successfully connected to " + host);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(queryString);

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
            assert conn != null;
            conn.close();//close connection
        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Failed to connect to " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        }//end catch
    }//end ConnectToDB

    public void sanitizeCities(/*ArrayList<Place>initPlaces, PrintWriter logger*/) {
        for (int i = 0; i < initPlaces.size(); i++) {
            for (int j = i + 1; j < initPlaces.size(); j++) {
                Place current = initPlaces.get(i);
                Place next = initPlaces.get(j);
                if (current.name.equals(next.name)) {
                    current.population += next.population;//add next population to current
                    current.zipcodeList.addAll(next.zipcodeList);
                    initPlaces.remove(next);
                    j--;//decrement j
                }//end matching cities
            }//end for
        }//end for
    }//end sanitize


    public void processRequest(String zipIn, double distance) {
        distance = (distance * distance)/distance; //take absolute value of distance.

        //haversine formula
        int index = 0;
        Place comparator; /*= new Place("", "", "",.00, .00, 0, "", "");
        *///search for zip location
        for (int i = 0; i < initPlaces.size(); i++) {
            if (initPlaces.get(i).zipcodeList.contains(zipIn)){
                index = i;
                break;
            }//exit loop
        }//end for

        comparator = initPlaces.get(index);

        //get Places that are within the requested distance.
        for (Place temp : initPlaces) {
            double distanceFromOrigin = haversine(comparator.latitude, temp.latitude, comparator.longitude, temp.longitude);
            if (distanceFromOrigin <= milesToKm(distance))
                //initPlaces.get(i).setDistanceFromOrigin(distanceFromOrigin);
                toPrint.add(temp);//if within distance add to PrintArray
        }//end for
        System.out.println("Selected City: " + comparator.name + "\n");
    }//end process Request


    public void printResults(){
        System.out.printf("%-25S %-15S %-15S %-15s %-15S\n", "City", "State", "Population", "Miles", "Km" );
        for (Place temp : toPrint) {
            System.out.printf("%-25S %-15S %-15S %-15s %-15S\n", temp.name, temp.region, temp.population, temp.distanceFromOrigin, milesToKm(temp.getDistanceFromOrigin()));
        }//end for
    }//end print


    private double haversine(float lat1, float lat2, float lon1, float lon2) {
        double R = 6371; // in Km
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

    private double milesToKm(double distanceMiles) {
        return distanceMiles * 1.60934;
    }

    public double kmToMiles(double distanceKm) {
        return distanceKm / 1.60934;
    }

}//end data service