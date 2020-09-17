/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author DELL
 */
public class UserBeans {
    
    private long userId;
    private String name,gender,dob,desig,mobile,email,address,role,password;
    private String ename,edesig,eemail,eadd;
    private int eid,esal,emob,eage,active;
    private String prdname,prdtype,prddate;
    private String iname,itype,idesc;
    private double iprice;
    private int bid,billqty;
    private double itemtotal,btotal,bprice;
    private String biname,bdate;
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getBillqty() {
        return billqty;
    }

    public void setBillqty(int billqty) {
        this.billqty = billqty;
    }

    public double getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(double itemtotal) {
        this.itemtotal = itemtotal;
    }

    public double getBtotal() {
        return btotal;
    }

    public void setBtotal(double btotal) {
        this.btotal = btotal;
    }

    public double getBprice() {
        return bprice;
    }

    public void setBprice(double bprice) {
        this.bprice = bprice;
    }

    public String getBiname() {
        return biname;
    }

    public void setBiname(String biname) {
        this.biname = biname;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }
   
     
    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getItype() {
        return itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
    }

    public String getIdesc() {
        return idesc;
    }

    public void setIdesc(String idesc) {
        this.idesc = idesc;
    }

    public double getIprice() {
        return iprice;
    }

    public void setIprice(double iprice) {
        this.iprice = iprice;
    }
    

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getPrdtype() {
        return prdtype;
    }

    public void setPrdtype(String prdtype) {
        this.prdtype = prdtype;
    }

    public String getPrddate() {
        return prddate;
    }

    public void setPrddate(String prddate) {
        this.prddate = prddate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrdamt() {
        return prdamt;
    }

    public void setPrdamt(double prdamt) {
        
        this.prdamt = prdamt;
    }

    public double getPrdcost() {
        return prdcost;
    }

    public void setPrdcost(double prdcost) {
        //prdcost=prdamt*qty;
        this.prdcost = prdcost;
    }
    private int qty;
    private double prdamt,prdcost;
    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEdesig() {
        return edesig;
    }

    public void setEdesig(String edesig) {
        this.edesig = edesig;
    }

    public String getEemail() {
        return eemail;
    }

    public void setEemail(String eemail) {
        this.eemail = eemail;
    }

    public String getEadd() {
        return eadd;
    }

    public void setEadd(String eadd) {
        this.eadd = eadd;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getEsal() {
        return esal;
    }

    public void setEsal(int esal) {
        this.esal = esal;
    }

    public int getEmob() {
        return emob;
    }

    public void setEmob(int emob) {
        this.emob = emob;
    }

    public int getEage() {
        return eage;
    }

    public void setEage(int eage) {
        this.eage = eage;
    }
    
     
    
    public UserBeans(){}
    public UserBeans(long userId,int active,String name,String gender, String dob, String desig, String mobile, String email, String address, String role, String password) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.desig = desig;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.role = role;
        this.password = password;
        this.active = active;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
   
}
