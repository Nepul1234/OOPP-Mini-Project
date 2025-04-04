 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mini_project;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nepul
 */
public class Admin_Frame extends javax.swing.JFrame {
   
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
  
    public Admin_Frame() {
        initComponents();
        conn = MyConnection.getConnection();
        date();
        Time();
        //panel round
        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 20, 20));
        
        table_update();
        getCourseCount();
        getStudentCount();
        getUsersCount();
        
        
    }
    
    public Admin_Frame(String name, String lname){
        initComponents();
        conn = MyConnection.getConnection();
        date();
        Time();
        //panel round
        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 20, 20));
        
        table_update();
        getCourseCount();
        getStudentCount();
        getUsersCount();
        name_label.setText("Welcome "+ name + " " + lname );
    }
    
    
    //methods for menu panel color change
    
    private void onclick(JPanel panel){
        panel.setBackground(new Color(0,51,255));
    }
    
    private void onLeaveclick(JPanel panel){
        panel.setBackground(new Color(255,0,0));
    }
    
    //method for date
     public void date(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        
        String date = sdf.format(d);
        Date_label.setText("Today is " + date);
    }
     
    //Set Time
    Timer t;
    SimpleDateFormat st;
    
    //method for Time
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
    
    private void table_update(){
        int cc;
        try{
            String sql = "SELECT * FROM course_unit";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ResultSetMetaData RSMD = (ResultSetMetaData) rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel DFT = (DefaultTableModel) course.getModel();
            DFT.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                
                for(int i=1;i<=cc;i++){
                    v2.add(rs.getString("course_code"));
                    v2.add(rs.getString("course_name"));
                    v2.add(rs.getString("type"));
                    v2.add(rs.getString("credits"));
                    v2.add(rs.getString("lecturer_incharge"));
                    v2.add(rs.getString("lecture_hours"));
                    
                }
                DFT.addRow(v2);
            }
        }catch(Exception e){
        
        }
    }
    
     

 
    public void getCourseCount() {
        try {
            
            String sql = "SELECT COUNT(*) AS row_count FROM course_unit";
            ps = conn.prepareStatement(sql);

            // Execute query
            rs = ps.executeQuery();
 
            if (rs.next()) {
                int rowCount = rs.getInt("row_count");

                // Set row count to JLabel
                courses.setText(String.valueOf(rowCount));
            }

             
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
    
     public void getStudentCount() {
        try {
            
            String sql = "SELECT COUNT(*) AS row_count FROM student";
            ps = conn.prepareStatement(sql);

            // Execute query
            rs = ps.executeQuery();
 
            if (rs.next()) {
                int rowCount = rs.getInt("row_count");

                // Set row count to JLabel
                students.setText(String.valueOf(rowCount));
            }

             
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
     
    public void getUsersCount() {
        try {
            
            String sql = "SELECT COUNT(*) AS row_count FROM user";
            ps = conn.prepareStatement(sql);

            // Execute query
            rs = ps.executeQuery();
 
            if (rs.next()) {
                int rowCount = rs.getInt("row_count");

                // Set row count to JLabel
                users.setText(String.valueOf(rowCount));
            }

             
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main_panel = new javax.swing.JPanel();
        Header_panel2 = new javax.swing.JPanel();
        TECMIS_Label = new javax.swing.JLabel();
        maximize_btn = new javax.swing.JLabel();
        minimize_btn = new javax.swing.JLabel();
        close_btn = new javax.swing.JLabel();
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
        center_panel2 = new javax.swing.JPanel();
        Tab1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        name_label = new javax.swing.JLabel();
        Date_label = new javax.swing.JLabel();
        Time_label = new javax.swing.JLabel();
        dboard_profile_pic = new mini_project.ImageAvatar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        users = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        students = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        courses = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Tab2 = new javax.swing.JPanel();
        Tab3 = new javax.swing.JPanel();
        Tab4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        course_code = new javax.swing.JTextField();
        course_name = new javax.swing.JTextField();
        Combo_type = new javax.swing.JComboBox<>();
        credits = new javax.swing.JTextField();
        lec_name = new javax.swing.JTextField();
        lec_hours = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        course = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        Tab5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Main_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header_panel2.setBackground(new java.awt.Color(51, 51, 51));
        Header_panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TECMIS_Label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TECMIS_Label.setForeground(new java.awt.Color(0, 51, 255));
        TECMIS_Label.setText("TECMIS");
        Header_panel2.add(TECMIS_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 7, -1, -1));

        maximize_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/maximize_icon.png"))); // NOI18N
        maximize_btn.setText(" ");
        maximize_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Header_panel2.add(maximize_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(679, 6, -1, -1));

        minimize_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize_icon.png"))); // NOI18N
        minimize_btn.setText(" ");
        minimize_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minimize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize_btnMouseClicked(evt);
            }
        });
        Header_panel2.add(minimize_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(706, 6, -1, -1));

        close_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close_icon.png"))); // NOI18N
        close_btn.setText(" ");
        close_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        close_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_btnMouseClicked(evt);
            }
        });
        Header_panel2.add(close_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 6, -1, -1));

        Main_panel.add(Header_panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        jLabel31.setText("Users");
        btn2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 10, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users_icon.png"))); // NOI18N
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

        Main_panel.add(Menu_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 490));

        center_panel2.setBackground(new java.awt.Color(255, 255, 255));
        center_panel2.setLayout(new java.awt.CardLayout());

        Tab1.setBackground(new java.awt.Color(255, 255, 255));

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
                .addContainerGap(25, Short.MAX_VALUE))
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

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(172, 122));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users_icon.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Users");

        users.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        users.setForeground(new java.awt.Color(255, 255, 255));
        users.setText("Num");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(users))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(users)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(172, 122));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/students_icon.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Students");

        students.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        students.setForeground(new java.awt.Color(255, 255, 255));
        students.setText("Num");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(students))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(students)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(172, 122));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/courses_icon.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Courses");

        courses.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        courses.setForeground(new java.awt.Color(255, 255, 255));
        courses.setText("Num");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(courses))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(courses)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user11.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Users");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel17)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.setPreferredSize(new java.awt.Dimension(112, 125));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/courses.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Courses");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.setPreferredSize(new java.awt.Dimension(112, 125));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notice11.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Notices");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addContainerGap())
        );

        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.setPreferredSize(new java.awt.Dimension(112, 125));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/timetable11.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Time table");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        javax.swing.GroupLayout Tab1Layout = new javax.swing.GroupLayout(Tab1);
        Tab1.setLayout(Tab1Layout);
        Tab1Layout.setHorizontalGroup(
            Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Tab1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Tab1Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(Tab1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)))))
                .addContainerGap())
        );
        Tab1Layout.setVerticalGroup(
            Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        center_panel2.add(Tab1, "card2");

        Tab2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Tab2Layout = new javax.swing.GroupLayout(Tab2);
        Tab2.setLayout(Tab2Layout);
        Tab2Layout.setHorizontalGroup(
            Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );
        Tab2Layout.setVerticalGroup(
            Tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        center_panel2.add(Tab2, "card2");

        javax.swing.GroupLayout Tab3Layout = new javax.swing.GroupLayout(Tab3);
        Tab3.setLayout(Tab3Layout);
        Tab3Layout.setHorizontalGroup(
            Tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );
        Tab3Layout.setVerticalGroup(
            Tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        center_panel2.add(Tab3, "card2");

        Tab4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 204));
        jLabel20.setText("Course name");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 204));
        jLabel21.setText("Course code");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 204));
        jLabel22.setText("Course type");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 204));
        jLabel23.setText("Credits");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setText("Lecture hours");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 204));
        jLabel25.setText("Lecturer name");

        course_code.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        course_code.setText(" ");
        course_code.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        course_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                course_codeActionPerformed(evt);
            }
        });

        course_name.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        course_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));

        Combo_type.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Combo_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical" }));

        credits.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        credits.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        credits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditsActionPerformed(evt);
            }
        });

        lec_name.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lec_name.setText(" ");
        lec_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));

        lec_hours.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lec_hours.setText(" ");
        lec_hours.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(credits, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lec_name))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(course_name))
                    .addComponent(jLabel21)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lec_hours))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(course_code, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(course_code, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(course_name, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(Combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(credits, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lec_name, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lec_hours, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        course.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course code", "Course name", "Type", "Credits", "Lecturer", "Lecture hours"
            }
        ));
        course.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(course);

        jButton1.setBackground(new java.awt.Color(0, 0, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Course section");

        javax.swing.GroupLayout Tab4Layout = new javax.swing.GroupLayout(Tab4);
        Tab4.setLayout(Tab4Layout);
        Tab4Layout.setHorizontalGroup(
            Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(Tab4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Tab4Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(Tab4Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel26)))
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Tab4Layout.setVerticalGroup(
            Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(Tab4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        center_panel2.add(Tab4, "card2");

        Tab5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Upload Time tables");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jButton4.setBackground(new java.awt.Color(0, 0, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Upload");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("Name");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(251, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addGap(40, 40, 40)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Tab5Layout = new javax.swing.GroupLayout(Tab5);
        Tab5.setLayout(Tab5Layout);
        Tab5Layout.setHorizontalGroup(
            Tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        Tab5Layout.setVerticalGroup(
            Tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        center_panel2.add(Tab5, "card2");

        Main_panel.add(center_panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 567, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void minimize_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimize_btnMouseClicked
        // TODO add your handling code here:
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimize_btnMouseClicked

    private void close_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_close_btnMouseClicked

    private void LogOut_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut_panelMouseClicked
        // TODO add your handling code here:
        Login_page lp = new Login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOut_panelMouseClicked

    private void btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseClicked
        // TODO add your handling code here:
        onclick(btn1);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
       
        Tab1.setVisible(true);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false);
        Tab5.setVisible(false);
        
    }//GEN-LAST:event_btn1MouseClicked

    private void btn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn1MouseEntered

    private void btn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseClicked
        // TODO add your handling code here:
        onclick(btn2);
        onLeaveclick(btn1);
        onLeaveclick(btn3);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
        
        //Switching panels

        Tab1.setVisible(false);
        Tab2.setVisible(true);
        Tab3.setVisible(false);
        Tab4.setVisible(false);
        Tab5.setVisible(false);
        
    }//GEN-LAST:event_btn2MouseClicked

    private void btn2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn2MouseEntered

    private void btn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseClicked
        // TODO add your handling code here:
        onclick(btn3);
        onLeaveclick(btn1);
        onLeaveclick(btn2);
        onLeaveclick(btn4);
        onLeaveclick(btn5);
       

        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(true);
        Tab4.setVisible(false);
        Tab5.setVisible(false);
    }//GEN-LAST:event_btn3MouseClicked

    private void btn3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn3MouseEntered

    private void btn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseClicked
        // TODO add your handling code here:
        onclick(btn4);
        onLeaveclick(btn2);
        onLeaveclick(btn3);
        onLeaveclick(btn1);
        onLeaveclick(btn5);

        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(true);
        Tab5.setVisible(false);
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

        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false);
        Tab5.setVisible(true);
    }//GEN-LAST:event_btn5MouseClicked

    private void btn5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn5MouseEntered

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
        Login_page lp = new Login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel32MouseClicked

    private void course_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_course_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_course_codeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    try {
    
    String cCode = course_code.getText();
    String cname = course_name.getText();
    String type = Combo_type.getSelectedItem().toString();
    int credit = Integer.valueOf(credits.getText()); 
    String lecName = lec_name.getText();
    int hours = Integer.valueOf(lec_hours.getText());
    
    // Prepare SQL statement
    String sql = "INSERT INTO course_unit (course_code, course_name, type, credits, lecturer_incharge, lecture_hours) VALUES (?, ?, ?, ?, ?, ?)";
    ps = conn.prepareStatement(sql);
    ps.setString(1, cCode);
    ps.setString(2, cname);
    ps.setString(3, type);
    ps.setInt(4, credit);
    ps.setString(5, lecName);
    ps.setInt(6, hours);
    
    // Execute SQL statement
    int rowsAffected = ps.executeUpdate();
    
    if (rowsAffected > 0) {
        // If insertion is successful, show a success message
        JOptionPane.showMessageDialog(this, "Record Saved");
        table_update(); //method for update table
        // Clear input fields
        course_code.setText("");
        course_name.setText("");
        credits.setText("");
        lec_name.setText("");
        lec_hours.setText("");
    } else {
        // If no rows were affected, something went wrong
        JOptionPane.showMessageDialog(this, "Failed to save record", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
} catch (NumberFormatException e) {
    // Handle the case where parsing text to integers fails
    JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and lecture hours", "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace(); // This will print the stack trace to help diagnose the issue
} catch (SQLException e) {
    // Handle database-related errors
    JOptionPane.showMessageDialog(this, "Error saving record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}

        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        try {
    DefaultTableModel model = (DefaultTableModel) course.getModel();
        int selectedIndex = course.getSelectedRow();
        
    String cCode =  String.valueOf(model.getValueAt(selectedIndex,0).toString());
     
    String cname = course_name.getText();
    String type = Combo_type.getSelectedItem().toString();
    int credit = Integer.valueOf(credits.getText()); 
    String lecName = lec_name.getText();
    int hours = Integer.valueOf(lec_hours.getText());
    
    // Prepare SQL statement
    String sql = "UPDATE course_unit SET course_name =?, type=?, credits=?, lecturer_incharge=?, lecture_hours=? WHERE course_code = ?";
    ps = conn.prepareStatement(sql);
    ps.setString(6, cCode);
    ps.setString(1, cname);
    ps.setString(2, type);
    ps.setInt(3, credit);
    ps.setString(4, lecName);
    ps.setInt(5, hours);
    
    // Execute SQL statement
    int rowsAffected = ps.executeUpdate();
    
    if (rowsAffected > 0) {
        // If insertion is successful, show a success message
        JOptionPane.showMessageDialog(this, "Record Updated successfully");
        table_update(); //method for update table
        // Clear input fields
        course_code.setText("");
        course_name.setText("");
        credits.setText("");
        lec_name.setText("");
        lec_hours.setText("");
    } else {
        // If no rows were affected, something went wrong
        JOptionPane.showMessageDialog(this, "Failed to save record", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
} catch (NumberFormatException e) {
    // Handle the case where parsing text to integers fails
    JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and lecture hours", "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace(); // This will print the stack trace to help diagnose the issue
} catch (SQLException e) {
    // Handle database-related errors
    JOptionPane.showMessageDialog(this, "Error saving record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
    DefaultTableModel model = (DefaultTableModel) course.getModel();
        int selectedIndex = course.getSelectedRow();
        
    String cCode =  String.valueOf(model.getValueAt(selectedIndex,0).toString());
     
    String cname = course_name.getText();
    String type = Combo_type.getSelectedItem().toString();
    int credit = Integer.valueOf(credits.getText()); 
    String lecName = lec_name.getText();
    int hours = Integer.valueOf(lec_hours.getText());
    
    // Prepare SQL statement
    String sql = "DELETE FROM course_unit WHERE course_code = ?";
    ps = conn.prepareStatement(sql);
    ps.setString(1, cCode);
    
    
    // Execute SQL statement
    int rowsAffected = ps.executeUpdate();
    
    if (rowsAffected > 0) {
        // If insertion is successful, show a success message
        JOptionPane.showMessageDialog(this, "Record Deleted successfully");
        table_update(); //method for update table
        // Clear input fields
        course_code.setText("");
        course_name.setText("");
        credits.setText("");
        lec_name.setText("");
        lec_hours.setText("");
    } else {
        // If no rows were affected, something went wrong
        JOptionPane.showMessageDialog(this, "Failed to save record", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
} catch (NumberFormatException e) {
    // Handle the case where parsing text to integers fails
    JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and lecture hours", "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace(); // This will print the stack trace to help diagnose the issue
} catch (SQLException e) {
    // Handle database-related errors
    JOptionPane.showMessageDialog(this, "Error saving record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void creditsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditsActionPerformed

    private void courseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) course.getModel();
        int selectedIndex = course.getSelectedRow();
        
        course_code.setText(model.getValueAt(selectedIndex,0).toString());
        course_name.setText(model.getValueAt(selectedIndex,1).toString());
        Combo_type.setSelectedItem(model.getValueAt(selectedIndex,2).toString());
        credits.setText(model.getValueAt(selectedIndex,3).toString());
        lec_name.setText(model.getValueAt(selectedIndex,4).toString());
        lec_hours.setText(model.getValueAt(selectedIndex,5).toString());
    }//GEN-LAST:event_courseMouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(true);
        Tab5.setVisible(false);
        
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
         Tab1.setVisible(false);
        Tab2.setVisible(true);
        Tab3.setVisible(false);
        Tab4.setVisible(false);
        Tab5.setVisible(false);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
         Tab1.setVisible(false);
        Tab2.setVisible(false);
        Tab3.setVisible(true);
        Tab4.setVisible(false);
        Tab5.setVisible(false);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
         Tab1.setVisible(true);
        Tab2.setVisible(false);
        Tab3.setVisible(false);
        Tab4.setVisible(false);
        Tab5.setVisible(true);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String name = jTextField2.getText();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            uploadFile(selectedFile,name);
        }


    }//GEN-LAST:event_jButton4MouseClicked

    private void uploadFile(File file,String name) {
        try {
                    FileInputStream fis = new FileInputStream(file);                   
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO time_tables (name, document) VALUES (?, ?)");
                    statement.setString(1, name);
                    statement.setBinaryStream(2, fis, (int) file.length());
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(this, "PDF file uploaded successfully.");

                    fis.close();
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error uploading PDF file to database: " + ex.getMessage());
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
            java.util.logging.Logger.getLogger(Admin_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Combo_type;
    private javax.swing.JLabel Date_label;
    private javax.swing.JPanel Header_panel2;
    private javax.swing.JPanel LogOut_panel;
    private javax.swing.JPanel Main_panel;
    private javax.swing.JPanel Menu_panel;
    private javax.swing.JLabel TECMIS_Label;
    private javax.swing.JPanel Tab1;
    private javax.swing.JPanel Tab2;
    private javax.swing.JPanel Tab3;
    private javax.swing.JPanel Tab4;
    private javax.swing.JPanel Tab5;
    private javax.swing.JLabel Time_label;
    private javax.swing.JPanel btn1;
    private javax.swing.JPanel btn2;
    private javax.swing.JPanel btn3;
    private javax.swing.JPanel btn4;
    private javax.swing.JPanel btn5;
    private javax.swing.JPanel center_panel2;
    private javax.swing.JLabel close_btn;
    private javax.swing.JTable course;
    private javax.swing.JTextField course_code;
    private javax.swing.JTextField course_name;
    private javax.swing.JLabel courses;
    private javax.swing.JTextField credits;
    private mini_project.ImageAvatar dboard_profile_pic;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField lec_hours;
    private javax.swing.JTextField lec_name;
    private javax.swing.JLabel maximize_btn;
    private javax.swing.JLabel minimize_btn;
    private javax.swing.JLabel name_label;
    private javax.swing.JLabel students;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables
}
