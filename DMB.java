package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
public class DMB {
	TimetableClient send = new TimetableClient();
	JFrame initWindow = new JFrame("8 times in life");
	JFrame signUP = new JFrame("회원가입 정보 입력");
	public DMB() throws IOException {
		JTextField IDtextField;
		JPasswordField passwordField;
		
		initWindow.setBounds(100, 100, 996, 771);
		initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel iPanel = new JPanel();
		initWindow.getContentPane().add(iPanel, BorderLayout.CENTER);
		iPanel.setLayout(null);
	
		JLabel lblId = new JLabel("ID :");
		lblId.setBounds(164, 127, 183, 104);
		iPanel.add(lblId);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		lblPassword.setBounds(164, 302, 183, 104);
		iPanel.add(lblPassword);
		
		IDtextField = new JTextField();
		IDtextField.setBounds(406, 127, 310, 104);
		iPanel.add(IDtextField);
		IDtextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(406, 302, 310, 104);
		iPanel.add(passwordField);
		
		JButton signIn = new JButton("로그인");
		signIn.setBounds(164, 478, 191, 82);
		iPanel.add(signIn);
		
		JButton signUp = new JButton("회원가입");
		signUp.setBounds(483, 478, 224, 82);
		iPanel.add(signUp);
						
		signUP.setBounds(100, 100, 500, 350);
		signUP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		signUP.getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		signUP.setContentPane(contentPane);
		
		JLabel idLabel = new JLabel("ID :");
		idLabel.setBounds(100, 25, 150, 90);
		contentPane.add(idLabel);

		JLabel pwLabel = new JLabel("PASSWORD : ");
		pwLabel.setBounds(100, 100, 150, 90);
		contentPane.add(pwLabel);
		
		JTextField IDField = new JTextField();
		IDField.setBounds(200, 35, 200, 50);
		contentPane.add(IDField); //아이디
		String id = IDField.getText();
		IDField.setColumns(10);
		
		JPasswordField pwField = new JPasswordField();
		pwField.setBounds(200, 110, 200, 50);
		contentPane.add(pwField); //비번
		String pw = pwField.getText();
						
		JButton btnNewButton = new JButton("가입하기");
		btnNewButton.setBounds(200, 250, 100, 30);
		contentPane.add(btnNewButton);
		
		JFrame complete = new JFrame();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				complete.setVisible(true);
				complete.setBounds(200, 200, 200, 100);
				JPanel panel = new JPanel();
				complete.add(panel);
				
				JLabel label = new JLabel("가입이 완료되었습니다!");
				panel.add(label);
				
				JButton btn = new JButton("확인");
				panel.add(btn);
				btn.addActionListener(new ActionListener(){			    	
					public void actionPerformed(ActionEvent e){
						complete.setVisible(false);
						send.signUp(id, pw);
						signUP.setVisible(false);
					}			
				});
			}
		});

		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//isLoginCheck();
			}
		});
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUP.setVisible(true);
			}
		});
	}	
}


