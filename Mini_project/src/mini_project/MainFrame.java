/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mini_project;

 
import com.mysql.cj.jdbc.Blob;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;


 //* @author ACER
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String path2;
    
    String uname;
    
    //Login_page lp = new Login_page();
 

    public MainFrame() {
        initComponents();
        date(); //method call for date
        Time(); //method call for time
        
        
        conn = MyConnection.getConnection();
        notice();
        attendance();
        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 20, 20));
    }
    
    public MainFrame(String name, String uname){
        initComponents();
        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 20, 20));
        date(); //method call for date
        Time(); //method call for time
        conn = MyConnection.getConnection();
        notice();
        this.uname = uname;
        setStuDetails(uname);
        uname = name;
        name_label.setText("Welcome "+ name);
        setProfilePicture(); //method call for set dashboard profile 
        attendance();  //method call for attendance
        finalMarks(); //method call for final marks table
        getSGPA(); //method call for get SGPA 
        medical();
        getcourseDetails();
        loadComboBox();
    }
    
    public MainFrame(String name){
        initComponents();
        date(); //method call for date
        Time(); //method call for time
        conn = MyConnection.getConnection();
        notice();
        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 20, 20));
        uname = name;
        setStuDetails(name);
        name_label.setText("Welcome "+ " Mr. " + " " + name);
        loadComboBox();
    }
    
    //my profile data set
    public void setStuDetails(String username){
        conn = MyConnection.getConnection();
        String regNo = username;
        String sqlquery = "SELECT * FROM student WHERE reg_no =? ";
        try{
            ps = conn.prepareStatement(sqlquery);
            ps.setString(1, regNo);
            rs = ps.executeQuery();
            if(rs.next()){
                String regNum = rs.getString(1);
                String Fname = rs.getString(2);
                String Lname = rs.getString(3);
                String Address = rs.getString(4);
                String DOB = rs.getString(5);
                String Email = rs.getString(6);
                String Sex = rs.getString(7);
                String Pno = rs.getString(8);
                String Dep = rs.getString(9);
                
                 // Retrieve the profile picture data
            InputStream is = rs.getBinaryStream("profile_picture");

            // Convert the InputStream to BufferedImage
            BufferedImage bi = ImageIO.read(is);

            // Close the InputStream
            is.close();

            // Create an ImageIcon from the BufferedImage
            ImageIcon icon = new ImageIcon(bi);

            // Set the ImageIcon as the icon for the JPanel
            profilePic.setImage(icon);
                
                name1.setText(Fname+ " " + Lname );
                regno.setText(regNum);
                dep1.setText(Dep);
                email1.setText(Email);
                phoneno.setText(Pno);
                address.setText(Address);
                bdate.setText(DOB);
                sex1.setText(Sex);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    //set Date
    public void date(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        
        String date = sdf.format(d);
        Date_label.setText("Today is " + date);
    }
    
    //Set Time
    Timer t;
    SimpleDateFormat st;
        
    public void Time(){
        
        
        t = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                
                Date dt = new Date();
                st =new SimpleDateFormat("hh:mm:ss a");
                
                String time = st.format(dt);
                Time_label.setText(time);
            }
        });
        t.start();
    }
    
    //method for notice table
    public void notice(){
        setBackground(new Color(0,0,0,0));
        notice_table.getTableHeader().setFont(new Font("segon UI",Font.BOLD, 13));
        //notice_table.getTableHeader().setOpaque(false);
        notice_table.getTableHeader().setBackground(new Color(0, 0 , 0));
        notice_table.getTableHeader().setForeground(Color.white);
        
        try{
            String sql = "SELECT * FROM notice";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String id = String.valueOf(rs.getInt("no"));
                String date = rs.getString("date");
                String time = rs.getString("time");
                String title = rs.getString("title");
                
                String tbData[] = {id, date, time, title};
                DefaultTableModel tblm = (DefaultTableModel) notice_table.getModel();
                
                tblm.addRow(tbData);
            } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //method for attendance table
    public void attendance(){
       
        try{
            String sql = "SELECT * FROM attendance WHERE reg_no =? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,uname);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String no = String.valueOf(rs.getInt(1));
                String cCode = rs.getString(3);
                String cType = rs.getString(8);
                String date = rs.getString(5);
                String hour = String.valueOf(rs.getInt(4));
                String status = rs.getString(6);
                
                String tbData[] = {no, cCode, cType, date, hour, status};
                DefaultTableModel tblm = (DefaultTableModel) attendance_table.getModel();
                
                tblm.addRow(tbData);
            } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //method for final marks table
     public void finalMarks(){
        try{
            String sql = "SELECT * FROM studentGrades WHERE reg_no =?" ;
                     
            ps = conn.prepareStatement(sql);
            ps.setString(1, uname);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String courseCode = rs.getString("course_code");
              //  Double marks = rs.getDouble("final_result");
                String grade = rs.getString("student_grade");
                String tbData[] = {courseCode, grade};
                DefaultTableModel tblm = (DefaultTableModel) final_result_table.getModel();
                
                tblm.addRow(tbData);
            } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void getSGPA(){
        try{
            String sql = "SELECT * FROM sgpa WHERE reg_no = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1,uname);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String sgpavalue = String.valueOf(rs.getDouble(2));
                sgpa_label.setText(sgpavalue);
                cgpa_label.setText(sgpavalue);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //method for get medical details
      public void medical(){
        try{
            String sql = "SELECT * FROM medical_details WHERE reg_no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,uname);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String id = String.valueOf(rs.getInt("id"));
                String coursecode = rs.getString("course_code");
                String date = rs.getString("date");
                String status = rs.getString("status");
          
                
                String tbData[] = {id, coursecode, date, status};
                DefaultTableModel tblm = (DefaultTableModel) medical_table.getModel();
                
                tblm.addRow(tbData);
            } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void getcourseDetails(){
       
        try{
            String sql = "SELECT * FROM course_unit ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String cCode = rs.getString(1);
                String cname = rs.getString(2);
                String type = rs.getString(3);
                String credits = String.valueOf(rs.getInt(4));
                String hours = String.valueOf(rs.getInt(6));
                
                String tbData[] = {cCode, cname, type, credits, hours};
                DefaultTableModel tblm = (DefaultTableModel) course_table.getModel();
                
                tblm.addRow(tbData);
            } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void loadComboBox(){
        try {
           
            String mystatement = "SELECT material_name FROM course_materials";
            ps = conn.prepareStatement(mystatement);
            rs = ps.executeQuery();

            while (rs.next()) {
                materialCombo_box.addItem(rs.getString(1));
            }
    } catch (SQLException e) {
            System.out.println("Error with loading data!" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

    }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    //mehods for menu panl click color
    private void onclick(JPanel panel){
        panel.setBackground(new Color(0,51,255));
    }
    
    private void onLeaveclick(JPanel panel){
        panel.setBackground(new Color(255,0,0));
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        Center_panel = new javax.swing.JPanel();
        Tab1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        name_label = new javax.swing.JLabel();
        Date_label = new javax.swing.JLabel();
        Time_label = new javax.swing.JLabel();
        dboard_profile_pic = new mini_project.ImageAvatar();
        Tab2 = new javax.swing.JPanel();
        page_title = new javax.swing.JLabel();
        reg_no = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        dep_name = new javax.swing.JLabel();
        sex = new javax.swing.JLabel();
        p_no_label = new javax.swing.JLabel();
        add = new javax.swing.JLabel();
        dob = new javax.swing.JLabel();
        regno = new javax.swing.JLabel();
        name1 = new javax.swing.JLabel();
        email1 = new javax.swing.JLabel();
        phoneno = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        bdate = new javax.swing.JLabel();
        sex1 = new javax.swing.JLabel();
        dep1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        profilePic = new mini_project.ImageAvatar();
        name2 = new javax.swing.JLabel();
        profile_edit_button = new javax.swing.JLabel();
        Tab3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notice_table = new javax.swing.JTable();
        Tab4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        course_table = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        cCode_txt = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        materialCombo_box = new javax.swing.JComboBox<>();
        view_btn = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        Tab5 = new javax.swing.JPanel();
        timeTable_label = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        Tab6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        medical_table = new javax.swing.JTable();
        timeTable_label1 = new javax.swing.JLabel();
        Tab7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendance_table = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        search_text = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        Tab8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        final_result_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        sgpa_label = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        cgpa_label = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        Tab9 = new javax.swing.JPanel();
        preferences_text = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        p_no = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        chooseProfile_pic = new mini_project.ImageAvatar();
        set_profilePic_btn = new javax.swing.JButton();
        data_save_button = new javax.swing.JLabel();
        edit_email_label = new javax.swing.JLabel();
        edit_email = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        Menu_panel = new javax.swing.JPanel();
        LogOut_panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn1 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        btn2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btn3 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        btn4 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btn5 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btn6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btn7 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        btn8 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        Header_panel = new javax.swing.JPanel();
        TECMIS_Label = new javax.swing.JLabel();
        maximize_btn = new javax.swing.JLabel();
        minimize_btn = new javax.swing.JLabel();
        close_btn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));

        Center_panel.setLayout(new java.awt.CardLayout());

        Tab1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Courses");

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("View Details");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Attendance");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attendance (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel4))
                .addGap(33, 33, 33))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 102, 0));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Class Time Table");

        jSeparator4.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("View Details");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 0, 0));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Notice Board");

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("View Details");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 153, 0));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Medical");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/medical_sub1 (1).jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel13))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(153, 153, 255));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Result Managemant");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/result_manage.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel16))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(133, 149, 203));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setText("Dashboard /");

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Log Out");
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        name_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_label.setText("Welcome, Name");

        Date_label.setBackground(new java.awt.Color(204, 204, 204));
        Date_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Date_label.setForeground(new java.awt.Color(255, 255, 255));
        Date_label.setText("Date");

        Time_label.setBackground(new java.awt.Color(102, 102, 102));
        Time_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Time_label.setForeground(new java.awt.Color(255, 255, 255));
        Time_label.setText("Time");

        dboard_profile_pic.setBorderColor(new java.awt.Color(255, 255, 255));
        dboard_profile_pic.setBorderSize(0);
        dboard_profile_pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/images/default_profile_icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dboard_profile_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jLabel32))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Date_label, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Time_label, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dboard_profile_pic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Date_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Time_label)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Tab1Layout = new javax.swing.GroupLayout(Tab1);
        Tab1.setLayout(Tab1Layout);
        Tab1Layout.setHorizontalGroup(
            Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(Tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Tab1Layout.setVerticalGroup(
            Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        Center_panel.add(Tab1, "card2");

        Tab2.setBackground(new java.awt.Color(255, 255, 255));

        page_title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        page_title.setText("Your Details");

        reg_no.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        reg_no.setText("Registration no  ");

        name.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        name.setText("Name ");

        email.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        email.setText("Email ");

        dep_name.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        dep_name.setText("Department name ");

        sex.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        sex.setText("Sex ");

        p_no_label.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        p_no_label.setText("Phone no ");

        add.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        add.setText("Address ");

        dob.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        dob.setText("DOB ");

        regno.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        regno.setText("Your reg no");

        name1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        name1.setText("Your name");

        email1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        email1.setText("Your email");

        phoneno.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        phoneno.setText("Your phone no");

        address.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        address.setText("Your address");

        bdate.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        bdate.setText("Your DOB");

        sex1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        sex1.setText("Your sex");

        dep1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dep1.setText("Your department");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setText(":");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel17.setText(":");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel18.setText(":");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setText(":");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setText(":");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel21.setText(":");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel22.setText(":");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel25.setText(":");

        profilePic.setBorderColor(new java.awt.Color(0, 51, 255));
        profilePic.setImage(new javax.swing.ImageIcon(getClass().getResource("/images/profile_icon2.png"))); // NOI18N

        name2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        name2.setText("Profile picture");

        profile_edit_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button (2).png"))); // NOI18N
        profile_edit_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profile_edit_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profile_edit_buttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Tab2Layout = new javax.swing.GroupLayout(Tab2);
        Tab2.setLayout(Tab2Layout);
        Tab2Layout.setHorizontalGroup(
            Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page_title)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Tab2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(p_no_label, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(sex, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(reg_no, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(Tab2Layout.createSequentialGroup()
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(69, 69, 69)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(2, 2, 2))
                        .addGroup(Tab2Layout.createSequentialGroup()
                            .addComponent(dep_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name1)
                            .addComponent(bdate)
                            .addComponent(address)
                            .addComponent(phoneno)
                            .addComponent(dep1)
                            .addComponent(email1)
                            .addComponent(sex1)
                            .addComponent(regno, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab2Layout.createSequentialGroup()
                                .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab2Layout.createSequentialGroup()
                                .addComponent(profilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))))
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addComponent(profile_edit_button)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        Tab2Layout.setVerticalGroup(
            Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(page_title)
                .addGap(18, 18, 18)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(name1)
                        .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab2Layout.createSequentialGroup()
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reg_no, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(regno)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dep_name, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dep1)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email1)
                            .addComponent(jLabel19))
                        .addGap(7, 7, 7)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p_no_label, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneno)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(address)
                            .addComponent(jLabel21)))
                    .addComponent(profilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bdate)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sex, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sex1)
                    .addComponent(jLabel25))
                .addGap(28, 28, 28)
                .addComponent(profile_edit_button)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        Center_panel.add(Tab2, "card3");

        Tab3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Notices");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        notice_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        notice_table.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        notice_table.setForeground(new java.awt.Color(153, 153, 153));
        notice_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Date", "Time", "Title"
            }
        ));
        notice_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        notice_table.setRowHeight(30);
        notice_table.setSelectionBackground(new java.awt.Color(51, 0, 153));
        notice_table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(notice_table);
        if (notice_table.getColumnModel().getColumnCount() > 0) {
            notice_table.getColumnModel().getColumn(0).setMinWidth(90);
            notice_table.getColumnModel().getColumn(0).setMaxWidth(100);
            notice_table.getColumnModel().getColumn(1).setMinWidth(90);
            notice_table.getColumnModel().getColumn(1).setMaxWidth(100);
            notice_table.getColumnModel().getColumn(2).setMinWidth(90);
            notice_table.getColumnModel().getColumn(2).setMaxWidth(100);
            notice_table.getColumnModel().getColumn(3).setMinWidth(600);
            notice_table.getColumnModel().getColumn(3).setMaxWidth(650);
        }

        javax.swing.GroupLayout Tab3Layout = new javax.swing.GroupLayout(Tab3);
        Tab3.setLayout(Tab3Layout);
        Tab3Layout.setHorizontalGroup(
            Tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(Tab3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Tab3Layout.setVerticalGroup(
            Tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        Center_panel.add(Tab3, "card4");

        Tab4.setBackground(new java.awt.Color(255, 255, 255));

        course_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course code", "Course name", "Type", "Credits", "Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(course_table);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Courses");
        jLabel48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel49.setBackground(new java.awt.Color(0, 0, 204));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 204));
        jLabel49.setText("Enter course code");

        cCode_txt.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 204));
        jLabel50.setText("Choose material name");

        materialCombo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialCombo_boxActionPerformed(evt);
            }
        });

        view_btn.setBackground(new java.awt.Color(0, 0, 204));
        view_btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        view_btn.setForeground(new java.awt.Color(255, 255, 255));
        view_btn.setText("View");
        view_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_btnMouseClicked(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 204));
        jLabel52.setText("Search Materials");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(47, 47, 47)
                        .addComponent(cCode_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(view_btn)
                            .addComponent(materialCombo_box, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52)
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(cCode_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(materialCombo_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(view_btn)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Tab4Layout = new javax.swing.GroupLayout(Tab4);
        Tab4.setLayout(Tab4Layout);
        Tab4Layout.setHorizontalGroup(
            Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(Tab4Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Tab4Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Tab4Layout.setVerticalGroup(
            Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );

        Center_panel.add(Tab4, "card5");

        Tab5.setBackground(new java.awt.Color(255, 255, 255));

        timeTable_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        timeTable_label.setText("Time Table");
        timeTable_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 204));
        jLabel53.setText("Enter name");

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComboBox1MouseEntered(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("View");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(301, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout Tab5Layout = new javax.swing.GroupLayout(Tab5);
        Tab5.setLayout(Tab5Layout);
        Tab5Layout.setHorizontalGroup(
            Tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab5Layout.createSequentialGroup()
                        .addComponent(timeTable_label, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Tab5Layout.setVerticalGroup(
            Tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeTable_label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        Center_panel.add(Tab5, "card6");

        Tab6.setBackground(new java.awt.Color(255, 255, 255));

        medical_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Course code", "Date", "Status"
            }
        ));
        jScrollPane5.setViewportView(medical_table);

        timeTable_label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        timeTable_label1.setText("Medical Details");
        timeTable_label1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout Tab6Layout = new javax.swing.GroupLayout(Tab6);
        Tab6.setLayout(Tab6Layout);
        Tab6Layout.setHorizontalGroup(
            Tab6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(Tab6Layout.createSequentialGroup()
                        .addComponent(timeTable_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Tab6Layout.setVerticalGroup(
            Tab6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeTable_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        Center_panel.add(Tab6, "card7");

        Tab7.setBackground(new java.awt.Color(255, 255, 255));

        attendance_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Corse code", "Type", "Date", "Hours", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(attendance_table);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Attendance Details");
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        search_text.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        search_text.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        search_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textActionPerformed(evt);
            }
        });
        search_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_textKeyReleased(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 51, 204));
        jLabel51.setText("Enter course code");

        javax.swing.GroupLayout Tab7Layout = new javax.swing.GroupLayout(Tab7);
        Tab7.setLayout(Tab7Layout);
        Tab7Layout.setHorizontalGroup(
            Tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab7Layout.createSequentialGroup()
                .addGroup(Tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
                    .addGroup(Tab7Layout.createSequentialGroup()
                        .addGroup(Tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Tab7Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search_text, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Tab7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Tab7Layout.setVerticalGroup(
            Tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(Tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(search_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        Center_panel.add(Tab7, "card8");

        Tab8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("Result section");

        final_result_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course code", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(final_result_table);

        jPanel2.setBackground(new java.awt.Color(0, 255, 51));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Your SGPA");

        sgpa_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sgpa_label.setForeground(new java.awt.Color(255, 255, 255));
        sgpa_label.setText(" sgpa");

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/final_pic.png"))); // NOI18N
        jLabel30.setText("jLabel30");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sgpa_label, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel29)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(sgpa_label))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setPreferredSize(new java.awt.Dimension(231, 156));

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Your CGPA");

        cgpa_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cgpa_label.setForeground(new java.awt.Color(255, 255, 255));
        cgpa_label.setText("cgpa");

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cgpa_icon.png"))); // NOI18N
        jLabel47.setText("jLabel30");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cgpa_label, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel46)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cgpa_label))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Tab8Layout = new javax.swing.GroupLayout(Tab8);
        Tab8.setLayout(Tab8Layout);
        Tab8Layout.setHorizontalGroup(
            Tab8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addGroup(Tab8Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        Tab8Layout.setVerticalGroup(
            Tab8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Tab8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        Center_panel.add(Tab8, "card9");

        Tab9.setBackground(new java.awt.Color(255, 255, 255));

        preferences_text.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        preferences_text.setText("Edit your preferences");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel27.setText("Enter your new contact number");

        p_no.setBorder(null);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        chooseProfile_pic.setBorderColor(new java.awt.Color(255, 255, 255));
        chooseProfile_pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/images/profile_icon2.png"))); // NOI18N

        set_profilePic_btn.setBackground(new java.awt.Color(0, 0, 102));
        set_profilePic_btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        set_profilePic_btn.setForeground(new java.awt.Color(255, 255, 255));
        set_profilePic_btn.setText("Choose picture");
        set_profilePic_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_profilePic_btnActionPerformed(evt);
            }
        });

        data_save_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_button_data.png"))); // NOI18N
        data_save_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_save_buttonMouseClicked(evt);
            }
        });

        edit_email_label.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        edit_email_label.setText("Enter your new email");

        edit_email.setBorder(null);

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout Tab9Layout = new javax.swing.GroupLayout(Tab9);
        Tab9.setLayout(Tab9Layout);
        Tab9Layout.setHorizontalGroup(
            Tab9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab9Layout.createSequentialGroup()
                .addGroup(Tab9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(preferences_text))
                    .addGroup(Tab9Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(Tab9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(p_no, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edit_email_label)
                            .addComponent(edit_email, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooseProfile_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(set_profilePic_btn)))
                    .addGroup(Tab9Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(data_save_button)))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        Tab9Layout.setVerticalGroup(
            Tab9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(preferences_text)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p_no, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(edit_email_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edit_email, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chooseProfile_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(set_profilePic_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(data_save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Center_panel.add(Tab9, "card10");

        Menu_panel.setBackground(new java.awt.Color(0, 0, 0));
        Menu_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut_panel.setBackground(new java.awt.Color(0, 0, 0));
        LogOut_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut_panel.setMinimumSize(new java.awt.Dimension(163, 41));
        LogOut_panel.setPreferredSize(new java.awt.Dimension(169, 40));
        LogOut_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut_panelMouseClicked(evt);
            }
        });
        LogOut_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_icon.png"))); // NOI18N
        jLabel3.setText("Log Out");
        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        LogOut_panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 0, -1, -1));

        Menu_panel.add(LogOut_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 190, -1));

        btn1.setBackground(new java.awt.Color(0, 0, 0));
        btn1.setMinimumSize(new java.awt.Dimension(163, 41));
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn1MouseEntered(evt);
            }
        });
        btn1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Dashboard");
        btn1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dashboard.png"))); // NOI18N
        jLabel43.setText("jLabel10");
        btn1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, -1));

        btn2.setBackground(new java.awt.Color(0, 0, 0));
        btn2.setMinimumSize(new java.awt.Dimension(163, 41));
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn2MouseEntered(evt);
            }
        });
        btn2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("My Profile");
        btn2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/myProfile_icon.png"))); // NOI18N
        jLabel33.setText("jLabel10");
        btn2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 190, -1));

        btn3.setBackground(new java.awt.Color(0, 0, 0));
        btn3.setMinimumSize(new java.awt.Dimension(163, 41));
        btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn3MouseEntered(evt);
            }
        });
        btn3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Notice");
        btn3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notice_icon.png"))); // NOI18N
        jLabel41.setText("jLabel10");
        btn3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 190, -1));

        btn4.setBackground(new java.awt.Color(0, 0, 0));
        btn4.setMinimumSize(new java.awt.Dimension(163, 41));
        btn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn4MouseEntered(evt);
            }
        });
        btn4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Courses");
        btn4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/course_icon.png"))); // NOI18N
        jLabel37.setText("jLabel10");
        btn4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 190, -1));

        btn5.setBackground(new java.awt.Color(0, 0, 0));
        btn5.setMinimumSize(new java.awt.Dimension(163, 41));
        btn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn5MouseEntered(evt);
            }
        });
        btn5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Time Table");
        btn5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/timetable_icon.png"))); // NOI18N
        jLabel39.setText("jLabel10");
        btn5.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 190, -1));

        btn6.setBackground(new java.awt.Color(0, 0, 0));
        btn6.setMinimumSize(new java.awt.Dimension(163, 41));
        btn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn6MouseEntered(evt);
            }
        });
        btn6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Medical");
        btn6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/medical_icon.png"))); // NOI18N
        jLabel24.setText("jLabel10");
        btn6.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 190, -1));

        btn7.setBackground(new java.awt.Color(0, 0, 0));
        btn7.setMinimumSize(new java.awt.Dimension(163, 41));
        btn7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn7MouseEntered(evt);
            }
        });
        btn7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Attendance");
        btn7.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attendance_icon.png"))); // NOI18N
        jLabel45.setText("jLabel10");
        btn7.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 190, -1));

        btn8.setBackground(new java.awt.Color(0, 0, 0));
        btn8.setMinimumSize(new java.awt.Dimension(163, 41));
        btn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn8MouseEntered(evt);
            }
        });
        btn8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Results");
        btn8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_icon.png"))); // NOI18N
        jLabel35.setText("jLabel10");
        btn8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, 28, 28));

        Menu_panel.add(btn8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 190, -1));

        Header_panel.setBackground(new java.awt.Color(51, 51, 51));
        Header_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TECMIS_Label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TECMIS_Label.setForeground(new java.awt.Color(0, 51, 255));
        TECMIS_Label.setText("TECMIS");
        Header_panel.add(TECMIS_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 7, -1, -1));

        maximize_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/maximize_icon.png"))); // NOI18N
        maximize_btn.setText(" ");
        maximize_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Header_panel.add(maximize_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(679, 6, -1, -1));

        minimize_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize_icon.png"))); // NOI18N
        minimize_btn.setText(" ");
        minimize_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minimize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize_btnMouseClicked(evt);
            }
        });
        Header_panel.add(minimize_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(706, 6, -1, -1));

        close_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close_icon.png"))); // NOI18N
        close_btn.setText(" ");
        close_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        close_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_btnMouseClicked(evt);
            }
        });
        Header_panel.add(close_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 6, -1, -1));

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(Header_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(Menu_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Center_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(Header_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Center_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Menu_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void close_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_close_btnMouseClicked

    private void minimize_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimize_btnMouseClicked
        // TODO add your handling code here:
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimize_btnMouseClicked

    private void btn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseClicked
        // TODO add your handling code here:
        onclick(btn6);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn1);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(true);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
         Tab9.setVisible(false);
    }//GEN-LAST:event_btn6MouseClicked

    private void btn6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn6MouseEntered

    private void btn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseClicked
        // TODO add your handling code here:
        onclick(btn2);
        onLeaveclick(btn1);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        //Switching panels
        
        Tab1.setVisible(false);
        Tab2.setVisible(true);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn2MouseClicked

    private void btn2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn2MouseEntered

    private void btn8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseClicked
        // TODO add your handling code here:
        onclick(btn8);
        onLeaveclick(btn1);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn2);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(true);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn8MouseClicked

    private void btn8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn8MouseEntered

    private void btn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseClicked
        // TODO add your handling code here:
        onclick(btn4);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn1);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(true); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn4MouseClicked

    private void btn4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn4MouseEntered

    private void btn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseClicked
        // TODO add your handling code here:
        onclick(btn5);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn1);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(true);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn5MouseClicked

    private void btn5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn5MouseEntered

    private void btn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseClicked
        // TODO add your handling code here:
        onclick(btn3);
        onLeaveclick(btn1);
        onLeaveclick(btn2);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(true);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn3MouseClicked

    private void btn3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn3MouseEntered

    private void btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseClicked
        // TODO add your handling code here:
         onclick(btn1);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn7);
        onLeaveclick(btn8);
        
        Tab1.setVisible(true);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_btn1MouseClicked

    private void btn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn1MouseEntered

    private void btn7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseClicked
        // TODO add your handling code here:
        onclick(btn7);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        onLeaveclick(btn6);
        onLeaveclick(btn1);
        onLeaveclick(btn8);
        
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(true);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
       
        attendance();
        
    }//GEN-LAST:event_btn7MouseClicked

    private void btn7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn7MouseEntered

    private void LogOut_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut_panelMouseClicked
        // TODO add your handling code here:
        Login_page lp = new Login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOut_panelMouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
        Login_page lp = new Login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel32MouseClicked

    private void profile_edit_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile_edit_buttonMouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(true);
        
        JOptionPane.showMessageDialog(null, "You can only edit your profile picture and contact details.");
        
    }//GEN-LAST:event_profile_edit_buttonMouseClicked

    //set profle picture when user click choose button
    private void set_profilePic_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set_profilePic_btnActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String path = f.getAbsolutePath();
        try{
            BufferedImage bi = ImageIO.read(new File(path));
          //  Image img = bi.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(bi);
            chooseProfile_pic.setImage(icon);
            chooseProfile_pic.repaint();
            path2 = path;
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_set_profilePic_btnActionPerformed

    private void data_save_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_save_buttonMouseClicked
        // TODO add your handling code here:
        String uname1 = uname;
        
        conn = MyConnection.getConnection();
        String pno = p_no.getText();
        String email_edit = edit_email.getText();
        
        try{
            InputStream is = new FileInputStream(new File(path2));
           
            String sql = "UPDATE student SET phone_no = ?, profile_picture = ?, email = ? WHERE reg_no = ?";

        try{
            ps = conn.prepareStatement(sql);
        // Set the parameters
            ps.setString(1, pno); // Set phone number
            ps.setBinaryStream(2, is); // Set profile picture
            ps.setString(3, email_edit); // Set email
            ps.setString(4, uname1); // Set registration number
            ps.executeUpdate();
            
            setProfilePicture();
            
            p_no.setText("");
            edit_email.setText("");
            chooseProfile_pic.setImage(null);
            
            JOptionPane.showMessageDialog(null, "Data saved successfully");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }catch(FileNotFoundException ex){
                ex.printStackTrace();
            }
    }//GEN-LAST:event_data_save_buttonMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(true); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(true);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(true);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(true);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(true);
        Tab7.setVisible(false);
        Tab8.setVisible(false);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false); 
        Tab5.setVisible(false);
        Tab6.setVisible(false);
        Tab7.setVisible(false);
        Tab8.setVisible(true);
        Tab9.setVisible(false);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void search_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textActionPerformed
        // TODO add your handling code here:
       /* DefaultTableModel obj = (DefaultTableModel)attendance_table.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        attendance_table.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(search_text.getText()));*/
    }//GEN-LAST:event_search_textActionPerformed

    private void search_textKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textKeyReleased
        // TODO add your handling code here:
        try {
    String coursecode = search_text.getText();
    String mystatement = "SELECT * FROM attendance WHERE course_code = ? AND reg_no=?";
    PreparedStatement ps = conn.prepareStatement(mystatement);
    ps.setString(1, coursecode);
    ps.setString(2, uname);
    DefaultTableModel dt = (DefaultTableModel) attendance_table.getModel();
    dt.setRowCount(0);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Vector<Object> v2 = new Vector<>();
        v2.add(rs.getString(1));
        v2.add(rs.getString(3));
        v2.add(rs.getString(8));
        v2.add(rs.getString(5));
        v2.add(rs.getString(4));
        v2.add(rs.getString(6));
        dt.addRow(v2);
    }
} catch (SQLException e) {
    System.out.println("Error in loading data! " + e.getMessage());
} catch (Exception e) {
    System.out.println("Error " + e.getMessage());
}

        
    }//GEN-LAST:event_search_textKeyReleased

    private void materialCombo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialCombo_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_materialCombo_boxActionPerformed

    private void view_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_btnMouseClicked
        // TODO add your handling code here:
        try{              
             
            String course_code = cCode_txt.getText();
            String mat_name = materialCombo_box.getSelectedItem().toString();

            ps = conn.prepareStatement("SELECT material FROM course_materials  WHERE material_id = ?");
            ps.setInt(1, 3);
            //ps.setString(2, mat_name);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blob blob = (Blob) rs.getBlob("material");

                // Read blob data into a byte array
                byte[] data = blob.getBytes(1, (int) blob.length());

                // Display blob data as a PDF
                displayPDF(data);

            } 
         }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }                            

    
    }//GEN-LAST:event_view_btnMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseEntered
             try {
            String mystatement = "SELECT name FROM time_tables";
            ps = conn.prepareStatement(mystatement);
            rs = ps.executeQuery();

            while (rs.next()) {
                jComboBox1.addItem(rs.getString(1));
            }
           
            
            
    } catch (SQLException e) {
            System.out.println("Error with loading data!" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }       
    }  
    private void displayPDF(byte[] data) {
        try {
            // Create a temporary file to store the PDF data
            File tempFile = File.createTempFile("temp", ".pdf");
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(data);
            fos.close();

            // Open the PDF file using the default PDF viewer
            Desktop.getDesktop().open(tempFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying PDF: " + ex.getMessage());
        }
        
        
    }//GEN-LAST:event_jComboBox1MouseEntered

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
          try{   
            String name = jComboBox1.getSelectedItem().toString();
          
            ps = conn.prepareStatement("SELECT document FROM time_tables  WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                Blob blob = (Blob) rs.getBlob("document");

                // Read blob data into a byte array
                byte[] data = blob.getBytes(1, (int) blob.length());

                // Display blob data as a PDF
                displayPDF(data);

                // Close resources
              
            } else {
                JOptionPane.showMessageDialog(null, "No blob data found for the specified material ID.");
            }
         }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }   

    }//GEN-LAST:event_jButton1MouseClicked

    
    
    private void uploadFile(File file){
        try {
                    FileInputStream fis = new FileInputStream(file);
                     
                    ps = conn.prepareStatement("INSERT INTO course_materials (course_code, material_name,material) VALUES (?, ?, ?)");
                    ps.setString(1, "ICT01");
                    ps.setString(2, "Course module description ICT01");
                    ps.setBinaryStream(3, fis, (int) file.length());
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "PDF file uploaded successfully.");

                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error uploading PDF file to database: " + ex.getMessage());
                }
    }
    
    
    // Method to retrieve and set profile picture to the JPanel in dashboard
