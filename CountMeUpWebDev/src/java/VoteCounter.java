/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Humza
 */
@WebServlet(name = "VoteCounterServlet", urlPatterns = {"/VoteCounter"})
@MultipartConfig
public class VoteCounter extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*Closest thing to Constants in JAVA but these are the database connection paramters.*/
    private static final String URL = "jdbc:mysql://localhost:3306/countmeup"; // url to the sql database running on localhost - may need to be changed to work with other machines
    private static final String USERNAME = "root"; // Accessing the database as root for the purporse of this assignment
    private static final String PSW = "Spiderman786"; // Random Password chosen for root.

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /*Define the paramters once and then pass them onto appropriate functions instead of redefining them. */
        String RadioBox = request.getParameter("Vote"); // Get the radio button that was checked, only one vote at a time and each button has a unique value associcated with it which is the same as the candidates ID in the table.
        PrintWriter out = response.getWriter(); // Writer needed

        Statement stmt = null; // Statement
        Statement Ustmt = null; // Statement

        // Swith case to determine which button was pressed and then call the addVote function with the appropriate ID being sent as a paramter.
        switch (RadioBox) {
            case "1":
                addVote(request, response, RadioBox, URL, USERNAME, PSW, out);
                break;
            case "2":
                addVote(request, response, RadioBox, URL, USERNAME, PSW, out);
                break;
            case "3":
                addVote(request, response, RadioBox, URL, USERNAME, PSW, out);
                break;
            case "4":
                addVote(request, response, RadioBox, URL, USERNAME, PSW, out);
                break;
            case "5":
                addVote(request, response, RadioBox, URL, USERNAME, PSW, out);
                break;
        }
    }

    // Add vote to the correct candidate depending on which ID was selected-  also give the paramters for the database connection from main rather than redefining them in the function. 
    protected void addVote(HttpServletRequest req, HttpServletResponse resp, String Cand, String url, String username, String password, PrintWriter out)
            throws ServletException, IOException {

        Statement stmt = null; // Statement
        ResultSet rs = null; // Result Set

        try {
            Class.forName("com.mysql.jdbc.Driver");  //simplest  way to load the driver required
            Connection connection = (Connection) DriverManager.getConnection(url, username, password); // make the connection

            stmt = connection.createStatement(); //create the statement

            /*Hidden Elements being retrieved AKA Attributes which were set and passed on from the Login page.*/
            int VotesLeft = Integer.parseInt(req.getParameter("VotesLeft")); //get the votes left from the JSP page - number was orignally gotten from the database and has been forwarded here.
            String UN = req.getParameter("User"); //get the user name of current user logged in

            if (VotesLeft > 0) {
                rs = stmt.executeQuery("SELECT * FROM candidates WHERE `ID` = '" + Cand + "'"); // execute query and obtain the results in rs - for this purporse we can select everything where the ID matches        
                rs.next(); // Move cursor down by as each ID is unique therefore only one result should be returned

                String Votes = rs.getString("Votes"); //Obtain the vote count for the correct candidate
                int VC = Integer.parseInt(Votes); // Convert string number into Int.
                VC++; // Add  onto the vote count by 1

                stmt.executeUpdate("UPDATE candidates SET `Votes` ='" + VC + "' WHERE `ID` ='" + Cand + "'"); //Update the candidates vote count to the new number.

                subUserVote(req, resp, UN, url, username, password, out); //subtract the votes remaining for the user by passing on the user name
               connection.close();
               
                /*If user has no votes remaining then direct them back to the login page and inform them they are out of votes*/
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Vote Accepted');");
                out.println("location='index.html';");
                out.println("</script>");

            } else {
                /*If user has no votes remaining then direct them back to the login page and inform them they are out of votes*/
                out.println("<script type=\"text/javascript\">");
                out.println("alert('No More Votes Left');");
                out.println("location='index.html';");
                out.println("</script>");
            }
        } catch (SQLException e) { // used to catch standard exceptions for SQL
            out.println("SQLException: " + e.getMessage());
            out.println("SQLState: " + e.getSQLState());
            out.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", e); //used to catch any other exception that may be generated
        } catch (ClassNotFoundException ex) {
            out.println(ex); // if class not found which normally occurs if JDBC driver is not in class path
        }
    }

    //subtract the vote from the user who just voted - again passing paramters that are needed to make the database connection rather than redefine them
    protected void subUserVote(HttpServletRequest req, HttpServletResponse resp, String Usr, String url, String username, String password, PrintWriter out)
            throws ServletException, IOException {

        Statement stmt = null; // Statement
        Statement Ustmt = null; // Statement
        ResultSet rs = null; // Result Set
        //  ResultSet URS = null; // Updated result set

        try {
            Class.forName("com.mysql.jdbc.Driver");  //simplest  way to load the driver required
            Connection connection = (Connection) DriverManager.getConnection(url, username, password); // make the connection

            stmt = connection.createStatement(); //create the statement

            rs = stmt.executeQuery("SELECT * FROM users WHERE `Name` = '" + Usr + "'"); // execute query and obtain the results in rs - for this purporse we can select everything where the username matches        
            rs.next(); // Move cursor down by as each username is unique therefore only one result should be returned

            String Votes = rs.getString("Vote Count"); //Obtain the vote count for the correct candidate
            int VL = Integer.parseInt(Votes); // Convert string number into Int.
            VL--; // Subtract Their Vote Count by 1

            stmt.executeUpdate("UPDATE users SET `Vote Count` ='" + VL + "' WHERE `Name` ='" + Usr + "'"); //Update the candidates vote count to the new number.

            connection.close();

        } catch (SQLException e) { // used to catch standard exceptions for SQL
            out.println("SQLException: " + e.getMessage());
            out.println("SQLState: " + e.getSQLState());
            out.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", e); //used to catch any other exception that may be generated
        } catch (ClassNotFoundException ex) {
            out.println(ex); // if class not found which normally occurs if JDBC driver is not in class path
        }
    }

    private int TotalVotes = 0; // int for total total votes cast global because it needs to be accessed doGet every second for live updates
    Map<String, String> CandMap = new LinkedHashMap<>(); // New Map needed for storage of results and easier to display

    /*Method to count totaL VOTES which were registered - again pass the constant params to make the database connection*/
    protected void CountVotes(HttpServletRequest req, HttpServletResponse resp, String url, String username, String password, PrintWriter out)
            throws ServletException, IOException {

        Statement stmt = null; // Statement
        Statement Ustmt = null; // Statement
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");  //simplest  way to load the driver required
            Connection connection = (Connection) DriverManager.getConnection(url, username, password); // make the connection

            DecimalFormat numberFormat = new DecimalFormat("#.00"); // Number format for low result turn out such as below 0.5%

            stmt = connection.createStatement(); //create the statement

            rs = stmt.executeQuery("SELECT * FROM candidates"); // execute query and obtain the results in rs - all the records in the candidate table     

            //Loop to count total votes
            while (rs.next()) { // go through the entire results
                TotalVotes += Integer.parseInt(rs.getString("Votes")); //Obtain the vote count for the correct candidate and convert them to int then add onto total votes.                
            }

            rs.beforeFirst(); //reset cursor to before first row

            //Go through the results again
            while (rs.next()) { // go through the entire results           

                /*Add values to the Map 
                 Candidate Name > Votes Obtained + % Equivlent.
                 Where % is calculated and put into the map as part of the second string as its easier to just use strings at this point.
                 */
                CandMap.put(rs.getString("Name"), rs.getString("Votes") + " (" + numberFormat.format((Double.parseDouble(rs.getString("Votes")) / TotalVotes) * 100) + " %)");
            }

            connection.close();

        } catch (SQLException e) { // used to catch standard exceptions for SQL
            out.println("SQLException: " + e.getMessage());
            out.println("SQLState: " + e.getSQLState());
            out.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", e); //used to catch any other exception that may be generated
        } catch (ClassNotFoundException ex) {
            out.println(ex); // if class not found which normally occurs if JDBC driver is not in class path
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet respo
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter(); // Writer needed
        CountVotes(request, response, URL, USERNAME, PSW, out);

        String json = new Gson().toJson(CandMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
