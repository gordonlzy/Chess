public class Pawn extends Piece {
    private boolean moved = false;
    public Pawn(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);

        if (withinRange) {
            int fileChange = Math.abs(end.getFile() - start.getFile());
            int rowChange = (end.getRow() - start.getRow());

            boolean whiteForwardOne = ((rowChange == 1) && (fileChange == 0) && start.getPiece().isWhite());
            boolean whiteForwardTwo = ((rowChange == 2) && (fileChange == 0) && start.getPiece().isWhite());
            boolean blackForwardOne = ((rowChange == -1) && (fileChange == 0) && !(start.getPiece().isWhite()));
            boolean blackForwardTwo = ((rowChange == -2) && (fileChange == 0) && !(start.getPiece().isWhite()));
            boolean capturingDiagonals = ((rowChange == 1) && (fileChange == 1));

            // if moving two steps in the beginning
            if ((moved == false) && (whiteForwardTwo || blackForwardTwo) && !occupied) {
                moved = true;
                return true;
            }
            // not moving two steps
            else {
                // if the pawn is moving forward
                if ((whiteForwardOne || blackForwardOne) && !occupied)
                    return true;

                    // not moving forward
                else {
                    // moving diagonally
                    if (capturingDiagonals) {
                        // if not occupied then illegal move
                        if (!occupied)
                            return false;
                            // if occupied
                        else {
                            boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                            // if piece belongs to opponent then capture
                            if (!sameColour)
                                return true;
                        }
                    }
                    return false;
                }
            }
        }
        else
            return false;
    }

    public String getName() {
        return (isWhite() ? "WP" : "BP");
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean canPromote(Spot end) {
        if (end.getRow() == 7)
            return true;
        else
            return false;
    }
}
