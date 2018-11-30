package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.mysql.jdbc.PreparedStatement;

import java.awt.event.*;
public class DMB_TABLE {
	static JFrame frame;
	public DMB_TABLE(String id, int grade, int semester) throws IOException {		
		frame = new JFrame("시간표");
		JPanel panel = new JPanel();
		ImageIcon icon1 = new ImageIcon(new ImageIcon("C:\\Users\\재신\\chat1.jpg").getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		JButton btn = new JButton(icon1);
		Container contentPane = frame.getContentPane();
		panel.add(btn);
		try {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
		
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/timedb?useUnicode=true&characterEncoding=utf8";
	        String user = "root", passwd = "12345";
	        con = (Connection) DriverManager.getConnection(url, user, passwd);
	        
	        System.out.println(id + " " + grade + " " + semester);
	        String sql = "select title, day, time from takes where grade=" + grade + " and semester=" + semester + " and id='" +  id + "'";
	        ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			String col[] = {"교시/요일","월","화","수","목","금"};
			String row[][] = {
					{"1교시 09:00~10:00","","","","",""},
					{"2교시\n 10:00~11:00","","","","",""},
					{"3교시\n 11:00~12:00","","","","",""},
					{"4교시\n 12:00~13:00","","","","",""},
					{"5교시\n 13:00~14:00","","","","",""},
					{"6교시\n 14:00~15:00","","","","",""}, // select 문 DB로 할 수 있음
					{"7교시\n 15:00~16:00","","","","",""},
					{"8교시\n 16:00~17:00","","","","",""},
					{"9교시\n 17:00~18:00","","","","",""},};
			
			while (rs.next()) {
		        String title = rs.getString(1);
		        if (rs.wasNull())
		        	title = "null";
		        
		        String day = rs.getString(2);
		        if (rs.wasNull())
		        	day = "null";
		        System.out.println(day);
		        String hour = rs.getString(3);
		        if(rs.wasNull())
		        	hour = "null";
		        
		        int firstTime, lastTime, length;
		        length = hour.length();
		        firstTime = hour.charAt(0) - '0';
		        lastTime = hour.charAt(length - 1) - '0';
		    
		        if(day.equalsIgnoreCase("월")) {
		        	for(int k = firstTime; k <= lastTime; k++) {
		        		row[k - 1][1] = title;
		        	}
		        }
		        if(day.equalsIgnoreCase("화")) {
		        	for(int k = firstTime; k <= lastTime; k++) {
		        		row[k - 1][2] = title;
		        	}
		        }
		        if(day.equalsIgnoreCase("수")) {
		        	for(int k = firstTime; k <= lastTime; k++) {
		        		row[k - 1][3] = title;
		        	}
		        }
		        if(day.equalsIgnoreCase("목")) {
		        	for(int k = firstTime; k <= lastTime; k++) {
		        		row[k - 1][4] = title;
		        	}
		        }
		        if(day.equalsIgnoreCase("금")) {
		        	for(int k = firstTime; k <= lastTime; k++) {
		        		row[k - 1][5] = title;
		        	}
		        }
		        String color[] = {"0x7ele9c","0x15b01a","0x0343df","0xff81c0","0x653700","0xe50000","0x95d0fc",
		        		"0x029386","0xffff14","0xe6daa6","0xcea2fd","0x13eac9","0x8e82fe","0x53fcal","0xaaff32","0xceb301"
		        };        
		    }
			DefaultTableModel model = new DefaultTableModel(row,col);
			
			JTable table = new JTable(model);
			   
			JScrollPane scrollpane = new JScrollPane(table);
			contentPane.add(scrollpane, BorderLayout.CENTER);
			
			
			table.setRowHeight(60); // row의 높이 조절
			table.setPreferredScrollableViewportSize(new Dimension(1000,535));
			table.getTableHeader().setFont(new Font("배달의민족 도현",Font.PLAIN,20));
			contentPane.add(panel,BorderLayout.SOUTH);
			
					
					
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
		    TableColumnModel tcm = table.getColumnModel();
		    
		    for(int i=0;i < tcm.getColumnCount();i++){
		    	tcm.getColumn(i).setCellRenderer(center); // 가운데 정렬
		    }
				  		
			scrollpane.setSize(500,200);
			frame.add(scrollpane);
			frame.setSize(500,200);
			frame.setBounds(100,100,1000,400);	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			table.setFont(new Font("배달의민족 도현",Font.PLAIN, 15));
		
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	
}	
