/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.DAO;

import com.bmth.DatabaseConnection.MyConnection;
import com.bmth.bean.Account;
import com.bmth.bean.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BuiNgocTruong
 */
public class Register {

    Connection conn = null;

    private final String tableUser = "user";
    private final String tableAccount = "account";

    public Register() {
    }

    //get all of account, add them into ArrayList
    public ArrayList selectAll() {
        ArrayList accountList = new ArrayList<>();
        String command = "SELECT id, userId, username, password, fullName, nickName, birthDay, gender, email, address, phoneNumber, avatarUrl From user, account WHERE account.userId=user.userId";
        conn = new MyConnection().Connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(command);
            while (rs.next()) {
                Account account = new Account(rs.getInt(1), rs.getInt(2), rs.getString(5), rs.getString(6), rs.getDate(7),
                        rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(3), rs.getString(4));
                accountList.add(account);
            }
        } catch (SQLException ex) {
            System.out.println("Select fail " + ex.toString());
        }
        MyConnection.close(conn);
        return accountList;
    }

    //Count number of rows of 1 a table
    public int getNumberRowTable(String table) {
        int numberRow = 0;
        conn = new MyConnection().Connect();
        String command = "select count(*) from " + table;
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                numberRow = rs.getInt("count(*)");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        MyConnection.close(conn);
        return numberRow;
    }

    // Check all of account when register. Return 'flase' if account exist, unless return 'true'
    public boolean CheckRegister(Account account) {
        conn = new MyConnection().Connect();
        try {
            String command = "SELECT email, username, password From account, " + "user WHERE user.userId=account.userId";
            PreparedStatement pst = conn.prepareStatement(command);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equals(account.getEmail()) || rs.getString(2).equals(account.getUsername()) || rs.getString(3).equals(account.getPassword())) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Check fail " + ex.toString());
            
            return false;
        }
        
        return true;
    }

