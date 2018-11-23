package Network_DMB;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

public class TimetableClient {
    static TimetableClient client;
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("8 Time in life");    
    private String id;
	private String pw;
	private String name;
	private String prof;
	private String day;
	String status="";

	private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //서버 IP주소 받기 위한 frame 생성     
    private void run() throws IOException {
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //서버로부터 읽어오는 input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //서버로 데이터 보내는 output stream

        DMB cli = new DMB();
        cli.initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cli.initWindow.setVisible(true);
        
        DMB_INFO info = new DMB_INFO();
       	info.infoWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       	
       	DMB_OPTION option = new DMB_OPTION();
       	option.optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
               	JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.", "WRONG PASSWORD",
        						JOptionPane.WARNING_MESSAGE);	
            }
            else if(status.startsWith("EXISTTABLE")) {
            	JOptionPane.showMessageDialog(null, "여기 있다 테이블", "TIMETABLE",
						JOptionPane.INFORMATION_MESSAGE);
            }
            else if(status.startsWith("NEWTABLE")) {
            	option.optionWindow.setVisible(true);
            }
   
        }
     
        /*
        if(회원가입 actionListener){
        	out.println("SIGNUP");
        	out.println(ID);
        	out.println(PW);
        }
        if(로그인 actionListener){
        	out.println("SIGNIN");
        	out.println(ID);z
        	out.println(PW);
        	getInfo();
        	out.println(ID);
        	out.println(name);
        	out.println(grade);
        	out.println(semester);
        }
        if(옵션 actionListener){
        	out.println("OPTION");
        	out.println(professor);
        	out.println(공강);
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
    //사용자 정보(학번, 이름, 학년, 학기) 입력 받기 위한 frame 생성    

    public static void main(String[] args) throws Exception {
    	client  = new TimetableClient();
        client.run();
    }
}