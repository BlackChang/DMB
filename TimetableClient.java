package Network_DMB;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import com.mysql.jdbc.Connection;

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
	private int grade;
	private int semester;
	String status="";
	static String origin="";
	static ArrayList<String> chatList = new ArrayList<String>();//chat member name 
	private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //���� IP�ּ� �ޱ� ���� frame ����     
    private void run() throws IOException, SQLException {
    	Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/timedb?useUnicode=true&characterEncoding=utf8";
			String user = "root"; 
			String pw = "12345";
			con = (Connection) DriverManager.getConnection(url, user, pw);
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
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
              	System.out.println(grade + " " + semester);
            	DMB_TABLE table = new DMB_TABLE(id, grade, semester);
            	
               	table.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	table.frame.setVisible(true);
              }
            else if(status.startsWith("NEWTABLE")) {
            	DMB_OPTION option = new DMB_OPTION();
    			PreparedStatement pps;
    			String sql;
    			sql = "insert into created_table values(?,?,?)";
    			pps = con.prepareStatement(sql);
    			pps.setString(1,id);
    			pps.setInt(2, grade);
    			pps.setInt(3, semester);
    			pps.executeUpdate();
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
            else if(status.startsWith("CREATED")) {
            	DMB_TABLE table = new DMB_TABLE(id, grade, semester);
            	
               	table.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	table.frame.setVisible(true);
            }
            else if (status.startsWith("MESSAGE")) // If you receive a sentence beginning with "MESSAGE" on the server, you will be able to delete the MESSAGE section and only see the chat contents.
            		DMB_TABLE.text.append(status.substring(8) + "\n");
            else if (status.startsWith("WHISPER")) // If the server receives a sentence beginning with "WHISPER", use the WHISPER function.
            		WHISPER(status);
            else if (status.startsWith("LIST")) //Check the sentence beginning with "LIST" to save the number of people participating in the chat.
            		chatList(status);
        }
    }

    public void signIn(String newID, String newPW) {
      	this.id = newID;
    	this.pw = newPW;
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
    	this.grade = Integer.parseInt(newGrade);
    	this.semester = Integer.parseInt(newSemester);
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
    public String getID() {
    	return this.id;
    }
    public int getGrade() {
    	return this.grade;
    }
    public int getSemester() {
    	return this.semester;
    }
    private void chatList(String line) {
    		line = line.substring(5);
    		if (line.startsWith("+")) {// To keep a list of people logged in, delete the function LIST, and add the person when the sentence starts with +.
    			line = line.substring(2);
    			
    			chatList.clear(); // Refresh every time new personnel comes in
    			DMB_TABLE.chat_model.clear();
    			String[] s = line.split(",");
    			if(origin.isEmpty())//Add first incoming staff
    				origin = s[s.length-1];
    			
    			for(int x=0; x < s.length;x++) { // Processing when more than two people come in
    				chatList.add(s[x]);
    				DMB_TABLE.chat_model.addElement(chatList.get(x));
    			}
    		}
    		else if(line.startsWith("-")) { // Delete the sentence's LIST and delete the person when the sentence begins.
    			line = line.substring(2);
    			DMB_TABLE.chat_model.clear(); // Refresh every time new personnel comes in
    			chatList.remove(line);
    			for(int x=0; x < chatList.size();x++)
    				DMB_TABLE.chat_model.addElement(chatList.get(x));
    		}
    }
    private void WHISPER(String line) {//The whisper function function sentence starts with WHISPER and then the recipient's name is stored at the end of the sender's name.
        String[] temp = line.split(" ");
		String x = null;
		if((temp[1].equalsIgnoreCase(origin) || temp[temp.length-1].equalsIgnoreCase(origin)) && temp[0].equals("WHISPER")) {
        		x = temp[2];
        		for(int k=3;k < temp.length-1;k++)
        			x = x + " " + temp[k];
        		if(!x.isEmpty())
        			DMB_TABLE.textw.append(temp[temp.length-1] + " : " + x + "\n");
        }
		if((temp[1].equalsIgnoreCase(origin)) && temp[0].equals("WHISPER") && !x.isEmpty()) // Notify the person who is the target of the whisper function
        		DMB_TABLE.text.append("You receive Whisper message from " + temp[temp.length-1] + "\n");//After segmenting, show the chat to the client.
    }
    public static void main(String[] args) throws Exception {
    	client  = new TimetableClient();
        client.run();
    }
}
