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
    //���� IP�ּ� �ޱ� ���� frame ���� 
    private String signIn() {
    	return "";
    }
    
    private String signUp() {
    	return "";
    }
    private String getInfo() {
    	return "";
    }
    //����� ����(�й�, �̸�, �г�, �б�) �Է� �ޱ� ���� frame ����    

    private void run() throws IOException {
        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        //���� IP �ּ� ����

        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //�����κ��� �о���� input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //������ ������ ������ output stream
        while (true) {
            String status = in.readLine();
            //�����κ��� ������ �о�� line ������ ����
            if (status.startsWith("SIGNIN")) {
                out.println(signIn()); 
                //�����κ��� �о�� �����Ͱ� SUBMITNAME�� �� getName�Լ� �̿뿡 ����� �̸� �Է� ����
            } 
            else if (status.startsWith("INFOACCEPTED")) {
            	//��ȣ ������, ���� ��¥ �� �ɼ� �����ϴ� UIâ ����
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TimetableClient client = new TimetableClient();
        //���ο� Ŭ���̾�Ʈ ����
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Ŭ���̾�Ʈ â ������ ���α׷� ����
        client.frame.setVisible(true);
        //Ŭ���̾�Ʈ â ����ȭ
        client.run();
        //Ŭ���̾�Ʈ â ����
    }
}