package com.bmth.MyServlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.bmth.DAO.ImageDAO;
import com.bmth.DAO.Register;
import static com.bmth.MyServlet.UploadServlet.UPLOAD_DIRECTORY;
import com.bmth.bean.Account;
import com.bmth.bean.Image;
import com.bmth.bean.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
    public static final String UPLOAD_DIRECTORY = "/home/quangbach/tomcat8/webapps/Avatar";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        Account account = new Account();
        // process only if it is multipart content
        String username = "1";
        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                // Parse the request
                List<FileItem> multiparts = upload.parseRequest(request);

                for (FileItem item : multiparts) {

                    if (!item.isFormField()) {
                        String fileName = UPLOAD_DIRECTORY + File.separator + username;
                        File file = new File(fileName);
                        item.write(file);
                        account.setAvatarUrl("http://localhost:8080/Avatar/" + file.getName());
                    } else {
                        String fieldName = item.getFieldName();
                        if (fieldName.equals("firstname")) {
                            account.setFullName(item.getString());
                        }
                        if (fieldName.equals("user_name")) {
                            username = item.getString();
                            account.setUsername(username);
                        }
                        if (fieldName.equals("nickname")) {
                            account.setNickName(item.getString());
                        }
                        if (fieldName.equals("user_password")) {
                            account.setPassword(item.getString());
                        }
                        if (fieldName.equals("user_email")) {
                            account.setEmail(item.getString());
                        }
                        if (fieldName.equals("sex")) {
                            int gender = item.getString().equals("male") ? 1 : 0;
                            account.setGender(gender);
                        }
                        if (fieldName.equals("date")) {
                            String date = item.getString();
                            String[] dates = date.split("-");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));
                            cal.set(Calendar.MONTH, Integer.parseInt(dates[1]));
                            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
                            Date birtday = new Date(cal.getTimeInMillis());
                            account.setBirthDay(birtday);
                        }
                        if (fieldName.equals("address")) {
                            account.setAddress(item.getString());
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        account.setPhoneNumber("01698662215");
        Register re = new Register();
        boolean check = re.AddUser(account);
        String json;
        if (check) {
           Account account1 = re.getAccountbyUsername(username);
            json = "{\"userId\":" + account1.getUserId() + "}";
        } else {
            json = "{\"userId\": 0}";
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

}
