package Network_DMB;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient
{
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JLabel label = new JLabel("Test");
    JButton bt = new JButton("Room #1");
   
    DefaultListModel<String> model = new DefaultListModel<String>();
    JList list;
    
    static ArrayList<String> chatList = new ArrayList<String>();

    public static String w="";//위스퍼가유효한지확인 위스퍼를 받을 사용자이름을 임시저장  
    private String origin; //각클라이언트를 구분할 고유한 사용자이름이다
  //Pop up Chat
    JFrame fr = new JFrame("Room #" );
	JTextField tf = new JTextField(20);
	JTextArea text = new JTextArea(10, 20);
	//Room
    public ChatClient() 
    {
        frame.setLocation(200,400);
        frame.getContentPane().add(bt, "West");
        frame.getContentPane().add(tf, "North");
        frame.pack();
        tf.setText("Enjoy your chatting");
        
        bt.addActionListener(new ActionListener() //위스퍼버튼을누르면 채팅룸이 생성됌.
    	        {
    	    			public void actionPerformed(ActionEvent e)
    	    			{
    	    				list = new JList(model);
    	        			fr.getContentPane().add(list, "East");
    	    				fr.getContentPane().add(tf, "North");
    	    		        fr.getContentPane().add(new JScrollPane(text), "Center");
    	    		        
    	    		        tf.addActionListener(new ActionListener()// 내용을입력하고 기존에 입력된 내용을 지워 새로 받을 준비한다. 
    	    		                {
    	    		                		public void actionPerformed(ActionEvent e)
    	    		                		{
    	    		                        out.println(tf.getText());
    	    		                        tf.setText("");
    	    		                    }
    	    		                });
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
            		tf.setEditable(true);
            }
            else if (line.startsWith("MESSAGE")) // 서버에서 "MESSAGE" 라고 시작되는 문장을받으면 MESSAGE부분을 지우고 채팅내용만을 볼수 있게된다.
            {
            		text.append(line.substring(8) + "\n");
            }
            else if (line.startsWith("LIST"))
            {
            		chatList(line);
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
        		text.append("<Whisper message from " + temp[temp.length-1] + " to " + w + " : " + x + ">\n");//구분지은뒤 채팅을 클라이언트에 보여준다.
        		w = "";// 위스퍼가완료되면 초기화시킨다.   
        }
    }
    private void chatList(String line) 
    {
    		line = line.substring(5);
    		if (line.startsWith("+"))
    		{
    			line = line.substring(2);
    			chatList.clear();
    			model.clear();
    			String[] s = line.split(",");
    			for(int x=0; x < s.length;x++)
    			{
    				chatList.add(s[x]);
    				model.addElement(chatList.get(x));
    			}
    		}
    		else if(line.startsWith("-"))
    		{
    			line = line.substring(2);
    			model.clear();
    			chatList.remove(line);
    			for(int x=0; x < chatList.size();x++)
    			{
    				model.addElement(chatList.get(x));
    			}
    		}
    }

    public static void main(String[] args) throws Exception 
    {
    		ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setLayout(new FlowLayout());
        client.frame.setVisible(true);
        client.run();
        
        ChatClient special = new ChatClient();
        special.fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        special.fr.setLayout(new FlowLayout());
        special.fr.setVisible(true);
        special.run();
    }
}
