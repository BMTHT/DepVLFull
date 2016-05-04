/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.MyServlet;

import com.bmth.DAO.ImageDAO;
import com.bmth.bean.Image;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author quangbach
 */
public class ImageServlet extends HttpServlet {

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
        String pr = request.getParameter("page");
        int page = Integer.parseInt(pr);
        String theme = request.getParameter("theme");
        ImageDAO img = new ImageDAO();
        boolean checkNumber = isNumberr(theme);

        int index = img.getNumberRowTableImage();
        index = (page-1) * 5;// > 0 ? index - page * 5 -1: (page-1)*5;
        List<Image> listImage = img.getFiveImageByTheme(index, theme);
        if(listImage.size() < 5) {
            Image nullImage = new Image();
            nullImage.setImgId(0);
            listImage.add(nullImage);
        }
        Gson gson = new Gson();
        String json = gson.toJson(listImage);

        if (checkNumber) {
            int id = Integer.parseInt(theme);
            index = index - page * 5 > 0 ? index - page * 5 : 0;
            listImage = img.getFiveImageByUserId(index, id);
            if(listImage.size() != 5) {
            Image nullImage = new Image();
            nullImage.setImgId(0);
            listImage.add(nullImage);
        }
            json = gson.toJson(listImage);

        }
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
        processRequest(request, response);
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

    private static boolean isNumberr(String s) {
        try {
            int d = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
