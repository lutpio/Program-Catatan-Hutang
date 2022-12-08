package wrt_catatan;

import java.sql.*;
import java.util.logging.*;


public class CRUD {
    public void add_customer(String nama){// buat penghutang nama
        try {
            String query = "INSERT INTO customer (name) VALUES (?)";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1, nama);            
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_customer(String nama,String hp){//buat penghutang nama,hp
        try {
            String query = "INSERT INTO customer (name, phone) VALUES (?,?)";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1, nama);
            pstmt.setString(2, hp);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_transaction(int cid,int duit,String catatan,String tgl){//buat penghutang nama,hp
        try {
            String query = "INSERT INTO transaction (id_customer, c_money, c_desc, c_date) VALUES (?,?,?,?)";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setInt(1, cid);
            pstmt.setInt(2, duit);
            pstmt.setString(3, catatan);
            pstmt.setString(4, tgl);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_transaction(int cid,int duit,String tgl){//buat penghutang nama,hp
        try {
            String query = "INSERT INTO transaction (id_customer, c_money, c_date) VALUES (?,?,?)";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setInt(1, cid);
            pstmt.setInt(2, duit);
            pstmt.setString(3, tgl);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_transaction(int cid,String stat, int duit,String tgl){//buat pelunasan
        try {
            String query = "INSERT INTO transaction (id_customer, c_money, c_status, c_date) VALUES (?,?,?,?)";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setInt(1, cid);
            pstmt.setInt(2, duit);
            pstmt.setString(3, stat);
            pstmt.setString(4, tgl);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void modify_stat(int cid,String status){//modif penghutang status
        try {
            String query = "UPDATE customer SET status = ? WHERE id_customer = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1, status);
            pstmt.setInt(2, cid);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modify_customer(int cid,String nama,String hp){//modif penghutang nama,hp
        try {
            String query = "UPDATE customer SET name = ?, SET phone = ? WHERE id_customer = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1, nama);
            pstmt.setString(2, hp);
            pstmt.setInt(3,cid);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void modify_customer(int cid,String nama){//modif penghutang nama,hp
        try {
            String query = "UPDATE customer SET name = ? WHERE id_customer = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1, nama);
            pstmt.setInt(2,cid);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    public int find_cid(String nama){
        int cid = 0;
        try {            
            String query = "SELECT id_customer FROM customer WHERE name = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nama); 
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                cid = (rs.getInt("id_customer"));
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cid;
    }
    public String find_phone(int id){
        String phone ="";
        try {            
            String query = "SELECT phone FROM customer WHERE id_customer = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id); 
            ResultSet rs = pstmt.executeQuery();
            
            if(rs != null){
                while(rs.next()){
                    phone = (rs.getString("phone"));
                    if (phone == null){
                        phone = "-";
                    }
                }
            }else{                
                phone = "-";
            }

        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phone;
    } 
    
    public int sisa(int id){
        int total = 0;
        try {
            int hutang = 0;
            int pelunasan = 0;
            
            String query_hutang = "select sum(c_money) from transaction where id_customer = ? and c_status = 'hutang'";
            String query_pelunasan = "select sum(c_money) from transaction where id_customer = ? and c_status = 'pelunasan'";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmth = conn.prepareStatement(query_hutang);
            PreparedStatement pstmtp = conn.prepareStatement(query_pelunasan);
            pstmth.setInt(1,id);
            pstmtp.setInt(1, id);
            ResultSet rsh = pstmth.executeQuery();
            ResultSet rsp = pstmtp.executeQuery();
            
            if(rsh != null){
                while(rsh.next()){
                    hutang = (rsh.getInt("sum(c_money)"));
                    if (hutang == 0){
                        hutang = 0;
                    }
                }
            }else{                
                hutang = 0;
            }
            
            if(rsp != null){
                while(rsp.next()){
                    pelunasan = (rsp.getInt("sum(c_money)"));
                    if (pelunasan == 0){
                        pelunasan = 0;
                    }
                }
            }else{                
                pelunasan = 0;
            }
            
            total = hutang - pelunasan;
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }
    
    public String cariuserpass(String nama, String password){
        String query = "SELECT * FROM user WHERE name = ? and password = ?";
        String has = "";
        try{
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);//cari ini user pass bisa!

            pstmt.setString(1, nama);//bisa 
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                has = "bisa";

            }else{
                has = "salah";
            }

        }
        catch(Exception e){

        }
        return has;
    }
   
    public void modify_activity(int cid){//jadi non aktif
        try {
            String query = "UPDATE customer SET activity = ? WHERE id_customer = ?";
            
            java.sql.Connection conn = (Connection)Confdb.configDB();
            PreparedStatement pstmt = conn.prepareStatement(query);   
            pstmt.setString(1,"non_aktif");
            pstmt.setInt(2,cid);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
