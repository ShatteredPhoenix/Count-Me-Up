/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package countmeup;

/**
 *
 * @author Humza
 */
public class Candidate {

    //Basic Attributes of the User
    private int ID;
    private String Forename;
    private String Surname;
    private int TotalVotes = 0;

    //Constructor 
    public Candidate(int id) {
        ID = id;
    }

    //Getters + Setters (All we really care about in this case is just number of votes the user has left) 
    public int getTotalVotes() {
        return TotalVotes;
    }

    public void setTotalVotes(int TV) {
        TotalVotes = TV;
    }

    public void addVote(){
        TotalVotes++;
    }

    public int getID() {
        return this.ID;
    }
}
