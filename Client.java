package ChatApplication;

import java.net.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class Client extends Frame implements KeyListener, WindowListener {
    Socket s;
    BufferedReader br;
    PrintWriter out;
    private Label l;
    private TextArea ta;
    private TextField tf;
    Font font = new Font("Roboto", Font.PLAIN, 20);

    public Client() {
        try {
            System.out.println("Sending request to server");
            s = new Socket("localhost", 7777);
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
            startReading();
            // startWriting();
            createGui();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void createGui() {
        this.setTitle("Chat Application ");
        this.setSize(600, 600);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        l = new Label("Client Area", Label.CENTER);
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
        Runnable r1 = () -> {
            System.out.println("Reader Started");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server Stopped");
                        s.close();
                        break;
                    }
                    ta.append("Server: " + msg + "  \n");
                }
            } catch (Exception e) {
                System.out.println("Connecton is closed");
                // e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started");
            try {
                while (!s.isClosed()) {

                    BufferedReader kyb = new BufferedReader(new InputStreamReader(System.in));
                    String content = kyb.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        s.close();
                        break;
                    }

                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connecton is closed");

            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();//calling constructor
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            ta.append("You: " + tf.getText() + "\n");
            out.println(tf.getText());
            out.flush();
            tf.setText(" ");
        }

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

}
