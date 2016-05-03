/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.MyServlet;

import com.bmth.DAO.Register;
import com.bmth.bean.Account;
import com.bmth.bean.User;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author quangbach
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String act = request.getParameter("get");
        response.setContentType("application/json");
        HttpSession session = request.getSession();
        Register res = new Register();
        Account account = (Account) session.getAttribute("account");
         Gson gson = new Gson();
        if (account == null) {      
            String json = "{\"userId\" : 0}";
            response.getWriter().write(json);
        } else {
            User user = res.getUserById(account.getUserId());
            String json = gson.toJson(user);
            response.getWriter().write(json);
        }

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

        response.setContentType("application/json");
        String post = request.getParameter("act");
        String username = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        Gson gson = new Gson();
        Register login = new Register();
        boolean checkLogIn = login.CheckLogIn(username, password);
        if (checkLogIn) {
            Account account = login.getAccountbyUsername(username);
            String json12 = gson.toJson(account);
            HttpSession session = request.getSession(true);
            session.setAttribute("account", account);
            response.getWriter().write(json12);

        } else {
            String json1 = "{\"id\": 0 }";
            response.getWriter().write(json1);
        }

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
