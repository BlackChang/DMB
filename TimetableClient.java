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
    BufferedReader in;
    static PrintWriter out;
    JFrame frame = new JFrame("8 Time in life");    
    private String id;
	private String pw;
	private String name;
	private String prof;
	private String day;
	String status="";
	static ArrayList<String> chatList = new ArrayList<String>();//chat member name 
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

        DMB cli = new DMB();
        cli.initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cli.initWindow.setVisible(true);
        
        DMB_INFO info = new DMB_INFO();
       	info.infoWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       	
       	DMB_OPTION option = new DMB_OPTION();
       	option.optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            	option.optionWindow.setVisible(true);
            }
            else if (status.startsWith("MESSAGE")) // 서버에서 "MESSAGE" 라고 시작되는 문장을받으면 MESSAGE부분을 지우고 채팅내용만을 볼수 있게된다.
            {
            		DMB.text.append(status.substring(8) + "\n");
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
    public void option(String professor, String day) {
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
    			DMB.model.clear();
    			String[] s = line.split(",");
    			for(int x=0; x < s.length;x++)
    			{
    				chatList.add(s[x]);
    				DMB.model.addElement(chatList.get(x));
    			}
    		}
    		else if(line.startsWith("-"))
    		{
    			line = line.substring(2);
    			DMB.model.clear();
    			chatList.remove(line);
    			for(int x=0; x < chatList.size();x++)
    			{
    				DMB.model.addElement(chatList.get(x));
    			}
    		}
    }
    public static void main(String[] args) throws Exception {
    	client  = new TimetableClient();
        client.run();
    }
}
