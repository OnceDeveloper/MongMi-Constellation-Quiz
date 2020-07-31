import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class QuizPage extends JFrame{

	JButton passB;//1.Start��ư
	//JLabel lReady, lExit, lBingo,background, dangsu, binggo_pan; //1.���� 2.������ 3.���� ��ư��
	JLabel background; //1.��� �г�
	JLabel timerL,quizL,hintL;
	JPanel cp, pMom;
	JTextField answerTf;
	Font font = new Font("����ǹ��� �־�", Font.PLAIN, 40);
	
	
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
	
	class MyPanel extends JPanel{//gif �õ�
		Image oB = null;
		
		MyPanel(){
			oB = Toolkit.getDefaultToolkit().createImage("img/test/cha.gif");
			//oB = this.getImage(getDocumentBase(),"img/test/ä��.gif");
		    
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
	}//gif�õ� Ŭ���� ��
	void init(){
		//ActionListener listener = new MainUser(this);
//���� �ǾƷ� �� �г�(�����̳�) ����//�����̳ʷ� ������ ���� ������ �����ȯ�� ����?
		cp = new JPanel();//ct
		cp.setBorder(null);
		setContentPane(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS)); //����->������ ������� ��ġ
		cp.setOpaque(true); //�����ϰ�
//ū�гε� ��Ƶ� �г� ����
		pMom = new JPanel();			
		pMom.setOpaque(true);		
		cp.add(pMom);
		pMom.setLayout(null);//�ۼַ�Ʈ
//���ȭ�� ����
		background = new JLabel(new ImageIcon("img/background/Quiz.png"));
		background.setBounds(0, 0, 350, 550); //��ư ��ġ(0.0)�� ũ��(350,550)����
		background.setOpaque(true);//��������
		pMom.add(background);
//���ȭ�鿡 ��ư,�� ����
		timerL = new JLabel("",JLabel.CENTER);
		timerL.setFont(font);
		timerL.setBounds(10, 15, 150, 45); //��ư ��ġ(0.0)�� ũ��(350,550)����
		timerL.setOpaque(false);//��������
		background.add(timerL);
		TimerModule tm = new TimerModule(this);//�ð��� ����
		tm.start();

		quizL = new JLabel("�ʼ� ���Ȥ�����������",JLabel.CENTER);
		quizL.setFont(font);
		quizL.setBounds(45, 85, 260, 50); //��ư ��ġ(0.0)�� ũ��(350,550)����
		quizL.setOpaque(false);//��������
		background.add(quizL);

		hintL = new JLabel("��Ʈ ���Ȥ�����������������������������",JLabel.CENTER);
		Font font = new Font("����ǹ��� �־�", Font.PLAIN, 20);
		hintL.setFont(font);
		hintL.setBounds(35,  480, 270, 50); //��ư ��ġ(0.0)�� ũ��(350,550)����
		hintL.setOpaque(false);//��������
		background.add(hintL);
//ct�� �Է��� ��Field
		answerTf = new JTextField();		
		
		answerTf.setBorder(BorderFactory.createEmptyBorder());
		answerTf.setFont(font);
		answerTf.setOpaque(false);
		answerTf.setBounds(40, 355, 280	,70);
		background.add(answerTf);

		passB = new JButton(new ImageIcon("img/button/PassB.png"));
		passB.setPressedIcon(new ImageIcon("img/button/PassBC.png"));
		
		passB.setBorderPainted(false);//�ܰ���
        passB.setFocusPainted(false);
        passB.setContentAreaFilled(false);//��ư ��� �����?
		passB.setBounds(170,-10,200,100);//��ư ��ġ(170,-10)�� ũ��(200,100) ����
		background.add(passB);

		setUI();
	}

	void setUI(){
		setTitle("���� �ʼ�����");
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

class TimerModule extends Thread{//���� �ð��� ������ MyScore�������� �Ѿ

	QuizPage qp;
	//int time = 150;
	int time = 20;
	TimerModule(QuizPage qp){
		this.qp = qp;	
	}
	public void run(){
		//System.out.println(time+"�� ���� ��� ����˴ϴ�.");
		
		while(time>0){
			qp.timerL.setText(Integer.toString(time));
			time--;
			try{
				Thread.sleep(1000);//1�ʾ����� ����!
			}catch(Exception e){}
		}
		qp.setVisible(false);
        qp.dispose();
		qp.temporary1400();
       // new MyScore().init();
	}
}