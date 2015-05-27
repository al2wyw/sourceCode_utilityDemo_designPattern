/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.*;
import java.io.*;
import java.util.Calendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Administrator
 */
public class Threadpoolclient extends JFrame {

    private int host;
    private String hostname;
    private String name;
    private JLabel hname;
    private JLabel hnum;
    private JLabel uname;
    private JTextField hnamecon;
    private JTextField hnumcon;
    private JTextField unamecon;
    private JButton ok;
    private JTextArea textdialog;
    private JScrollPane jsdialog;
    private JTextArea text;
    private JScrollPane js;
    private JButton send;
    private Socket s;
    private ObjectOutputStream dout;
    private ObjectInputStream din;

    public Threadpoolclient() {
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(dout == null)
                    Threadpoolclient.this.dispose();
                else{
                try {
                            dout.writeObject("EXIT");
                            dout.writeObject(Calendar.getInstance());
                            dout.flush();
                            
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(Threadpoolclient.this, "Thread IO errors occured!");
                            Threadpoolclient.this.dispose();
                            System.out.println("IOErrors");
                        }
                }
            }
        });
        JPanel north = new JPanel();
        uname = new JLabel("Your name: ", JLabel.LEFT);
        uname.setFont(new Font("Arial", Font.PLAIN, 18));
        hname = new JLabel("Host name: ", JLabel.LEFT);
        hname.setFont(new Font("Arial", Font.PLAIN, 18));
        hnum = new JLabel("Port name: ", JLabel.LEFT);
        hnum.setFont(new Font("Arial", Font.PLAIN, 18));
        unamecon = new JTextField(10);
        unamecon.setFont(new Font("Arial", Font.PLAIN, 18));
        hnamecon = new JTextField(10);
        hnamecon.setFont(new Font("Arial", Font.PLAIN, 18));
        hnumcon = new JTextField(10);
        hnumcon.setFont(new Font("Arial", Font.PLAIN, 18));
        ok = new JButton("Connect");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ok.setEnabled(false);
                hostname = hnamecon.getText();
                host = Integer.parseInt(hnumcon.getText());
                name = unamecon.getText();
                
                JPanel center = new JPanel(new GridLayout(2, 1, 100, 100));

                textdialog = new JTextArea(10, 20);
                textdialog.setEditable(false);
                textdialog.setLineWrap(true);
                textdialog.setFont(new Font("Serif", Font.PLAIN, 18));
                jsdialog = new JScrollPane();
                jsdialog.setViewportView(textdialog);
                jsdialog.setAutoscrolls(true);

                text = new JTextArea(10, 30);
                text.setFont(new Font("Serif", Font.PLAIN, 18));
                js = new JScrollPane();
                js.setViewportView(text);
                send = new JButton("send");
                send.setHorizontalAlignment(JButton.RIGHT);
                send.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        String t = text.getText();

                        try {
                            dout.writeObject(t);
                            dout.writeObject(Calendar.getInstance());
                            dout.flush();
                            text.setText("");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(Threadpoolclient.this, "Thread IO errors occured!");
                            Threadpoolclient.this.dispose();
                            System.out.println("IOErrors");
                        }
                    }
                });

                center.add(jsdialog);
                JPanel center1 = new JPanel();
                center1.add(js);
                center1.add(send);
                center.add(center1);

                Threadpoolclient.this.add(center);

                try {
                    System.out.println(hostname);
                    System.out.println(host);
                    s = new Socket(InetAddress.getByName(hostname), host);

                    System.out.println("Connection is establishing!");
                    dout = new ObjectOutputStream(s.getOutputStream());
                    din = new ObjectInputStream(s.getInputStream());
                    
                    dout.writeObject(name);

                    new Thread() {

                        public void run() {
                            try {
                                boolean flag = true;
                                String n = "";
                                while (flag) {
                                    n = (String)din.readObject();
                                    String get = (String)din.readObject();
                                    Calendar c = (Calendar) din.readObject();
                                    
                                    textdialog.append(c.getTime().toString() + "\n");
                                    textdialog.append(n+" said : \n");
                                    textdialog.append(get);
                                    textdialog.append("\n\n");
                                    textdialog.setCaretPosition(textdialog.getText().length()); //!!!!!!!!!!!!!!!!!!!!!!!!
                                    
                                    if (get.equals("EXIT")) {
                                        System.out.println("sdfsdfsdfdsfdsf");
                                        flag = false;
                                        Threadpoolclient.this.dispose();
                                    }
                                }
                                
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(Threadpoolclient.this, "Thread IO errors occured!");
                                Threadpoolclient.this.dispose();
                                System.out.println("Thread IOErrors");
                            } catch (ClassNotFoundException c) {
                                System.out.println("classErrors");
                            }
                        }
                    }.start();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Threadpoolclient.this, "Can not connect to the server!");
                    Threadpoolclient.this.dispose();
                    System.out.println("Initial IOErrors");
                }

                Threadpoolclient.this.validate();
            }
        });
        north.add(uname);
        north.add(unamecon);
        north.add(hname);
        north.add(hnamecon);
        north.add(hnum);
        north.add(hnumcon);
        north.add(ok);
        north.setPreferredSize(new Dimension(700, 100));
        this.add(north, "North");

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                Threadpoolclient f = new Threadpoolclient();
                f.setVisible(true);
            }
        });
    }
}
