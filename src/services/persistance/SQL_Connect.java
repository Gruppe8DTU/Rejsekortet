package services.persistance;
import java.sql.*;
import java.util.ArrayList;
/*
 * Skaber forbindelse til vores database
 */ 
//=== Provides an interface to execute SQL statements
// WHAT THE COMMIT!
//=== 2011 Henrik Hauge - vi har �ndret en smule i den 
public class SQL_Connect {
	private String driver			= "com.mysql.jdbc.Driver";;
	private String database_url	= "jdbc:mysql://localhost:8889/Matador";
	private String username		= "root";	
	private String password		= "root";
	
	  public Object[][] executeQuery(String query) throws SQLException
	  {  
	    if (query == null)
	      return null;
	 
	    Connection connection 	= null;
	    Statement statement 	= null;
	    ResultSet rs 			= null;
	    
	    Object[][] res 	= null;
	    
	    try
	    {
	      // Connect
	      Class.forName(driver);
	      connection = DriverManager.getConnection(database_url, username, password);                   
	      statement = connection.createStatement();  
	          
	      // execute the query
	      rs = statement.executeQuery(query);         
	    
	      // get number of columns in the result (= number of attributes)
	      ResultSetMetaData metaData = rs.getMetaData();
	      int numberOfColumns = metaData.getColumnCount();      
	  
	      // transfer resultset to ArrayList of array of Objects
	      ArrayList<Object[]> al = new ArrayList<Object[]>();
	      while (rs.next())
	      {
	        Object[] row = new Object[numberOfColumns]; 
	        for (int i = 1; i <= numberOfColumns; i++)
	          row[i-1] = rs.getObject(metaData.getColumnName(i));
	        al.add(row);
	      } 
	      res = al.toArray(new Object[0][0]); // convert to 2-dim array
	    }
	    catch (Exception e)
	    {
	       e.printStackTrace();
	       System.exit(1);
	    }
	    finally
	    {
	      rs.close();
	      statement.close();
	      connection.close();  
	    }   
	    return res; 
	  }

	  //===	Execute an insert, update or delete statement 
	  //	Returns the number of rows affected
	  public int executeUpdate(String query) throws SQLException
	  {
	    if( query == null)
	      return -1;
	      
	    Statement statement 	= null;
	    Connection connection 	= null;
	    
	    int rows 				= 0;
	    
	    try 
	    {  
	      //=== connect
	      Class.forName(driver);
	      connection = DriverManager.getConnection(database_url, username, password);                   
	      statement = connection.createStatement();
	  
	      //=== execute statement                      
	       rows = statement.executeUpdate(query);       
	    }
	    catch (Exception e)
	    {
	       e.printStackTrace();
	       System.exit(1);
	    }
	    finally
	    {  
	      statement.close();
	      connection.close();        
	    } 
	    return rows;
	  }
	  
	  public int createUser(UserData user) throws SQLException
	  {
	      
	    PreparedStatement preparedStatement	= null;
	    Connection connection 	= null;
	    
	    int rows 				= 0;
	    
	    try 
	    {  
	      //=== connect
	      Class.forName(driver);
	      connection = DriverManager.getConnection(database_url, username, password);                   
	      String insertSQL = "insert into Owned values(?,?,?,?,?)";
	      
	      //=== execute statement
	      preparedStatement = connection.prepareStatement(insertSQL); // create statement object
	  
   	   	  preparedStatement.setString(1,user.getUserName());				// exchange placeholders for values 
   	   	  preparedStatement.setString(2,user.getfirstName());
   	   	  preparedStatement.setString(3,user.getLastName());
   	   	  preparedStatement.setString(4,user.getEmail());
   	   	  preparedStatement.setString(5, user.getPassword());
	      rows = preparedStatement.executeUpdate();                     
	          
	    }
	    catch (Exception e)
	    {
	       e.printStackTrace();
	       System.exit(1);
	    }
	    finally
	    {  
	    	preparedStatement.close();
	      connection.close();        
	    } 
	    return rows;
	  }
	  /*
	   * Checks if the username and password matches
	   */
	  public boolean checkLogin(String userName, String password1) throws SQLException
	  {
	    PreparedStatement preparedStatement	= null;
	    Connection connection 	= null;
	    ResultSet resultSet = null;
	    boolean accepted = false;		
   	    try 
	    {  
	      //=== connect
	      Class.forName(driver);
	      connection = DriverManager.getConnection(database_url, username, password);                   
	      String executeQuery = "SELECT userName FROM users WHERE userName = "
					+ '?' + " AND password = " + '?';

	      //=== execute statement
	      preparedStatement = connection.prepareStatement(executeQuery); // create statement object

   	   	  preparedStatement.setString(1,userName);				// exchange placeholders for values 
   	   	  preparedStatement.setString(2, password1);
	      resultSet = preparedStatement.executeQuery();                     
	      
	      if (resultSet.next()){
		accepted = true; }
	    }
	    catch (Exception e)
	    {
	       e.printStackTrace();
	       System.exit(1);
	    }
	    finally
	    {  
	      preparedStatement.close();
	      connection.close();
	      
	    } 
   	    
   	    return accepted;
	  }
	
}