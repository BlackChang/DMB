package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
public class DMB_OPTION {
	// chat room
	static JFrame optionWindow = new JFrame("OPTION");
	static JFrame preference = new JFrame("PREFERENCE");
	String prof;
	String restTime;
	public DMB_OPTION() throws IOException {		
		optionWindow.setBounds(100, 100, 480, 800);
		optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel iPanel = new JPanel();
		optionWindow.getContentPane().add(iPanel, BorderLayout.CENTER);
		iPanel.setLayout(null);
		iPanel.setBackground(Color.white);	
		
		JLabel empty = new JLabel(new ImageIcon("empty.jpg"));
		
		empty.setBounds(30,30,403,485);
		iPanel.add(empty);

		
		JButton create = new JButton("생성하기");
		create.setBounds(30, 530, 200, 200);
		iPanel.add(create);
		create.setFont(new Font("배달의민족 도현",Font.PLAIN, 30));
		create.setBorderPainted(false);
		create.setBackground(new Color(222,110,70));
		create.setForeground(Color.white);
		create.setOpaque(true);
		
		JButton back = new JButton("돌아가기");
		back.setBounds(235, 530, 200, 200);
		iPanel.add(back);
		back.setFont(new Font("배달의민족 도현",Font.PLAIN, 30));
		back.setBorderPainted(false);
		back.setBackground(new Color(222,110,70));
		back.setForeground(Color.white);
		back.setOpaque(true);
		
		
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionWindow.dispose();
				preference.setVisible(true);				
			}
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionWindow.dispose();
				DMB_INFO.infoWindow.setVisible(true);
			}
		});
		
		preference.setBounds(100, 100, 480, 200);
		preference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel Panel = new JPanel();
		preference.getContentPane().add(Panel, BorderLayout.CENTER);
		Panel.setLayout(null);
		Panel.setBackground(Color.white);	

		JLabel lblOption = new JLabel("선호하는 교수님과 원하는 공강일을 선택하세요");
		lblOption.setBounds(30, 30, 480, 30);
		Panel.add(lblOption);
		lblOption.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblOption.setForeground(new Color(222,110,70));

		
		JComboBox<String> profBox = new JComboBox<String>(TimetableClient.prof_list);
		profBox.setBackground(Color.white);
		profBox.setBounds(30, 70, 120, 30);
		profBox.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		Panel.add(profBox);
		
		JLabel lblProf = new JLabel("교 수 님");
		lblProf.setBounds(155, 70, 90, 30);
		Panel.add(lblProf);
		lblProf.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblProf.setForeground(new Color(222,110,70));

		
		String[] day_list = { "상관없음", "월", "화", "수", "목", "금" };
		JComboBox<String> dayBox = new JComboBox<String>(day_list);
		dayBox.setBackground(Color.white);
		dayBox.setBounds(250, 70, 120, 30);
		dayBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		dayBox.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		Panel.add(dayBox);

		JLabel lblDay = new JLabel("요  일");
		lblDay.setBounds(375, 70, 90, 30);
		Panel.add(lblDay);
		lblDay.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblDay.setForeground(new Color(222,110,70));
		
		
		JButton check = new JButton("확인");
		check.setBounds(345, 110, 90, 30);
		Panel.add(check);
		check.setFont(new Font("배달의민족 도현",Font.PLAIN, 15));
		check.setBorderPainted(false);
		check.setBackground(new Color(222,110,70));
		check.setForeground(Color.white);
		check.setOpaque(true);

		prof = profBox.getSelectedItem().toString();
		restTime = dayBox.getSelectedItem().toString();

		profBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ev) {
				prof = ev.getItem().toString();			
			}
		});		
		dayBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ev) {
				restTime = ev.getItem().toString();
			}
		});
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimetableClient.client.getOption(prof,restTime);
				preference.dispose();				
			}
		});
	}
}
