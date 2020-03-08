import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private boolean notMoved = true;
    Spot checkedBy;

    public King(boolean white) {
        super(white);
    }

    // check if piece can move from start to end
    public boolean canMove(Board board, Spot start, Spot end) {
        boolean withinRange = ((end.getFile() >=0 && end.getFile() < 8) && (end.getRow() >= 0 && end.getRow() < 8));
        boolean occupied = (end.getPiece() != null);
        int fileChange = Math.abs(start.getFile() - end.getFile());
        int rowChange = Math.abs(start.getRow() - end.getRow());

        boolean normalMove = (fileChange > -2 && fileChange < 2) && (rowChange > -2 && rowChange < 2);

        if (withinRange) {
            // not occupied
            if (!occupied) {
                return (normalMove);
            }
            // occupied
            else {
                boolean sameColour = (end.getPiece().isWhite() == this.isWhite());
                if (!sameColour)
                    return normalMove;
                else
                    return false;
            }
        }
        else
            return false;
    }

    // check if king can still move
    public boolean canStillMove(Board board, Player player, Spot currentSpot) {
        Spot threateningPieceSpot = player.getThreateningPieceSpot(board);
        int threateningFileRelativeToKing = threateningPieceSpot.getFile() - player.getKingFile();
        int threateningRowRelativeToKing = threateningPieceSpot.getRow() - player.getKingRow();
        int file = currentSpot.getFile();
        int row = currentSpot.getRow();

        // check all directions
        boolean left; boolean right; boolean up; boolean down; boolean upperLeft; boolean upperRight;
        boolean lowerLeft; boolean lowerRight;

        switch (file) {
            default:
                switch (row) {
                    default:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));
                        upperLeft = canMove(board, currentSpot, board.getBox(file - 1, row + 1));
                        upperRight = canMove(board, currentSpot, board.getBox(file + 1, row + 1));
                        lowerLeft = canMove(board, currentSpot, board.getBox(file - 1, row - 1));
                        lowerRight = canMove(board, currentSpot, board.getBox(file + 1, row - 1));
                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            if (threateningRowRelativeToKing > 0)
                                up = false;
                            else
                                down = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningFileRelativeToKing > 0) {
                                if (threateningRowRelativeToKing == 0)
                                    right = false;
                                else {
                                    if (threateningRowRelativeToKing > 0)
                                        upperRight = false;
                                    else
                                        lowerRight = false;
                                }
                            }
                            else {
                                // left
                                if (threateningRowRelativeToKing == 0)
                                    left = false;
                                else {
                                    if (threateningRowRelativeToKing > 0)
                                        upperLeft = false;
                                    else
                                        lowerLeft = false;
                                }
                            }
                        }
                        return (left || right || up || down || upperLeft || upperRight || lowerLeft || lowerRight);
                    case 0:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));
                        upperLeft = canMove(board, currentSpot, board.getBox(file - 1, row + 1));
                        upperRight = canMove(board, currentSpot, board.getBox(file + 1, row + 1));

                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            up = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningFileRelativeToKing > 0) {
                                if (threateningRowRelativeToKing == 0)
                                    right = false;
                                else {
                                    upperRight = false;
                                }
                            }
                            else {
                                // left
                                if (threateningRowRelativeToKing == 0)
                                    left = false;
                                else {
                                    upperLeft = false;
                                }
                            }
                        }
                        return (up || left || right || upperLeft || upperRight);
                    case 7:
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));
                        lowerLeft = canMove(board, currentSpot, board.getBox(file - 1, row - 1));
                        lowerRight = canMove(board, currentSpot, board.getBox(file + 1, row - 1));

                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            down = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningFileRelativeToKing > 0) {
                                if (threateningRowRelativeToKing == 0)
                                    right = false;
                                else {
                                    lowerRight = false;
                                }
                            }
                            else {
                                // left
                                if (threateningRowRelativeToKing == 0)
                                    left = false;
                                else {
                                    lowerLeft = false;
                                }
                            }
                        }
                        return (down || left || right || lowerLeft || lowerRight);
                }
            case 0:
                switch (row) {
                    default:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));
                        upperRight = canMove(board, currentSpot, board.getBox(file + 1, row + 1));
                        lowerRight = canMove(board, currentSpot, board.getBox(file + 1, row - 1));

                        if (threateningFileRelativeToKing == 0) {
                            if (threateningRowRelativeToKing > 0)
                                up = false;
                            else
                                down = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningRowRelativeToKing == 0)
                                right = false;
                            else {
                                if (threateningRowRelativeToKing > 0)
                                    upperRight = false;
                                else
                                    lowerRight = false;
                            }
                        }
                        return (up || down || right || upperRight || lowerRight);
                    case 0:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        upperRight = canMove(board, currentSpot, board.getBox(file + 1, row + 1));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));

                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            up = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningFileRelativeToKing > 0) {
                                if (threateningRowRelativeToKing == 0)
                                    right = false;
                                else {
                                    upperRight = false;
                                }
                            }
                        }
                        return (up || upperRight || right);
                    case 7:
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        right = canMove(board, currentSpot, board.getBox(file + 1, row));
                        lowerRight = canMove(board, currentSpot, board.getBox(file + 1, row - 1));

                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            down = false;
                        }
                        // different file
                        else {
                            // right
                            if (threateningRowRelativeToKing == 0)
                                right = false;
                            else {
                                lowerRight = false;
                            }
                        }
                        return (down || right || lowerRight);
                }
            case 7:
                switch (row) {
                    default:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        upperLeft = canMove(board, currentSpot, board.getBox(file - 1, row + 1));
                        lowerLeft = canMove(board, currentSpot, board.getBox(file - 1, row - 1));

                        // same file
                        if (threateningFileRelativeToKing == 0) {
                            if (threateningRowRelativeToKing > 0)
                                up = false;
                            else
                                down = false;
                        }
                        // different file
                        else {
                            // left
                            if (threateningRowRelativeToKing == 0)
                                left = false;
                            else {
                                if (threateningRowRelativeToKing > 0)
                                    upperLeft = false;
                                else
                                    lowerLeft = false;
                            }
                        }
                        return (up || down || left || upperLeft || lowerLeft);
                    case 0:
                        up = canMove(board, currentSpot, board.getBox(file, row + 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        upperLeft = canMove(board, currentSpot, board.getBox(file - 1, row + 1));

                        if (threateningFileRelativeToKing == 0) {
                            up = false;
                        }
                        // different file
                        else {
                            // left
                            if (threateningRowRelativeToKing == 0)
                                left = false;
                            else {
                                upperLeft = false;
                            }
                        }
                        return (up || left || upperLeft);
                    case 7:
                        down = canMove(board, currentSpot, board.getBox(file, row - 1));
                        left = canMove(board, currentSpot, board.getBox(file - 1, row));
                        lowerLeft = canMove(board, currentSpot, board.getBox(file - 1, row - 1));

                        if (threateningFileRelativeToKing == 0) {
                            down = false;
                        }
                        // different file
                        else {
                            // left
                            if (threateningRowRelativeToKing == 0)
                                left = false;
                            else {
                                lowerLeft = false;
                            }
                        }

                        return (down || left || lowerLeft);
                }
        }
    }

    // return name of piece, WK - white king, BK - black king
    public String getName() {
        return isWhite() ? "WK" : "BK";
    }

    // check if piece is king, overwrite method in Piece.java
    @Override
    public boolean isKing() {
        return true;
    }

    // check if king can castle, overwrite method in Piece.java
    @Override
    public boolean canCastle(Board board, Spot start, Spot end) {
        // if king has not moved
        if (hasNotMoved()) {
            int kingRow = start.getRow();
            Spot castlingKingSide = board.getBox(6, kingRow);
            Spot castlingQueenSide = board.getBox(2, kingRow);

            // if castling king's side
            if (end == castlingKingSide) {
                Piece kingSideRook = board.getBox(7, kingRow).getPiece();
                boolean spaceAvailable = ((board.getBox(5, kingRow).getPiece() == null) &&
                        (board.getBox(6, kingRow).getPiece() == null));

                // rook has not moved and there is space for castling
                if (kingSideRook.hasNotMoved() && spaceAvailable)
                    return true;
                // rook has moved or no space for castling
                else
                    return false;
            }

            // not castling king's side
            else {
                // instead castling queen's side
                if (end == castlingQueenSide) {
                    Piece queenSideRook = board.getBox(0, kingRow).getPiece();
                    boolean spaceAvailable = ((board.getBox(3, kingRow).getPiece() == null) &&
                            (board.getBox(2, kingRow).getPiece() == null) &&
                            (board.getBox(1, kingRow).getPiece() == null));

                    // rook has not moved and there is space for castling
                    if (queenSideRook.hasNotMoved() && spaceAvailable)
                        return true;
                    // rook has moved or no space for castling
                    else
                        return false;
                }
                // invalid castling move
                return false;
            }
        }
        // king has moved, cannot castle
        return false;
    }

    // check if king has moved, overwrite method in Piece.java
    @Override
    public boolean hasNotMoved() {
        return this.notMoved == true;
    }

    // check for possible opponent knight that can threaten king, overwrite method in Piece.java
    @Override
    public boolean checkOpponentKnight(Board board, Spot target) {
        int file = target.getFile();
        int row = target.getRow();
        List<Spot> spots = new ArrayList<>();

        // put possible locations of knights into a list
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

        // looping through the list
        for (Spot spot : spots) {
            int spotFile = spot.getFile();
            int spotRow = spot.getRow();
            if ((spotFile < 8 && spotFile >= 0) && (spotRow < 8 && spotRow >= 0)) {
                Piece piece = spot.getPiece();
                // if the spot is occupied
                if (piece != null) {
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    // spot occupied by enemy knight
                    if (!sameColour && piece.isKnight()) {
                        checkedBy = board.getBox(spotFile, spotRow);
                        return false;
                    }
                    // spot not occupied by enemy knight
                    else
                        return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    // check below, overwrite method in Piece.java
    @Override
    public boolean checkOpponentLowerRow(Board board, Spot target) {
        // if king is on the most bottom row, then not threatened
        if (target.getRow() == 0)
            return true;
        else {
            // looping through the spot between king's row and the most bottom row
            for (int row = target.getRow() - 1; row >= 0; row--) {
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    // if the piece belongs to player, then king is not threatened
                    if (sameColour)
                        return true;
                    // if belongs to opponent
                    else {
                        // if piece is not a rook or piece is not a queen, then not threatened
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // else threatened
                        else {
                            // the piece threatening king is updated as checkedBy
                            checkedBy = board.getBox(target.getFile(), row);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // check above, overwrite method in Piece.java
    @Override
    public boolean checkOpponentUpperRow(Board board, Spot target) {
        // if king is on the uppermost row, then not threatened
        if (target.getRow() == 7)
            return true;
        else {
            // looping through the spots between king's row and the uppermost row
            for (int row = target.getRow() + 1; row < 8; row++) {
                // if the spot is occupied
                if (board.getBox(target.getFile(), row).getPiece() != null) {
                    Piece piece = board.getBox(target.getFile(), row).getPiece();
                    boolean sameColour = piece.isWhite() == this.isWhite();
                    // if piece belongs to player, then safe
                    if (sameColour)
                        return true;
                    // piece belongs to oppenent
                    else {
                        // if piece is not rook or not queen, then not threatened
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // else threatened
                        else {
                            // the piece threatening king is updated as checkedBy
                            checkedBy = board.getBox(target.getFile(), row);
                            return false;
                        }
                    }
                }
            }
        }
        //  after looping through, and cannot find a spot that is occupied, then safe
        return true;
    }

    // check left, overwrite method in Piece.java
    @Override
    public boolean checkOpponentLeftFile(Board board, Spot target) {
        // if king is on the most left file, then not threatened
        if (target.getFile() == 0)
            return true;
        else {
            // looping through the spots between king's file and the most left file
            for (int file = target.getFile() - 1; file >= 0; file--) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());
                    
                    // if piece belongs to player, then not threatened
                    if (sameColour)
                        return true;
                    // piece belongs to oppenent
                    else {
                        // if piece is not rook or not queen, then not threatened
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // else threatened
                        else {
                            // the piece threatening king is updated as checkedBy
                            checkedBy = board.getBox(file, target.getRow());
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // check right, overwrite method in Piece.java
    @Override
    public boolean checkOpponentRightFile(Board board, Spot target) {
        // if king is on the most right file, then not threatened
        if (target.getFile() == 7) {
            return true;
        }
        else {
            // looping through the spots between king's file and the most right file
            for (int file = target.getFile() + 1; file < 8; file++) {
                if (board.getBox(file, target.getRow()).getPiece() != null) {
                    Piece piece = board.getBox(file, target.getRow()).getPiece();
                    boolean sameColour = (piece.isWhite() == this.isWhite());

                    // if piece belongs to player, then not threatened
                    if (sameColour)
                        return true;
                    // piece belongs to oppenent
                    else {
                        // if piece is not rook or not queen, then not threatened
                        if (!(piece.isRook() || piece.isQueen()))
                            return true;
                        // else threatened
                        else {
                            // the piece threatening king is updated as checkedBy
                            checkedBy = board.getBox(file, target.getRow());
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // check upper left diagonal, overwrite method in Piece.java
    @Override
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
                                checkedBy = board.getBox(file, row);
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    checkedBy = board.getBox(file, row);
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
                                // the piece threatening king is updated as checkedBy
                                checkedBy = board.getBox(file, row);
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

    // check upper right diagonal, overwrite method in Piece.java
    @Override
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
                                checkedBy = board.getBox(file, row);
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    checkedBy = board.getBox(file, row);
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
                                // the piece threatening king is updated as checkedBy
                                checkedBy = board.getBox(file, row);
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

    // check lower left diagonal, overwrite method in Piece.java
    @Override
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
                                checkedBy = board.getBox(file, row);
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    // the piece threatening king is updated as checkedBy
                                    checkedBy = board.getBox(file, row);
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
                                checkedBy = board.getBox(file, row);
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

    // check lower right diagonal, overwrite method in Piece.java
    @Override
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
                                checkedBy = board.getBox(file, row);
                                return false;
                            }
                            else {
                                // piece is not pawn but is bishop or queen
                                if (piece.isBishop() || piece.isQueen()) {
                                    // the piece threatening king is updated as checkedBy
                                    checkedBy = board.getBox(file, row);
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
                                checkedBy = board.getBox(file, row);
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

    // find the spot of the piece checking the king
    public Spot getCheckedBy() {
        return checkedBy;
    }
}
