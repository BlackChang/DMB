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
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("8 Time in life");    
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //서버 IP주소 받기 위한 frame 생성 
    public void signIn(String newID, String newPW) {
    	out.println("SIGNIN");
    	out.println(newID);
    	out.println(newPW);
    }
    
    public void signUp(String newID, String newPW) {
    	out.println("SIGNUP");
    	out.println(newID);
    	out.println(newPW);
    }
    public void option(String professor, String day) {
    	out.println("OPTION");
    	out.println(professor);
    	out.println(day);
    }
    //사용자 정보(학번, 이름, 학년, 학기) 입력 받기 위한 frame 생성    

    private void run() throws IOException {
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //서버로부터 읽어오는 input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //서버로 데이터 보내는 output stream

        DMB client = new DMB();
        client.initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.initWindow.setVisible(true);
        
        
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

    public static void main(String[] args) throws Exception {
        TimetableClient client = new TimetableClient();
        client.run();
    }
}