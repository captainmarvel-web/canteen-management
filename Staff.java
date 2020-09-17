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
import javafx.scene.control.Alert.AlertType;

public class Staff {
    private DbConnection db;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private int total, current;
    
    public Staff(){
        try{
            db = new DbConnection();
            conn = db.getConnection();
            stmt = db.getStatement();
            rs = stmt.executeQuery("select (*) from staff_table");
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            //Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
           // db.setWarningMessage("Staff", e.toString());

        }
    }
    public boolean insertaction(UserBeans e){
        boolean flag = true;
        try
        {
            String sql = "insert into staff_table values(?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, e.getEid());
            p.setString(2, e.getEname());
            p.setInt(3, e.getEage());
            p.setString(4, e.getEdesig());
            p.setInt(5, e.getEsal());
            p.setInt(6, e.getEmob());
            p.setString(7, e.getEemail());
            p.setString(8, e.getEadd());
            flag = p.execute();
            System.out.println("flag = "+flag);
        }catch(SQLException ex){
            db.setWarningMessage("Staff.insertaction", ex.toString());
        }
        return flag;
   }
    
    public UserBeans updateRecord(){
        UserBeans e = null;
        if(current>1){
           try{
                rs = stmt.executeQuery("select * from staff_table");
                rs.next();
                /*int cur = 0;
                do{
                  rs.next();
                  cur++;
                }while(cur<current);
                 current = cur;*/
                 e = setStaff(rs);
             }
             catch(SQLException ex){
                 db.setWarningMessage("Staff.updateRecord", ex.toString());
             } 
        }   
        return e;
    }
    
    public boolean updateStaffInfo(UserBeans e){
        boolean flag=true;
            try{
               String sql="update staff_table set emp_id=?,age=?,design=?,salr=?,mob=?,email=?,addre=? where emp_name=?";
              PreparedStatement p=conn.prepareStatement(sql);
           
            p.setInt(1, e.getEid());
            p.setInt(2, e.getEage());
            p.setString(3, e.getEdesig());
            p.setInt(4, e.getEsal());
            p.setInt(5, e.getEmob());
            p.setString(6, e.getEemail());
            p.setString(7, e.getEadd());
            p.setString(8,rs.getString("emp_name"));
              flag=p.execute();          
           }
            catch(SQLException ex){
                db.setWarningMessage("StaffModel.updateItem", ex.toString());
                /*Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Exception Raised in method EmployeeModel.updateEmployeeInfo");
                alert.setContentText(ex.toString());
                alert.show();*/
            }
           return flag;
       }

    public UserBeans setStaff(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setEid(rs.getInt("emp_id"));
                e.setEname(rs.getString("emp_name"));
                e.setEdesig(rs.getString("design"));
                e.setEage(rs.getInt("age"));
                e.setEsal(rs.getInt("salr"));
                e.setEmob(rs.getInt("mob"));
                e.setEadd(rs.getString("addre"));
                e.setEemail(rs.getString("email"));
                
              }
            catch(SQLException ex){
                db.setWarningMessage("Staff.setStaff", ex.toString());
            }
            
        }
        return e;
    }
    
    private void refreshDatabase(){
        try{
            rs = stmt.executeQuery("select * from staff_table");
            //int cur=1;
            //for(rs.next(); cur<current; cur++, rs.next());
        }
        catch(SQLException e){
            System.out.println(e.toString());
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Exception Raised in method EmployeeModel.refreshDatabase");
            alert.setContentText(e.toString());
            alert.show();
        }
    }
    
     public ObservableList<UserBeans> showTableRecords(){
        ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from staff_table");
                while(rs.next()){
                    list.add(setStaff(rs));
                }
               updateRecord();                
            }
            catch(SQLException ex){
                db.setWarningMessage("StaffModel.showTableRecords", ex.toString());
            }
        return list;
    }
     
    public UserBeans searchStaff(String Ename){
       UserBeans e=null;
        try{
         String sql="select * from staff_table where emp_name='"+Ename+"'";
         rs=stmt.executeQuery(sql);
         if(rs.next()){
           e=setStaff(rs);
         }
         else{
             db.setWarningMessage(" Staff Info does not Exist!","");  
            }
        
         }
        catch(SQLException ex){
         db.setWarningMessage("StaffModel.searchLeaveId",ex.toString());
       }
       return e;
     }
 
    public boolean deleteItem(String Ename){
        boolean flag = true;
        try{
            rs = stmt.executeQuery("delete from staff_table where emp_name='"+Ename+"'");
            rs.next();
        }
        catch(SQLException ex){
            db.setWarningMessage("StaffModel.deleteStaffInfo", ex.toString());
            //flag = false;
        }
        return flag;
    }
  

    

}

    

