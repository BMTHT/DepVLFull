/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.DAO;


import com.bmth.DatabaseConnection.MyConnection;
import com.bmth.bean.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BuiNgocTruong
 */
public class ImageDAO {
    Connection conn = null;
    private final String table = "image";
    
    //get number of row of image's table
    public int getNumberRowTableImage(){
        int numberRow = 0;
        conn = new MyConnection().Connect();
        String command = "select count(*) from " + table;
        try {  
            PreparedStatement pst = conn.prepareStatement(command);
            ResultSet rs = pst.executeQuery();           
            while(rs.next()){
                numberRow = rs.getInt("count(*)");
            }         
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        MyConnection.close(conn);
        return numberRow;
    }
    
    //get all of image of image's table
    public ArrayList<Image> getAllImage(){
        ArrayList<Image> imageList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "select * from " + table;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(command);
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    }
    
    //get 5 images from last image's table
    public ArrayList<Image> getFiveImage(int index){
        ArrayList<Image> imageList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "SELECT * FROM image ORDER BY 1 DESC LIMIT " + index + ", 5";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    }
    
    //get 5 images from last image's table, it depends on imageTheme
    public ArrayList<Image> getFiveImageByTheme(int index,String imageTheme){
        ArrayList<Image> imageList = new ArrayList<>();
        if(imageTheme.equals("home")) {
            imageList = getFiveImage(index);
            return imageList;
        }
        if(imageTheme.equals("top")) return RankImage();
        conn = new MyConnection().Connect();
        String command = "SELECT * FROM image WHERE theme = ? ORDER BY 1 DESC LIMIT " + index + ", 5";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setString(1, imageTheme);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    }
    
    //get 5 images from last image's table, it depends on userId
    public ArrayList<Image> getFiveImageByUserId(int index,int id){
        ArrayList<Image> imageList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "SELECT * FROM image WHERE userId = ? ORDER BY 1 DESC LIMIT " + index + ", 2";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    }
    
    //get all of image of the same people by userId
    public ArrayList<Image> getAllImageUserId(int userId){
        ArrayList<Image> imageList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "select * from " + table + " where userId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    } 
    
    //get all of image of the same theme by theme
    public ArrayList<Image> getAllImageTheme(String theme){
        ArrayList<Image> imageList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "select * from " + table + " where theme = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setString(1, theme);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Image image = new Image();
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
                imageList.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return imageList;
    }
    
    //get 10 images what have top of the table
    public ArrayList<Image> RankImage(){
        ArrayList<Image> imageRank = new ArrayList<>();
        ArrayList<Image> imageList = getAllImage();
        int dem = 0;
        int maxIndex = imageList.size() < 10 ? imageList.size() : 10;
        int max;
        while(dem < maxIndex){
            max = 0;
            for(int i = 1;i<imageList.size();i++){
                if(imageList.get(max).getPoint() < imageList.get(i).getPoint()){
                    max = i;
                }
            }
            imageRank.add(imageList.get(max));
            dem++;
            imageList.remove(max);
        }
        MyConnection.close(conn);
        return imageRank;
    }
    
    // add image
    public void AddImage(Image image){
        String query = "insert into image values(?,?,?,?,?,?,?)";
        conn = new MyConnection().Connect();
        String sql = "SET FOREIGN_KEY_CHECKS=0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, getNumberRowTableImage() + 1);
            pst.setInt(2, image.getUserId());
            pst.setString(3, image.getImgDescribe());
            pst.setString(4, image.getImageDateToString());
            pst.setString(5, image.getTheme());
            pst.setFloat(6, image.getPoint());
            pst.setString(7, image.getImgUrl());
            
            if(pst.executeUpdate() > 0){
                System.out.println("Insert data success");
            }
            
        } catch (SQLException ex) {
            System.out.println("Insert data fail " + ex.toString());
        }
        MyConnection.close(conn);
    }
    
    //get image by imgId
    public Image getImageId(int imgId){
        Image image = new Image();
        String command = "select * from image where imgId = ?";
        conn = new MyConnection().Connect();
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, imgId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                image.setImgId(rs.getInt(1));
                image.setUserId(rs.getInt(2));
                image.setImgDescribe(rs.getString(3));
                image.setImgDate(rs.getDate(4));
                image.setTheme(rs.getString(5));
                image.setPoint(rs.getFloat(6));
                image.setImgUrl(rs.getString(7));
            }
            if(pst.executeUpdate() > 0){
                System.out.println("select data success");
            }
        } catch (SQLException ex) {
            System.out.println("select data fail " + ex.toString());
        }
        MyConnection.close(conn);
        return image;
    }
    
    //update point for image
    public void UpdateImagePoint(float a,int imgId){
        Image image = getImageId(imgId);
        image.setPoint(image.getPoint()+a);
        String command = "update image set point = ? where imgId = ?";
        conn = new MyConnection().Connect();
        try { 
            PreparedStatement cs = conn.prepareStatement(command);
            cs.setFloat(1, image.getPoint());
            cs.setInt(2, imgId);
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
    }
    
    //edit discribe of image
    public void UpdateImageDescribe(String discribe,int imgId){
        Image image = getImageId(imgId);
        image.setImgDescribe(discribe);
        String command = "update image set imgDescribe = ? where imgId = ?";
        conn = new MyConnection().Connect();
        try { 
            PreparedStatement cs = conn.prepareStatement(command);
            cs.setString(1, image.getImgDescribe());
            cs.setInt(2, imgId);
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
    }
    
    public static void main(String[] args) throws ParseException{
        ImageDAO imageDao = new ImageDAO();
        
//        Image image = new Image();
//        image.setUserId(4);
//        image.setImgDescribe("that la sung suong");
//        String birthday = "1995-10-25";
//        Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
//        image.setImgDate(utilDate);
//        image.setTheme("thien nhien");
//        image.setPoint(50f);
//        image.setImgUrl("chua co URL");
//        imageDao.AddImage(image);

          ArrayList<Image> imageList = new ArrayList<>();
          imageList = imageDao.RankImage();
          System.out.println("imgId   userID   imgDesribe          imgDate          theme          point     URL");
          for(int i=0;i<imageList.size();i++){
              System.out.println(imageList.get(i).getImgId()+"   "+imageList.get(i).getUserId()+"   "+imageList.get(i).getImgDescribe()+"          "+imageList.get(i).getImageDateToString()+"          "+imageList.get(i).getTheme()+"          "+imageList.get(i).getPoint()+"     "+imageList.get(i).getImgUrl());
          }
            
    }
    
}
