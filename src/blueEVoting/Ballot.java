package blueEVoting;

/*short-term data storing class called ballot
	gets candidate information and stores user data such as their vote and hashed ID number
	the ballot is submitted or cancelled*/

public class Ballot {
	
	private int voterID; // Hash for use short-term identifying the proper Voter for the ballot.
	private String candidateName = "joe default";
	private Candidate candidate;
	private Candidate[] candidates;
	
	static final int PRESIDENT = 0;
	static final int VICE_PRESIDENT = 1;
	static final int SENATOR = 2;
	static final int REPRESENTATIVE = 3;
	
	
	
	public Ballot(int numberOfPositions) {
		setNumberOfCandidates(numberOfPositions);
	}
	
	public Ballot(Candidate candidate, int position, int numberOfPositions) {
		setNumberOfCandidates(numberOfPositions);
		setCandidate(candidate, position);
	}

	/*sumbmission of ballot in order to store it into database
		Ballot is wiped after transfer is confirmed*/
	public void submit() {
		;
		;
	}
	
	public void setVoterID(int ID){
		this.voterID = ID;
	}
	
	public void setCandidate(Candidate candidate, int position) {
		this.candidate = candidate;
		this.candidates[position] = candidate;
		this.candidateName = candidate.getCandidateName() == null ? "" : candidate.getCandidateName();
	}
	
	public void setNumberOfCandidates(int numberOfPositions) {
		if ( numberOfPositions < 1 ) numberOfPositions = 1;
		if ( candidates == null ) candidates = new Candidate[numberOfPositions];
		else if ( !(candidates.length > numberOfPositions) ) {
			Candidate[] formerCandidates = candidates;
			candidates = new Candidate[numberOfPositions];
			for ( int i = 0; i < formerCandidates.length; i++ ) candidates[i] = formerCandidates[i];
		}
	}
	
	/*cancel the ballot and wipe it clean*/
	public void cancel() {
		candidateName = null;
		candidate = null;
		voterID = 0;
	}
	
	//for debugging purposes
	public void print(){
		System.out.println("Voter ID = " + voterID);
		System.out.println("Candidate name = " + candidateName);
		
	}

	/**
	 * Returns the selected Candidates in a displayable way as a String.
	 * 
	 * @return	Returns all the selected candidate(s). 
	 */
	public String getSelections() {
		if (candidates != null) {
			String returnString = "";
			for ( int i = 0; i < candidates.length; i++ ) {
				returnString = returnString + "\n" + candidates[i].getCandidateName() + 
						"(" + candidates[i].getCandidatePosition() + "),";
			}
			returnString = returnString.substring(0, returnString.length() - 1);
			return returnString;
		}
		return candidate.getCandidateName();
	}
	
	public Candidate[] getCandidates() {
		return candidates;
	}

	public int getVoterID() {
		return voterID;
	}

}
