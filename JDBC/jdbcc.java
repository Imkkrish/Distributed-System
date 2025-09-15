// Java program to implement a simple JDBC application
import java.sql.*;
public class jdbcc {
   public static void main(String[] args)
   {
       String url   = "jdbc:mysql://localhost:3306/student";
       String username = "root";
       String password = "admin@123";
 
       String query            = "INSERT INTO student (id, name) VALUES (109, 'abc')";
 
       try {
         
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection c = DriverManager.getConnection( url, username, password);
          Statement st = c.createStatement();
           int count = st.executeUpdate(query);
           System.out.println(count + " rows updated: " );
            st.close();
           c.close();
           System.out.println("Connection closed.");
       }
       catch (ClassNotFoundException e) {
           System.err.println("JDBC Driver not found: "
                              + e.getMessage());
       }
       catch (SQLException e) {
           System.err.println("SQL Error: "
                              + e.getMessage());
       }
   }
}