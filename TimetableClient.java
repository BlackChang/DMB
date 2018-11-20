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
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //�����κ��� �о���� input stream
        
        out = new PrintWriter(socket.getOutputStream(), true);
        //������ ������ ������ output stream

        DMB client = new DMB();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        
        /*
        if(ȸ������ actionListener){
        	out.println("SIGNUP");
        	out.println(ID);
        	out.println(PW);
        }
        if(�α��� actionListener){
        	out.println("SIGNIN");
        	out.println(ID);
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

    public static void main(String[] args) throws Exception {
        TimetableClient client = new TimetableClient();
        client.run();
    }
}