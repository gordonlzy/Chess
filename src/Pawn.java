public class Pawn extends Piece {
    private boolean moved = false;
    public Pawn(boolean white) {
        super(white);
    }

    // check if pawn can move
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);

        // if end is in board
        if (withinRange) {
            int fileChange = Math.abs(end.getFile() - start.getFile());
            int rowChange = (end.getRow() - start.getRow());

            // two types of movements, if white, pawn can move upwards one or two spots in the first move
            // black pawn can move downwards one or two spots in the first move
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

    // return name, WP - white pawn, BP - black pawn
    public String getName() {
        return (isWhite() ? "WP" : "BP");
    }

    // check if piece is pawn
    @Override
    public boolean isPawn() {
        return true;
    }

    // check if pawn can be promoted
    @Override
    public boolean canPromote(Spot end) {
        // if piece is white and on the uppermost row or piece is black and on the most bottom row, then can promote
        if ((isWhite() == true && end.getRow() == 7) || (isWhite() == false && end.getRow() == 0))
            return true;
        // else cannot promote
        else
            return false;
    }
}
