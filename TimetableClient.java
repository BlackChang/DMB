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
    String name = "";
    public TimetableClient() {
        // Add Listeners
    }
    
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //서버 IP주소 받기 위한 frame 생성 
    private String signIn() {
    	return "";
    }
    
    private String signUp() {
    	return "";
    }
    private String getInfo() {
    	return "";
    }
    //사용자 정보(학번, 이름, 학년, 학기) 입력 받기 위한 frame 생성    

    private void run() throws IOException {
        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        //서버 IP 주소 저장

        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //서버로부터 읽어오는 input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //서버로 데이터 보내는 output stream
        while (true) {
            String status = in.readLine();
            //서버로부터 데이터 읽어와 line 변수에 저장
            if (status.startsWith("SIGNIN")) {
                out.println(signIn()); 
                //서버로부터 읽어온 데이터가 SUBMITNAME일 때 getName함수 이용에 사용자 이름 입력 받음
            } 
            else if (status.startsWith("INFOACCEPTED")) {
            	//선호 교수님, 공강 날짜 및 옵션 선택하는 UI창 띄우기
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TimetableClient client = new TimetableClient();
        //새로운 클라이언트 생성
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //클라이언트 창 닫으면 프로그램 종료
        client.frame.setVisible(true);
        //클라이언트 창 가시화
        client.run();
        //클라이언트 창 실행
    }
}