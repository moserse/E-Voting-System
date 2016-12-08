package blueEVoting;
import java.security.Key;
import java.sql.*;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



/*This class exists to allow interaction between vote control objects/classes and the database. 
	Allows for reading and writing of file, hashing of IDs and vote data to allow for safe
	transfer between user, vote control, and database*/

/** 		
 * 	-BEFORE USING THIS, YOU MUST CHANGE YOUR USERNAME AND PASSWORD TO MATCH 
 * 	-THOSE CORRESPONDING TO YOUR OWN MYSQL INFO
 *
 * In order to adapt to more candidates (1-4 are only options) You must manually change 
 * The number of positions (located line 606), also must create the candidates as Admin ID (12347).
 * 
 *Some JDBC connection code was copy/pasted from JDBC exercises 
 *
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
		createDatabase();
		createTables();
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
	
	public void createDatabase() {
		
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=" +
													this.userName + "&password=" + this.password); 
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
			System.out.println("Database BEVOTING created (if not already created)");
			
		} catch (SQLException e) {
			System.out.println("cannot connect to create database");
			//e.printStackTrace();
		}
	}
	

	
	/**
	 * CREATING TABLES FOR UNIVERSAL RUN 
	 * VOTERS table is to store voters and their didCast boolean
	 * BALLOTS table is to store ballot info per voter (voter ID and who they voted for)
	 */
	private void createTables(){
		
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
					//create VOTERS table with 100 voters
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
									"ID VARBINARY(100), " + 
									"Candidate VARCHAR(45), " + 
									"Position VARCHAR(45))";
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
					
					//This string creates contents within the candidates table, (3 to be exact)
					String CandidateCreation = "INSERT INTO CANDIDATES VALUES ('Joe Pres', 'President'), " + 
									"('Greg Pres', 'President'), ('Nancy Vice', 'Vice President'), ('Doug Vice', 'Vice President'), " +
									"('George Sen', 'Senator'), ('Bobby Sen', 'Senator')";
					this.executeUpdate(conn, CandidateCreation);
					System.out.println("Candidate information was added. Voting processes can begin normally with 3 candidates. ");
				}	
			}	
			
		
			
		 } catch (SQLException e) {
				System.out.println("ERROR: Could not create the table");
				e.printStackTrace();
				return;
			}
		
	}
	
	public void storeCandidate(Candidate candidate){
		
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
	private void dropTable(){
		
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
					System.out.println("Dropped all VOTERS, BALLOTS, CANDIDATES table");
			    } catch (SQLException e) {
					System.out.println("ERROR: Could not drop the table");
					e.printStackTrace();
					return;
				}
	}
	
	
	
	
	
	/**
	 * showVoters shows the voters table, which includes Voter IDs and a 
	 * boolean revealing if that voter has voted yet (0=false, 1=true)
	 * Will become admin only in the future
	 */
	
	public void showVoters() {
		
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
	public void showBallots(){
		
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

	/**
	 * showCandidates does as the other show functions do, 
	 * printing for admin purposes.
	 */
	public void showCandidates(){
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
	 * getting Candidates f
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
		
		Candidate[] candidates = new Candidate[2]; //this means only 2 options per position are available
		for (int i = 0; i < candidates.length; i++){
			candidates[i] = new Candidate();
		}
		
		String pos = "";
		
		
		
			try {
				if (position == 0) pos = "President";
				else if(position == 1) pos = "Vice President";
				else if(position == 2) pos = "Senator";
				else if(position == 3) pos = "Representative";
				
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
	
	public void submitBallot(Ballot ballot) { 
		
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
				
				//to show data
				//ballot.print();
				
				for ( int i = 0; i < ballot.getCandidates().length; i++ ) {
								//inserting into BALLOTS table
					String insertBallot = "INSERT INTO BALLOTS " + 
							"VALUES ('" + encrypt( Integer.toString( ballot.getVoterID() ) ) + " ', '" + 
								ballot.getCandidates()[i].getCandidateName() + "', '" +
							ballot.getCandidates()[i].getCandidatePosition() + "')";
					this.executeUpdate(conn, insertBallot);
					
								//updating ID from VOTERS table
					String removeVoter = "UPDATE VOTERS SET didVote = 1 WHERE ID = " + ballot.getVoterID();
					this.executeUpdate(conn, removeVoter);
				}
			
				
		    } catch (SQLException e) {
				System.out.println("ERROR: reading from database(in submitBallot)");
				e.printStackTrace();
				return;
			}
		
				
		
	}
	
	/*Validaton of Admin as an extra security measure for access to 
	 * 	vote data and user registration numbers*/
	public boolean validateAdmin() {
		//search if admin password and id match
		return false;		
	}
	
	/**
	 * Checks if User can vote, i.e the ID number exists and they haven't voted yet. 
	 * @param ID
	 * @return true or false
	 */
	public boolean validateVoter(int ID) {
		
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
	public String[] countResults(){
		
		Connection conn = null;
		String[] returnString = new String[getNumberOfPositions()];
		try {
			conn = this.getConnection();
			//System.out.println("Connected to database " + this.dbName);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return null;
			}
		
		try {	
			
			String query = "SELECT * FROM BALLOTS";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			for (int i = 0; i < returnString.length; i++){
				Candidate candidates[] = getCandidates(i);
				//System.out.println("Current results: ");
					while (rs.next()){
						
						//System.out.println("Candidate point for:   " + rs.getString("Candidate"));
						
						if(rs.getString("Candidate").equals(candidates[0].getCandidateName()) ){
							candidateCountA++;
							//System.out.println("ok");
						}
						if (rs.getString("Candidate").equals(candidates[1].getCandidateName())){
							candidateCountB++;
							//System.out.println("ok2");
						}
					}
					returnString[i] = "|" + candidates[0].getCandidateName() + ": " 
							+ candidateCountA + ", " + candidates[1].getCandidateName()
							+ ": " + candidateCountB + ".";
					//print();
					candidateCountA = 0;
					candidateCountB = 0;
					rs = st.executeQuery(query);
				}
			
				
			
	    } catch (SQLException e) {
			System.out.println("ERROR: reading from database (in countResults)");
			e.printStackTrace();
			return null;
		}
		
		return returnString;
		
	}
	
	
	/**
	 * Returns number of positions that candidates are running for, dictates number of screens.
	 * 
	 * @return positions	Number of positions election is done for
	 */
	int getNumberOfPositions(){
		// Debug currently
		return 3;
	}
	
	void print(){
		System.out.println("Candidate count for A = " + candidateCountA +
				" Candidate count for B = " + candidateCountB);
	}
	
	// http://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
	private byte[] encrypt(String input) {
		try {
	        String key = "Bar12347Bar12347"; // 128 bit key
	        // Create key and cipher
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        // encrypt the text
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	        byte[] encrypted = cipher.doFinal(input.getBytes());
	        System.out.println(encrypted);
	        return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String decrypt(byte[] input) {
		try {
	        String key = "Bar12347Bar12347"; // 128 bit key
	        // Create key and cipher
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	     // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(input));
            System.out.println(decrypted);
	        return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This function checks and see if a recount is applicable 
	 * meaning that if the number of voters who voted does not equal the amount of ballots/numberofpositions
	 * then there was a problem and a recount needs to be administered
	 * @return's true on success, false if there are any issues
	 * 
	 */
	
	public boolean recountCertification(){
		
		int votedCount = 0;
		int ballotCount = 0;
		
		Connection conn = null;
		try {
			conn = this.getConnection();
		} catch (SQLException e) {
			System.out.println("ERROR: connecting with recountCert");
			e.printStackTrace();
			return false;
			}

		try{
			Statement st = conn.createStatement();
			
			String VoterQuery = "SELECT * FROM VOTERS WHERE didVote = 1";
			ResultSet rs = st.executeQuery(VoterQuery);		
			while(rs.next()) votedCount++;
			
			String BallotQuery = "SELECT * FROM BALLOTS";
			ResultSet rs2 = st.executeQuery(BallotQuery);
			while(rs2.next()) ballotCount++;
			
			if( votedCount == ( ballotCount/getNumberOfPositions() ) ){
				//test print
				//System.out.println("votedCount = " + votedCount + " ballotCount = " + ballotCount 
					//				+ " .... ballotCount div 3 = " + ballotCount/3);
				return true;
			}
			else return false;	
			
			
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Problem in recountCertification");
			return false;
		}
		
	}
	
	public void testCrypto() {
		decrypt(encrypt("10044"));
	}

}



















