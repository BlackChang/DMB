import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DMBinput2 extends JFrame {

	public String id;
	public String pw;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel label;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel label_1;
	private JLabel label_2;
	private JButton btnNewButton;
	private JButton btn;
	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMBinput2 frame = new DMBinput2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DMBinput2() {
		setTitle("\uD68C\uC6D0\uAC00\uC785");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 737);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		lblNewLabel.setBounds(113, 36, 207, 90);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(364, 43, 368, 78);
		contentPane.add(textField); //아이디
		String id = textField.getText();
		textField.setColumns(10);
		
		label = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label.setBounds(113, 157, 207, 90);
		contentPane.add(label);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(364, 164, 368, 78);
		contentPane.add(passwordField); //비번
		String pw = passwordField.getText();
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(364, 299, 368, 78);
		contentPane.add(textField_1); //이름
		String name = textField_1.getText();
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(364, 417, 368, 78);
		contentPane.add(textField_2); //학번
		String sn = textField_2.getText();
		
		label_1 = new JLabel("\uC774\uB984");
		label_1.setBounds(113, 292, 207, 90);
		contentPane.add(label_1);
		
		label_2 = new JLabel("\uD559\uBC88");
		label_2.setBounds(113, 410, 207, 90);
		contentPane.add(label_2);
		
		btnNewButton = new JButton("\uAC00\uC785\uD558\uAE30");
		JFrame frame2 = new JFrame();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
				frame2.setSize(200, 200);
				JLabel label = new JLabel("가입이 완료되었습니다!");
				JPanel panel = new JPanel();
				frame2.add(panel);
			    panel.add(label);
			    btn = new JButton("확인");
			    panel.add(btn);
			    
				btn.addActionListener(new ActionListener(){
			    	
				    public void actionPerformed(ActionEvent e){
				    	frame2.setVisible(false);
				    	DMBinput1.frame.setVisible(true);
				
				    }

						
						

						
						
				});
			    
			    
			    
			}
		});
	

	
			    
			  
		btnNewButton.setBounds(334, 539, 255, 90);
		contentPane.add(btnNewButton);
	}

}
