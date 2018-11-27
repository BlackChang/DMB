import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class table {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame2 = new JFrame();
		JPanel panel = new JPanel();
		ImageIcon icon1 = new ImageIcon(new ImageIcon("C:\\Users\\재신\\chat1.jpg").getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		JButton btn = new JButton(icon1); // 버튼의 채팅 아이콘 삽입 후 그 크기에 맞게 조절
		frame.add(btn);
		Container con = frame.getContentPane();
		con.setLayout(new FlowLayout());
			
		String col[] = {"교시/요일","월","화","수","목","금"};
		String row[][] = {
				{"1","val1","val2","val3","val4","val5"},
				{"2","val1","val2","val3","val4","val5"},
				{"3","val1","val2","val3","val4","val5"},
				{"4","val1","val2","val3","val4","val5"},
				{"5","val1","val2","val3","val4","val5"},
				{"6","val1","val2","val3","val4","val5"}, // select 문 DB로 할 수 있음
				{"7","val1","val2","val3","val4","val5"},
				{"8","val1","val2","val3","val4","val5"},
				{"9","val1","val2","val3","val4","val5"},
		};
		JTable table = new JTable(row,col);
		JScrollPane scroll = new JScrollPane(table);
		
		
		table.setRowHeight(60); // row의 높이 조절
		table.setPreferredScrollableViewportSize(new Dimension(1000,700));
		scroll.setSize(500,200);

		frame.setSize(400, 300);
		frame.add(scroll);
		frame.pack();// 테이블을 프레임크기에 맞추기 위해 출력

		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
	    TableColumnModel tcm = table.getColumnModel();
	    for(int i=0;i<tcm.getColumnCount();i++){
	    	tcm.getColumn(i).setCellRenderer(center); // 가운데 정렬
	    }

	    
	    frame.setVisible(true);
		frame.setResizable(false);

		
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			
			}
		});
		
	}	
	
	
	}
