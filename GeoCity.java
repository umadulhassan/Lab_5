/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocity;
import java.sql.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author zulfiqar.bscs13seecs
 */
public class Geocity {

       // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/GeoCity";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
   public static void main(String[] args) throws SQLException{
   Connection conn = null;
   Statement stmt = null;
   try{
          //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String pop; //Update Query for populating database
//     // sql = "SELECT id, first, last, age FROM Employees";
//      //ResultSet rs = stmt.executeQuery(sql);
//
       String csvFile = "C:\\Users\\Umad Rai\\Documents\\NetBeansProjects\\Geocity\\src\\geocity\\Geo.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        int rows=0,count = 0;
	try {
                System.out.println("Populating Database");
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
                        
		        // use comma as separator
			String[] ln = line.split(cvsSplitBy,-1);//ln is one line read from csv file
                        for(int i =0; i<9;i++){
                            if(ln[i].length()==0){
                                ln[i] = "Not Available";
                            }
                            
                        }
			 pop = "INSERT INTO locations (locId, country, region,city,postalCode,latitude,longitude,metroCode,areaCode) values (?,?,?,?,?,?,?,?,?)";
                        PreparedStatement statement = conn.prepareStatement(pop);
                           statement.setString(1,ln[0]);
                           statement.setString(2,ln[1]);
                           statement.setString(3,ln[2]);
                           statement.setString(4,ln[3]);
                           statement.setString(5,ln[4]);
                           statement.setString(6,ln[5]);
                           statement.setString(7,ln[6]);
                           statement.setString(8,ln[7]);
                           statement.setString(9,ln[8]);
                           rows = statement.executeUpdate();
                           if(rows>0){
                               count++;
                           }
                               if(count ==500){
                                   break;
                               }
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
    
}
