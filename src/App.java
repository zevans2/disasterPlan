import com.mysql.jdbc.StringUtils;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static double distance = -99;
    static String zipIn = "fail";
    static boolean isZip;
    static boolean isDistance;

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        DataService dataService = new DataService();//Launch Data Service Handler

        dataService.connectToDb();//Connect to zip2Database

        dataService.sanitizeCities();//Remove all duplicate zipcodes and city names

        //request Zip and Distance
        requestZip(input);
        requestDistance(input);

        dataService.processRequest(zipIn, distance);//calculate distance
        dataService.printResults();//print places within requested distance

    }//end main

    public static void requestZip(Scanner input){
        while (!isZip) {
            System.out.println("Enter 5 digit zipcode: ");
            zipIn = input.nextLine();
            if (StringUtils.isStrictlyNumeric(zipIn) == true) {
                if(zipIn.length() == 5)
                    isZip = true;
                else
                    System.out.println("Zip Codes must be 5 characters long");
            }else
                System.out.println("Please enter a valid zip code");
        }//end ziptest
    }//end requestZip

    public static void requestDistance(Scanner input){
        while(!isDistance){
            System.out.println("Enter distance in miles: ");
            String temp = input.nextLine();
            if(StringUtils.isStrictlyNumeric(temp) == true){
                distance = Double.valueOf(temp);
                if(distance < 3000)
                    isDistance=true;
                else
                    System.out.println("Distance cannot be greater than 3000 miles");
            }
            else
                System.out.println("Please enter a numeric distance less than 3000 miles. \nOnly use whole numbers.");
        }//end distance test
    }//end request Distance

}//end Program


