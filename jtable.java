import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.sql.*;
public class jtable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("시간표");
		frame.setLocation(500,400);
		JPanel panel = new JPanel();
		ImageIcon icon1 = new ImageIcon(new ImageIcon("C:\\Users\\재신\\chat1.jpg").getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		JButton btn = new JButton(icon1);
		Container contentPane = frame.getContentPane();
		panel.add(btn);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/timedb?useUnicode=true&characterEncoding=UTF-8";
	        String user = "root", passwd = "12345";
	        con = DriverManager.getConnection(url, user, passwd);

			stmt = con.createStatement();
			String sql = "select title, day, time from takes";
			rs = stmt.executeQuery(sql);
			String col[] = {"교시/요일","월","화","수","목","금"};
			String row[][] = {
					{"1","","","","",""},
					{"2","","","","",""},
					{"3","","","","",""},
					{"4","","","","",""},
					{"5","","","","",""},
					{"6","","","","",""}, // select 문 DB로 할 수 있음
					{"7","","","","",""},
					{"8","","","","",""},
					{"9","","","","",""},};
			
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
			table.setPreferredScrollableViewportSize(new Dimension(1000,700));
				
					
			contentPane.add(panel,BorderLayout.SOUTH);
					
					
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
		    TableColumnModel tcm = table.getColumnModel();
		    for(int i=0;i<tcm.getColumnCount();i++){
		    	tcm.getColumn(i).setCellRenderer(center); // 가운데 정렬
		    }
				  		
			scrollpane.setSize(500,200);
			frame.add(scrollpane);
			frame.setSize(500,300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
					
			} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}