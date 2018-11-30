package Network_DMB;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

public class TimetableClient {
    static TimetableClient client;
    static String[] prof_list;
    BufferedReader in;
    BufferedReader list;
    static PrintWriter out;
    JFrame frame = new JFrame("8 Time in life");    
    private String id;
	private String pw;
	private String name;
	private String prof;
	private String day;
	String status="";
	private String origin;
	static ArrayList<String> chatList = new ArrayList<String>();//chat member name 
	public static String w="";
	private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //���� IP�ּ� �ޱ� ���� frame ����     
    private void run() throws IOException {
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //�����κ��� �о���� input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //������ ������ ������ output stream
        list = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DMB cli = new DMB();
        cli.initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cli.initWindow.setVisible(true);
        
        DMB_INFO info = new DMB_INFO();
       	info.infoWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       	
       	//	option.optionWindow.setVisible(true);
        while(true) {
            status = in.readLine();
        	System.out.println(status);
            if(status.startsWith("Duplicate ID")) {
				JOptionPane.showMessageDialog(null, "이미 있는 ID입니다.", "ID Duplication",
						JOptionPane.WARNING_MESSAGE);

            }
            else if(status.startsWith("COMPLETE")) {
            	JOptionPane.showMessageDialog(null, "ID가 생성되었습니다", "COMPLETE",
						JOptionPane.INFORMATION_MESSAGE);	
            	cli.signUP.dispose();
            	cli.initWindow.setVisible(true);
            }
            else if(status.startsWith("SIGNIN")) {
             	cli.initWindow.dispose();
               	info.infoWindow.setVisible(true);
            }
            else if(status.startsWith("NOTEXIST")) {
               	JOptionPane.showMessageDialog(null, "ID가 존재하지 않습니다.", "ID NOT EXISTS",
        						JOptionPane.WARNING_MESSAGE);	
            }
            else if(status.startsWith("WRONGPW")) {
               	JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "WRONG PASSWORD",
        						JOptionPane.WARNING_MESSAGE);	
            }
            else if(status.startsWith("EXISTTABLE")) {
            	JOptionPane.showMessageDialog(null, "뭐였지", "TIMETABLE",
						JOptionPane.INFORMATION_MESSAGE);
            }
            else if(status.startsWith("NEWTABLE")) {
            	DMB_OPTION option = new DMB_OPTION();
               	option.optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                option.optionWindow.setVisible(true);
            }
            else if(status.startsWith("PROFLIST")) {
                int i = 0;
                int num = 0;
                String receive = in.readLine();                
                num = Integer.parseInt(receive);
                
                prof_list = new String[num + 1];
                while(i < num + 1) {
                	prof_list[i] = in.readLine();
                	i++;
                }                    	
            }
            else if (status.startsWith("MESSAGE")) // 서버에서 "MESSAGE" 라고 시작되는 문장을받으면 MESSAGE부분을 지우고 채팅내용만을 볼수 있게된다.
            {
            		DMB_OPTION.text.append(status.substring(8) + "\n");
            }
            else if (status.startsWith("WHISPER")) //// 서버에서 "WHISPER" 라고 시작되는 문장을받으면 WHISPER 함수를 사용한다. 
            {
            		WHISPER(status);
            }
            else if (status.startsWith("LIST"))
            {
            		chatList(status);
            }
        }
     
        /*
        if(ȸ������ actionListener){
        	out.println("SIGNUP");
        	out.println(ID);
        	out.println(PW);
        }
        if(�α��� actionListener){
        	out.println("SIGNIN");
        	out.println(ID);z
        	out.println(PW);
        	getInfo();
        	out.println(ID);
        	out.println(name);
        	out.println(grade);
        	out.println(semester);
        }
        if(�ɼ� actionListener){
        	out.println("OPTION");
        	out.println(professor);
        	out.println(����);
        }
        */	
    }

    public void signIn(String newID, String newPW) {
    	out.println("SIGNIN");
    	out.println(newID);
    	out.println(newPW);
    }
    
    public void signUp(String newID, String newPW, String newName) {
    	out.println("SIGNUP");
    	out.println(newID);
    	out.println(newPW);
    	out.println(newName);
    }
    public void getInfo(String newGrade, String newSemester) {
    	out.println("INFO");
    	out.println(newGrade);
    	out.println(newSemester);
    }
    public void getOption(String professor, String day) {
    	out.println("OPTION");
    	out.println(professor);
    	out.println(day);
    }
    public void prof_list(String professor) {
    	out.println("GETPROF");
    }
    //����� ����(�й�, �̸�, �г�, �б�) �Է� �ޱ� ���� frame ����    

    private void chatList(String line) 
    {
    		line = line.substring(5);
    		if (line.startsWith("+"))
    		{
    			line = line.substring(2);
    			chatList.clear();
    			DMB_OPTION.model.clear();
    			String[] s = line.split(",");
    			for(int x=0; x < s.length;x++)
    			{
    				chatList.add(s[x]);
    				DMB_OPTION.model.addElement(chatList.get(x));
    			}
    		}
    		else if(line.startsWith("-"))
    		{
    			line = line.substring(2);
    			DMB_OPTION.model.clear();
    			chatList.remove(line);
    			for(int x=0; x < chatList.size();x++)
    			{
    				DMB_OPTION.model.addElement(chatList.get(x));
    			}
    		}
    }
    private void WHISPER(String line) 
    {
        String[] temp = line.split(" ");
		String x = null;
    		if((temp[1].equalsIgnoreCase(origin) || temp[temp.length-1].equalsIgnoreCase(origin))&& temp[0].equals("WHISPER"))
        {
        		x = temp[2];
        		for(int k=3;k < temp.length-1;k++)
        			x = x + " " + temp[k];
        		DMB_OPTION.text.append("<Whisper message from " + temp[temp.length-1] + " to " + w + " : " + x + ">\n");//구분지은뒤 채팅을 클라이언트에 보여준다.
        		w = "";// 위스퍼가완료되면 초기화시킨다.   
        }
    }
    public static void main(String[] args) throws Exception {
    	client  = new TimetableClient();
        client.run();
    }
}