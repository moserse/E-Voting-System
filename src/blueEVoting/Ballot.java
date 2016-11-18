package blueEVoting;

/*short-term data storing class called ballot
	gets candidate information and stores user data such as their vote and hashed ID number
	the ballot is submitted or cancelled*/

public class Ballot {
	
	int voterID; // Hash for use short-term identifying the proper Voter for the ballot.
	String candidateName = "joe default";
	Candidate candidate;
	Candidate[] candidates;
	
	static final int PRESIDENT = 0;
	static final int VICE_PRESIDENT = 1;
	static final int REPRESENTATIVE = 2;
	static final int SENATOR = 3;
	
	
	
	public Ballot(int numberOfPositions) {
		setNumberOfCandidates(numberOfPositions);
	}
	
	public Ballot(Candidate candidate, int position, int numberOfPositions) {
		setNumberOfCandidates(numberOfPositions);
		setCandidate(candidate, position);
	}

	/*sumbmission of ballot in order to store it into database
		Ballot is wiped after transfer is confirmed*/
	void submit() {
		;
		;
	}
	
	void setVoterID(int ID){
		this.voterID = ID;
	}
	
	void setCandidate(Candidate candidate, int position) {
		this.candidate = candidate;
		this.candidateName = candidate.getCandidateName() == null ? "" : candidate.getCandidateName();
	}
	
	void setNumberOfCandidates(int numberOfPositions) {
		if ( candidates == null ) candidates = new Candidate[numberOfPositions];
		else if ( !(candidates.length > numberOfPositions) ) {
			Candidate[] formerCandidates = candidates;
			candidates = new Candidate[numberOfPositions];
			for ( int i = 0; i < formerCandidates.length; i++ ) candidates[i] = formerCandidates[i];
		}
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
	
	public Candidate[] getCandidates() {
		return candidates;
	}

}
