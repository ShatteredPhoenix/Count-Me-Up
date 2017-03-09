/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package countmeup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Humza
 */
public class CountMeUp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        long startTime = System.currentTimeMillis();
        int OverallTotalVotes = 0;

        List<User> Usr = new ArrayList<>();
        List<Candidate> Cnd = new ArrayList<>();

        Initialise(Usr, Cnd); // Inittialise Users and Candidates

        for (int i = 0; i < Usr.size(); i++) {
            SimulateVotingSystem(Usr.get(i), Cnd);
        }
        for (int x = 0; x < Cnd.size(); x++) {
            OverallTotalVotes = OverallTotalVotes + Cnd.get(x).getTotalVotes();
        }
        
        for (int x = 0; x < Cnd.size(); x++) {
            System.out.println("Candidate ID " + Cnd.get(x).getID() + " Has " + Cnd.get(x).getTotalVotes() + " Votes" + "(" + (Cnd.get(x).getTotalVotes() * 100) / OverallTotalVotes + "%)");

            //  System.out.println("Usr ID " + Usr.get(x).getID() + " Has " + Usr.get(x).getTotalVotes() + " Votes Left");
        }

        long Stoptime = System.currentTimeMillis();
        long Exec = Stoptime - startTime;

        System.out.println(Exec);
    }

    // Initialise the system
    public static void Initialise(List<User> Usr, List<Candidate> CnD) {
        // Instantitate Users and Candidates 5 in this case but can be founded for more or less
        for (int i = 0; i < 33; i++) {

            User U = new User(i);
            Usr.add(U);
        }

        for (int i = 0; i < 5; i++) {
            Candidate C = new Candidate(i);
            CnD.add(C);
        }
    }

    //Simulates the Voting System by taking the candidate list and a user
    public static void SimulateVotingSystem(User Usr, List<Candidate> CnD) {
        Random randomGenerator = new Random(); // New Random Generator

        while (Usr.getTotalVotes() > 0) { // While user has votes remaining

            int randomInt = randomGenerator.nextInt(5); // Select Random Candidate to Vote For
            Usr.subVote(); // Subtract from User Vote Count
            CnD.get(randomInt).addVote(); // 'Update their current vote count by adding onto it' 

        }
    }

}
