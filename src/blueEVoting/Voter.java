package blueEVoting;

public class Voter {
	
	int voterID;
	java.util.Date dateofBirth;
	String voterName;
	boolean didCastVote;
	
	boolean didCastVote( int voterID, Candidate candidate) { 
		return true;
	}
	
	boolean compareHash(Ballot ballot) {
		return false;
	}
	
	void reverseVote (int voterID, Candidate candidate ) {};
}
