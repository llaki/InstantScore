

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//			MatchesFileReadUtils.fakeUpdateViaMatchList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		BufferedReader br = new BufferedReader(new FileReader("/usr/share/tomcat7/scores"));
		String str;
		while((str = br.readLine()) != null){
			writer.println(str);
		}
		writer.flush();
		br.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("data");
		Request req = new Request(str);
		System.out.println(req.getPhoneNumber()+" \n"+req.getMatchIds());
	}

}
