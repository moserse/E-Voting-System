package blueEVoting;
import java.sql.*;
import java.util.Properties;



/*This class exists to allow interaction between vote control objects/classes and the database. 
	Allows for reading and writing of file, hashing of IDs and vote data to allow for safe
	transfer between user, vote control, and database*/

/** 					!!!!!!!HOL UP HOLD UP HOL UP HOL UP!!!!!!!! 
 * 	-BEFORE USING THIS, YOU NEED TO CREATE A DATABASE CALLED 'BEVOTING'
 * 	-OR YOU CAN CHANGE dbName TO WHATEVER YOU WANT IT TO BE.
 * 	-only command needed is 'CREATE DATABASE databaseName;'
 * 
 *  some methods copied from DBDemo by *xenia (if she wrote them??)*
 * This class is messy af because every method has a getConnection, 
 * it can be tough to navigate. 
 *
 */

public class DatabaseController {
	
	private final String userName = "root";
	private final String password = "jonny123";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	/** The name of the database */
	private final String dbName = "BEVOTING";
	
	/** VOTERS is the voter info table, BALLOTS is the ballot info table */
	private final String tableName = "VOTERS";
	private final String tableName2 = "BALLOTS"; //will come up with better names later..
	private final String tableName3 = "CANDIDATES";

	private int candidateCountA = 0;
	private int candidateCountB = 0;
	
	
	
	public DatabaseController() {
		createTables();
	}
	
