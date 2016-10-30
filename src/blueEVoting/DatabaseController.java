package blueEVoting;

/*This class exists to allow interaction between vote control objects/classes and the database. 
	Allows for reading and writing of file, hashing of IDs and vote data to allow for safe
	transfer between user, vote control, and database*/

public class DatabaseController {
	
	Ballot ballot;
	Candidate candidate;
	int candidateCount;
	
	/*read database file from database*/
	void readDatabaseFile() {
		
	}
	/*write onto databasefile using TBD encryption protocol*/
	void writeDatabaseFile() {}
	
	/*Admin only method that reveals results to a verified official. */
	void showResults(Admin admin) {}
	
	/*Changes candidate within database*/
	void changeCandidate(Candidate candidate) {}
	
	/*submission of ballot*/
	void submitBallot() {};
	
	/*Validaton of Admin as an extra security measure for access to vote data and user registration numbers*/
	void validateAdmin() {};
	
	/*hashing of Voter identificaiton via TBD protocol*/
	void hashVoterID() {};
	
	/*Validation of voter similar to that of Admin process*/
	void validateVoter() {};

}
