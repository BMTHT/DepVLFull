/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.MyServlet;

import com.bmth.DAO.ImageDAO;
import com.bmth.DAO.Register;
import com.bmth.bean.Account;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author quangbach
 */
public class PointImageServlet extends HttpServlet {

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
        response.setContentType("application/json");
        String re = request.getParameter("userid");
        int userId = Integer.parseInt(re);
        Register register = new Register();
        List<Integer> likes = register.liked(userId);
        Gson gson = new Gson();
        String json = gson.toJson(likes);
        response.getWriter().write(json);

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
        processRequest(request, response);
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
        String re = request.getParameter("act");
        String imgid = request.getParameter("imgid");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        ImageDAO dao = new ImageDAO();
        Register res = new Register();
        String json = "";
        List<Integer> liked = res.liked(account.getUserId());
        int imageId = Integer.parseInt(imgid);
        if (re.equals("like")) {
            dao.UpdateImagePoint(1, imageId);
            liked.add(imageId);
            res.updateLiked(liked,account.getUserId());
            json = "{\"point\":" + dao.getImageId(imageId).getPoint() + "}";
        } 
        if(re.equals("dislike")) {
            dao.UpdateImagePoint(-1, imageId);
            liked.remove(new Integer(imageId));
            res.updateLiked(liked,account.getUserId());
            json = "{\"point\":" + dao.getImageId(imageId).getPoint() + "}";
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
