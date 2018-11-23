package Network_DMB;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
public class DMB {
	JFrame initWindow = new JFrame("8 TIMES IN LIFE");
	JFrame signUP = new JFrame("ȸ������ ���� �Է�");
	String id;
	String pw;
	String name;
	String prof;
	String day;
	public DMB() throws IOException {
		JTextField IDtextField;
		JPasswordField passwordField;
		
		initWindow.setBounds(100, 100, 320, 500);
		initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel iPanel = new JPanel();
		initWindow.getContentPane().add(iPanel, BorderLayout.CENTER);
		iPanel.setLayout(null);
		iPanel.setBackground(Color.white);			
		
		JLabel icon = new JLabel(new ImageIcon("icon.png"));
		icon.setBounds(50,30,200,200);
		iPanel.add(icon);
		
		
		JLabel lblId = new JLabel("���̵�");
		lblId.setBounds(50, 260, 50, 30);
		iPanel.add(lblId);
		lblId.setFont(new Font("����ǹ��� ����", Font.PLAIN, 15));
		lblId.setForeground(new Color(222,110,70));
		
		IDtextField = new JTextField();
		IDtextField.setBounds(50, 290, 200, 30);
		iPanel.add(IDtextField);
		IDtextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("��й�ȣ");
		lblPassword.setBounds(50, 330, 100, 30);
		iPanel.add(lblPassword);
		lblPassword.setFont(new Font("����ǹ��� ����", Font.PLAIN, 15));
		lblPassword.setForeground(new Color(222,110,70));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(50, 360, 200, 30);
		iPanel.add(passwordField);
		
		JButton signIn = new JButton("�α���");
		signIn.setBounds(50, 395, 95, 30);
		iPanel.add(signIn);
		signIn.setFont(new Font("����ǹ��� ����",Font.PLAIN, 15));
		signIn.setBorderPainted(false);
		signIn.setBackground(new Color(222,110,70));
		signIn.setForeground(Color.white);
		
		JButton signUp = new JButton("ȸ������");
		signUp.setBounds(155, 395, 95, 30);
		iPanel.add(signUp);
		signUp.setFont(new Font("����ǹ��� ����",Font.PLAIN, 15));
		signUp.setBorderPainted(false);
		signUp.setBackground(new Color(222,110,70));
		signUp.setForeground(Color.white);
						
		signUP.setBounds(125, 130, 275, 330);
		signUP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		signUP.getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);

		signUP.setContentPane(contentPane);
		
		JLabel idLabel = new JLabel("���̵�");
		idLabel.setBounds(30, 30, 100, 30);
		idLabel.setFont(new Font("����ǹ��� ����", Font.PLAIN, 15));
		idLabel.setForeground(new Color(222,110,70));
		contentPane.add(idLabel);

		JTextField IDField = new JTextField();
		IDField.setBounds(30, 60, 200, 30);
		contentPane.add(IDField); //���̵�
		IDField.setColumns(9);

		JLabel pwLabel = new JLabel("��й�ȣ");
		pwLabel.setBounds(30, 90, 100, 30);
		pwLabel.setFont(new Font("����ǹ��� ����", Font.PLAIN, 15));
		pwLabel.setForeground(new Color(222,110,70));
		contentPane.add(pwLabel);
		
		JPasswordField pwField = new JPasswordField();
		pwField.setBounds(30, 120, 200, 30);
		contentPane.add(pwField); //���
		
		JLabel nameLabel = new JLabel("�̸�");
		nameLabel.setBounds(30, 150, 100, 30);
		nameLabel.setFont(new Font("����ǹ��� ����", Font.PLAIN, 15));
		nameLabel.setForeground(new Color(222,110,70));
		contentPane.add(nameLabel);		
		
		JTextField nameField = new JTextField();
		nameField.setBounds(30, 180, 200, 30);
		contentPane.add(nameField); //�̸�
		nameField.setColumns(30);

		JButton signUpButton = new JButton("�����ϱ�");
		signUpButton.setBounds(30, 220, 200, 30);
		signUpButton.setFont(new Font("����ǹ��� ����",Font.PLAIN, 15));
		signUpButton.setBorderPainted(false);
		signUpButton.setBackground(new Color(222,110,70));
		signUpButton.setForeground(Color.white);
		contentPane.add(signUpButton);

		
	
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id = IDField.getText();
				pw = pwField.getText();
				name = nameField.getText();
				
				if(id.length()!=9) {
				   	JOptionPane.showMessageDialog(null, "ID�� �й� ������� �Է����ּ���.", "TOO SHORT ID",
    						JOptionPane.WARNING_MESSAGE);	
				}
				else if(pw.length() < 5) {
				   	JOptionPane.showMessageDialog(null, "PW�� 5�ڸ� �̻� �Է����ּ���.", "TOO SHORT PW",
    						JOptionPane.WARNING_MESSAGE);	
				}
				else if(name.length() == 0) {
				   	JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���.", "TOO SHORT NAME",
    						JOptionPane.WARNING_MESSAGE);	
				}
				else
					TimetableClient.client.signUp(id, pw, name);
            	
            	IDField.setText("");
            	pwField.setText("");
            	nameField.setText("");
			}
		});

		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id = IDtextField.getText();
				pw = passwordField.getText();
				
				TimetableClient.client.signIn(id, pw);
			}
		});
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUP.setVisible(true);
			}
		});
	}	
}


