package blueEVoting;

/*Voter class is unique to the user, storing their specific ID, date of birth, name and an assurance that the user
	has or has not already casted a vote. */

public class Voter {
	
	static int voterID;
	java.util.Date dateofBirth;
	static String voterName;
	boolean didCastVote;
	
	/*Sees if voter ID's storage has already been filled with votes to ensure that the votes cannot be doubled*/
	boolean didCastVote( int voterID, Candidate candidate) { 
		return didCastVote;
	}
	
	/*compare hash'd ID within ballot*/
	boolean compareHash(Ballot ballot) {
		return false;
	}
	
	/*Vote reversal in case of user accidental vote or mind change*/
	void reverseVote (int voterID, Candidate candidate ) {};
	
	void print(){
		System.out.printf("Voter ID = %d\nVoter name = %s\nDid this voter cast already: %b\n", voterID, voterName, didCastVote);
	}
}
