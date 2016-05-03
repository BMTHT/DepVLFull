/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.DAO;

import com.bmth.DatabaseConnection.MyConnection;
import com.bmth.bean.Comment;
import com.bmth.DAO.ImageDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BuiNgocTruong
 */
public class CommentDAO {
    Connection conn = null;
    private final String table = "comment";
    
    public int getNumberRowTableComment(){
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
    
    public void AddComment(Comment comment){
        String command = "Insert Into comment VALUES(?,?,?,?)";
        conn = new MyConnection().Connect();
        String sql = "SET FOREIGN_KEY_CHECKS=0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, getNumberRowTableComment()+1);
            pst.setInt(2, comment.getImgId());
            pst.setInt(3, comment.getUserId());
            pst.setString(4, comment.getComment());
            
            if(pst.executeUpdate() > 0){
                System.out.println("Insert data success");
            }
            
        } catch (SQLException ex) {
            System.out.println("Insert data fail " + ex.toString());
        }
        MyConnection.close(conn);
    }
    
    public ArrayList<Comment> getAllCommentByImageId(int imgId){
        ArrayList<Comment> commentList = new ArrayList<>();
        conn = new MyConnection().Connect();
        String command = "select * from " + table + " where imgId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, imgId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Comment comment = new Comment();
                comment.setId(rs.getInt(1));
                comment.setImgId(rs.getInt(2));
                comment.setUserId(rs.getInt(3));
                comment.setComment(rs.getString(4));
       
                commentList.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
        return commentList;
    }
    
    public static void main(String[] args){
        CommentDAO commentDao = new CommentDAO();
        
        ArrayList<Comment> imageList = new ArrayList<>();
        imageList = commentDao.getAllCommentByImageId(2);
        System.out.println("id    imgId    userId    comment");
        for(int i=0;i<imageList.size();i++){
            System.out.println(imageList.get(i).getId()+"   "+imageList.get(i).getImgId()+"   "+imageList.get(i).getUserId()+"   "+imageList.get(i).getComment());
        }
    }
}