//    public boolean CheckLogIn(Account account){
//        conn = new MyConnection().Connect();
//        try {
//            String command = "SELECT username, password From account, " + "user WHERE user.userId=account.userId";
//            PreparedStatement pst = conn.prepareStatement(command);
//            ResultSet rs = pst.executeQuery();
//            
//            while(rs.next()){
//                if(rs.getString(1).equals(account.getUsername()) && rs.getString(2).equals(account.getPassword())){
//                    return true;
//                }
//            }
//        } catch (SQLException ex) {
//            System.out.println("Check fail " + ex.toString());
//            return false;
//        }
//        return false;
//    }
    public boolean CheckLogIn(String userName, String passWord) {
        conn = new MyConnection().Connect();
        try {
            String command = "SELECT username, password From account, " + "user WHERE user.userId=account.userId";
            PreparedStatement pst = conn.prepareStatement(command);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equals(userName) && rs.getString(2).equals(passWord)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Check fail " + ex.toString());
            return false;
        }

        return false;
    }

    public Account getAccountbyUsername(String userName) {
        Account account = new Account();
        String command = "select * from account where username = ?";
        conn = new MyConnection().Connect();
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                account.setId(rs.getInt(1));
                account.setUserId(rs.getInt(2));
                account.setUsername(rs.getString(3));
                account.setPassword(rs.getString(4));
            }

        } catch (SQLException ex) {
            System.out.println("select data fail " + ex.toString());
        }
        //MyConnection.close(conn);
        return account;
    }

    //Add data into database when check succeed
    public boolean AddUser(Account account) {
       Connection con = new MyConnection().Connect();

        String sql = "SET FOREIGN_KEY_CHECKS=0";

        String command = "Insert Into account(id,userId,username,password) VALUES(?,?,?,?)";
        String command2 = "INSERT INTO user(userId,fullName,nickName,birthDay,gender,email,address,phoneNumber,avatarUrl,liked) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            int userId = getNumberRowTable("user") + 1;
            PreparedStatement cs2 = con.prepareStatement(command2);
            cs2.setInt(1, userId);
            cs2.setString(2, account.getFullName());
            cs2.setString(3, account.getNickName());
            cs2.setString(4, account.getBirthdayToString());
            cs2.setInt(5, account.getGender());
            cs2.setString(6, account.getEmail());
            cs2.setString(7, account.getAddress());
            cs2.setString(8, account.getPhoneNumber());
            cs2.setString(9, account.getAvatarUrl());
            cs2.setString(10, "");

            PreparedStatement cs = con.prepareStatement(command);
            cs.setInt(1, getNumberRowTable("account") + 1);
            cs.setInt(2, userId);
            cs.setString(3, account.getUsername());
            cs.setString(4, account.getPassword());
            if (cs2.executeUpdate() > 0) {
                if (cs.executeUpdate() > 0) {
                    
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Insert data fail " + ex.toString());
            ex.printStackTrace();
            return false;
        }
       
        return false;
    }

    public boolean UpdateUser(Account account) {
        String query = "UPDATE account a,user b SET username=?, password=?, nickName=?, gender=?, email=?, address=?, phoneNumber=?, avatarUrl=? "
                + "WHERE a.id=? AND a.userId=b.userId";
        conn = new MyConnection().Connect();
        try {
            PreparedStatement cs = conn.prepareStatement(query);
            cs.setString(1, account.getUsername());
            cs.setString(2, account.getPassword());
            cs.setString(3, account.getNickName());
            cs.setInt(4, account.getGender());
            cs.setString(5, account.getEmail());
            cs.setString(6, account.getAddress());
            cs.setString(7, account.getPhoneNumber());
            cs.setString(8, account.getAvatarUrl());
            cs.setInt(9, account.getId());
            if (cs.executeUpdate() > 0) {
                MyConnection.close(conn);
                return true;
            }
            System.out.println("Update data succeed ");
        } catch (SQLException ex) {
            System.out.println("Update data fail " + ex.toString());
            MyConnection.close(conn);
            return false;
        }
        MyConnection.close(conn);
        return true;
    }

    public void delete() {
        String sql = "SET FOREIGN_KEY_CHECKS=0";

        String command = "delete from account where id > 0";
        conn = new MyConnection().Connect();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();

            PreparedStatement pst = conn.prepareStatement(command);
            //pst.setInt(1, userId);
            if (pst.executeUpdate(command) > 0) {
                System.out.println("aaa");
            }
        } catch (SQLException ex) {
            System.out.println("delete fail " + ex.toString());
        }
        MyConnection.close(conn);
    }

    public void insertAccount(Account account) {
        try {
            String command = "insert into account values(?,?,?,?)";
            conn = new MyConnection().Connect();
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, account.getId());
            pst.setInt(2, account.getUserId());
            pst.setString(3, account.getUsername());
            pst.setString(4, account.getPassword());
            if (pst.executeUpdate() > 0) {
                System.out.println("success");
            }
        } catch (SQLException ex) {
            System.out.println("fail " + ex.toString());
        }
        MyConnection.close(conn);
    }

    public User getUserById(int id) {
        User user = new User();
        String command = "select * from user where userId = ?";
        conn = new MyConnection().Connect();
        try {
            PreparedStatement pst = conn.prepareStatement(command);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.setUserId(id);
                user.setFullName(rs.getString("fullName"));
                user.setNickName(rs.getString("nickName"));
                user.setBirthDay(rs.getDate("birthDay"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAvatarUrl(rs.getString("avatarUrl"));
                user.setGender(rs.getInt("gender"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
            }

        } catch (SQLException ex) {
            System.out.println("select data fail vv" + ex.toString());
        }
        // MyConnection.close(conn);
        return user;
    }
    
    public List<Integer> liked(int userid){
        List<Integer> like = new ArrayList<>();
        String sql = "select liked from user where userId=?";
        Connection con = new MyConnection().Connect();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            String s = "";
            while(rs.next()){
                s = rs.getString(1);
            }
            String likes[] = s.split(",");
            int i=0;
            while(i < likes.length){
                like.add(Integer.parseInt(likes[i]));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return like;
    }

     public void updateLiked(List<Integer> liked , int userid){
        String likes="";
        for(int i = 0; i < liked.size()-1; i++){
            likes += liked.get(i)+",";
        }
        likes = likes + liked.get(liked.size()-1)+"";
        String command = "update user set liked = ? where userId = ?";
        conn = new MyConnection().Connect();
        try { 
            PreparedStatement cs = conn.prepareStatement(command);
            cs.setString(1, likes);
            cs.setInt(2, userid);
            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnection.close(conn);
    }
    
    public static void main(String[] args) throws ParseException {
        Register register = new Register();
        List<Integer> list = register.liked(1);
        list.add(12);
        register.updateLiked(list, 1);
    }
}
