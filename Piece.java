import java.util.ArrayList;
import java.util.List;

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

    public abstract boolean canMove(Board board, Spot start, Spot end);

    public abstract String getName();

    public boolean hasNotMoved() {
        return this.notMoved == true;
    }

    public boolean canPromote(Spot end) {
        return false;
    }

    public boolean canCastle(Board board, Spot start, Spot end) {
        return false;
    }

    public boolean isKing() {return false;}

    public boolean isQueen() {return false;}

    public boolean isBishop() {return false;}

    public boolean isKnight() {return false;}

    public boolean isRook() {return false;}

    public boolean isPawn() {return false;}

    public boolean isSafeFromOpponent(Board board, Spot end) {
        // check file
        boolean isFileSafe = checkOpponentFile(board, end);
        // check row
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

    public boolean canStillMove(Board board, Spot currentSpot) {
        return true;
    }

    public List<Spot> listKnightLocations(Board board, Spot target) {
        int file = target.getFile();
        int row = target.getRow();
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

    public boolean checkOpponentKnight(Board board, Spot target) {
        List<Spot> spots = listKnightLocations(board, target);

        for (Spot spot : spots) {
            int spotFile = spot.getFile();
            int spotRow = spot.getRow();
            if ((spotFile < 8 && spotFile >= 0) && (spotRow < 8 && spotRow >= 0)) {
                Piece piece = spot.getPiece();
                if (piece != null) {
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    if (!sameColour && piece.isKnight()) {
                        return false;
                    }
                    else
                        return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public boolean checkOpponentRow(Board board, Spot target) {
        boolean isUpperSafe = checkOpponentUpperRow(board, target);
        boolean isLowerSafe = checkOpponentLowerRow(board, target);

        return isUpperSafe && isLowerSafe;
    }

    public boolean checkOpponentLowerRow(Board board, Spot target) {
        if (target.getRow() == 0)
            return true;
        else {
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    if (sameColour)
                        return true;
                    else {
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checkOpponentUpperRow(Board board, Spot target) {
        if (target.getRow() == 7)
            return true;
        else {
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    if (sameColour)
                        return true;
                    else {
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checkOpponentFile(Board board, Spot king) {
        boolean isLeftSafe = checkOpponentLeftFile(board, king);
        boolean isRightSafe = checkOpponentRightFile(board, king);

        return isLeftSafe && isRightSafe;
    }

    public boolean checkOpponentLeftFile(Board board, Spot target) {
        if (target.getFile() == 0)
            return true;
        else {
            for (int file = target.getFile() - 1; file >= 0; file--) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());

                    if (sameColour)
                        return true;
                    else {
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checkOpponentRightFile(Board board, Spot target) {
        if (target.getFile() == 7) {
            return true;
        }
        else {
            for (int file = target.getFile() + 1; file < 8; file++) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());

                    if (sameColour)
                        return true;
                    else {
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checkOpponentUpperLeftDiagonal(Board board, Spot target) {
        int file = target.getFile() - 1;
        if (target.getFile() == 0)
            return true;
        else {
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

    public boolean checkOpponentUpperRightDiagonal(Board board, Spot target) {
        int file = target.getFile() + 1;
        if (target.getFile() == 7)
            return true;
        else {
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

    public boolean checkOpponentLowerLeftDiagonal(Board board, Spot target) {
        int file = target.getFile() - 1;
        if (target.getFile() == 0)
            return true;
        else {
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

    public boolean checkOpponentLowerRightDiagonal(Board board, Spot target) {
        int file = target.getFile() + 1;
        if (target.getFile() == 7)
            return true;
        else {
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