	void createDatabase() {
		try {
			executeUpdate(getConnection(), "CREATE DATABASE " + dbName + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get a new database connection, copied from DBDemo. used throughout methods
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}
	
	
	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * -Also copied from DBdemo
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	
	/**
	 * CREATING TABLES FOR UNIVERSAL RUN 
	 * VOTERS table is to store voters and their didCast boolean
	 * BALLOTS table is to store ballot info per voter (voter ID and who they voted for)
	 */
	
	void createTables(){
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
							
		
		try {
			
			//checks to see if tables already exist
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet resV = dbm.getTables(null, null, "VOTERS", null);
			ResultSet resB = dbm.getTables(null, null, "BALLOTS", null);
			ResultSet resC = dbm.getTables(null, null, "CANDIDATES", null);
			
			if(resV.next() && resB.next() && resC.next()) {
				System.out.println("All 3 tables already exist");
				return;
			}
			else{
				
				if(!resV.next()){
					//create VOTERS table
					String createVoters =
					    "CREATE TABLE " + this.tableName + " ( " +
					    "ID INTEGER NOT NULL, " +
					    "didVote TINYINT NOT NULL)";
					           
					this.executeUpdate(conn, createVoters);
					
					for(int i = 0; i < 100; i++){
						String insert = "INSERT INTO VOTERS " + 
						"VALUES (" + (10000 + i) + ", 0)"; //10000 + i is the ID number
						this.executeUpdate(conn, insert);
					}
					System.out.println("Created Voters table");
				}
					
				if(!resB.next()){
										//create BALLOTS table
				String createBallots = "CREATE TABLE " + this.tableName2 + " ( " +
									"ID INTEGER NOT NULL, " + 
									"Candidate VARCHAR(45))";
				this.executeUpdate(conn, createBallots);
				System.out.println("Created Ballots table");
					
				}
				
				if(!resC.next()){
										//create CANDIDATES table
					String createCandidates = "CREATE TABLE " + this.tableName3 + " ( " +
											"Name VARCHAR(45), " + 
											"Position VARCHAR(45))";
					this.executeUpdate(conn, createCandidates);
					System.out.println("Created Candidates table");
				}	
			}	
			
		
			
		 } catch (SQLException e) {
				System.out.println("ERROR: Could not create the table");
				e.printStackTrace();
				return;
			}
		
	}
	
	
	void storeCandidate(Candidate candidate){
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
		
		try {
			String add = "INSERT INTO CANDIDATES"
					+ "	VALUES ('" + candidate.getCandidateName() + "', '" + candidate.getCandidatePosition() + "')";
			this.executeUpdate(conn, add);
			
		}catch (SQLException e){
			System.out.println("couldn't store in candidates table");
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
	/**
	 * Deletes the tables, in the end we won't use this. this is really 
	 * just so we can continually test. can remove whatever when necessary
	 */
	void dropTable(){
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
			
						// Drop the table(s)
				try {
				    String dropVoting = "DROP TABLE " + this.tableName;
					this.executeUpdate(conn, dropVoting);
					String dropBallots = "DROP TABLE " + this.tableName2;
					this.executeUpdate(conn, dropBallots);
					String dropCandidates = "DROP TABLE " + this.tableName3;
					this.executeUpdate(conn, dropCandidates);
					System.out.println("Dropped both VOTERS, BALLOTS, CANDIDATES table");
			    } catch (SQLException e) {
					System.out.println("ERROR: Could not drop the table");
					e.printStackTrace();
					return;
				}
	}
	
	
	
	
	
	/*read database file from database*/
	
	void readDatabaseFile() {
		
		//search through database file, this will be for when we check 
		//if user IDs are valid or if they've voted.
		
	}
	
	
	/*write onto database file using TBD encryption protocol*/
	static void writeDatabaseFile() {
		
		//copy from db
	   
	}
	
	/**
	 * showVoters shows the voters table, which includes Voter IDs and a 
	 * boolean revealing if that voter has voted yet (0=false, 1=true)
	 * Will become admin only in the future
	 */
	
	void showVoters() {
		
		//if validateAdmin() == true, then do this. else: sorry, nope!
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
		
		
		//printing out VOTERS table
		try {	
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from VOTERS"); 
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){
				for(int i = 1; i <= columnsNumber; i++) System.out.print(rs.getString(i) + " ");				
				System.out.println();	
			}
	    } catch (SQLException e) {
			System.out.println("ERROR: reading from VOTER table");
			e.printStackTrace();
			return;
		}
		
	}
	
	/**
	 * showBallots so far reveals results stored in BALLOTS table.
	 * BALLOTS table stores VoterIDs and the they candidate voted for 
	 * Will also be an admin only function in the future. 
	 * Will also need to be decrypted when we implement encryption
	 */
	void showBallots(){
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
		
				//printing out ballots table
		try {	
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from BALLOTS"); 
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			System.out.println("Ballots table");
			while (rs.next()){
				for(int i = 1; i <= columnsNumber; i++) System.out.print(rs.getString(i) + " ");				
				System.out.println();	
			}
	    } catch (SQLException e) {
			System.out.println("ERROR: reading from database(in showBallots)");
			e.printStackTrace();
			return;
		}
	}

	
	void showCandidates(){
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
		
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from CANDIDATES");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){
				for(int i = 1; i <= columnsNumber; i++) System.out.print(rs.getString(i) + " ");				
				System.out.println();	
			}
			
		}catch (SQLException e){
			System.out.println("ERROR: reading from database(in showBallots)");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Candidate[] getCandidates(int position) {
		
				//getting connection
		Connection conn = null;
		try {
			conn = this.getConnection();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
		
		Candidate[] candidates = new Candidate[2];
		for (int i = 0; i < candidates.length; i++){
			candidates[i] = new Candidate();
		}
		
		String pos = "";
		
		
		
			try {
				if (position == 0) pos = "President";
				else if(position == 1) pos = "Vice President";
				else if(position == 2) pos = "Representative";
				else if(position == 3) pos = "Senator";
				
				String query = "SELECT * FROM CANDIDATES WHERE Position = '" + pos + "'";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				
				int i = 0;
				while(rs.next()){
					candidates[i].setCandidateName(rs.getString("Name"));
					candidates[i].setCandidatePosition(rs.getString("Position"));
					i++;
				}
				return candidates;
				
				
			} catch (SQLException e) {
				System.out.println("error in getCandidates");
				e.printStackTrace();
				return candidates;
			}
		
		
		
		
		
		
	}
	
	/**
	 * submitBallot accepts a ballot and takes its information to 
	 * store into the BALLOTS table. (int ID, str candidate). 
	 * This will eventually encrypt the information before storing. 
	 * 
	 * There is no turning back from here for the voter choice
	 * @param ballot
	 */
	
	void submitBallot(Ballot ballot) { 
		
		//getting connection
			Connection conn = null;
			try {
				conn = this.getConnection();
				//System.out.println("Connected to database for Ballot insert " + this.dbName);
			} catch (SQLException e) {
				System.out.println("ERROR: Could not connect to the database");
				e.printStackTrace();
				return;
			}
			
			try {
				
				ballot.print();
				
				
							//inserting into BALLOTS table
				String insertBallot = "INSERT INTO BALLOTS " + 
						"VALUES (" + ballot.voterID + ", '" + ballot.candidateName + "')";
				this.executeUpdate(conn, insertBallot);
				
							//removing the ID from VOTERS table
				String removeVoter = "DELETE FROM VOTERS WHERE ID = " + ballot.voterID;
				this.executeUpdate(conn, removeVoter);
				
							//replacing removed ID with same ID + boolean confirming vote success.
				String insertVoters = "INSERT INTO VOTERS (ID, didVote)" + 
						"VALUES( " + ballot.voterID + ", (1))";
				this.executeUpdate(conn, insertVoters);
				
				
				
		    } catch (SQLException e) {
				System.out.println("ERROR: reading from database(in submitBallot)");
				e.printStackTrace();
				return;
			}
		
				
		
	}
	
	/*Validaton of Admin as an extra security measure for access to 
	 * 	vote data and user registration numbers*/
	boolean validateAdmin() {
		//search if admin password and id match and shit
		return false;		
	}
	
	/*hashing of Voter identification via TBD protocol*/
	void hashVoterID() {
		//hash voter ID, will probably be used by writedatabase
		
		
	}
	
	/**
	 * Checks if User can vote, i.e the ID number exists and they haven't voted yet. 
	 * @param ID
	 * @return true or false
	 */
	boolean validateVoter(int ID) {
		
		//getting connection
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return false;
			}
		//doing the deed
		try {	
			
			int i = 1;
			String query = "SELECT * from VOTERS WHERE ID = " + ID;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				if(rs.absolute(i) && rs.getInt("didVote") == 0){
					System.out.println("User with ID #" + ID + " is validated to vote");
					System.out.println();
					conn.close();
					return true;
				}
			}
			
			
	    } catch (SQLException e) {
			System.out.println("ERROR: reading from database(in validate voter)");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("*Voter is not validated: with ID #" + ID);
		System.out.println("*Voter ID does not exist in system or ID has already voted");
		System.out.println();
		return false;
	}
	
	
	/**
	 * countResults will iterate through the database and collect
	 * counts of whatever voter voted for whichever candidate. 
	 * will set the private ints? not sure if they are to be private,
	 * not sure how to handle security of this function rather than making
	 * it admin only as well.
	 * @return
	 */
	/*
	void countResults(){
		
		Connection conn = null;
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
			}
		
		try {	
			
			String query = "SELECT * FROM BALLOTS";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			Candidate candidates[] = getCandidates();
	
	//THIS DOES NOT WORK, FUCK WHY
			while (rs.next()){
				System.out.println("name = " + rs.getString("Candidate"));
				System.out.println("name2 = " + candidates[1].getCandidateName());
				if(rs.getString("Candidate") == candidates[0].getCandidateName()){
					candidateCountA++;
				}
				if (rs.getString("Candidate") == candidates[1].getCandidateName()){
					candidateCountB++;
				}
				print();
			}
				
			
	    } catch (SQLException e) {
			System.out.println("ERROR: reading from database (in countResults)");
			e.printStackTrace();
			return;
		}
		
	}*/
	
	
	/**
	 * Returns number of positions that candidates are running for, dictates number of screens.
	 * 
	 * @return positions	Number of positions election is done for
	 */
	int getNumberOfPositions(){
		// Debug currently
		return 1;
	}
	
	void print(){
		System.out.println("Candidate count for A = " + candidateCountA +
				" Candidate count for B = " + candidateCountB);
	}

}
