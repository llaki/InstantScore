package servlets;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request.Request;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/ActiveMatchesServlet")
public class ActiveMatchesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public ActiveMatchesServlet() throws Exception {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String[] fileNames = new String[]{"scores.Live"};
		for(String filename : fileNames) {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str;
			while((str = br.readLine()) != null){
				writer.println(str);
			}
			br.close();
		}
		writer.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		String phoneNum = request.getParameter("phonenum");
		String securityCode = request.getParameter("securitycode");
		new Request(data, phoneNum, securityCode);
	}

}
