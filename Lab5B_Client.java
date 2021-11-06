// By: Caesar Watts-Hall
// Date: March 12, 2019
// Java II
// Lab Assignment #5B
package lab5b_client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Lab5B_Client extends Frame implements ActionListener, Runnable {

    //My Declarations
    Button crwhB;
    TextField crwhTF;
    TextArea crwhTA;
    Socket crwhS;
    PrintWriter crwhPW;
    BufferedReader crwhBR;
    Thread crwhT;

    public Lab5B_Client() {
        Frame crwhF = new Frame("C.R.W.H. Client: "); //Frame for Server 
        crwhF.setLayout(new FlowLayout());
        crwhF.setBackground(Color.black);

        crwhB = new Button("Send");
        crwhB.setBackground(Color.green);
        crwhB.addActionListener(this);
        crwhF.addWindowListener(new W1());
        crwhTF = new TextField(25);
        crwhTA = new TextArea(20,30);
        crwhTA.setBackground(Color.white);

        crwhF.add(crwhTF);//Add TextField to the frame
        crwhF.add(crwhB);//Add send Button to the frame
        crwhF.add(crwhTA);//Add TextArea to the frame
        
        try {
            crwhS = new Socket(InetAddress.getLocalHost(), 50713);//Socket for server
            //below line reads input from InputStreamReader
            crwhBR = new BufferedReader(new InputStreamReader(crwhS.getInputStream()));
            //below line writes output to OutPutStream
            crwhPW = new PrintWriter(crwhS.getOutputStream(), true);
        
        } catch(Exception e) { 
        }
        
        crwhT = new Thread(this);//start a new thread
        crwhT.setDaemon(true);//set the thread as demon
        crwhT.start();
        
        setFont(new Font("Times New Roman", Font.BOLD,12));
        crwhF.setSize(400,400);//set the size
        crwhF.setVisible(true);
        crwhF.setLocation(300,500);//set the location
        crwhF.validate();
    }
    //method required to close the Frame on clicking "X" icon.

    private class W1 extends WindowAdapter {

        public void windowClosing(WindowEvent we) {
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
                crwhTA.append(crwhBR.readLine() + "\n");//Append to TextArea
            } catch (Exception e) {
            }
        }
    }
    //Main method

    public static void main(String args[]) {
        //Instantiate AppServer class
        Lab5B_Client crwhMyClient = new Lab5B_Client();
    }
}