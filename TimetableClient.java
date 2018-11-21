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
        
        while(true) {
            status = in.readLine();
        	System.out.println(status);
            if(status.startsWith("Duplicate ID")) {
				JOptionPane.showMessageDialog(null, "Entered ID already Signed up.", "ID Duplication",
						JOptionPane.WARNING_MESSAGE);

            }
            else if(status.startsWith("COMPLETE")) {
            	JOptionPane.showMessageDialog(null, "ID is created successfully.", "COMPLETE",
						JOptionPane.INFORMATION_MESSAGE);
            	
            	cli.signUP.dispose();
            	cli.initWindow.setVisible(true);
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
    
    public void signUp(String newID, String newPW) {
    	out.println("SIGNUP");
    	out.println(newID);
    	out.println(newPW);;
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