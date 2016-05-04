/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.MyServlet;

import com.bmth.DAO.ImageDAO;
import com.bmth.DAO.Register;
import com.bmth.bean.Account;
import com.bmth.bean.Image;
import com.bmth.bean.User;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

/**
 *
 * @author quangbach
 */
public class UploadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static final String UPLOAD_DIRECTORY = "/home/quangbach/tomcat8/webapps/Image";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Register re = new Register();
        User user = re.getUserById(account.getUserId());
        // process only if it is multipart content
        String status = "aaa";
        String category = "other";
        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                // Parse the request
                List<FileItem> multiparts = upload.parseRequest(request);
                ImageDAO img = new ImageDAO();
                int imgId = (img.getNumberRowTableImage() + 1);
                Image image = new Image();
                image.setImgId(imgId);
                image.setUserId(user.getUserId());
                image.setPoint(0f);
                image.setImgDate(Calendar.getInstance().getTimeInMillis());

                for (FileItem item : multiparts) {

                    if (!item.isFormField()) {
                        String contentType = item.getContentType();
                        String [] ext = contentType.split("/");
                        
                        String fileName = UPLOAD_DIRECTORY + File.separator + user.getUserId() + "_" + imgId  ;
                        File file = new File(fileName);
                        item.write(file);
                        
                        image.setImgUrl("http://localhost:8080/Image/"+ file.getName());
                    } else {
                        String fieldName = item.getFieldName();
                        if (fieldName.equals("status")) {
                            image.setImgDescribe(item.getString());
                        }
                        if (fieldName.equals("optionsRadios")) {
                            image.setTheme(theme(item.getString()));
                        }
                    }
                }

                img.AddImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String json = "{\"message\": \"success\"}";
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

    private String theme(String theme1) {
        String theme = "";
        int themeId = Integer.parseInt(theme1);
        switch (themeId) {
            case 1:
                theme = "nature";
                break;
            case 2:
                theme = "portrait";
                break;
            case 3:
                theme = "hotgirl";
                break;
            case 4:
                theme = "stilllife";
                break;
            case 5:
                theme = "other";
                break;
            default:
                theme = "other";
                break;
        }
        return theme;
    }
}
