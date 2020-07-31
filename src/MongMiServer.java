package mongmi.server;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.net.*;
import java.util.*;

class MongMiServer extends JFrame implements ActionListener, Runnable
{
	//=============================<UI>============================
	JPanel cp, bcp, tp, bt;
	JTextArea jt;
	JScrollPane js;
	JLabel jl, jn;
	JButton bOn, bOff;
	Font font = new Font("����ǹ��� �־�", Font.PLAIN, 23);

	//=============================<���>============================
	ServerSocket ss;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
    
	String ipClient;
	String id;
	int score;
    int port = 7000;

	Vector<String> ctList = new Vector<String>();
	//���� ������ �����ϴ� vector
	Hashtable<Integer, String> rankHT = new Hashtable<Integer, String>();                                                                              
	//������ �̸� �����ϴ� hashtable
	TreeSet<Integer> keys = new TreeSet<Integer>();
	//key�����ϴ� treeset


//=============================<UI>============================
	
	void init(){

		defaultIn(); // rankHT �� ������ ���� ������ ����Ʈ ��(score, id)�� �ʱ�ȭ��.

		cp = new JPanel();
		cp.setBorder(null);
		setContentPane(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		cp.setOpaque(true);
		
		bcp = new JPanel();
		bcp.setOpaque(true);
		cp.add(bcp);
		bcp.setLayout(null);

		jl = new JLabel(new ImageIcon("img/background/Server.png"));
		jl.setBounds(0, 0, 350, 550); //��ư ��ġ(0.0)�� ũ��(350,550)����
		//jl.setOpaque(true);//����������
		bcp.add(jl);

		jn = new JLabel("MongMi Server",JLabel.CENTER);
		jn.setFont(font);
		jn.setBounds(83, 19, 192, 62); //��ư ��ġ(0.0)�� ũ��(350,550)����
		jn.setOpaque(false);//����������
		jl.add(jn);

		tp = new JPanel();
		tp.setLayout(null);
		tp.setBounds(25, 118, 300, 288);
		
		jt = new JTextArea();
		jt.setEditable(true);
		jt.setBounds(0, 0, 300, 288);
		jt.setBorder(new LineBorder(Color.DARK_GRAY));
		jt.setLineWrap(true); // �ؽ�Ʈ ���� �ٹٲ�
		jt.setEditable(false); //���� ���� ����
		jt.setLayout(null);
		
		//jl.add(js);
		
		js = new JScrollPane(jt);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setBounds(0, 0, 300, 288);
		//js.setLayout(null);
		js.setViewportView(jt); // ��ũ���� ���� jt�ø���.
		jl.add(tp);
		tp.add(js);
		

		bOn = new JButton(new ImageIcon("img/button/OnB.png"));
		bOn.setPressedIcon(new ImageIcon("img/button/OnBC.png"));
		bOn.setOpaque(true);
		bOn.setBounds(43, 420, 116, 117);
		bOn.setBorder(null);
		bOn.setFocusPainted(false);
		bOn.setBorderPainted(false);
		bOn.setContentAreaFilled(false);
		jl.add(bOn);
		bOn.addActionListener(this);

		bOff = new JButton(new ImageIcon("img/button/OffB.png"));
		bOff.setPressedIcon(new ImageIcon("img/button/OffBC.png"));
		bOff.setOpaque(true);
		bOff.setBounds(200, 420, 116, 117);
		bOff.setBorder(null);
		bOff.setFocusPainted(false);
		bOff.setBorderPainted(false);
		bOff.setContentAreaFilled(false);
		jl.add(bOff);
		bOff.addActionListener(this);

		setUI();

	}

	void setUI(){
		setTitle("���� �ʼ�����");
		setIconImage(new ImageIcon("img/icon/ICon.png").getImage());
		setSize(360, 585);
		setVisible(true);
		setLocation(200,150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		 Object obj = e.getSource();
		if(obj == bOff){ 
			if(ss!= null){ 
				try{
					ss.close();
				}catch(IOException io){
					io.printStackTrace();
				}
			}

			int answer = JOptionPane.showConfirmDialog(
					null, 
					"���� �����Ͻðڳ���?(?_?)", 
					"Exit", 
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon("img/char/Char7.png"));
			
			if(answer == JOptionPane.YES_OPTION){
				System.exit(0);
			}else{
				JOptionPane.showMessageDialog(null, "�˰ھ��.(^-^)");
			}		
		
		}else if(obj == bOn){
			bOn.setEnabled(false);
			bOff.setEnabled(true);
			new Thread(this).start();
			jt.append(" [ ������ ���۵Ǿ����ϴ�. ]" + "\n");
		}else{}
	}

//=============================<���>============================

	public void run(){
		try{
			ss = new ServerSocket(port); // �������� ����
			while(true){ // Ŭ���̾�Ʈ ���� �޴� �κ�
				s = ss.accept();
				connect();
				GUser gu = new GUser();
				gu.start();
			}
		}catch(IOException ie){
			ie.printStackTrace();
		}finally{	
			closeAll();
		}
	}
	
	void connect(){
		try{
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());

		}catch(IOException ie){
				ie.printStackTrace();
		}
	}

	void defaultIn(){
		rankHT.put(1, "�߻���");
		rankHT.put(2, "���");
		rankHT.put(15, "����");
	}

	void makeRanking(){
		keyOut();
		valOut();
	}
	
	void keyOut(){ // key ��� �� ����
		try{
			Enumeration<Integer> e = rankHT.keys();
			while(e.hasMoreElements()){
				Integer key = e.nextElement();
				//System.out.println("Key ��: "+ key);
				keys.add(key);
			}
		}catch(NoSuchElementException nse){
			nse.printStackTrace();
		}
	}

	void valOut(){ //�������� ���
		Iterator<Integer> iter = keys.descendingIterator();
		try{
			while(iter.hasNext()){
				int key = iter.next();
				String val = rankHT.get(key);
				//System.out.println("key: " + key  + ", value: " + val);
				dos.writeUTF(val);
				dos.writeByte(key);
			}	
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}

	public void closeAll(){
		try{
			if(dos != null) dos.close();
			if(dis != null) dis.close();
			if(s != null) s.close();
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}

//////////////////////////����Ŭ���� ����///////////////////////////////////////////
	class GUser extends Thread{
		public void run(){
			try{
				ipClient = s.getInetAddress().getHostAddress();
				ctList.add(ipClient);
				id = dis.readUTF();
				jt.append(id+"(IP:"+ipClient+") ���� ����!!" + "[������ �� :"+ ctList.size() +"�� ]" + "\n");
				//jt.append("������ ������� ���̵�� " + id + "\n");
				score = dis.readByte();
				rankHT.put(score, id);
				boolean serverOn = dis.readBoolean();
				makeRanking();
				boolean serverOn2 = dis.readBoolean();
			}catch(IOException ie){
				ctList.remove(ipClient);
				jt.append(id+"(IP:"+ipClient+") ����!!" + "[������ �� :"+ ctList.size() +"�� ]" + "\n");
			}
		}
	}
//////////////////////////����Ŭ���� ��///////////////////////////////////////////	
	
	public static void main(String[] args)
	{
		new MongMiServer().init();
	}

}




	
	