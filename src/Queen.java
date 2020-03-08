public class Queen extends Piece {
    public Queen(boolean white) {
        super(white);
    }

    // check if queen can move
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());

        boolean movingDiagonally = (fileChange == rowChange);
        boolean movingVertically = ((fileChange == 0) && (rowChange != 0));
        boolean movingHorizontally = ((fileChange != 0) && (rowChange == 0));

        // if end is in board and the path is not blocked
        if (withinRange && !isBlocked(board, start, end)) {
            // if end is not occupied
            if (!occupied) {
                return (movingDiagonally || movingVertically || movingHorizontally);
            }
            // if end is occupied
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                // if occupied by enemy
                if (!sameColour)
                    return (movingDiagonally || movingVertically || movingHorizontally);
                // if occupied by player
                else
                    return false;
            }
        }
        // end not on board
        else
            return false;
    }

    // get piece name, WQ - white queen, BQ - black queen
    public String getName() {
        return isWhite() ? "WQ" : "BQ";
    }

    // determine if piece is queen
    @Override
    public boolean isQueen() {
        return true;
    }

    // check if the path of movement is blocked
    public boolean isBlocked(Board board, Spot start, Spot end) {
        // fileSign and rowSign determine the direction, eg: positive fileSign and negative rowSign means lowerRight direction
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int fileSign = (((end.getFile() - start.getFile()) >= 0) ? 1 : -1);
        int rowChange = Math.abs(start.getRow() - end.getRow());
        int rowSign = (((end.getRow() - start.getRow()) >= 0) ? 1 : -1);
        int startFile = start.getFile();
        int startRow = start.getRow();

        boolean movingDiagonally = (fileChange == rowChange);

        // moving diagonally
        if (movingDiagonally) {
            // looping through the spots between start and end
            for (int count = 1; count <= Math.abs(fileChange); count++) {
                // if there is a piece between then path is blocked
                if (board.getBox(startFile + count * fileSign, startRow + count * rowSign).getPiece() != null)
                    return true;
            }
            return false;
        }

        // not moving diagonally
        else {
            // if moving horizontally
            if (fileChange == 0 && rowChange > 0) {
                // looping through row
                for (int row = 1; row <= rowChange; row++) {
                    // if there is a piece between then path is blocked
                    if (board.getBox(startFile, startRow + (row * rowSign)).getPiece() != null)
                        return true;
                }
                return false;
            }

            // not moving horizontally
            else {
                // if moving vertically
                if (fileChange > 0 && rowChange == 0) {
                    // looping through file
                    for (int file = 1; file <= fileChange; file++) {
                        // if there is a piece between then path is blocked
                        if (board.getBox(startFile + (file * fileSign), startRow).getPiece() != null)
                            return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
