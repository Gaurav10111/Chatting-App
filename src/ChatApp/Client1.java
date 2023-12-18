package ChatApp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client1 implements ActionListener, Runnable{

	private static final long serialVersionUID = 1L;
	
	JTextField text;
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();
	
	static JFrame f = new JFrame();
	static DataOutputStream dout;
    BufferedReader reader;
    BufferedWriter writer;
    String name="Babita JI";
    
	public Client1()
	{
      f.setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5,20,25,25);
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/society.png"));
		Image i5 = i4.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40,1,90,90);
		p1.add(profile);
		
		JLabel name = new JLabel("Society Group");
		name.setBounds(120,20,150,18);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF",Font.BOLD, 18));
		p1.add(name);
		
		JLabel member = new JLabel("Me, Jethalal, Mehta Sahab, Iyer");
		member.setBounds(120,45,220,18);
		member.setForeground(Color.WHITE);
		member.setFont(new Font("SAN_SERIF",Font.BOLD, 12));
		p1.add(member);
		
	    text = new JTextField();
		text.setBounds(5,655,310,40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);
		
		JButton send = new JButton("Send");
		send.setBounds(320,655,123,40);
		send.setBackground(new Color(7,94,84));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);
		send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
		f.add(send);
		
		f.setSize(450, 700);
		f.setLocation(490,20);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		f.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 75, 440, 570);
		f.getContentPane().add(scrollPane);
		
		a1 = new JPanel();
		scrollPane.setViewportView(a1);
		a1.setBackground(Color.WHITE);
		f.setVisible(true);
		
		try {
			
			Socket socket = new Socket("localhost",2600);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try {
		String out = "<html><p>" + name + "</p><p>" + text.getText() + "</p></html>";
		
		JPanel p2 = formatLabel(out);
		
		a1.setLayout(new BorderLayout());
		
		JPanel right = new JPanel(new BorderLayout());
		right.add(p2, BorderLayout.LINE_END);
		right.setBackground(Color.WHITE);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		a1.add(vertical, BorderLayout.PAGE_START);
		
		try {
			
			writer.write(out);
			writer.write("\r\n");
			writer.flush();
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		text.setText("");
		
		f.repaint();
		f.invalidate();
		f.validate();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static JPanel formatLabel(String out)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
		output.setFont(new Font("Tahoma",Font.PLAIN, 16));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(0,15,0,15));
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}
	
	public void run()
	{
		try
		{
			String msg="";
			while(true)
			{
				msg = reader.readLine();
				if(msg.contains(name))
				{
					continue;
				}
				
				JPanel panel= formatLabel(msg);
				
				JPanel left = new JPanel(new BorderLayout());
				left.setBackground(Color.WHITE);
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);
			    
				a1.add(vertical, BorderLayout.PAGE_START);
				
				f.repaint();
				f.invalidate();
				f.validate();		
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void main(String arg[])
	{
		Client1 two = new Client1();
		Thread t1 = new Thread(two);
		t1.start();
		
	}
}
