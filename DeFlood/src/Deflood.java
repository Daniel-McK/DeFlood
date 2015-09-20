import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Deflood{
	static int lowest;
	static int[] halfmax, max;
	static Board best;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame f = new JFrame("Flood-it!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Directory path here
		String path = "."; 
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		int numFiles = 0;
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();
				if (files.endsWith(".png") || files.endsWith(".PNG") || files.endsWith(".jpg"))
				{
					numFiles++;
				}
			}
		}
		if (numFiles == 0){
			JOptionPane.showMessageDialog(f, "Please place the '.png' file in the current directory.");
			System.exit(1);
		}
		String[] choices = new String[numFiles];
		numFiles = 0;
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();
				if (files.endsWith(".png") || files.endsWith(".PNG") || files.endsWith(".jpg"))
				{
					choices[numFiles] = files;
					numFiles++;
		        }
			}
		}
		String input = (String) JOptionPane.showInputDialog(null, "Which image would you like to solve?",
				"File Chooser", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]); 
		System.out.println(input);
		
		// ----------------------------------
		Board temp = new Board(input);
		Game out = new Game(temp.getArray(), temp.lastMove);
		f.add(out);
		f.setSize(615,700);
		f.setVisible(true);
		lowest = 100;
		max = new int[30];
		for(@SuppressWarnings("unused") int curr: max)
			curr = -100;
		
		best = new Board(input).dq();
		bestOption(temp);
		JOptionPane.showMessageDialog(f, "The solution will now be displayed.");
		ArrayList<Integer> sol = best.demMoves();	
		out.showSol(sol);
		for (int i = 0; i < sol.size(); i ++){
			temp = temp.makeMove((int)sol.get(i));
			out.updateBoard(temp.getArray(),temp.lastMove, i);
			Thread.sleep(500);
		}
		System.out.println(best);
	}
	
	private static void bestOption(Board board) throws IOException{
		if ( board.numMoves() + board.coloursRemaining() >= Math.min(22, lowest)){
			return;
		}
		else if (board.isDone()){
			best=board;
			lowest = board.numMoves();
			return;
		}
		else if (board.numMoves()>5 && board.checked() > max[board.numMoves()])
			max[board.numMoves()] = board.checked();	
		else if (board.numMoves()>5 && board.checked()< Math.max(max[board.numMoves()*1/2], max[board.numMoves()]-15))
			return;
		ArrayList<Integer> opt = board.getMoves();
		for(int i = 0; i < opt.size(); i ++){
			bestOption(board.makeMove(opt.get(i)));
		}
		return;
	}
	
}
