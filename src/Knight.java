public class Knight extends Piece {
    public Knight(boolean white) {
        super(white);
    }

    // check if knight can move from start to end
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(end.getFile() - start.getFile());
        int rowChange = Math.abs(end.getRow() - start.getRow());
        boolean movingLikeAKnight = (fileChange * rowChange == 2);

        // if end is in board
        if (withinRange) {
            // if end is not occupied
            if (!occupied) {
                return movingLikeAKnight;
            }
            // end is occupied
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                // if occupied by opponent
                if (!sameColour)
                    return movingLikeAKnight;
                // if occupied by player
                else
                    return false;
            }
        }
        // end is not in board
        else
            return false;
    }

    // return name, WN - white knight, BN - black knight
    public String getName() {
        return isWhite() ? "WN" : "BN";
    }

    // check if piece is knight
    @Override
    public boolean isKnight() {
        return true;
    }
}
