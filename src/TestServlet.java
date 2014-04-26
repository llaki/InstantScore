

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		BufferedReader br = new BufferedReader(new FileReader("scores"));
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
		// Security Code Request for: +995598374203
		if(str.startsWith("Security Code Request for")){
			int index = str.indexOf(":");
			String phoneNumber = (index==-1 || index==str.length()-1 ? "" : str.substring(index+1));
			while(phoneNumber.length()>0 && phoneNumber.charAt(0)==' '){
				phoneNumber = phoneNumber.substring(1);
			}
			if(phoneNumber.length()>0){
				String genCode = CodeGenerator.generateCodeForPhoneNumber(phoneNumber);
				CodeGenerator.writeIntoFile(phoneNumber, genCode);
				CodeGenerator.sendSecurityCodeToUser(phoneNumber, genCode);
			}
			return;
		}
		// New matches subscription case
		Request req = new Request(str);
		System.out.println(req.getPhoneNumber()+" \n"+req.getMatchIds());
	}

}
