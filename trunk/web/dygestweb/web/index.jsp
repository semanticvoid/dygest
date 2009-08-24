<%-- 
    Document   : index
    Created on : Aug 9, 2009, 2:08:10 PM
    Author     : anand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dygest - summarize the web</title>
    </head>
    <body>
        <center>
            <div style="margin-top: 10%">
        <table border="0">
            <tr>
                <td><center><img src="images/logo.png"/></center></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>
                    <form action="/dygest/summarize">
                        <input type="text" size="40" name="u">&nbsp;&nbsp;<input type="submit" value="summarize">
                    </form>
                </td>
            </tr>
        </table>
        </div>
        </center>
    </body>
</html>
