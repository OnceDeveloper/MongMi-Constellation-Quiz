import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class MainPage extends JFrame {//

	JButton startB;//1.Start��ư
	JLabel background; //1.��� �г�
	JPanel cp, pMom;
	JTextField idTf;
	Font font = new Font("����ǹ��� �־�", Font.PLAIN, 40);
	
	String id;
	LoginPage lp;
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;

	MainPage(LoginPage lp, Socket s){
		this.lp = lp;
		this.s = s;
		try{
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		}catch(IOException ie){}
	}
	
	void init(){
		
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
//���ȭ�� ����//������Ʈ
		background = new JLabel(new ImageIcon("img/background/Main.png"));
		background.setBounds(0, 0, 350, 550); //��ư ��ġ(0.0)�� ũ��(350,550)����
		background.setOpaque(true);//��������
		pMom.add(background);
//ū �гε� ��ġ
		idTf = new JTextField();		
		
		idTf.setBorder(BorderFactory.createEmptyBorder());//�ܰ���X
		idTf.setFont(font);
		idTf.setOpaque(false);
		idTf.setBounds(110, 367, 200,70);
		background.add(idTf);
		KeyListener keyListener = new MainKeyL(this);
		idTf.addKeyListener(keyListener);

		startB = new JButton(new ImageIcon("img/button/StartB.png"));
		startB.setPressedIcon(new ImageIcon("img/button/StartBC.png"));
		
		startB.setBorderPainted(false);//�ܰ���
        startB.setFocusPainted(false);
        startB.setContentAreaFilled(false);//��ư ��� �����?
		startB.setBounds(-5,440,360,100);//��ư ��ġ(-5,440)�� ũ��(360,100) ����
		background.add(startB);
		ActionListener listener = new MainActL(this);
		startB.addActionListener(listener);//���� ��ư ������ �׼�(QuizPage�� ��ȯ)

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

}

class MainActL implements ActionListener{
	MainPage mp;

	MainActL(MainPage mp){
		this.mp = mp;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		//m.enterB.addKeyListener(m.keyListener);
		
		Object obj = e.getSource();
		if(obj == mp.startB){
			mp.id = mp.idTf.getText();
			if(mp.id.equals("")) mp.id = "GUEST";
		try{
			dos.writeUTF(mp.id);
		}
		catch (IOException ie){
		}
			mp.idTf.setEnabled(false);//id�Է� �� ��Ȱ��ȭ
			mp.setVisible(false);
			mp.dispose();
			new QuizPage(mp, s).init();
		}

	}
}

class MainKeyL implements KeyListener{
	MainPage mp;

	MainKeyL(MainPage mp){
		this.mp = mp;
	}
	@Override
	public void keyTyped(KeyEvent e) { 
	}
	@Override
	public void keyPressed(KeyEvent e) {   
	}
	@Override
	public void keyReleased(KeyEvent e) {//Ű�� ���ȴٰ� ����������

		if (e.getKeyCode()==KeyEvent.VK_ENTER) {//������ Ű���� ������ �ִ� �ڵ� ���� ��Ÿ����
												//VK_ENTER = ��� , ���� Ű�� ���� Ű���� �ǹ��Ѵ�
			mp.id = mp.idTf.getText();
			if(mp.id.equals("")) mp.id = "GUEST";
			try{
				dos.writeUTF(mp.id);
			}catch(IOException ie){}
			mp.idTf.setEnabled(false);//id�Է� �� ��Ȱ��ȭ
			mp.setVisible(false);
			mp.dispose();
			new QuizPage(mp, s).init();
		}
	}
}