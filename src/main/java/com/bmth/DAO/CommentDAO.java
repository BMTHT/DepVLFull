/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.DAO;

import com.bmth.DatabaseConnection.MyConnection;
import com.bmth.bean.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public int getCommentId(){
        int id=0;
        String command = "select id from comment order by id desc limit 1";
        Connection con = new MyConnection().Connect();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(command);
            while(rs.next()){
                id = rs.getInt(1)+1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
        
    }
    
    public void AddComment(Comment comment){
        String command = "Insert Into comment VALUES(?,?,?,?)";
        conn = new MyConnection().Connect();
        String sql = "SET FOREIGN_KEY_CHECKS=0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, getCommentId());
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
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentList;
    }
    
    public static void main(String[] args){
        CommentDAO commentDao = new CommentDAO();
        
       System.out.print(commentDao.getCommentId());
    }
}
