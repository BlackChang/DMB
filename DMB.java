package Network_DMB;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
public class DMB {
	BufferedReader in;
	PrintWriter out;
	static JFrame frame = new JFrame("8 times in life");
	
	public DMB() throws IOException {
		JTextField textField;
		JPasswordField passwordField;
		frame.setBounds(100, 100, 996, 771);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	
		JLabel lblId = new JLabel("ID :");
		lblId.setBounds(164, 127, 183, 104);
		panel.add(lblId);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(164, 302, 183, 104);
		panel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(406, 127, 310, 104);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(406, 302, 310, 104);
		panel.add(passwordField);
		
		JButton signIn = new JButton("로그인");
		signIn.setBounds(164, 478, 191, 82);
		panel.add(signIn);
		
		JButton signUP = new JButton("회원가입");
		signUP.setBounds(483, 478, 224, 82);
		panel.add(signUP);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//isLoginCheck();
			}
		});
		signUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUP.setVisible(true);
				//DMBinput2 nw = new DMBinput2();
				//nw.NewScreen();								
			}
		});
	}	
	public void signUP() throws IOException {
		JFrame signUp = new JFrame("회원가입 정보");
		String id;
		String pw;
		JPanel contentPane;
		JLabel label;
		JPasswordField passwordField;
		JTextField IDField;
		JTextField PWField;
		JTextField nameField;
	
		JLabel label_1;
		JLabel label_2;
		JButton btnNewButton;
		
		
		signUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		signUp.setBounds(100, 100, 998, 737);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		signUp.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		signUp.getContentPane().add(contentPane, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		lblNewLabel.setBounds(113, 36, 207, 90);
		contentPane.add(lblNewLabel);
		
		IDField = new JTextField();
		IDField.setBounds(364, 43, 368, 78);
		contentPane.add(IDField); //아이디
		id = IDField.getText();
		IDField.setColumns(10);
		
		label = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label.setBounds(113, 157, 207, 90);
		contentPane.add(label);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(364, 164, 368, 78);
		contentPane.add(passwordField); //비번
		pw = passwordField.getText();
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(364, 299, 368, 78);
		contentPane.add(nameField); //이름
		String name = nameField.getText();
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(364, 417, 368, 78);
		contentPane.add(IDField); //학번
		String sn = IDField.getText();
		
		label_1 = new JLabel("\uC774\uB984");
		label_1.setBounds(113, 292, 207, 90);
		contentPane.add(label_1);
		
		label_2 = new JLabel("\uD559\uBC88");
		label_2.setBounds(113, 410, 207, 90);
		contentPane.add(label_2);
		
		btnNewButton = new JButton("가입하기");
		btnNewButton.setBounds(334, 539, 255, 90);
		contentPane.add(btnNewButton);

		JFrame frame2 = new JFrame();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
				frame2.setSize(200, 200);
				
				JPanel panel = new JPanel();
				frame2.add(panel);

				JLabel label = new JLabel("가입이 완료되었습니다!");
			    panel.add(label);
	
			    JButton btn = new JButton("확인");
			    panel.add(btn);
				btn.addActionListener(new ActionListener(){			    	
				    public void actionPerformed(ActionEvent e){
				    	frame2.setVisible(false);

				    }		
				});
			}
		});
	}
	/*public static void main(String[] args) throws Exception {
		/*EventQueue.invokeLater(new Runnable() {
			public void run(){
				try {
					DMB client = new DMB();
					client.frame.setVisible(true);
					client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}	
		});
		DMB cli = new DMB();
		cli.gogo();
	}*/	
}


