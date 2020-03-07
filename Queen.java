public class Queen extends Piece {
    public Queen(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());

        boolean movingDiagonally = (fileChange == rowChange);
        boolean movingVertically = ((fileChange == 0) && (rowChange != 0));
        boolean movingHorizontally = ((fileChange != 0) && (rowChange == 0));

        if (withinRange && !isBlocked(board, start, end)) {
            if (!occupied) {
                return (movingDiagonally || movingVertically || movingHorizontally);
            }
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                if (!sameColour)
                    return (movingDiagonally || movingVertically || movingHorizontally);
                else
                    return false;
            }
        }
        else
            return false;
    }

    public String getName() {
        return isWhite() ? "WQ" : "BQ";
    }

    @Override
    public boolean isQueen() {
        return true;
    }

    public boolean isBlocked(Board board, Spot start, Spot end) {
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int fileSign = (((end.getFile() - start.getFile()) >= 0) ? 1 : -1);
        int rowChange = Math.abs(start.getRow() - end.getRow());
        int rowSign = (((end.getRow() - start.getRow()) >= 0) ? 1 : -1);
        int startFile = start.getFile();
        int startRow = start.getRow();

        boolean movingDiagonally = (fileChange == rowChange);

        if (movingDiagonally) {
            for (int count = 1; count <= Math.abs(fileChange); count++) {
                if (board.getBox(startFile + count * fileSign, startRow + count * rowSign).getPiece() != null)
                    return true;
            }
            return false;
        }

        else {
            if (fileChange == 0 && rowChange > 0) {
                for (int row = 1; row <= rowChange; row++) {
                    if (board.getBox(startFile, startRow + (row * rowSign)).getPiece() != null)
                        return true;
                }
                return false;
            }


            else {
                if (fileChange > 0 && rowChange == 0) {
                    for (int file = 1; file <= fileChange; file++) {
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
