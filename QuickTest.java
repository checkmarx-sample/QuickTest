import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartServlet.
 * See <a href="http://www.javacodegeeks.com/2012/11/sql-injection-in-java-application.html">http://www.javacodegeeks.com/2012/11/sql-injection-in-java-application.html</a>
 * Call with <code>sdfssd' or '1'='1</code> or <code>ramki' UNION SELECT * FROM mysql.`user` u --</code>
 */
public class QuickTest extends HttpServlet {

    /** serialVersionUID description. */
    private static final long serialVersionUID = -3727179258077207351L;

   

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>SQL Injection Example</h1><br/><br/>");
		
		myClass testSample = new myClass(); 
		
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet userCheck</title>");
            out.println("</head>");
            out.println("<body>");
            String user = request.getParameter("user");

            System.out.println("MySQL Connect Example.");
            Connection conn = null;
            String url = "jdbc:mysql://127.0.0.1:3306/";
            String dbName = "sql_inject";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root";
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                System.out.println("Connected to the database");

                
                //----Add a custom sanitizer-----------------------------------------------------------------------------------            
                // Highlight fixThis and right click to add fixThis as a custom sanitizer to the Find_SQL_Sanitize rule. 
                // The overidden rule should look something like this
                // 
                //   result = base.Find_SQL_Sanitize();
                //   result.Add(All.FindByName("QuickTest.processRequest.testSample.fixThis") +
                //   All.FindAllReferences(All.FindDefinition(All.FindByName("QuickTest.processRequest.testSample.fixThis"))) +
                //   All.FindAllReferences(All.FindByName("QuickTest.processRequest.testSample.fixThis"))+ 
                //   All.FindByMemberAccess("myClass.fixThis"));
                //------------------------------------------------------------------------------------------------------------- 


                
                //---Classic SQL Injection--------------------------------------------------------------------------
                Statement st = conn.createStatement();
                String query = "SELECT * FROM  User where userid='" + user + "'";
                query=testSample.fixThis(query);
                ResultSet res = st.executeQuery(query);
                //--------------------------------------------------------------------------------------------------

                out.println("Query : " + query);   
//               //---Parameterize Query Instead--------------------------------------------------------------------------
//               PreparedStatement st=conn.prepareStatement("SELECT * FROM  usercheck where username=?");
//               st.setString(1, user);
//               ResultSet res = st.executeQuery();
//               //---------------------------------------------------------------------------------------  
                

                out.println("<br/><br/>Results");
                while (res.next()) {
                    // int i = res.getInt("Emp_code");
                    String s = res.getString("userId");
                    out.println("<br/><br/>\t\t" + s);
 
              
                }

                conn.close();
                System.out.println("Disconnected from database");
            } catch (Exception e) {
                e.printStackTrace();
            }

            out.println("</body>");
            out.println("</html>");

        }

        finally {
            out.close();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}