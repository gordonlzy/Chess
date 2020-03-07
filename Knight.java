public class Knight extends Piece {
    public Knight(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(end.getFile() - start.getFile());
        int rowChange = Math.abs(end.getRow() - start.getRow());
        boolean movingLikeAKnight = (fileChange * rowChange == 2);

        if (withinRange) {
            if (!occupied) {
                return movingLikeAKnight;
            }
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                if (!sameColour)
                    return movingLikeAKnight;
                else
                    return false;
            }
        }
        else
            return false;
    }

    public String getName() {
        return isWhite() ? "WN" : "BN";
    }

    @Override
    public boolean isKnight() {
        return true;
    }
}
