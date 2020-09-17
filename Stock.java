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
import javafx.scene.control.Alert;

/**
 *
 * @author DELL
 */
public class Stock {
    private DbConnection db;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
   // private int total, current;
    
    public Stock(){
    try 
    {
            db = new DbConnection();
            conn = db.getConnection();
            stmt = db.getStatement();
            rs = stmt.executeQuery("select * from stock_mstr");
    } 
         catch (SQLException ex) {
           // Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
           db.setWarningMessage("StockModel", ex.toString());
        }
    
    }
    public boolean insertstock(UserBeans e){
        boolean flag = true;
        try
        {
            String sql = "insert into stock_mstr values(?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, e.getPrdname());
            p.setString(2, e.getPrdtype());
            p.setInt(3, e.getQty());
            p.setDouble(4, e.getPrdamt());
            p.setDouble(5, e.getPrdcost());
            p.setString(6, e.getPrddate());
            flag = p.execute();
            //System.out.println("flag = "+flag);
        }catch(SQLException ex){
            db.setWarningMessage("Stock.insertstock", ex.toString());
        }
        return flag;
   }
    public UserBeans setStock(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setPrdname(rs.getString("pr_name"));
                e.setPrdtype(rs.getString("pr_type"));
                e.setQty(rs.getInt("qty"));
                e.setPrdamt(rs.getDouble("pr_amt"));
                e.setPrdcost(rs.getDouble("total_cost"));
                e.setPrddate(rs.getString("pr_date"));
                
              }
            catch(SQLException ex){
                db.setWarningMessage("Stock.setStock", ex.toString());
            }
            
        }
        return e;
    }
    
     private void refreshDatabase(){
        try{
            rs = stmt.executeQuery("select * from stock_mstr");
            
        }
        catch(SQLException e){
            System.out.println(e.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Exception Raised in method StockModel.refreshDatabase");
            alert.setContentText(e.toString());
            alert.show();
        }
    }
     public ObservableList<UserBeans> showTableRecords(){
        ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from stock_mstr");
                while(rs.next()){
                    list.add(setStock(rs));
                }
               updateRecord();                
            }
            catch(SQLException ex){
                db.setWarningMessage("StockModel.showTableRecords", ex.toString());
            }
        return list;
    }
     
       
    public UserBeans updateRecord(){
        UserBeans e = null;
           try{
                rs = stmt.executeQuery("select * from stock_mstr");
                rs.next();
                 e = setStock(rs);
             }
             catch(SQLException ex){
                 db.setWarningMessage("StockModel.updateRecord", ex.toString());
             } 
           return e;
    }   
    public boolean updateStock(UserBeans e){
     boolean flag = true;
     try{
         String sql = "update stock_mstr set pr_type=?,qty=?,pr_amt=?,total_cost=?,pr_date=? where pr_name=?";
         PreparedStatement p = conn.prepareStatement(sql);
         //p.setString(1, e.getPrdname());
         p.setString(1,e.getPrdtype());
         p.setInt(2,e.getQty());
         p.setDouble(3,e.getPrdamt());
         p.setDouble(4,e.getPrdcost());
         p.setString(5,e.getPrddate());
         p.setString(6,rs.getString("pr_name"));

        // ps.setString(4, e.getIname());
         
         flag = p.execute();
     }
     catch(SQLException ex){
        db.setWarningMessage("StockModel.updateItem", ex.toString());
    }
     return flag;
 }

    public UserBeans searchStock(String Prdname){
       UserBeans e=null;
     try{
         String sql="select * from stock_mstr where pr_name='"+Prdname+"'";
         rs=stmt.executeQuery(sql);
         if(rs.next()){
           e=setStock(rs);
         }
         else{
             db.setWarningMessage(" Stock Info does not Exist!","");  
         }
        
     }
     catch(SQLException ex){
         db.setWarningMessage("StockModel.searchLeaveId",ex.toString());
     }
     return e;
     
    }

}
