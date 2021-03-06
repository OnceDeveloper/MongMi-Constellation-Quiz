import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class QuizPage extends JFrame{

	JButton passB;//1.Start獄動
	//JLabel lReady, lExit, lBingo,background, dangsu, binggo_pan; //1.傾巨 2.蟹亜奄 3.桜壱 獄動虞婚
	JLabel background; //1.壕井 鳶確
	JLabel timerL,quizL,hintL;
	JPanel cp, pMom;
	JTextField answerTf;
	Font font = new Font("壕含税肯膳 爽焼", Font.PLAIN, 40);
	
	
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
	
	class MyPanel extends JPanel{//gif 獣亀
		Image oB = null;
		
		MyPanel(){
			oB = Toolkit.getDefaultToolkit().createImage("img/test/cha.gif");
			//oB = this.getImage(getDocumentBase(),"img/test/辰繊.gif");
		    
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
	}//gif獣亀 適掘什 魁
	void init(){
		//ActionListener listener = new MainUser(this);
//亜舌 固焼掘 薗鍵 鳶確(珍砺戚格) 竺舛//珍砺戚格稽 幻級走 省精 戚政澗 舌檎穿発聖 是背?
		cp = new JPanel();//ct
		cp.setBorder(null);
		setContentPane(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS)); //図楕->神献楕 授辞企稽 壕帖
		cp.setOpaque(true); //燈誤馬惟
//笛鳶確級 薗焼却 鳶確 竺舛
		pMom = new JPanel();			
		pMom.setOpaque(true);		
		cp.add(pMom);
		pMom.setLayout(null);//詔車欠闘
//壕井鉢檎 竺舛
		background = new JLabel(new ImageIcon("img/background/Quiz.png"));
		background.setBounds(0, 0, 350, 550); //獄動 是帖(0.0)貢 滴奄(350,550)竺舛
		background.setOpaque(true);//災燈誤竺舛
		pMom.add(background);
//壕井鉢檎拭 獄動,虞婚 隔製
		timerL = new JLabel("",JLabel.CENTER);
		timerL.setFont(font);
		timerL.setBounds(10, 15, 150, 45); //獄動 是帖(0.0)貢 滴奄(350,550)竺舛
		timerL.setOpaque(false);//災燈誤竺舛
		background.add(timerL);
		TimerModule tm = new TimerModule(this);//獣娃段 竺舛
		tm.start();

		quizL = new JLabel("段失 蟹身しししししし",JLabel.CENTER);
		quizL.setFont(font);
		quizL.setBounds(45, 85, 260, 50); //獄動 是帖(0.0)貢 滴奄(350,550)竺舛
		quizL.setOpaque(false);//災燈誤竺舛
		background.add(quizL);

		hintL = new JLabel("微闘 蟹身ししししししししししししししし",JLabel.CENTER);
		Font font = new Font("壕含税肯膳 爽焼", Font.PLAIN, 20);
		hintL.setFont(font);
		hintL.setBounds(35,  480, 270, 50); //獄動 是帖(0.0)貢 滴奄(350,550)竺舛
		hintL.setOpaque(false);//災燈誤竺舛
		background.add(hintL);
//ct亜 脊径拝 岩Field
		answerTf = new JTextField();		
		
		answerTf.setBorder(BorderFactory.createEmptyBorder());
		answerTf.setFont(font);
		answerTf.setOpaque(false);
		answerTf.setBounds(40, 355, 280	,70);
		background.add(answerTf);

		passB = new JButton(new ImageIcon("img/button/PassB.png"));
		passB.setPressedIcon(new ImageIcon("img/button/PassBC.png"));
		
		passB.setBorderPainted(false);//須飲識
        passB.setFocusPainted(false);
        passB.setContentAreaFilled(false);//獄動 壕井 走酔奄?
		passB.setBounds(170,-10,200,100);//獄動 是帖(170,-10)貢 滴奄(200,100) 竺舛
		background.add(passB);

		setUI();
	}

	void setUI(){
		setTitle("功耕 段失訂綜");
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

class TimerModule extends Thread{//訂綜 獣娃戚 魁蟹檎 MyScore凪戚走稽 角嬢姶

	QuizPage qp;
	//int time = 150;
	int time = 20;
	TimerModule(QuizPage qp){
		this.qp = qp;	
	}
	public void run(){
		//System.out.println(time+"段 疑照 訂綜亜 遭楳桔艦陥.");
		
		while(time>0){
			qp.timerL.setText(Integer.toString(time));
			time--;
			try{
				Thread.sleep(1000);//1段梢生稽 竺舛!
			}catch(Exception e){}
		}
		qp.setVisible(false);
        qp.dispose();
		qp.temporary1400();
       // new MyScore().init();
	}
}