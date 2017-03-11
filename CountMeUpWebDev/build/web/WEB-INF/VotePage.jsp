<%-- 
    Document   : VotePage
    Created on : 08-Mar-2017, 22:37:03
    Author     : Humza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="CMU.css">
        <script type="text/javascript" src="JS/CMU.js"></script> 
        <% int VC = (Integer) request.getAttribute("VoteCount"); // Retrieving user vote count.
            String Username = (String) request.getAttribute("User"); // Retrieving username. %>
    </head>
    <body>
        <h1> Welcome to the Voting System <%=Username%> </h1> 
        <div class ="Container ">
            <form  method="POST" action="VoteCounter">
                <h1> Votes Remaining: <%=VC%> </h1>
                <h3> Please Select Who You Want You Vote For: </h3> 
                <label> Candidate 1: </label>
                <input type="radio" name="Vote" value="1">
                <br>
                <label> Candidate 2: </label>
                <input type="radio" name="Vote" value="2" >
                <br>
                <label> Candidate 3: </label>
                <input type="radio" name="Vote" value="3" >
                <br>
                <label> Candidate 4: </label>
                <input type="radio" name="Vote" value="4" >
                <br>
                <label> Candidate 5: </label>
                <input type="radio" name="Vote" value="5" >
                <br>
                <input type="hidden" name="VotesLeft" value=<%=VC%>  >
                <input type="hidden" name="User" value=<%=Username%>  >
                <button type="submit" name = "SubVote"> Submit Vote! </button>
            </form>
        </div>
    </body>
</html>

