public class Bishop extends Piece {
    public Bishop(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());
        boolean movingDiagonally = (fileChange == rowChange);

        if (withinRange && !isBlocked(board, start, end)) {
            if (!occupied) {
                return movingDiagonally;
            }
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                if (!sameColour)
                    return movingDiagonally;
                else
                    return false;
            }
        }
        else
            return false;
    }

    public String getName() {
        return isWhite() ? "WB" : "BB";
    }

    @Override
    public boolean isBishop() {
        return true;
    }

    public boolean isBlocked(Board board, Spot start, Spot end) {
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int fileSign = (((end.getFile() - start.getFile()) >= 0) ? 1 : -1);
        int rowSign = (((end.getRow() - start.getRow()) >= 0) ? 1 : -1);
        int startFile = start.getFile();
        int startRow = start.getRow();

        for (int count = 1; count <= fileChange; count++) {
            if (board.getBox(startFile + (count * fileSign), startRow + (count * rowSign)).getPiece() != null)
                return true;
        }
        return false;
    }
}
