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
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    JButton whisper = new JButton("Whisper");
    String name = "";
    public TimetableClient() {

        // Layout GUI
        frame.getContentPane().add(textField, "North");
        //text field를 frame에 추가
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        //text area를 frame에 추가
        frame.getContentPane().add(whisper,"South");
        //귓속말 기능 위한 whisper 버튼을 frame에 추가
        frame.pack();
       
        // Add Listeners
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(name + textField.getText());
                textField.setText("");
                name = "";
            }
            //text field에 메세지 내용 입력 받아 서버에 전달
            //name이 ""인 이유는 귓속말 여부에 상관 없이 다음 채팅에 전체 채팅으로 전환 위함
        });        
    }
    
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
    //서버 IP주소 받기 위한 frame 생성
    
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
            String line = in.readLine();
            //서버로부터 데이터 읽어와 line 변수에 저장
            String id = "";
            String name="";
            if (line.startsWith("SUBMITINFO")) {
                out.println(getInfo()); 
                //서버로부터 읽어온 데이터가 SUBMITNAME일 때 getName함수 이용에 사용자 이름 입력 받음
            } else if (line.startsWith("INFOACCEPTED")) {
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