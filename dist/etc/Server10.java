import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.net.*;
import java.util.*;

class Server10 extends JFrame implements ActionListener, Runnable
{
	//=============================<UI>============================
	JPanel cp, bcp, tp, bt;
	JTextArea jt;
	JScrollPane js;
	JLabel jl, jn;
	JButton bOn, bOff;
	Font font = new Font("배달의민족 주아", Font.PLAIN, 23);

	//=============================<기능>============================
	ServerSocket ss;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
    
	String ipClient;
	String id;
	int score;
    int port = 7000;

	Vector<String> ctList = new Vector<String>();
	//누적 접속자 저장하는 vector
	Hashtable<Integer, String> rankHT = new Hashtable<Integer, String>();                                                                              
	//점수랑 이름 저장하는 hashtable
	TreeSet<Integer> keys = new TreeSet<Integer>();
	//key저장하는 treeset



	void init(){

		defaultIn(); // rankHT 에 저장할 가상 유저의 디폴트 값(score, id)을 초기화함.

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
		jl.setBounds(0, 0, 350, 550); //버튼 위치(0.0)및 크기(350,550)설정
		//jl.setOpaque(true);//불투명설정
		bcp.add(jl);

		jn = new JLabel("MongMi Server",JLabel.CENTER);
		jn.setFont(font);
		jn.setBounds(83, 19, 192, 62); //버튼 위치(0.0)및 크기(350,550)설정
		jn.setOpaque(false);//불투명설정
		jl.add(jn);

		tp = new JPanel();
		tp.setLayout(null);
		tp.setBounds(25, 118, 300, 288);
		
		jt = new JTextArea();
		jt.setEditable(true);
		jt.setBounds(0, 0, 300, 288);
		jt.setBorder(new LineBorder(Color.DARK_GRAY));
		jt.setLineWrap(true); // 텍스트 영역 줄바꿈
		jt.setEditable(false); //편집 가능 여부
		jt.setLayout(null);
		
		//jl.add(js);
		
		js = new JScrollPane(jt);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setBounds(0, 0, 300, 288);
		//js.setLayout(null);
		js.setViewportView(jt); // 스크롤팬 위에 jt올린다.
		jl.add(tp);
		tp.add(js);
		

		bOn = new JButton(new ImageIcon("img/button/OnB.png"));
		bOn.setPressedIcon(new ImageIcon("img/button/OnBC.png"));
		bOn.setOpaque(true);
		bOn.setBounds(43, 432, 116, 117);
		bOn.setBorder(null);
		bOn.setFocusPainted(false);
		bOn.setBorderPainted(false);
		bOn.setContentAreaFilled(false);
		jl.add(bOn);
		bOn.addActionListener(this);

		bOff = new JButton(new ImageIcon("img/button/OffB.png"));
		bOff.setPressedIcon(new ImageIcon("img/button/OffBC.png"));
		bOff.setOpaque(true);
		bOff.setBounds(200, 430, 116, 117);
		bOff.setBorder(null);
		bOff.setFocusPainted(false);
		bOff.setBorderPainted(false);
		bOff.setContentAreaFilled(false);
		jl.add(bOff);
		bOff.addActionListener(this);

		setUI();

	}

	void setUI(){
		setTitle("몽미 초성퀴즈");
		setSize(360, 585);
		setVisible(true);
		setLocation(200,100);
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
				}catch(IOException io){}
			}

			int answer = JOptionPane.showConfirmDialog(
					null, 
					"정말 종료하시겠나요?(?_?)", 
					"Q&A", 
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon("img/char/Char7.png"));
			if(answer == JOptionPane.YES_OPTION){
						System.exit(0);
			}else{
				JOptionPane.showMessageDialog(null, "알겠어요.(^-^)");
			}		
		
		}else if(obj == bOn){
			bOn.setEnabled(false);
			bOff.setEnabled(true);
			new Thread(this).start();
			jt.append(" [ 서버가 시작되었습니다. ]" + "\n");
			
		}else{}
	}

//=============================<기능>============================

	public void run(){
		try{
			ss = new ServerSocket(port); // 서버소켓 연결
			while(true){ // 클라이언트 접속 받는 부분
				s = ss.accept();
				connect();
				ipClient = s.getInetAddress().getHostAddress();
				ctList.add(ipClient);
				jt.append("클라이언트(IP:"+ipClient+") 접속 성공!!" + "[누적 접속자 수 :"+ ctList.size() +"명 ]" + "\n");
				getUsers();
				makeRanking();
			}
		}catch(IOException ie){
		}finally{
			try{	
				closeAll();
			}catch(IOException ie){}
		}
	}

	
	
	public void connect(){
		try{
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());

		}catch(IOException ie){
				return;
		}
	}

	void makeRanking(){
		keyOut();
		valOut();
	}
	

	void defaultIn(){
		rankHT.put(1, "중삼이");
		rankHT.put(18, "당수");
		rankHT.put(17, "수스");
	}
	public void getUsers(){
	
		try{
			id = dis.readUTF();
			jt.append("접속한 사용자의 아이디는 " + id + "\n");
			score = dis.readByte();
			System.out.println(scoreStr);
			score = Integer.parseInt(scoreStr);
			rankHT.put(score, id);
		}catch(IOException ie){
			return;
		}
	}

	void keyOut(){ // key 출력
		try{
			Enumeration<Integer> e = rankHT.keys();
			while(e.hasMoreElements()){
				Integer key = e.nextElement();
				System.out.println("Key 명: "+ key);
				keys.add(key);
			}
		}catch(NoSuchElementException nse){
			System.out.println(nse);
		}
	}

	void valOut(){ //내림차순 출력 (ver 2)
		Iterator<Integer> iter = keys.descendingIterator();
		try{
			while(iter.hasNext()){
				int key = iter.next();
				String val = rankHT.get(key);
				System.out.println("key: " + key  + ", value: " + val);
				dos.writeUTF(val);
				dos.writeByte(key);
			}	
		}catch(IOException ie){}
	}

	public void closeAll(){
		try{
			if(dos != null) dos.close();
			if(dis != null) dis.close();
			if(s != null) s.close();
		}catch(IOException ie){}
	}

	
	public static void main(String[] args)
	{
		Server10 s1 = new Server10();
		s1.init();
	}

}




	
	
