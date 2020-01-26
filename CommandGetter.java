import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommandGetter implements ActionListener{
	
	JButton firewall_e, firewall_d, ip, ping, wifi_d, wifi_e, getIp;
	JFrame fr, ipdialog;
	public void setup(){
		fr = new JFrame("Convenient Manager");
		fr.setVisible(true);
		fr.setSize(400,400);
		GridLayout gl = new GridLayout(4,2);
		fr.setLayout(gl);
		firewall_e = new JButton("Firewall Enable");
		firewall_d = new JButton("Firewall Disable");
		ip = new JButton("IP Address");
		ping = new JButton("Ping to IP");
		wifi_e = new JButton("Wi-Fi Enable");
		wifi_d = new JButton("Wi-Fi Disable");
		getIp = new JButton("Get IP from Hostname");
	}
	
	public void addToFrame(){
		fr.add(firewall_e);
		fr.add(firewall_d);
		fr.add(ip);
		fr.add(ping);
		fr.add(wifi_e);
		fr.add(wifi_d);
		fr.add(getIp);
	}
	
	public void addListeners(){
		firewall_e.addActionListener(this);
		firewall_d.addActionListener(this);
		ip.addActionListener(this);
		ping.addActionListener(this);
		wifi_e.addActionListener(this);
		wifi_d.addActionListener(this);
		getIp.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		RuntimeExec.StreamWrapper output;
		if(e.getSource()==firewall_e){
			try{
				Process p = Runtime.getRuntime().exec("netsh advfirewall set domainprofile state on");
				RuntimeExec rtexe = new RuntimeExec(p);
				JOptionPane.showMessageDialog(ipdialog,"Firewall Enabled...", "Alert", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==firewall_d){
			try{
				Process p = Runtime.getRuntime().exec("netsh advfirewall set domainprofile state off");
				RuntimeExec rtexe = new RuntimeExec(p);
				JOptionPane.showMessageDialog(ipdialog,"Firewall Disabled...","Alert",JOptionPane.WARNING_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==ip){
			try{
				Process p = Runtime.getRuntime().exec("ipconfig");
				RuntimeExec rtexe = new RuntimeExec(p);
				output = rtexe.getStreamWrapper(p.getInputStream(), "OUTPUT");
				output.printOp();
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
				String ipAddr = extractIp(output.message);
				JOptionPane.showMessageDialog(ipdialog,"Your IP : " + ipAddr,"Alert",JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==ping){
			try{
				String pingTo = JOptionPane.showInputDialog(ipdialog,"Enter IP/hostname to which you want to ping"); 
				Process p = Runtime.getRuntime().exec("ping " + pingTo);
				RuntimeExec rtexe = new RuntimeExec(p);
				output = rtexe.getStreamWrapper(p.getInputStream(), "OUTPUT");
				output.printOp();
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
				JOptionPane.showMessageDialog(ipdialog,"Ping Result :\n" + output.message.substring(output.message.indexOf("Ping statistics")),"Alert",JOptionPane.WARNING_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==wifi_e){
			try{
				Process p = Runtime.getRuntime().exec("netsh interface set interface Wi-Fi 2 enable");
				RuntimeExec rtexe = new RuntimeExec(p);
				JOptionPane.showMessageDialog(ipdialog,"Wi-Fi Enabled...","Alert",JOptionPane.WARNING_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==wifi_d){
			try{
				Process p = Runtime.getRuntime().exec("netsh interface set interface Wi-Fi 2 disable");
				RuntimeExec rtexe = new RuntimeExec(p);
				JOptionPane.showMessageDialog(ipdialog,"Wi-Fi Disabled...","Alert",JOptionPane.WARNING_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
		if(e.getSource()==getIp){
			try{
				String pingTo = JOptionPane.showInputDialog(ipdialog,"Enter hostname for which you want to get IP address"); 
				Process p = Runtime.getRuntime().exec("ping " + pingTo);
				RuntimeExec rtexe = new RuntimeExec(p);
				output = rtexe.getStreamWrapper(p.getInputStream(), "OUTPUT");
				output.printOp();
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
				String ipAddr = extractIp(output.message);
				String msg = "Hostname : " + pingTo + "\nIP Address : " + ipAddr;
				JOptionPane.showMessageDialog(ipdialog, msg,"Alert",JOptionPane.WARNING_MESSAGE);
			}
			catch(Exception ee){
				System.out.println(ee);
			}
		}
	}
	
	public String extractIp(String ipString){
		String IPADDRESS_PATTERN = 
        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ipString);
		if (matcher.find()) {
			return matcher.group();
		} else{
			return "0.0.0.0";
		}
	}
	
	public static void main(String args[]){
		CommandGetter cgObj = new CommandGetter();
		cgObj.setup();
		cgObj.addToFrame();
		cgObj.addListeners();
	}
}