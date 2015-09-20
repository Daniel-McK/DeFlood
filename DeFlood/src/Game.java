import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Game extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] currentBoard;
	private int lastMove, count;
	private ArrayList<Integer> sol;
	
	
	public Game (int[][] board, int move){
		super();
		sol = null;
		lastMove = move;
		currentBoard = board;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		for(int y = 0; y < 12; y ++){
			for (int x = 0; x < 12; x++){
				int val = currentBoard[x][y];
				if (val==-1)
					val = lastMove;
				if(val==0)
					g.setColor(Color.GREEN);
				else if(val==1)
					g.setColor(Color.PINK);
				else if(val==2)
					g.setColor(new Color(-12144158)); 
				else if(val==3)
					g.setColor(Color.RED);
				else if(val==4)
					g.setColor(Color.YELLOW);
				else if(val==5)
					g.setColor(Color.BLUE);
				g.fillRect(50*x, 50*y, 50, 50);
			}
		}
		if (sol != null){
			for(int i = 0; i < sol.size(); i++){
				int val = sol.get(i);
				if(val==0)
					g.setColor(Color.GREEN);
				else if(val==1)
					g.setColor(Color.PINK);
				else if(val==2)
					g.setColor(new Color(-12144158)); 
				else if(val==3)
					g.setColor(Color.RED);
				else if(val==4)
					g.setColor(Color.YELLOW);
				else if(val==5)
					g.setColor(Color.BLUE);
				g.fillRect(25*i+10, 640, 25, 25);
			}
			g.setColor(Color.WHITE);
			g.fillOval(25*count+20, 625, 5, 5);
			g.drawString("STEPS", 500, 620);
			g.drawString("" + (count+1), 516, 637);

			g.drawString("SOLUTION", 10, 620);
		}
		
	}
	
	public void updateBoard(int[][] newBoard, int move, int index){
		currentBoard = newBoard;
		lastMove = move;
		count=index;
		repaint();	
	}
	
	public void showSol(ArrayList<Integer> solution){
		sol = solution;
		repaint();
	}

}
