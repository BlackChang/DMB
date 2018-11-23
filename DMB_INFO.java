package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
public class DMB_INFO {
	JFrame infoWindow = new JFrame("8 times in life");
	String id;
	String pw;
	String name;
	String prof;
	String day;
	int grade;
	int semester;
	public DMB_INFO() throws IOException {
		JTextField IDtextField;
		JPasswordField passwordField;
		
		infoWindow.setBounds(100, 100, 490, 250);
		infoWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel iPanel = new JPanel();
		infoWindow.getContentPane().add(iPanel, BorderLayout.CENTER);
		iPanel.setLayout(null);
		iPanel.setBackground(Color.white);			
	
		JLabel lblInfo = new JLabel("불러올 시간표의 학년과 학기를 선택하세요");
		lblInfo.setBounds(50, 50, 500, 30);
		iPanel.add(lblInfo);
		lblInfo.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblInfo.setForeground(new Color(222,110,70));

		JLabel lblGrade = new JLabel("학    년");
		lblGrade.setBounds(50, 100, 100, 30);
		iPanel.add(lblGrade);
		lblGrade.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblGrade.setForeground(new Color(222,110,70));

		String[] grd = { "1", "2", "3", "4" };
		JComboBox<String> gradeBox = new JComboBox<String>(grd);
		gradeBox.setBackground(Color.white);
		
		gradeBox.setBounds(130, 100, 93, 30);
		gradeBox.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		iPanel.add(gradeBox);
		
		JLabel lblSem = new JLabel("학    기");
		lblSem.setBounds(240, 100, 100, 30);
		iPanel.add(lblSem);
		lblSem.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		lblSem.setForeground(new Color(222,110,70));
		
		String[] sem = { "1", "2" };
		JComboBox<String> semBox = new JComboBox<String>(sem);
		semBox.setBackground(Color.white);
		semBox.setBounds(320, 100, 93, 30);
		semBox.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		iPanel.add(semBox);
		
		JButton check = new JButton("확인");
		check.setBounds(340, 150, 80, 30);
		iPanel.add(check);
		check.setFont(new Font("배달의민족 도현",Font.PLAIN, 15));
		check.setBorderPainted(false);
		check.setBackground(new Color(222,110,70));
		check.setForeground(Color.white);

		gradeBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ev) {
				String grd = ev.getItem().toString();
				grade = Integer.parseInt(grd);				
			}
		});		
		semBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ev) {
				String sem = ev.getItem().toString();
				semester = Integer.parseInt(sem);				
			}
		});
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(grade + " " + semester);
				infoWindow.dispose();
				TimetableClient.client.signIn(id, pw);
			}
		});

	}
}