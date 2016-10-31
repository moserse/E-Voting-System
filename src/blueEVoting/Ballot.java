package blueEVoting;

/*short-term data storing class called ballot
	gets candidate information and stores user data such as their vote and hashed ID number
	the ballot is submitted or cancelled*/

public class Ballot {
	
	String candidateName;
	String voterIDHash; // Hash for use short-term identifying the proper Voter for the ballot. Not meant for long-term.
	
	/*sumbmission of ballot in order to store it into database
		Ballot is wiped after transfer is confirmed*/
	void submit(Ballot ballot) {
		candidateName = null;
		voterIDHash = null;
	}
	
	/*cancel the ballot and wipe it clean*/
	void cancel() {
		candidateName = null;
		voterIDHash = null;
	}

}
