package blueEVoting;

/*short-term data storing class called ballot
	gets candidate information and stores user data such as their vote and hashed ID number
	the ballot is submitted or cancelled*/

public class Ballot {
	
	int voterID; // Hash for use short-term identifying the proper Voter for the ballot.
	String candidateName = "joe default";
	Candidate candidate;
	
	
	public Ballot(Candidate candidate) {
		setCandidate(candidate);
	}

	/*sumbmission of ballot in order to store it into database
		Ballot is wiped after transfer is confirmed*/
	void submit(Ballot ballot) {
		candidateName = null;
		voterID = 0;
	}
	
	void setCandidate(Candidate candidate) {
		this.candidate = candidate;
		this.candidateName = candidate.getCandidateName() == null ? "" : candidate.getCandidateName();
	}
	
	/*cancel the ballot and wipe it clean*/
	void cancel() {
		candidateName = null;
		candidate = null;
		voterID = 0;
	}
	
	//for debugging purposes
	void print(){
		System.out.println("Cadidate ID = " + voterID);
		System.out.println("Candidate name = " + candidateName);
		
	}

	/**
	 * Returns the selected Candidates in a displayable way as a String.
	 * 
	 * @return	Returns all the selected candidate(s). 
	 */
	public String getSelections() {
		return candidate.getCandidateName();
	}

}
