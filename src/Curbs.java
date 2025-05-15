import java.util.LinkedList;

public class Curbs {
    // creating of сurbs (створення бордюрі)
    public static void makeCurbs(LinkedList<Integer> leftCurbs, LinkedList<Integer> rightCurbs, int boardHeight, int tileSize) {
        for (int i = 0, board = 0; i < boardHeight / tileSize; i++) {
			if(board < 2) {
				leftCurbs.add(0);
				rightCurbs.add(0);
				board++;
			} else if (board < 5) {
				leftCurbs.add(1);
				rightCurbs.add(1);
				board++;
				if (board == 5) board = 0;
			}  
		}
    }

    // placement of track boundaries (розміщення межів траси)
	public static void placeCurbs(LinkedList<Integer> leftCurbs, LinkedList<Integer> rightCurbs) {
        if (leftCurbs.isEmpty() || rightCurbs.isEmpty()) return;

		if (leftCurbs.get(leftCurbs.size() - 1) == 1) {
			leftCurbs.addFirst(1);
			rightCurbs.addFirst(1);	
		} else {
			leftCurbs.addFirst(0);
			rightCurbs.addFirst(0);
		}

		leftCurbs.removeLast();
    	rightCurbs.removeLast();
	}
}
