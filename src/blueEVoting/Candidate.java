package blueEVoting;

/*Candidate class stores all candiate information 
	Methods in this class are admin only */

public class Candidate {
	
	String candidateName;
	String candidateParty;
	int candidateID;
	String candidatePosition;
	
	/*Change the name of the candidate, non set candidates are set to "joe"*/
	void changeName( String name ) {};
	
	/*change the candidates position for voting activity context*/
	void changePosition() {};

}
