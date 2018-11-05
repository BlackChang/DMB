package Socket2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient
{
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    JButton bt = new JButton("Whisper");
    public static String w="";//위스퍼가유효한지확인 위스퍼를 받을 사용자이름을 임시저장  
    private String origin; //각클라이언트를 구분할 고유한 사용자이름이다.
    public ChatClient() 
    {
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");//채팅내용을입력하는부분  
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");// 채팅내역이 보이는부분 
        frame.getContentPane().add(bt, "South");
        frame.pack();
        
        textField.addActionListener(new ActionListener()// 내용을입력하고 기존에 입력된 내용을 지워 새로 받을 준비한다. 
        {
        		public void actionPerformed(ActionEvent e)
        		{
                if(!w.equals(""))//위스퍼대상을 확인하여 채워져있다면 위스퍼 실행  
                		out.println("WHISPER " + w + " " + textField.getText());
                else
                		out.println(textField.getText());
                textField.setText("");
            }
        });
        bt.addActionListener(new ActionListener() //위스퍼버튼을누르면 실행된다  
        {
    			public void actionPerformed(ActionEvent e)
    			{
    				JFrame fr = new JFrame("Set Whisper");
    				JTextField tf = new JTextField(40);
    		        fr.getContentPane().add(tf, "Center");
    		        tf.setText("Set name");
    		        
    		        tf.addActionListener(new ActionListener()// w에 입력받을 대상을 저장  
    		        	{
                		public void actionPerformed(ActionEvent e)
                    {
                			w = tf.getText();
                			tf.setText("Set name");
                			fr.setVisible(false);
                    }
                });
    		        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		        fr.setVisible(true);
    		        fr.pack();
    			}
        });
    }
    private String getServerAddress() // 아이피주소를 받아 서버에 접속한다.
    {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    private String getName() //이름을입력하여 새로운 클라이언트를 준비한다. 
    {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
    private void run() throws IOException //스레드가 실행되는부분 
    {
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9000);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        while (true) 
        {
            String line = in.readLine();//서버에서받은데이터를 저장한다.  
            if (line.startsWith("SUBMITNAME")) // 서버에서 "SUBMITNAME" 라고 시작되는 문장을받으면 이름을 입력하겠다는 의미가 되므로 이름을 저장시킨다. 
            {
            		origin = getName();
            		out.println(origin);
            }
            else if (line.startsWith("NAMEACCEPTED")) // 서버에서 "NAMEACCEPTEDE" 라고 시작되는 문장을받으면 본격적인 채팅을 할 수 있게된다.
            {
            		textField.setEditable(true);
            }
            else if (line.startsWith("MESSAGE")) // 서버에서 "MESSAGE" 라고 시작되는 문장을받으면 MESSAGE부분을 지우고 채팅내용만을 볼수 있게된다.
            {
            		messageArea.append(line.substring(8) + "\n");
            }
            else if (line.startsWith("WHISPER")) //// 서버에서 "WHISPER" 라고 시작되는 문장을받으면 WHISPER 함수를 사용한다. 
            {
            		WHISPER(line);
            }
        }
    }
 // 입력된 라인을 공백부분별로나누어 새로 저장시킨뒤 첫번째 부분은 WHISPER 문자 두번재부분은 채팅을 받을 대상자 세번쨰 이후부터 고유한 채팅내용을 갖고
 	//마지막부분에 보낸 사람의 이름이 저장되어있어 구분할수있다.
	private void WHISPER(String line) 
    {
        String[] temp = line.split(" ");
		String x = null;
    		if((temp[1].equalsIgnoreCase(origin) || temp[temp.length-1].equalsIgnoreCase(origin))&& temp[0].equals("WHISPER"))
        {
        		x = temp[2];
        		for(int k=3;k < temp.length-1;k++)
        			x = x + " " + temp[k];
        		messageArea.append("<Whisper message from " + temp[temp.length-1] + " to " + w + " : " + x + ">\n");//구분지은뒤 채팅을 클라이언트에 보여준다.
        		w = "";// 위스퍼가완료되면 초기화시킨다.   
        }
    }

    public static void main(String[] args) throws Exception 
    {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setLayout(new FlowLayout());
        client.frame.setVisible(true);
        client.run();
    }
}