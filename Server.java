package ChatApplication;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;

import java.net.*;

import java.io.*;

public class Server extends Frame implements KeyListener, WindowListener {
    ServerSocket ss;
    Socket s;
    BufferedReader br; //read  data
    PrintWriter out;   //write data
    private Label l;
    private TextArea ta;
    private TextField tf;
    Font font = new Font("Roboto", Font.PLAIN, 20);
//constructor...
    public Server() {
        try {
            ss = new ServerSocket(7777);
            System.out.println(" Server is ready to accept connection");
            System.out.println(" Server is waiting");
            s = ss.accept();

            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());

            startReading();
            createGui();
            // startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGui() {
        this.setTitle("Chat Application ");
        this.setSize(600, 600);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        l = new Label("Server Area", Label.CENTER);
        l.setFont(font);
        add(l, BorderLayout.NORTH);

        ta = new TextArea(50, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
        ta.setFont(font);
        ta.setEditable(false);

        add(ta, BorderLayout.CENTER);

        tf = new TextField(50);
        tf.setFont(font);
        add(tf, BorderLayout.SOUTH);

        tf.addKeyListener(this);
        this.addWindowListener(this);

    }

    public void startReading() {
        // lamda expression
      
        Runnable r1 = () -> {          // this is for reading the data
            System.out.println("Reader Started");
            try {
                while (true) {      
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Cleint Stopped");
                        s.close();
                        break;
                    }
                    ta.append("Client: " + msg + "  \n");
                }

            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connecton is closed");
            }
        };
        new Thread(r1).start(); //object of thread r1
    }

    public void startWriting() {
         // this will use data from the user and  then it will send it to client
        Runnable r2 = () -> {
            System.out.println("Writer started");
            try {
                while (!s.isClosed()) {

                    BufferedReader kyb = new BufferedReader(new InputStreamReader(System.in));
                    String content = kyb.readLine();

                    if (content.equals("exit")) {
                        s.close();
                        break;
                    }
                    ta.append("Client: " + content + "  \n");

                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connecton is closed");

            }
        };
        new Thread(r2).start();    //object of thred r2
    }

    public static void main(String[] args) {
        // System.out.println("This si server... going to start");
        new Server();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == 10) { // 10 is for enter key
            ta.append("You: " + tf.getText() + "\n");
            out.println(tf.getText());
            out.flush();
            tf.setText(" ");
        }

    }
}