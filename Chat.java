
import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Chat extends JFrame {
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
					frame.setVisible(true);
					frame.setSize(550,550);
					frame.getContentPane().setBackground(new Color(220,110,70));
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Chat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 43, 336, 383);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTextPane txtRoomChat = new JTextPane();
		txtRoomChat.setEditable(false);
		txtRoomChat.setBounds(0, 0, 336, 334);
		panel.add(txtRoomChat);
		
		TextField txtRoomChatWrite = new TextField();
		txtRoomChatWrite.setBounds(10, 340, 241, 33);
		panel.add(txtRoomChatWrite);
		
		JButton btnRoomSend = new JButton("전송");
		btnRoomSend.setBounds(259, 344, 65, 29);
		panel.add(btnRoomSend);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(348, 0, 166, 426);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(35, 7, 96, 33);
		textField_1.setText("접속멤버");
		textField_1.setBackground(new Color(220,110,70));
		textField_1.setForeground(new Color(255,255,255));
		textField_1.setFont(new Font("배달의 민족", Font.BOLD, 20));
		textField_1.setHorizontalAlignment(JTextField.CENTER);
		textField_1.setEditable(false);
		
		panel_1.add(textField_1);
		
		JList listRoomMember = new JList();
		listRoomMember.setBounds(12, 47, 142, 369);
		panel_1.add(listRoomMember);
		
		
		JTextField textField = new JTextField();
		textField.setFont(new Font("배달의 민족", Font.BOLD, 20));
		textField.setText("채팅방");
		textField.setBackground(new Color(220,110,70));
		textField.setForeground(new Color(255,255,255));
		textField.setBounds(109, 5, 101, 33);
		textField.setEditable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);
	
		contentPane.add(textField);
		
	}
}
