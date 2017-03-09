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
public class User {

    //Basic Attributes of the User
    private int ID;
    private String Forename;
    private String Surname;
    private int TotalVotesLeft = 3;

    //Constructor 
    public User(int id) {
        ID = id;
    }

    //Getters + Setters (All we really care about in this case is just number of votes the user has left) 
    public int getTotalVotes() {
        return TotalVotesLeft;
    }

    public void setTotalVotes(int TV) {
        TotalVotesLeft = TV;
    }

    public void subVote ()
    {
        TotalVotesLeft--;
    }
    public int getID() {
        return this.ID;
    }

}
