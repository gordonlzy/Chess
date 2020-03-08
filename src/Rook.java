public class Rook extends Piece {
    private boolean notMoved = true;

    public Rook(boolean white) {
        super(white);
    }

    // check if rook can move
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());

        boolean movingVertically = ((fileChange == 0) && (rowChange != 0));
        boolean movingHorizontally = ((fileChange != 0) && (rowChange == 0));

        // if end is in board, and path is not blocked
        if (withinRange && !isBlocked(board, start, end)) {
            // if end is not occupied
            if (!occupied) {
                return (movingVertically || movingHorizontally);
            }
            // if end is occupied
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                // if piece belongs to opponent
                if (!sameColour)
                    return (movingVertically || movingHorizontally);
                // if piece belongs to player
                else
                    return false;
            }
        }
        else
            return false;
    }

    // return name of piece, WR - white rook, BR -black rook
    public String getName() {
        return isWhite() ? "WR" : "BR";
    }

    // determine if piece i rook
    @Override
    public boolean isRook() {
        return true;
    }

    // check if rook has moved
    @Override
    public boolean hasNotMoved() {
        return this.notMoved == true;
    }

    // check if rook is blocked
    public boolean isBlocked(Board board, Spot start, Spot end) {
        // rowSign determines direction of row, eg: negative rowSign means downwards, and vice versa
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int fileSign = (((end.getFile() - start.getFile()) >= 0) ? 1 : -1);
        int rowChange = Math.abs(start.getRow() - end.getRow());
        int rowSign = (((end.getRow() - start.getRow()) >= 0) ? 1 : -1);
        int startFile = start.getFile();
        int startRow = start.getRow();

        // if moving horizontally
        if (fileChange == 0 && rowChange != 0) {
            // looping through row
            for (int row = 1; row <= rowChange; row++) {
                // if spot is occupied, path is blocked
                if (board.getBox(startFile, startRow + (row * rowSign)).getPiece() != null)
                    return true;
            }
            return false;
        }

        // if not moving horizontally
        else {
            // if moving vertically
            if (fileChange != 0 && rowChange == 0) {
                // looping through file
                for (int file = 1; file <= fileChange; file++) {
                    // if spot is occupied, path is blocked
                    if (board.getBox(startFile + (file * fileSign), startRow).getPiece() != null)
                        return true;
                }
                return false;
            }
        }
        return false;
    }
}
