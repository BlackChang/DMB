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
	JFrame signUP = new JFrame("ȸ������ ���� �Է�");
	String id;
	String pw;
	String name;
	String prof;
	String day;
	private String status;
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
		
		JButton signIn = new JButton("�α���");
		signIn.setBounds(164, 478, 191, 82);
		iPanel.add(signIn);
		
		JButton signUp = new JButton("ȸ������");
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
		contentPane.add(IDField); //���̵�
		//id = IDField.getText();
		IDField.setColumns(10);
		
		JPasswordField pwField = new JPasswordField();
		pwField.setBounds(200, 110, 200, 50);
		contentPane.add(pwField); //���
		//pw = pwField.getText();

		JButton signUpButton = new JButton("�����ϱ�");
		signUpButton.setBounds(200, 250, 100, 30);
		contentPane.add(signUpButton);
		
	
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = "SIGNUP";
				id = IDField.getText();
				pw = pwField.getText();		
				
				TimetableClient.client.signUp(id, pw);
            	
            	IDField.setText("");
            	pwField.setText("");
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
				initWindow.setVisible(false);
			}
		});
	}	
}


