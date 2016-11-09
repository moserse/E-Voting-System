package blueEVoting;

/*This is the admin class. Admin can edit Candidate, Voter info, initiate recounts and printouts of vote data, etc. 
	In this initiative the admin will also act as a polling official for the sake of simplicity
	Admin will have a special ID and password unique to voters/users and will contain an access log*/

public class Admin {
	
	int adminID;
	String adminPassword;
	java.util.Date[] accessTimes;
	String accessLog;
	java.util.Date lastAccess;
	
	/*accessability of admin*/
	boolean didAccess() {
		return lastAccess != null;
	}
	
	void print(){
		System.out.printf("adminID = %d\n ", adminID);
	}

}
