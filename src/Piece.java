import java.util.ArrayList;
import java.util.List;
// crate abstract class piece
public abstract class Piece {
    private boolean white;
    private boolean notMoved = true;

    Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return this.white == true;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }
    
    // abstract method to check if a piece can move
    public abstract boolean canMove(Board board, Spot start, Spot end);
    
    // abstract method to get name of the piece
    public abstract String getName();
    
    // to be inherited and overwritten by king or rook to check if they have moved
    public boolean hasNotMoved() {
        return this.notMoved == true;
    }
    
    // to be inherited and overwritten by pawn to check if pawn can promote
    public boolean canPromote(Spot end) {
        return false;
    }
    
    // to be inherited and overwritten by king to check if king can castle
    public boolean canCastle(Board board, Spot start, Spot end) {
        return false;
    }
    
    // to determine the identity of each piece when method is called
    public boolean isKing() {return false;}

    public boolean isQueen() {return false;}

    public boolean isBishop() {return false;}

    public boolean isKnight() {return false;}

    public boolean isRook() {return false;}

    public boolean isPawn() {return false;}
    
    // checking all directions to determine if a spot is safe to move to from opponent pieces
    public boolean isSafeFromOpponent(Board board, Spot end) {
        // check file (up and down)
        boolean isFileSafe = checkOpponentFile(board, end);
        // check row (left and right)
        boolean isRowSafe = checkOpponentRow(board, end);
        // check all diagonals
        boolean isUpperLeftSafe = checkOpponentUpperLeftDiagonal(board, end);
        boolean isUpperRightSafe = checkOpponentUpperRightDiagonal(board, end);
        boolean isLowerLeftSafe = checkOpponentLowerLeftDiagonal(board, end);
        boolean isLowerRightSafe = checkOpponentLowerRightDiagonal(board, end);
        // check for knights
        boolean isKnightSafe = checkOpponentKnight(board, end);

        return  ((isFileSafe) && (isRowSafe) && (isUpperLeftSafe) && (isUpperRightSafe) &&
                (isLowerLeftSafe) && (isLowerRightSafe) && (isKnightSafe));
    }
    
    // to be inherited and overwritten to see if a piece can still move
    public boolean canStillMove(Board board, Spot currentSpot) {
        return true;
    }
    
    // add the possible spots that a knight can move to a list from target, provided that the spot is in the board
    public List<Spot> listKnightLocations(Board board, Spot target) {
        int file = target.getFile();
        int row = target.getRow();
        // create a list called spots to store knight's spots
        List<Spot> spots = new ArrayList<>();

        if (((file - 1) >= 0) && ((row - 2) >= 0)) {
            Spot spot1 = board.getBox(file - 1, row - 2);
            spots.add(spot1);
        }
        if (((file - 2) >= 0) && ((row - 1) >= 0)) {
            Spot spot2 = board.getBox(file - 2, row - 1);
            spots.add(spot2);
        }
        if (((file - 2) >= 0) && (((row + 1)) < 8)) {
            Spot spot3 = board.getBox(file - 2, row + 1);
            spots.add(spot3);
        }
        if (((file - 1) >= 0) && ((row + 2) < 8)) {
            Spot spot4 = board.getBox(file - 1, row + 2);
            spots.add(spot4);
        }
        if (((row - 1) >= 0) && ((file + 2) < 8)) {
            Spot spot5 = board.getBox(file + 2, row - 1);
            spots.add(spot5);
        }
        if (((row - 2) >= 0) && ((file + 1) < 8)) {
            Spot spot6 = board.getBox(file + 1, row - 2);
            spots.add(spot6);
        }
        if (((file + 1) < 8) && ((row + 2) < 8)) {
            Spot spot7 = board.getBox(file + 1, row + 2);
            spots.add(spot7);
        }
        if (((file + 2) < 8) && ((row + 1) < 8)) {
            Spot spot8 = board.getBox(file + 2, row + 1);
            spots.add(spot8);
        }
        return spots;
    }

    // for spots stored in the list see if there is an opponent knight that can threaten the target spot
    public boolean checkOpponentKnight(Board board, Spot target) {
        List<Spot> spots = listKnightLocations(board, target);

        for (Spot spot : spots) {
            int spotFile = spot.getFile();
            int spotRow = spot.getRow();
            // if spot is in the board
            if ((spotFile < 8 && spotFile >= 0) && (spotRow < 8 && spotRow >= 0)) {
                Piece piece = spot.getPiece();
                // if the spot is not empty
                if (piece != null) {
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // if the spot has a knight that belongs to the opponent
                    if (!sameColour && piece.isKnight()) {
                        return false;
                    }
                    // if the spot doesn't have a knight that belongs to the opponent
                    else
                        return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }
    
    // check above and below the target spot
    public boolean checkOpponentRow(Board board, Spot target) {
        boolean isUpperSafe = checkOpponentUpperRow(board, target);
        boolean isLowerSafe = checkOpponentLowerRow(board, target);

        return isUpperSafe && isLowerSafe;
    }

    // check below
    public boolean checkOpponentLowerRow(Board board, Spot target) {
        // if target spot is on the most bottom row then safe from opponent
        if (target.getRow() == 0)
            return true;
        else {
            // for rows between the target spot and the most bottom row
            for (int row = target.getRow() - 1; row >= 0; row--) {
                // if the spot being looped is not empty
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    // there is a piece that belongs to player to block, then the target spot is safe
                    if (sameColour)
                        return true;
                    // there is a piece that belongs to opponent
                    else {
                        // if the piece is not a rook or not a queen, then still safe
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // if not, then not safe
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        // if after looping through and cannot find a spot that is occupied, then safe
        return true;
    }

    // check above
    public boolean checkOpponentUpperRow(Board board, Spot target) {
        // if piece on the uppermost row, then safe from above
        if (target.getRow() == 7)
            return true;
        else {
            // for rows between the target spot and the uppermost row
            for (int row = target.getRow() + 1; row < 8; row++) {
                // if the spot being looped is not empty
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    // there is a piece that belongs to player to block, then the target spot is safe
                    if (sameColour)
                        return true;
                    // there is a piece that belongs to opponent
                    else {
                        // if the piece is not a rook or not a queen, then still safe
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // if not, then not safe
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        // if after looping through and cannot find a spot that is occupied, then safe
        return true;
    }

    // check left and right
    public boolean checkOpponentFile(Board board, Spot king) {
        boolean isLeftSafe = checkOpponentLeftFile(board, king);
        boolean isRightSafe = checkOpponentRightFile(board, king);

        return isLeftSafe && isRightSafe;
    }

    // check left
    public boolean checkOpponentLeftFile(Board board, Spot target) {
        // if target on the most left file then safe
        if (target.getFile() == 0)
            return true;
        else {
            // for files between the target spot and the most left file
            for (int file = target.getFile() - 1; file >= 0; file--) {
                // if the spot being looped is not empty
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // there is a piece that belongs to player to block, then the target spot is safe
                    if (sameColour)
                        return true;
                    // there is a piece that belongs to opponent
                    else {
                        // if the piece is not a rook or not a queen, then still safe
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // if not, then not safe
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        // if after looping through and cannot find a spot that is occupied, then safe
        return true;
    }
    
    // check right
    public boolean checkOpponentRightFile(Board board, Spot target) {
        // if target on the most right file then safe
        if (target.getFile() == 7) {
            return true;
        }
        else {
            // for files between the target spot and the most right file
            for (int file = target.getFile() + 1; file < 8; file++) {
                // if the spot being looped is not empty
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // there is a piece that belongs to player to block, then the target spot is safe
                    if (sameColour)
                        return true;
                    // there is a piece that belongs to opponent
                    else {
                        // if the piece is not a rook or not a queen, then still safe
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // if not, then not safe
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        // if after looping through and cannot find a spot that is occupied, then safe
        return true;
    }

    // check upper left diagonal
    public boolean checkOpponentUpperLeftDiagonal(Board board, Spot target) {
        int file = target.getFile() - 1;
        // if target is on the most left file or uppermost row, then safe
        if (target.getFile() == 0 || target.getRow() == 7)
            return true;
        else {
            // looping through each spot in the upper left diagonal
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // piece is same colour
                    if (sameColour)
                        return true;
                        // piece belongs to opponent
                    else {
                        // king is white
                        if (this.isWhite() == true) {
                            // if piece is pawn and is directly upper left of king
                            if (piece.isPawn() && (file == (target.getFile() - 1))) {
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    return false;
                                }
                                // the opponent piece is not bishop or queen, but protects our king hence safe
                                else
                                    return true;
                            }
                        }
                        // king is not white
                        else {
                            // if piece is bishop or queen, not safe
                            if (piece.isBishop() || piece.isQueen()) {
                                return false;
                            }
                            // the opponent piece is not bishop or queen, but protects our king hence safe
                            else
                                return true;
                        }
                    }
                }
                file--;
                if (file < 0)
                    break;
            }
        }
        return true;
    }

    // check upper right diagonal
    public boolean checkOpponentUpperRightDiagonal(Board board, Spot target) {
        int file = target.getFile() + 1;
        // if target is on the most right file or the uppermost row, then safe
        if (target.getFile() == 7 || target.getRow() == 7)
            return true;
        else {
            // looping through each spot in the upper right diagonal
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // piece is same colour
                    if (sameColour)
                        return true;
                        // piece belongs to opponent
                    else {
                        // king is white
                        if (this.isWhite() == true) {
                            // if piece is pawn and is directly upper left of king
                            if (piece.isPawn() && (file == (target.getFile() + 1))) {
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    return false;
                                }
                                // the opponent piece is not bishop or queen, but protects our king hence safe
                                else
                                    return true;
                            }
                        }
                        // king is not white
                        else {
                            // if piece is bishop or queen, not safe
                            if (piece.isBishop() || piece.isQueen()) {
                                return false;
                            }
                            // the opponent piece is not bishop or queen, but protects our king hence safe
                            else
                                return true;
                        }
                    }
                }
                file++;

                if (file > 7)
                    break;
            }
        }
        return true;
    }

    // check lower left diagonal
    public boolean checkOpponentLowerLeftDiagonal(Board board, Spot target) {
        int file = target.getFile() - 1;
        // if target is on the most left file or the most bottom row, then safe
        if (target.getFile() == 0 || target.getRow == 0)
            return true;
        else {
            // looping through each spot in the lower left diagonal
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // piece is same colour
                    if (sameColour)
                        return true;
                        // piece belongs to opponent
                    else {
                        // king is black
                        if (this.isWhite() == false) {
                            // if piece is pawn and is directly lower left of king
                            if (piece.isPawn() && (file == (target.getFile() - 1))) {
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    return false;
                                }
                                // the opponent piece is not bishop or queen, but protects our king hence safe
                                else
                                    return true;
                            }
                        }
                        // king is not white
                        else {
                            // if piece is bishop or queen, not safe
                            if (piece.isBishop() || piece.isQueen()) {
                                return false;
                            }
                            // the opponent piece is not bishop or queen, but protects our king hence safe
                            else
                                return true;
                        }
                    }
                }
                file--;
                if (file < 0)
                    break;
            }
        }
        return true;
    }

    // check lower right diagonal
    public boolean checkOpponentLowerRightDiagonal(Board board, Spot target) {
        int file = target.getFile() + 1;
        // if target is on the most right file or the most bottom row, then safe
        if (target.getFile() == 7 || target.getRow == 0)
            return true;
        else {
            // looping through each spot in the lower right diagonal
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // piece is same colour
                    if (sameColour)
                        return true;
                        // piece belongs to opponent
                    else {
                        // king is black
                        if (this.isWhite() == false) {
                            // if piece is pawn and is directly lower right of king
                            if (piece.isPawn() && (file == (target.getFile() + 1))) {
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    return false;
                                }
                                // the opponent piece is not bishop or queen, but protects our king hence safe
                                else
                                    return true;
                            }
                        }
                        // king is not white
                        else {
                            // if piece is bishop or queen, not safe
                            if (piece.isBishop() || piece.isQueen()) {
                                return false;
                            }
                            // the opponent piece is not bishop or queen, but protects our king hence safe
                            else
                                return true;
                        }
                    }
                }
                file++;
                if (file > 7)
                    break;
            }
        }
        return true;
    }
}
