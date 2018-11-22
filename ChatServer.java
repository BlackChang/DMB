package Project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ChatServer 
{
    private static final int PORT = 2015;//포트넘버설정  

    private static HashSet<String> names = new HashSet<String>();//클라이언트에서 입력하는 이름들을 해시셋에저장하여관리한다  

    public static ArrayList<String> list = new ArrayList<String>();
    
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();//데이터를 클라이언트에 전송할준비단계이다. 

    public static void main(String[] args) throws Exception
    {
        System.out.println("The chat server is running.");//서버시작알림  
        ServerSocket listener = new ServerSocket(PORT);
        try 
        {
            while (true)
            {
                new Handler(listener.accept()).start();
            }
        }
        finally 
        {
            listener.close();
        }
    }

    private static class Handler extends Thread//쓰레드를이용한다  
    {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        
        public Handler(Socket socket)//네트워크통신을위한 소켓생성  
        {
            this.socket = socket;
        }

		public void run() //스레드가작업할내용  
        {
            try 
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//클라이언트로부터받는내용 
                out = new PrintWriter(socket.getOutputStream(), true);//클라이언트로내용을전송 

                while (true) 
                {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null)
                    {
                        return;
                    }
                    synchronized (names)//이구간을실행할때 다른 스레드가 방해하지 못하도록 방지 스레드 동기화 
                    {
                        if (!names.contains(name)) //입력된값이있을시 방에 들어갔다는 내용을보여줌  
                        {
                            names.add(name);
                            list.add(name);
                            out.println("LIST " + "+ " + name);
                    		writers.add(out);
                            out.println("NAMEACCEPTED");
                            writers.add(out);
                            for (PrintWriter writer : writers) 
                            		writer.println("MESSAGE " + name + " JOIN ROOM !");  
                            break;
                        }
                    }
                }

                out.println("NAMEACCEPTED");
                writers.add(out);
                while (true) //output
                {
                    String input = in.readLine();
                    if (input == null)
                    {
                        return;
                    }
                    else
                    {
                    		for (PrintWriter writer : writers) // 모든 클라이언트에게 전송한다  
                        {
                    			if(input.startsWith("WHISPER")) // 채팅의 시작부분이 WHISPER일시 WHISPER함수를 이용하게만든다 
                    				writer.println(input + " " + name);
                    			else//아닐시 일반적인채팅기능  
                    				writer.println("MESSAGE " + name + ": " + input);
                        }
                    }
                }
            } 
            catch (IOException e)
            {
                System.out.println(e);
            }
            finally
            {
            		for (PrintWriter writer : writers) // 사용자가 나가서 스레드가 끝났음을 모든 클라이언트에게 제공 
                {
            			list.remove(name);
            			out.println("LIST " + "- " + name);
            			writers.add(out);
            			writer.println("MESSAGE " + name + " EXIT ROOM...");
                }
                if (name != null)
                {
                    names.remove(name);
                }
                if (out != null) 
                {
                    writers.remove(out);
                }
                try
                {
                    socket.close();
                } 
                catch (IOException e)
                {
                }
            }
        }
    }
}
