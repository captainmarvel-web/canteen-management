/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.UserBeans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DELL
 */
public class Bill {

    
    private DbConnection db;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private int total;
    
    public Bill(){
        try{
            db = new DbConnection();
            conn = db.getConnection();
            stmt = db.getStatement();
            rs = stmt.executeQuery("Select * from bill_item");
            if(rs.next()){
                total = rs.getInt(1);
            }
            
        } catch (SQLException e) {
          Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, e);
           db.setWarningMessage("Bill", e.toString());

        }
    }

    public boolean insertitem(UserBeans e) {
        boolean flag = true;
        try
        {
            String sql = "insert into bill_item values(?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, e.getBiname());
            p.setInt(2, e.getBillqty());
            p.setDouble(3, e.getBprice());
            p.setDouble(4, e.getItemtotal());
            flag = p.execute();
            System.out.println("flag = "+flag);
        }catch(SQLException ex){
            db.setWarningMessage("Bill.insertaction", ex.toString());
        }
        return flag;
     }
     public UserBeans setBill(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setBiname(rs.getString("bi_name"));
                e.setBillqty(rs.getInt("bqty")); 
                e.setBprice(rs.getDouble("bitem_name"));         
                e.setItemtotal(rs.getDouble("btotal_price"));
             }
            catch(SQLException ex){
                db.setWarningMessage("Bill_item.setBill", ex.toString());
            }
        }
        return e;
    }

    public ObservableList<UserBeans> showTableRecords() {
        ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from bill_item");
                while(rs.next()){
                    list.add(setBill(rs));
                }
              // updateRecord();               
            }
            catch(SQLException ex){
                db.setWarningMessage("Bill_item Model.showTableRecords", ex.toString());
            }
        return list;
    }
      public UserBeans searchItem(String Iname){
       UserBeans e=null;
     try{
         String sql="select * from bill_item where bi_name='"+Iname+"'";
         rs=stmt.executeQuery(sql);
         if(rs.next()){
           e=setBill(rs);
         }
         else{
             db.setWarningMessage(" Item Info does not Exist!","");  
         }
        
     }
     catch(SQLException ex){
         db.setWarningMessage("ItemModel.searchLeaveId",ex.toString());
     }
     return e;
      }

    public boolean billrun(UserBeans e) {
        try {
            rs = stmt.executeQuery("Select * from bill_gen");
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean flag = true;
        try
        {
            String sql = "insert into bill_gen values(?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, e.getBid());
            p.setString(2, e.getBdate());
            p.setDouble(3, e.getBtotal());
            
            flag = p.execute();
            System.out.println("flag = "+flag);
        }catch(SQLException ex){
            db.setWarningMessage("Bill.billrun", ex.toString());
        }
        return flag;
    }
     public UserBeans setBill1(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setBid(rs.getInt("b_id"));
                e.setBdate(rs.getString("bill_date")); 
                e.setBtotal(rs.getDouble("bill_total"));         
                
             }
            catch(SQLException ex){
                db.setWarningMessage("Bill_gen.setBill1", ex.toString());
            }
        }
        return e;
    }

    public ObservableList<UserBeans> showTableRecords1()
    {
        ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from bill_gen");
                while(rs.next()){
                    list.add(setBill1(rs));
                }
              // updateRecord();               
            }
            catch(SQLException ex){
                db.setWarningMessage("Bill_gen Model.showTableRecord1", ex.toString());
            }
        return list;
    }

    /*public UserBeans searchItem1(Integer name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   */
    }
   


