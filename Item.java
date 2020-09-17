/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.UserBeans;
import java.io.File;
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
import java.util.HashMap;
import javafx.scene.control.Alert.AlertType;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DELL
 */
public class Item {
     private DbConnection db;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private int total, current;
    
    public Item(){
    try 
    {
            db = new DbConnection();
            conn = db.getConnection();
            stmt = db.getStatement();
            rs = stmt.executeQuery("select (*) from item_mstr");

        } 
         catch (SQLException ex) {
            //Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    public boolean insertitem(UserBeans e){
        boolean flag = true;
        try
        {
            String sql = "insert into item_mstr values(?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, e.getIname());
            p.setDouble(2, e.getIprice());
            p.setString(3, e.getIdesc());
            p.setString(4, e.getItype());
            flag = p.execute();
            System.out.println("flag = "+flag);
        }catch(SQLException ex){
            db.setWarningMessage("Item.insertitem", ex.toString());
        }
        return flag;
   }
    public UserBeans setItem(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setIname(rs.getString("item_name"));
                e.setIprice(rs.getDouble("item_price"));
                e.setIdesc(rs.getString("item_descr"));                
                e.setItype(rs.getString("type"));
               
              }
            catch(SQLException ex){
                db.setWarningMessage("Item.setItem", ex.toString());
            }
        }
        return e;
    }
    
    private void refreshDatabase(){
        try{
            rs = stmt.executeQuery("select * from item_mstr");
        }
        catch(SQLException e){
            System.out.println(e.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Exception Raised in method Item.refreshDatabase");
            alert.setContentText(e.toString());
            alert.show();
        }
    } 
    
    public ObservableList<UserBeans> showTableRecords(){
        ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from item_mstr");
                while(rs.next()){
                    list.add(setItem(rs));
                }
               updateRecord();                
            }
            catch(SQLException ex){
                db.setWarningMessage("Itemmodel.showTableRecords", ex.toString());
            }
        return list;
    }
    
    public UserBeans updateRecord(){
        UserBeans e = null;
           try{
                rs = stmt.executeQuery("select * from item_mstr");
                rs.next();
                e = setItem(rs);
             }
             catch(SQLException ex){
                 db.setWarningMessage("ItemModel.updateRecord", ex.toString());
             } 
           return e;
    }   
    public boolean updateItem(UserBeans e){
     boolean flag = true;
     try{
         String sql = "update item_mstr set item_price=?,item_descr=?,type=? where item_name=?";
         PreparedStatement p = conn.prepareStatement(sql);
         p.setDouble(1,e.getIprice());
         p.setString(2,e.getIdesc());
         p.setString(3,e.getItype());
         p.setString(4,rs.getString("item_name"));

        // ps.setString(4, e.getIname());
         
         flag = p.execute();
     }
     catch(SQLException ex){
        db.setWarningMessage("ItemModel.updateItem", ex.toString());
    }
     return flag;
 }

    public boolean deleteItem(String iname){
        boolean flag = true;
        try{
            rs = stmt.executeQuery("delete from item_mstr where item_name='"+iname+"'");
            rs.next();
        }
        catch(SQLException ex){
            db.setWarningMessage("LeaveModel.deleteLeaveInfo", ex.toString());
            flag = false;
        }
        return flag;
    }
  

    public UserBeans searchItem(String Iname){
       UserBeans e=null;
     try{
         String sql="select * from item_mstr where item_name='"+Iname+"'";
         rs=stmt.executeQuery(sql);
         if(rs.next()){
           e=setItem(rs);
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
    
    public void printItemInfo(String sql){
        try{
          JasperDesign jasperDesign=JRXmlLoader.load(new File("").getAbsolutePath()+"/src/LoginPage/report/itemreport.jrxml");
          JRDesignQuery jrQuery=new JRDesignQuery();
          jrQuery.setText(sql);
          jasperDesign.setQuery(jrQuery);         
          JasperReport jasperReport=JasperCompileManager.compileReport(jasperDesign);
          JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,null, conn);
          JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
          
          jasperViewer.setVisible(true);
          
        }
        catch(Exception ex){
            db.setWarningMessage("ItemModel.printEmpInfo",ex.toString());
        }
    }
    
    public  void printRecord(UserBeans e){
        try{
            HashMap hp=new HashMap();
            hp.put("iname",e.getIname());
            hp.put("iprice",e.getIprice());
            hp.put("idesc",e.getIdesc());
            hp.put("itype",e.getItype());
            JasperDesign jasperDesign=JRXmlLoader.load(new File("").getAbsolutePath()+"src/LoginPage/report/itemreport.jrxml");
            JasperReport jasperReport=JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,hp, conn);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); 
            jasperViewer.setVisible(true);
        }
         catch(Exception ex){
            db.setWarningMessage("ItemModel.printRecord",ex.toString());
        }
        
    }
}
