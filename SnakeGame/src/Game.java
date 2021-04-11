import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{

	
	
	public boolean left = false,right = false ,up = false ,down = false;
	
	public int score = 0;
	
	public int macaX =0;
	
	public int macaY =0;
	
	public int spd = 10;
	
	public int frameSpd = 10;
	public Node[] nodeSnake = new Node[20];
	
	public Game() {
		this.setPreferredSize(new Dimension(480,480));
		for(int i = 0; i< nodeSnake.length; i++) {
			nodeSnake[i] = new Node(0 , 0); 
			
		}
	
	
		this.addKeyListener(this);
	}
	
	public void tick () {
		for(int i= nodeSnake.length - 1; i>0; i--) { //COMPRIMENTO
			nodeSnake[i].x = nodeSnake[i-1].x; //persegue snake
			nodeSnake[i].y = nodeSnake[i-1].y;
		
		}
		
		if(nodeSnake[0].x + 10 <0) {
			nodeSnake[0].x = 480;
		}else if (nodeSnake[0].x>=480) {
			nodeSnake[0].x = -10;}
		else if(nodeSnake[0].y + 10 <0) {
			nodeSnake[0].y = 480;
		}else if (nodeSnake[0].y>=480) {
			nodeSnake[0].y = -10;}
		
		
		if(right) {
			nodeSnake[0].x+=spd;
			collision();
		}else if (up) {
			nodeSnake[0].y-=spd;
			collision();
		}else if(down) {
			nodeSnake[0].y+=spd;
			collision();
		}else if(left) {
			nodeSnake[0].x-=spd;
			collision();
		}
		//MAÇAS
		if (new Rectangle(nodeSnake[0].x,nodeSnake[0].y,10,10).intersects(new Rectangle(macaX,macaY,10,10))) {
		macaX = new Random().nextInt(480-10);
		macaY = new Random().nextInt(480-10);
		score ++;
		frameSpd++;
		
		System.out.println("Pontos: "+score);
		
		}
	}//fim do tick
	
	
	public void collision () {
		for(int i = 0; i< nodeSnake.length; i++) {
			if(i == 0)continue;
				
				Rectangle box1 = new Rectangle (nodeSnake[0].x, nodeSnake[0].y, 10, 10);
				Rectangle box2 = new Rectangle (nodeSnake[i].x, nodeSnake[i].y, 10, 10);
				
				if(box1.intersects(box2)) {
					System.out.println("Game Over!");
					frameSpd = 20;
					score = 0;
					right = false;
					up = false;
					left = false;
					down = false;
					for(int o = 0; o< nodeSnake.length; o++) {
						nodeSnake[o] = new Node(0 , 0); 
					
				}
			}
		}
	} //fim colisao
	
	public void render() {
		BufferStrategy bs=this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 480, 480);
		
		for(int i=0; i<nodeSnake.length; i++) {
			g.setColor(Color.pink);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10,10);
			
		}
		
		g.setColor(Color.MAGENTA);
		g.fillRect(macaX, macaY, 10, 10);
		g.dispose();
		bs.show();
	}
	
	public static void main (String [] args) {
		Game game = new Game ();
		JFrame frame = new JFrame ("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/frameSpd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

		//refatorar teclas abaixo com array
	@Override //aqui eu estou chamando os botões do teclado, ouseja, quando acionar algum botao que eu instanciei chamara a variavel e acionará o boolean
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false; 
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			left = true;
			up = false;
			down = false;
			right = false;
		}else if(e.getKeyCode()==KeyEvent.VK_UP) {
			up = true;
			down = false;
			right = false;
			left = false;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			down = true;
			right = false;
			left = false;
			up = false;
		}
	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
