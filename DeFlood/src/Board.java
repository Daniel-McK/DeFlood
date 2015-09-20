import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Board {
	private int[][] board;
	private ArrayList<Integer> moves;
	public int lastMove;
	private int sum;
	public Board(String input) throws IOException{
		BufferedImage pic = ImageIO.read(new File(input));
		/*if (pic.getWidth() != 768 || pic.getHeight() != 1280){
			JOptionPane.showMessageDialog(null, "The file must be 768px by 1280px");
			System.exit(1);
		}*/
		System.out.println("" + pic.getRGB(0, 0));
		board = new int[12][12];
		moves = new ArrayList<Integer>();
		sum = 0;
		for(int y = 0; y < 12; y++){
			for(int x = 0; x <= 11; x++){
				int val = pic.getRGB(x*56+90,y*56+340);
				if (val == -8479458)
					board[x][y] = 0;//green
				else if (val == -1216351)
					board[x][y] = 1;//pink
				else if (val == -12144158)
					board[x][y] = 2;//blue(light)
				else if (val == -2340320)
					board[x][y] = 3;//red
				else if (val == -788963)
					board[x][y] = 4;//yellow
				else if (val == -10462040)
					board[x][y] = 5;//navy
				else
					board[x][y] = val;
			}//x
		}//y
		init();
	}
	
	private void init(){
		lastMove = board[0][0];
		board[0][0] = -1;
		sum ++;
		int prevSum=-1;
		while(sum != prevSum){
			prevSum=sum;
			for (int y = 0; y < 12; y++){
				for (int x = 0; x < 12; x++){
					if (board[x][y] == lastMove &&( board[x][Math.max(0, y-1)] == -1 // UP
											|| board[x][Math.min(11, y+1)] == -1 // DOWN
											|| board[Math.max(0,x-1)][y] == -1   //LEFT
											|| board[Math.min(11,x+1)][y] == -1)){//RIGHT
						sum++;
						board[x][y] = -1;
					}
				}
			}
		}
	}
	
	public ArrayList<Integer> demMoves(){
		return moves;
	}
	
	public Board(int[][] current, ArrayList<Integer> seq, int lMove, int newSum){
		board = current;
		moves = seq;
		sum = newSum;
		lastMove = lMove;
	}
	
	public Board makeMove(int move){
		int[][] tempBoard = new int[12][12];
		for(int i = 0; i < 12; i++)
			tempBoard[i] = board[i].clone();
		@SuppressWarnings("unchecked")
		ArrayList<Integer> tempMoves = (ArrayList<Integer>) moves.clone(); 
		tempMoves.add(move);
		
		int prevSum=-1;
		int newSum = sum;
		while(newSum != prevSum){
			prevSum=newSum;
			for (int y = 0; y < 12; y++){
				for (int x = 0; x < 12; x++){
					if (tempBoard[x][y] == move &&( tempBoard[x][Math.max(0, y-1)] == -1 // UP
											|| tempBoard[x][Math.min(11, y+1)] == -1 // DOWN
											|| tempBoard[Math.max(0,x-1)][y] == -1   //LEFT
											|| tempBoard[Math.min(11,x+1)][y] == -1)){//RIGHT
						newSum++;
						tempBoard[x][y] = -1;
					}
				}
			}
		}		
		return new Board(tempBoard, tempMoves, move, newSum);
		
	}
	
	public boolean isDone(){
		if (sum == 144)
			return true;
		else 
			return false;
	}
	
	public ArrayList<Integer> getMoves (){
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		for (int y = 0; y < 12; y++){
			for (int x = 0; x < 12; x++){
				if ( board[x][Math.max(0, y-1)] == -1 // UP
						|| board[x][Math.min(11, y+1)] == -1 // DOWN
						|| board[Math.max(0,x-1)][y] == -1   //LEFT
						|| board[Math.min(11,x+1)][y] == -1){//RIGHT
					if (!possibilities.contains(board[x][y]) && board[x][y] != -1)
						possibilities.add(board[x][y]);
				}
			}
		}

		return possibilities;
		
	}
	
	public int numMoves(){
		return moves.size();
	}
	
	public String toString(){
		String out = numMoves() + "-" + sum +" [";
		for (int i = 0; i < moves.size(); i++){
			out+=(String.format("%s-", colorOf(moves.get(i))));
		}
		out+= "]";
		return out;
		
	}

	public String colorOf(int num){
		if (num == 0)
			return "green";//green
		else if (num == 1)
			return "pink";//pink
		else if (num == 2)
			return "light";//blue(light)
		else if (num == 3)
			return "red";//red
		else if (num == 4)
			return "yellow";//yellow
		else if (num == 5)
			return "navy";
		else
			return "?";
	}
	
	public int checked(){
		return sum;

	}
	
	public Board dq (){
		for (int i = 7; i < 40;i++){
			moves.add(i);
		}
		return this;
	}
	public int[][] getArray (){
		return board;
	}
	public int coloursRemaining(){
		int[] remaining = new int[6];
		for (@SuppressWarnings("unused") int col: remaining)
			col = 0;
		for (int y = 0; y < 12; y++){
			for (int x = 0; x < 12; x++){
				if (board[x][y] >= 0 && board[x][y] < 6)
					remaining[board[x][y]]++;
			}
		}
		int sum = 0;
		for (int col: remaining)
			if (col != 0)
				sum++;
		return sum;
	}
	
}
