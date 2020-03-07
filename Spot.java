import java.util.ArrayList;
import java.util.List;

public class Spot {
    private Piece piece;
    private int file;
    private int row;

    public Spot(Piece piece, int file, int row) {
        this.piece = piece;
        this.file = file;
        this.row = row;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getFile() {
        return file;
    }

    public int getRow() {
        return row;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isAccessibleToOwn(Board board, Player player, Spot end) {
        // check file
        boolean isFileAccessible = checkOwnFile(board, player, end);
        // check row
        boolean isRowAccessible = checkOwnRow(board, player, end);
        // check all diagonals
        boolean isUpperLeftAccessible = checkUpperLeftDiagonal(board, player, end);
        boolean isUpperRightAccessible = checkUpperRightDiagonal(board, player, end);
        boolean isLowerLeftAccessible = checkLowerLeftDiagonal(board, player, end);
        boolean isLowerRightAccessible = checkLowerRightDiagonal(board, player, end);
        // check for knights
        boolean isKnightAccessible = checkOwnKnight(board, player, end);

        return  ((isFileAccessible) || (isRowAccessible) || (isUpperLeftAccessible) || (isUpperRightAccessible) ||
                (isLowerLeftAccessible) || (isLowerRightAccessible) || (isKnightAccessible));
    }

    public boolean checkOwnKnight(Board board, Player player, Spot target) {
        List<Spot> spots = listKnightLocations(board, target);

        for (Spot spot : spots) {
            int spotFile = spot.getFile();
            int spotRow = spot.getRow();
            if ((spotFile < 8 && spotFile >= 0) && (spotRow < 8 && spotRow >= 0)) {
                Piece piece = spot.getPiece();
                if (piece != null) {
                    boolean sameColour = (piece.isWhite() == player.isWhite());
                    if (sameColour && piece.isKnight()) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean checkOwnRow(Board board, Player player, Spot target) {
        boolean isUpperProtected = checkAbove(board, player, target);
        boolean isLowerProtected = checkBelow(board, player, target);

        return isUpperProtected && isLowerProtected;
    }

    // check if target spot is accessible from below
    public boolean checkBelow(Board board, Player player, Spot target) {
        // if target is on row 0
        if (target.getRow() == 0)
            return false;
        // target not on row 0
        else {
            // for each row between target and row 0
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == player.isWhite();
                    // if occupied by enemy
                    if (!sameColour)
                        return false;
                    // occupied by own piece
                    else {
                        // if occupied by rook or queen
                        if (piece.isRook() || piece.isQueen())
                            return true;
                        // not rook or queen
                        else {
                            // if is a white pawn directly below
                            if (piece.isPawn() && (piece.isWhite() == true) && (row == target.getRow() - 1)) {
                                return true;
                            }
                            else
                                return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    // check if target spot is accessible from above
    public boolean checkAbove(Board board, Player player, Spot target) {
        if (target.getRow() == 7)
            return false;
        else {
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == player.isWhite();
                    // if occupied by enemy
                    if (!sameColour)
                        return false;
                    // occupied by own piece
                    else {
                        // if occupied by rook or queen
                        if (piece.isRook() || piece.isQueen())
                            return true;
                        else {
                            // if is a black pawn directly above
                            if (piece.isPawn() && (piece.isWhite() == false) && (row == target.getRow() + 1)) {
                                return true;
                            }
                            else
                                return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkOwnFile(Board board, Player player, Spot king) {
        boolean isLeftAccessible = checkLeft(board, player, king);
        boolean isRightAccessible = checkRight(board, player, king);

        return isLeftAccessible && isRightAccessible;
    }

    // check if target spot is accessible from left
    public boolean checkLeft(Board board, Player player, Spot target) {
        if (target.getFile() == 0)
            return false;
        else {
            for (int file = target.getFile() - 1; file >= 0; file--) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());

                    if (!sameColour)
                        return false;
                    else {
                        if (piece.isRook() || piece.isQueen())
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    // check if target spot is accessible from right
    public boolean checkRight(Board board, Player player, Spot target) {
        if (target.getFile() == 7) {
            return false;
        }
        else {
            for (int file = target.getFile() + 1; file < 8; file++) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());

                    if (!sameColour)
                        return false;
                    else {
                        if (piece.isRook() || piece.isQueen())
                            return true;
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    // check if target spot is accessible from upperLeft
    public boolean checkUpperLeftDiagonal(Board board, Player player, Spot target) {
        int file = target.getFile() - 1;
        if (target.getFile() == 0 || target.getRow() == 7)
            return false;
        else {
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());
                    // piece is different colour
                    if (!sameColour)
                        return false;
                        // piece belongs to player
                    else {
                        if (piece.isBishop() || piece.isQueen()) {
                            return true;
                        }
                        else
                            return false;
                    }
                }
                file--;
                if (file < 0)
                    break;
            }
        }
        return false;
    }

    // check if target spot is accessible from upperRight
    public boolean checkUpperRightDiagonal(Board board, Player player, Spot target) {
        int file = target.getFile() + 1;
        if (target.getFile() == 7 || target.getRow() == 7)
            return false;
        else {
            for (int row = target.getRow() + 1; row < 8; row++) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());
                    // piece is different colour
                    if (!sameColour)
                        return false;
                        // piece belongs to player
                    else {
                        if (piece.isBishop() || piece.isQueen()) {
                            return true;
                        }
                        else
                            return false;
                    }
                }
                file++;

                if (file > 7)
                    break;
            }
        }
        return false;
    }

    // check if target spot is accessible from lowerLeft
    public boolean checkLowerLeftDiagonal(Board board, Player player, Spot target) {
        int file = target.getFile() - 1;
        if (target.getFile() == 0 || target.getRow() == 0)
            return false;
        else {
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());
                    // piece is different colour
                    if (!sameColour)
                        return false;
                        // piece belongs to player
                    else {
                        if (piece.isBishop() || piece.isQueen()) {
                            return true;
                        }
                        else
                            return false;
                    }
                }
                file--;
                if (file < 0)
                    break;
            }
        }
        return false;
    }

    // check if target spot is accessible from lowerRight
    public boolean checkLowerRightDiagonal(Board board, Player player, Spot target) {
        int file = target.getFile() + 1;
        if (target.getFile() == 7 || target.getRow() == 0)
            return false;
        else {
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(file, row).getPiece() != null) {
                    Piece piece = board.getBox(file, row).getPiece();
                    boolean sameColour = (piece.isWhite() == player.isWhite());
                    // piece is different colour
                    if (!sameColour)
                        return false;
                        // piece belongs to player
                    else {
                        if (piece.isBishop() || piece.isQueen()) {
                            return true;
                        }
                        else
                            return false;
                    }
                }
                file++;
                if (file > 7)
                    break;
            }
        }
        return false;
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
}
