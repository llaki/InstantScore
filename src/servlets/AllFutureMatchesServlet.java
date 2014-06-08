package servlets;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import livescore.CountryUrl;
import livescore.UrlConstants;
import request.Request;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/AllFutureMatchesServlet")
public class AllFutureMatchesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public AllFutureMatchesServlet() throws Exception {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * TODO make it thread safe
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		if (firstRun) {
			createAllFileObjects();
			firstRun = false;
		}
		
		PrintWriter writer = response.getWriter();		
		int totalNumTournaments = 0;
		for(File file : ALL_SCORES_FILES) {
			synchronized (file) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				br.close();
				if(line==null) continue;
				totalNumTournaments += Integer.parseInt(line);
			}
		}
		writer.println(totalNumTournaments);
		for(File file : ALL_SCORES_FILES) {
			synchronized (file) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String str;
				str = br.readLine();
				if(str==null) {
					br.close();
					continue;
				}
				if(Integer.parseInt(str) == 0) {
					br.close();
					continue;
				}
				while((str = br.readLine()) != null){
					writer.println(str);
				}
				br.close();
			}
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
	
	private void createAllFileObjects() {
		int index = 0;
		for(CountryUrl curl : UrlConstants.COUNTRIES_AND_URLS) {
			String countryName = curl.getCountryName();
			ALL_SCORES_FILES[index] = new File("scores."+countryName);
			index++;
		}
	}
	
	private static boolean firstRun = true;
	
	private static final File[] ALL_SCORES_FILES = new File[UrlConstants.COUNTRIES_AND_URLS.length];
	
}
