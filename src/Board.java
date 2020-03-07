public class Board {
    Spot[][] boxes = new Spot[8][8];

    public Board() {
        this.resetBoard();
    }

    public void resetBoard() {
        // place pawns
        for (int file = 0; file < 8; file++) {
            boxes[file][1] = new Spot(new Pawn(true), file, 1);
            boxes[file][6] = new Spot(new Pawn(false), file, 6);
        }

        // place rooks
        boxes[0][0] = new Spot(new Rook(true), 0, 0);
        boxes[0][7] = new Spot(new Rook(false), 0, 7);
        boxes[7][0] = new Spot(new Rook(true), 7, 0);
        boxes[7][7] = new Spot(new Rook(false), 7, 7);

        // place knights
        boxes[1][0] = new Spot(new Knight(true), 1, 0);
        boxes[1][7] = new Spot(new Knight(false), 1, 7);
        boxes[6][0] = new Spot(new Knight(true), 6, 0);
        boxes[6][7] = new Spot(new Knight(false), 6, 7);

        // place bishops
        boxes[2][0] = new Spot(new Bishop(true), 2, 0);
        boxes[2][7] = new Spot(new Bishop(false), 2, 7);
        boxes[5][0] = new Spot(new Bishop(true), 5, 0);
        boxes[5][7] = new Spot(new Bishop(false), 5, 7);

        // place queens
        boxes[3][0] = new Spot(new Queen(true), 3, 0);
        boxes[3][7] = new Spot(new Queen(false), 3, 7);

        // place kings
        boxes[4][0] = new Spot(new King(true), 4, 0);
        boxes[4][7] = new Spot(new King(false), 4, 7);

        // initialize remaining boxes without any pieces
        for (int row = 2; row < 6; row++) {
            for (int file = 0; file < 8; file++) {
                boxes[file][row] = new Spot(null, file, row);
            }
        }
    }

    public void printBoard() {
        System.out.println("   _0_ _1_ _2_ _3_ _4_ _5_ _6_ _7_ ");
        for (int row = 7; row >= 0; row--) {
            System.out.printf("%d |", row);
            for (int file = 0; file < 8; file++) {
                if (boxes[file][row].getPiece() == null)
                    System.out.printf("___|");
                else
                    System.out.printf("%s_|", boxes[file][row].getPiece().getName());
            }
            System.out.println();
        }
    }

    public Spot getBox(int file, int row) {
        return boxes[file][row];
    }
}
