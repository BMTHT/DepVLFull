package com.bmth.MyServlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.bmth.DAO.Register;
import com.bmth.bean.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author quangbach
 */
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        response.setContentType("applcation/json");
        String re = request.getParameter("act");
        String name = request.getParameter("firstname");
        String username = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        String email = request.getParameter("user_email");
        String sex = request.getParameter("sex");
        String date = request.getParameter("date");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("ddd");

        String[] dates = date.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dates[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
        Date birtday = new Date(cal.getTimeInMillis());

        int gender = sex.equals("male") ? 1 : 0;
        Account account = new Account(name, username, birtday, gender, email, address, "01698662215", username, password);
        account.setAvatarUrl("Image/avatar/default.png");
        Register register = new Register();
        boolean check = register.AddUser(account);
        String json;
        if (check) {
            account = register.getAccountbyUsername(username);
            json = "{\"userId\":" + account.getUserId() + "}";
        } else {
            json = "{\"userId\": 0}";
        }

        response.getWriter().write(json);
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
