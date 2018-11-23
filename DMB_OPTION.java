package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
public class DMB_OPTION {
	JFrame optionWindow = new JFrame("OPTION");
	JFrame preference = new JFrame("PREFERENCE");
	String prof;
	String restTime;
	public DMB_OPTION() throws IOException {		
		optionWindow.setBounds(100, 100, 480, 800);
		optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel iPanel = new JPanel();
		optionWindow.getContentPane().add(iPanel, BorderLayout.CENTER);
		iPanel.setLayout(null);
		iPanel.setBackground(Color.white);	
		
		JButton create = new JButton("생성하기");
		create.setBounds(30, 30, 200, 200);
		iPanel.add(create);
		create.setFont(new Font("배달의민족 도현",Font.PLAIN, 30));
		create.setBorderPainted(false);
		create.setBackground(new Color(222,110,70));
		create.setForeground(Color.white);

		JButton back = new JButton("돌아가기");
		back.setBounds(235, 30, 200, 200);
		iPanel.add(back);
		back.setFont(new Font("배달의민족 도현",Font.PLAIN, 30));
		back.setBorderPainted(false);
		back.setBackground(new Color(222,110,70));
		back.setForeground(Color.white);
		
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
		preference.setBounds(100, 100, 480, 800);
		preference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel Panel = new JPanel();
		preference.getContentPane().add(Panel, BorderLayout.CENTER);
		Panel.setLayout(null);
		Panel.setBackground(Color.white);	
	}
}
