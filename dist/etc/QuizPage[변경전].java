import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class QuizPage extends JFrame{

	JButton passB;//1.Start버튼
	//JLabel lReady, lExit, lBingo,background, dangsu, binggo_pan; //1.레디 2.나가기 3.빙고 버튼라벨
	JLabel background; //1.배경 패널
	JLabel timerL,quizL,hintL;
	JPanel cp, pMom;
	JTextField answerTf;
	Font font = new Font("배달의민족 주아", Font.PLAIN, 40);
	
	
	MainPage mp;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	String id;

	QuizPage(MainPage mp,Socket s){
		this.mp = mp;
		this.s = s;
		this.dis = mp.dis;
		this.dos = mp.dos;
		this.id = mp.id;
	}
	
	class MyPanel extends JPanel{//gif 시도
		Image oB = null;
		
		MyPanel(){
			oB = Toolkit.getDefaultToolkit().createImage("img/test/cha.gif");
			//oB = this.getImage(getDocumentBase(),"img/test/채점.gif");
		    
		}
		public void paintComponent(Graphics g){  
		    super.paintComponent(g);  
		    if (oB != null) {  
		      g.drawImage(oB, 0, 0, this);  
		    }
		  }
		  
		 /*
		 public void paint(Graphics g) {  
		    g.drawImage(this.oB, 50, 50, 100, 100, this);
		  }
		 */ 
	}//gif시도 클래스 끝
	void init(){
		//ActionListener listener = new MainUser(this);
//가장 맨아래 깔린 패널(컨테이너) 설정//컨테이너로 만들지 않은 이유는 장면전환을 위해?
		cp = new JPanel();//ct
		cp.setBorder(null);
		setContentPane(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS)); //왼쪽->오른쪽 순서대로 배치
		cp.setOpaque(true); //투명하게
//큰패널들 깔아둘 패널 설정
		pMom = new JPanel();			
		pMom.setOpaque(true);		
		cp.add(pMom);
		pMom.setLayout(null);//앱솔루트
//배경화면 설정
		background = new JLabel(new ImageIcon("img/background/Quiz.png"));
		background.setBounds(0, 0, 350, 550); //버튼 위치(0.0)및 크기(350,550)설정
		background.setOpaque(true);//불투명설정
		pMom.add(background);
//배경화면에 버튼,라벨 넣음
		timerL = new JLabel("",JLabel.CENTER);
		timerL.setFont(font);
		timerL.setBounds(10, 15, 150, 45); //버튼 위치(0.0)및 크기(350,550)설정
		timerL.setOpaque(false);//불투명설정
		background.add(timerL);
		TimerModule tm = new TimerModule(this);//시간초 설정
		tm.start();

		quizL = new JLabel("초성 나옴ㅇㅇㅇㅇㅇㅇ",JLabel.CENTER);
		quizL.setFont(font);
		quizL.setBounds(45, 85, 260, 50); //버튼 위치(0.0)및 크기(350,550)설정
		quizL.setOpaque(false);//불투명설정
		background.add(quizL);

		hintL = new JLabel("힌트 나옴ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",JLabel.CENTER);
		Font font = new Font("배달의민족 주아", Font.PLAIN, 20);
		hintL.setFont(font);
		hintL.setBounds(35,  480, 270, 50); //버튼 위치(0.0)및 크기(350,550)설정
		hintL.setOpaque(false);//불투명설정
		background.add(hintL);
//ct가 입력할 답Field
		answerTf = new JTextField();		
		
		answerTf.setBorder(BorderFactory.createEmptyBorder());
		answerTf.setFont(font);
		answerTf.setOpaque(false);
		answerTf.setBounds(40, 355, 280	,70);
		background.add(answerTf);

		passB = new JButton(new ImageIcon("img/button/PassB.png"));
		passB.setPressedIcon(new ImageIcon("img/button/PassBC.png"));
		
		passB.setBorderPainted(false);//외곽선
        passB.setFocusPainted(false);
        passB.setContentAreaFilled(false);//버튼 배경 지우기?
		passB.setBounds(170,-10,200,100);//버튼 위치(170,-10)및 크기(200,100) 설정
		background.add(passB);

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

	void temporary1400(){
		new MyScore(this, s).init();
	}

	public static String extString(String input)
	{
		input = input.trim();
		if(input.indexOf(" ") != -1)
			return input.substring(0, input.indexOf(" "));
		return input;
	}	

}

class TimerModule extends Thread{//퀴즈 시간이 끝나면 MyScore페이지로 넘어감

	QuizPage qp;
	//int time = 150;
	int time = 20;
	TimerModule(QuizPage qp){
		this.qp = qp;	
	}
	public void run(){
		//System.out.println(time+"초 동안 퀴즈가 진행됩니다.");
		
		while(time>0){
			qp.timerL.setText(Integer.toString(time));
			time--;
			try{
				Thread.sleep(1000);//1초씩으로 설정!
			}catch(Exception e){}
		}
		qp.setVisible(false);
        qp.dispose();
		qp.temporary1400();
       // new MyScore().init();
	}
}