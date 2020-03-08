public class Bishop extends Piece {
    public Bishop(boolean white) {
        super(white);
    }

    // check if piece can move from start to end
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());
        boolean movingDiagonally = (fileChange == rowChange);

        // if the end is in the board, and the path is not blocked by other pieces
        if (withinRange && !isBlocked(board, start, end)) {
            // if the end spot is not occupied
            if (!occupied) {
                return movingDiagonally;
            }
            // if occupied
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                // if belongs to opponent
                if (!sameColour)
                    // can move to the spot (capture)
                    return movingDiagonally;
                // piece belongs to player
                else
                    // cannot move
                    return false;
            }
        }
        else
            return false;
    }

    // get the name of the piece, either WB - white bishop, or BB - black bishop
    public String getName() {
        return isWhite() ? "WB" : "BB";
    }

    // method to determine is the piece is a bishop
    @Override
    public boolean isBishop() {
        return true;
    }

    // check if the bishop is blocked in the direction between start and end
    // fileSign and rowSign determine the direction, eg: negative fileSign and positive rowSign mean upperLeft direction
    public boolean isBlocked(Board board, Spot start, Spot end) {
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int fileSign = (((end.getFile() - start.getFile()) >= 0) ? 1 : -1);
        int rowSign = (((end.getRow() - start.getRow()) >= 0) ? 1 : -1);
        int startFile = start.getFile();
        int startRow = start.getRow();
        // loop through each spot between start and end in the correct direction
        for (int count = 1; count <= fileChange; count++) {
            // if any of the spot is not occupied, then the path is not blocked
            if (board.getBox(startFile + (count * fileSign), startRow + (count * rowSign)).getPiece() != null)
                return true;
        }
        return false;
    }
}
