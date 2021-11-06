// By: Caesar Watts-Hall
// Date: March 12, 2019
// Java II
// Lab Assignment #5A
package lab5a_server;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Lab5A_Server extends Frame implements ActionListener, Runnable {

    //My Declarations
    Button          crwhButton;
    TextField       crwhTF;
    TextArea        crwhTA;
    ServerSocket    crwhSS;
    Socket          crwhSocket;
    PrintWriter     crwhPW;
    BufferedReader  crwhBR;
    Thread          crwhThreads;

    public Lab5A_Server() 
    {
        Frame crwhF = new Frame("C.R.W.H. Server: "); //Frame for Server 
        crwhF.setLayout(new FlowLayout());
        crwhF.setBackground(Color.black);
        crwhButton = new Button("Send");
        crwhButton.setBackground(Color.green);
        crwhButton.addActionListener(this);
        crwhTF = new TextField(25);
        crwhTA = new TextArea(20,30);
        crwhTA.setBackground(Color.white);
        crwhF.addWindowListener(new W1());
        crwhF.add(crwhTF);//Add TextField to the frame
        crwhF.add(crwhButton);//Add send Button to the frame
        crwhF.add(crwhTA);//Add TextArea to the frame
        
        try {
            crwhSS = new ServerSocket(50713);//Socket for server
            crwhSocket = crwhSS.accept();//accepts request from client
            System.out.println(crwhSocket);
            //below line reads input from InputStreamReader
            crwhBR = new BufferedReader(new InputStreamReader(crwhSocket.getInputStream()));
            //below line writes output to OutPutStream
            crwhPW = new PrintWriter(crwhSocket.getOutputStream(), true);
        
        } catch(Exception e) { 
        }
        
        crwhThreads = new Thread(this); //start a new thread
        crwhThreads.setDaemon(true); //set the thread as demon
        crwhThreads.start();
        
        setFont(new Font("Times New Roman", Font.BOLD,12));
        crwhF.setSize(400,400);//set the size
        crwhF.setLocation(500,500);//set the location
        crwhF.setVisible(true);
        crwhF.validate();
    }
    //method required to close the Frame on clicking "X" icon.
    private class W1 extends WindowAdapter 
    {
        public void windowClosing(WindowEvent we) 
        {
            System.exit(0);
        }
    }
    //This method will called after clicking on Send button.

    public void actionPerformed(ActionEvent ae) {
        crwhPW.println(crwhTF.getText()); //write the value of textfield into PrintWriter
        crwhTF.setText(""); //clean the textfield
    }
    //Thread running as a process in background

    public void run() {
        while (true) {
            try {
                String crwhString = crwhBR.readLine();//reads the input from textfield
                crwhTA.append(crwhString + "\n");//Append to TextArea
            } catch (Exception e) {
            }
        }
    }
    //Main method

    public static void main(String args[]) {
        Lab5A_Server crwhMyServer = new Lab5A_Server();
    }
}