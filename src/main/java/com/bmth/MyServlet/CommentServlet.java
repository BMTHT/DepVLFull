/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.MyServlet;

import com.bmth.DAO.CommentDAO;
import com.bmth.bean.Account;
import com.bmth.bean.Comment;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author quangbach
 */
public class CommentServlet extends HttpServlet {

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
        String imgParameter = request.getParameter("imgId");
        int imgId = Integer.parseInt(imgParameter);
        CommentDAO comment = new CommentDAO();
        ArrayList<Comment> listComment = comment.getAllCommentByImageId(imgId);
        Gson gson = new Gson();
        String json = gson.toJson(listComment);
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
        String post = request.getParameter("act");
        response.setContentType("application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "aa";
        if (br != null) {
            json = br.readLine();
        }
        CommentDAO commentDAO = new CommentDAO();
        Comment comment = new Comment();
        Gson gson = new Gson();
        comment = gson.fromJson(json, Comment.class);
        comment.setId(commentDAO.getNumberRowTableComment() + 1);
        commentDAO.AddComment(comment);
        String json2 = gson.toJson(comment);
        response.getWriter().write(json2);
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
