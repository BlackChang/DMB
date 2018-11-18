import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DMBinput1 extends DMBinput2 {

	static JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMBinput1 window = new DMBinput1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DMBinput1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\uB85C\uADF8\uC778");
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
		
		JButton btn1 = new JButton("Login");
		btn1.setBounds(164, 478, 191, 82);
		panel.add(btn1);
		
		JButton btn2 = new JButton("Join");
		btn2.setBounds(483, 478, 224, 82);
		panel.add(btn2);
		btn2.addActionListener(new ActionListener() {
			
			

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				DMBinput2 nw = new DMBinput2();
				nw.NewScreen();
				
				
				btn1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
					isLoginCheck();
		
					
				}
				
				});
			}

			public void isLoginCheck() {
			DMBinput2 id = new DMBinput2();
			DMBinput2 pw = new DMBinput2();
			String 아이디 = DMBinput1.this.id;
			String 비밀번호 = DMBinput1.this.pw;
			
				if(this.equals(아이디)&&this.equals(비밀번호))
					JOptionPane.showMessageDialog(null, "Success");	
					
				else
					JOptionPane.showMessageDialog(null, "Failed");
				
			}
			
		});
		
		}	
	
	}


