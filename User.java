/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.UserBeans;
import java.io.File;
import static java.lang.System.exit;
import loginpage.LoginPage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import loginpage.LoginPage.LoginBeans;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DELL
 */
public class User {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private DbConnection db;
    
    public User(){
      db=new DbConnection();
      conn=db.getConnection();
      stmt=db.getStatement();
    } 
    
    public boolean userLogin(UserBeans ub){
        boolean flag=false;
        
        try{
            String sql="select * from temp_users where name='"+ub.getName()+"' and password='"+ub.getPassword()+"'";
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                flag=true;
      }
        }catch(SQLException e){
            db.setWarningMessage("UserModel.userLogin",e.toString());
        }
        return flag;
    }
    
    public boolean adminlogin(UserBeans ub){
        boolean flag=false;
        try{
            String sql="select * from users where name='"+ub.getName()+"' and password='"+ub.getPassword()+"' and role='Admin'";
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                flag=true;
            }
        }catch(SQLException e){
            db.setWarningMessage("UserModel.adminLogin",e.toString());
        }

        return (flag);
    }
    
    
     public LoginBeans getLoginUser(){
         LoginBeans lb = null;
         try{
            String sql = "select * from temp_user";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
               // lb = new LoginBeans();
                lb.setId(rs.getLong("id"));
                lb.setName(rs.getString("name"));
            }
         }catch(SQLException e){
            db.setWarningMessage("UserModel.getLoginUser",e.toString());
        }
         return lb;
     }
    
    public ObservableList<UserBeans>addAllUser(){
        ObservableList<UserBeans> list=null;
        try{
            list=FXCollections.observableArrayList();
           String sql="select * from users";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
               list.add(getUserInfo(rs));
           }
           refreshDatabase();
        }
        catch(SQLException e){
            db.setWarningMessage("UserModel.addAllUser",e.toString());
        }
        
        return list;
    }
    


    public UserBeans getUserInfo(ResultSet rs){
        UserBeans u=null;
        if(rs!=null){
        try{
          u=new UserBeans();
          u.setUserId(rs.getInt("user_id"));
          u.setName(rs.getString("name"));
          u.setGender(rs.getString("gender"));
          u.setDob(rs.getDate("dob")+"");
          u.setDesig(rs.getString("desig"));
          u.setMobile(rs.getString("mobile"));
          u.setAddress(rs.getString("address"));
          u.setEmail(rs.getString("email"));
          u.setRole(rs.getString("role"));
          u.setPassword(rs.getString("password"));
          u.setActive(rs.getInt("active"));
          
        }
        catch(SQLException e){
            db.setWarningMessage("UserModel.getUserInfo",e.toString());
        }
    }
        return u;
    }
         
  
    
    public boolean createNewUser(UserBeans u){
        boolean flag = true;
        try{
            String sql = "insert into temp_users values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareCall(sql);
            p.setString(1, u.getName());
            p.setString(2, u.getGender());
            p.setString(3, u.getDob());
            p.setString(4, u.getDesig());
            p.setString(5, u.getMobile());
            p.setString(6, u.getEmail());
            p.setString(7, u.getAddress());
            p.setString(8, u.getRole());
            p.setString(9, u.getPassword());
            p.setInt(10, u.getActive());
            flag = p.execute();
        }
        catch(SQLException e){
            db.setWarningMessage("UserModel.createNewUser",e.toString());
        }
        return flag;
    }

   /* public Object getLoginUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    public boolean adminLogin(ActionEvent event)
    {
        boolean flg=true;
     exit(0);
     return flg;
    }

    public UserBeans searchUserInfo(long id) {
        UserBeans b = null;
        try{
            String sql = "select * from users where user_id="+id;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                b = getUserInfo(rs);
            }
        }
        catch(SQLException e){
            db.setWarningMessage("UserModel.searchUserInfo",e.toString());
        }
        return b;

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean updateUser(UserBeans u) {
        boolean flag=true;
           try{
               String sql="update users set name=?,gender=?,dob=?,desig=?,mobile=?,email=?,address=?,role=?,password=?,active=? where user_id=?";
               PreparedStatement p=conn.prepareStatement(sql);
   
               p.setString(1,u.getName());
               p.setString(2,u.getGender());
               p.setString(3,u.getDob());
               p.setString(4,u.getDesig());
               p.setString(5,u.getMobile());
               p.setString(6,u.getEmail());
               p.setString(7,u.getAddress());
               p.setString(8,u.getRole());
               p.setString(9,u.getPassword());
               p.setInt(10,u.getActive());
               p.setString(11,rs.getString("user_id"));
               flag=p.execute();
           }
           catch(SQLException ex){
              db.setWarningMessage("UserModel.UpdateUser",ex.toString());
            }
           return flag;
     
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void printRecord(UserBeans u) {
        try{
         /*   HashMap hp=new HashMap();
            hp.put("id",u.getUserId());
            hp.put("name",u.getName());
            hp.put("gender",u.getGender());
            hp.put("desig",u.getDesig());
            hp.put("mobile",u.getMobile());
            hp.put("role",u.getRole());
            JasperDesign jasperDesign=JRXmlLoader.load(new File("").getAbsolutePath()+"/src/hrms/jasperReport/UserReport.jrxml");
            JasperReport jasperReport=JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint;
            jasperPrint = JasperFillManager.fillReport(jasperReport,hp, conn);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); 
            jasperViewer.setVisible(true);*/
        }
         catch(Exception ex){
            db.setWarningMessage("UserModel.printRecord",ex.toString());
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean deleteAdminUser(String id) {
        boolean flag = true;
        try{
            rs = stmt.executeQuery("delete from users where user_id='"+id+"'");
            rs.next();
        }
        catch(SQLException ex){
            //db.setWarningMessage("LeaveModel.deleteLeaveInfo", ex.toString());
            flag = false;
        }
        refreshDatabase();
        return flag;
    }
        private void refreshDatabase(){
        try{
            rs = stmt.executeQuery("select * from users");
        }
        catch(SQLException e){
          db.setWarningMessage("UserModel.refreshDatabase",e.toString());
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean updateAdminUser(UserBeans u) {
        boolean flag=true;
           try{
               String sql="update users set name=?,gender=?,dob=?,qualification=?,mobile=?,email=?,address=?,role=?,active=?,password=? where user_id=?";
               PreparedStatement p=conn.prepareStatement(sql);
   
               p.setString(1,u.getName());
               p.setString(2,u.getGender());
               p.setString(3,u.getDob());
               p.setString(4,u.getDesig());
               p.setString(5,u.getMobile());
               p.setString(6,u.getEmail());
               p.setString(7,u.getAddress());
               p.setString(8,u.getRole());
               p.setInt(10,u.getActive());
               p.setString(9,u.getPassword());
               p.setString(11,rs.getString("user_id"));
               flag=p.execute();
           }
           catch(SQLException ex){
              db.setWarningMessage("UserModel.UpdateUser",ex.toString());
            }
           return flag;

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