private void setProfilePicture() {
    conn = MyConnection.getConnection();
    
    try {
        
        String sql = "SELECT profile_picture FROM student WHERE reg_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uname); // Set the registration number
        ResultSet rs = ps.executeQuery();

        // Check if a result was returned
        if (rs.next()) {
            // Retrieve the profile picture data
            InputStream is = rs.getBinaryStream("profile_picture");

            // Convert the InputStream to BufferedImage
            BufferedImage bi = ImageIO.read(is);

            // Close the InputStream
            is.close();

            // Create an ImageIcon from the BufferedImage
            ImageIcon icon = new ImageIcon(bi);

            // Set the ImageIcon as the icon for the JPanel
            dboard_profile_pic.setImage(icon);
            profilePic.setImage(icon);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch(IOException ex){
        ex.printStackTrace();
    }
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Center_panel;
    private javax.swing.JLabel Date_label;
    private javax.swing.JPanel Header_panel;
    private javax.swing.JPanel LogOut_panel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel Menu_panel;
    private javax.swing.JLabel TECMIS_Label;
    private javax.swing.JPanel Tab1;
    private javax.swing.JPanel Tab2;
    private javax.swing.JPanel Tab3;
    private javax.swing.JPanel Tab4;
    private javax.swing.JPanel Tab5;
    private javax.swing.JPanel Tab6;
    private javax.swing.JPanel Tab7;
    private javax.swing.JPanel Tab8;
    private javax.swing.JPanel Tab9;
    private javax.swing.JLabel Time_label;
    private javax.swing.JLabel add;
    private javax.swing.JLabel address;
    private javax.swing.JTable attendance_table;
    private javax.swing.JLabel bdate;
    private javax.swing.JPanel btn1;
    private javax.swing.JPanel btn2;
    private javax.swing.JPanel btn3;
    private javax.swing.JPanel btn4;
    private javax.swing.JPanel btn5;
    private javax.swing.JPanel btn6;
    private javax.swing.JPanel btn7;
    private javax.swing.JPanel btn8;
    private javax.swing.JTextField cCode_txt;
    private javax.swing.JLabel cgpa_label;
    private mini_project.ImageAvatar chooseProfile_pic;
    private javax.swing.JLabel close_btn;
    private javax.swing.JTable course_table;
    private javax.swing.JLabel data_save_button;
    private mini_project.ImageAvatar dboard_profile_pic;
    private javax.swing.JLabel dep1;
    private javax.swing.JLabel dep_name;
    private javax.swing.JLabel dob;
    private javax.swing.JTextField edit_email;
    private javax.swing.JLabel edit_email_label;
    private javax.swing.JLabel email;
    private javax.swing.JLabel email1;
    private javax.swing.JTable final_result_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JComboBox<String> materialCombo_box;
    private javax.swing.JLabel maximize_btn;
    private javax.swing.JTable medical_table;
    private javax.swing.JLabel minimize_btn;
    private javax.swing.JLabel name;
    private javax.swing.JLabel name1;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel name_label;
    private javax.swing.JTable notice_table;
    private javax.swing.JTextField p_no;
    private javax.swing.JLabel p_no_label;
    private javax.swing.JLabel page_title;
    private javax.swing.JLabel phoneno;
    private javax.swing.JLabel preferences_text;
    private mini_project.ImageAvatar profilePic;
    private javax.swing.JLabel profile_edit_button;
    private javax.swing.JLabel reg_no;
    private javax.swing.JLabel regno;
    private javax.swing.JTextField search_text;
    private javax.swing.JButton set_profilePic_btn;
    private javax.swing.JLabel sex;
    private javax.swing.JLabel sex1;
    private javax.swing.JLabel sgpa_label;
    private javax.swing.JLabel timeTable_label;
    private javax.swing.JLabel timeTable_label1;
    private javax.swing.JButton view_btn;
    // End of variables declaration//GEN-END:variables

}
