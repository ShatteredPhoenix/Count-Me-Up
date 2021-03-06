/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "SignInServlet", urlPatterns = {"/SignIn"})
@MultipartConfig
public class Validation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        PrintWriter out = response.getWriter();
        String logIn = request.getParameter("LogIn"); // store the value of Login Button, will be used later to determine which button was clicked.
        String Signup = request.getParameter("SignUp"); //store value of Signup button to register a user to the database.

        /*Closest thing to Constants in JAVA but these are the database connection paramters.*/
        final String URL = "jdbc:mysql://localhost:3306/countmeup"; // url to the sql database running on localhost - may need to be changed to work with other machines
        final String USERNAME = "root"; // Accessing the database as root for the purporse of this assignment
        final String PSW = "Spiderman786"; // Random Password chosen for root.

        Class.forName("com.mysql.jdbc.Driver");  //simplest  way to load the driver required
        Connection connection = (Connection) DriverManager.getConnection(URL, USERNAME, PSW); // make the connection
        
        if (logIn
                != null) { // if Login button value is not null i.e it has been clicked
            LogIn(request, response, connection, out); // call the Login function which logs the user in then passes their vote count to the VotePage.
        } else if (Signup
                != null) {
            SignUp(request, response, connection, out); // Call the signup function which basically adds the user to the database and gives them 3 votes.
        }
    }

    /*This will register any new users - it should be noted all ysers by default have 3 votes*/
    protected void SignUp(HttpServletRequest request, HttpServletResponse response, Connection connection, PrintWriter out) throws ServletException, IOException {

        Statement stmt = null; // Statement     

        try { //Standard try and catch for exception handling

            String Name = request.getParameter("uname"); // obtain the name from the html forum
            String Pass = request.getParameter("psw"); // obtain the password

            stmt = connection.createStatement(); //create the statement
            stmt.executeUpdate("INSERT INTO `users`(Name, Password) VALUE ('" + Name + "','" + Pass + "')");  // Insert new account into Table to allow for more voters

            connection.close(); //close connection when your done.

            /*Redirect them back to the login page with an error box made in javascript.*/
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Account Created - Please Log In');");
            out.println("location='index.html';");
            out.println("</script>");

        } catch (SQLException e) { // used to catch standard exceptions for SQL
            out.println("SQLException: " + e.getMessage());
            out.println("SQLState: " + e.getSQLState());
            out.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", e); //used to catch any other exception that may be generated
        }

    }

    /*If Login has been pressed then this method will be called, simply put this will validate the users credentials against the sql database before letting proceed*/
    protected void LogIn(HttpServletRequest request, HttpServletResponse response, Connection connection, PrintWriter out) throws ServletException, IOException {

        Statement stmt = null; // Statement
        ResultSet rs = null; // Result Set

        try { //Standard try and catch for exception handling

            String Name = request.getParameter("uname"); // obtain the name from the html forum
            String Pass = request.getParameter("psw"); // obtain the password

            stmt = connection.createStatement(); //create the statement
            rs = stmt.executeQuery("SELECT * FROM users"); // execute query and obtain the results in rs - for this purporse we can select everything

            while (rs.next()) { //go through all the results obtained
                if (Name.equals(rs.getString("Name")) && Pass.equals(rs.getString("Password"))) { /*if name and password match with any results from the querey
                     then move on (no salts of security for the assignment)*/

                    String VotesRemaining = rs.getString("Vote Count"); // Make new string and store the vote count as String of the user.               

                    int VR = Integer.parseInt(VotesRemaining); // Convert string number into Int.
                    String Uname = rs.getString("Name"); // get the name of the user thats logging in from the database
                    connection.close(); //close connection when your done.
                    Forward(request, response, VR, Uname); // call doGet which will forward user onto Voting Page along with the reamining votes they have
                }
            }

            /*Redirect them back to the login page with an error box made in javascript if login credentials are invalid.
             Assuming they haven't been redirected to the votepage by the above validation this code will occur*/
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("location='index.html';");
            out.println("</script>");

        } catch (SQLException e) { // used to catch standard exceptions for SQL
            out.println("SQLException: " + e.getMessage());
            out.println("SQLState: " + e.getSQLState());
            out.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", e); //used to catch any other exception that may be generated
        }
    }

    //Simply use dispatcher to forward users onto the voting Page
    protected void Forward(HttpServletRequest request, HttpServletResponse response, int Votes, String User)
            throws ServletException, IOException {
        //Set votes remaining attribute for the current user - Votes count is retrived from the database..
        request.setAttribute("VoteCount", Votes);

        //Store username attribbute, to remember which user is logged in and pass it onto the vote page.
        request.setAttribute("User", User);

        String address = "/WEB-INF/VotePage.jsp"; //Hidden from nonregistered users

        //forward onto the VotingPage along with their remaining votes to be displayed
        request.getRequestDispatcher(address).forward(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter(); // needed to print out exceptions
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            out.println(ex);
        }
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
        
        PrintWriter out = response.getWriter(); // needed to print out exceptions
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            out.println(ex);
        }
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
