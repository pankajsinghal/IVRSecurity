<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html style="height: 100%;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><tiles:insertAttribute name="title" ignore="true" /></title>
    </head>
    <body style= "margin:0;padding:0;height: 100%;">
        <table  align="center" width="100%" height="100%" cellspacing = "0" cellpadding ="0" bgcolor="#dedede">
            <tr height="20">
                <td colspan="2">
                    <tiles:insertAttribute name="header" />
                </td>
            </tr>
            
              <tr>
                <td valign="top">
                   <tiles:insertAttribute name="body" />
                </td>
            </tr>
            <tr height="20">
                <td  colspan="2">
                    <tiles:insertAttribute name="footer" />
                </td>
            </tr>
        </table>
    </body>
</html>
