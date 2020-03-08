import java.util.Scanner;

public class Player {
    private boolean white;
    private int kingFile;
    private int kingRow;
    private int piecesLeft;

    public Player(boolean white, int kingFile, int kingRow, int piecesLeft) {
        this.white = white;
        this.kingFile = kingFile;
        this.kingRow = kingRow;
        this.piecesLeft = piecesLeft;
    }

    // getters and setters
    public int getKingFile() {
        return kingFile;
    }

    public void setKingFile(int kingFile) {
        this.kingFile = kingFile;
    }

    public int getKingRow() {
        return kingRow;
    }

    public void setKingRow(int kingRow) {
        this.kingRow = kingRow;
    }

    public boolean isWhite() {
        return this.white == true;
    }
    
    public int getPiecesLeft() {
        return piecesLeft;
    }

    public void setPiecesLeft(int piecesLeft) {
        this.piecesLeft = piecesLeft;
    }

    // return if the king is being checked
    public boolean notChecked(Board board) {
        Spot kingSpot = board.getBox(getKingFile(), getKingRow());
        Piece king = kingSpot.getPiece();
        
        return king.isSafeFromOpponent(board, kingSpot);
    }

    // move piece from start(startFile, startRow) to end(endFile, endRow) and return if the move is done
    public boolean move(Board board, int startFile, int startRow, int endFile, int endRow) {
        Spot start = board.getBox(startFile, startRow);
        Spot end = board.getBox(endFile, endRow);
        Piece piece = start.getPiece();
        boolean doneMove = false;

        // if piece can castle and is safe from opponent after castling
        if (piece.canCastle(board, start, end) && piece.isSafeFromOpponent(board, end)) {
            // update king's file and row
            setKingFile(end.getFile());
            setKingRow(end.getRow());

            // perform castling
            castle(board, startFile, startRow, endFile, endRow);

            doneMove = true;
        }

        else {
            // piece is king
            if (piece.isKing()) {
                // king can move and the end spot is safe from opponent
                if ((piece.canMove(board, start, end)) && piece.isSafeFromOpponent(board, end)) {
                    // update king's file and row
                    setKingFile(end.getFile());
                    setKingRow(end.getRow());

                    // update piece locations
                    end.setPiece(piece);
                    start.setPiece(null);

                    doneMove = true;
                }
                // move is not safe
                else {
                    System.out.println("Illegal Move. Please try again.");
                }
            }
            // piece is not king
            else {
                if (piece.canMove(board, start, end)) {
                    // update piece location
                    end.setPiece(piece);
                    start.setPiece(null);

                    // if piece can promote, then promote
                    if (piece.canPromote(end)) {
                        promote(piece, end);
                    }

                    doneMove = true;
                }
                else {
                    System.out.println("Illegal Move. Please try again.");
                }
            }
        }
        return doneMove;
    }

    // perform castling
    public void castle(Board board, int startFile, int startRow, int endFile, int endRow) {
        Spot start = board.getBox(startFile, startRow);
        Spot end = board.getBox(endFile, endRow);
        Piece piece = start.getPiece();

        if (piece.canCastle(board, start, end)) {
            // castling king's side
            Spot kingSideRookSpot = board.getBox(startFile + 3, startRow);

            if (end.getFile() == (start.getFile() + 2)) {
                end.setPiece(piece);
                start.setPiece(null);
                board.getBox(startFile + 1, startRow).setPiece(kingSideRookSpot.getPiece());
                kingSideRookSpot.setPiece(null);
            }
            else {
                // castling queen's side
                Spot queenSideRookSpot = board.getBox(startFile - 4, startRow);

                if (end.getFile() == (start.getFile() - 2)) {
                    end.setPiece(piece);
                    start.setPiece(null);
                    board.getBox(startFile - 1, startRow).setPiece(queenSideRookSpot.getPiece());
                    queenSideRookSpot.setPiece(null);
                }
            }
        }
    }

    // promote pawn
    public void promote(Piece piece, Spot end) {
        if (piece.canPromote(end)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Promote pawn to: Q-Queen, R-Rook, B-Bishop or N-Knight?");
            // prompt player to select promotion
            String choice = scanner.next();
            switch (choice) {
                case "Q":
                    end.setPiece(new Queen(piece.isWhite()));
                    break;
                case "R":
                    end.setPiece(new Rook(piece.isWhite()));
                    break;
                case "B":
                    end.setPiece(new Bishop(piece.isWhite()));
                    break;
                case "N":
                    end.setPiece(new Knight(piece.isWhite()));
                    break;
            }
        }
    }

    // check if player is checkmated
    public boolean isCheckmated(Board board, Player player) {
        Spot kingSpot = board.getBox(kingFile, kingRow);
        King king = (King) kingSpot.getPiece();

        boolean isCheckmate = (!(king.canStillMove(board, player, kingSpot))
                && !(canBlockThreateningPiece(board, player)) && !(canCaptureThreateningPiece(board, player)));
        return isCheckmate;
    }

    // check if can block piece threatening king
    public boolean canBlockThreateningPiece(Board board, Player player) {
        Spot threateningPieceSpot = player.getThreateningPieceSpot(board);
        Piece threateningPiece = player.getThreateningPieceSpot(board).getPiece();
        // if threatening piece is knight or pawn, cannot block, only option is to move away or to capture the piece
        if (threateningPiece.isKnight() || threateningPiece.isPawn())
            return false;
        // not knight or pawn
        else {
            if (threateningPiece.isQueen()) {
                int fileDiff = Math.abs(threateningPieceSpot.getFile() - player.getKingFile());
                int rowDiff = Math.abs(threateningPieceSpot.getRow() - player.getKingRow());

                // if diagonal
                if (fileDiff == rowDiff) {
                    return tryToBlockDiagonals(board, player);
                }
                // if cross directions
                else {
                    return tryToBlockCross(board, player);
                }
            }
            // not queen
            else {
                if (threateningPiece.isRook()) {
                    return tryToBlockCross(board, player);
                }
                else {
                    if (threateningPiece.isBishop()) {
                        return tryToBlockDiagonals(board, player);
                    }
                    // not one of the threatening pieces, ie king
                    return true;
                }
            }
        }
    }

    // check if can capture piece threatening king
    public boolean canCaptureThreateningPiece(Board board, Player player) {
        Spot threateningPieceSpot = player.getThreateningPieceSpot(board);

        boolean canCapture = canKingCapture(board, player) ||
                threateningPieceSpot.isAccessibleToOwn(board, player, threateningPieceSpot);

        return canCapture;
    }

    // check if king can capture threatening piece
    public boolean canKingCapture(Board board, Player player) {
        int file = player.getThreateningPieceSpot(board).getFile();
        int row = player.getThreateningPieceSpot(board).getRow();
        int fileDifference = Math.abs(file - getKingFile());
        int rowDifference = Math.abs(row - player.getKingRow());
        Spot start = board.getBox(getKingFile(), getKingRow());
        Spot end = board.getBox(file, row);
        Piece king = start.getPiece();

        if ((fileDifference < 2) && (rowDifference < 2) && (fileDifference + rowDifference < 3)) {
            if (king.canMove(board, start, end) && king.isSafeFromOpponent(board, end))
                return true;
        }
        return false;
    }

    // check if can block in cross direction(up, down, left, right)
    public boolean tryToBlockCross(Board board, Player player) {
        Spot threateningPieceSpot = player.getThreateningPieceSpot(board);

        int fileDiff = Math.abs(threateningPieceSpot.getFile() - player.getKingFile());
        int fileDifference = threateningPieceSpot.getFile() - player.getKingFile();
        int rowDifference = threateningPieceSpot.getRow() - player.getKingRow();

        // if vertical
        if (fileDiff == 0) {
            // if threatening piece above
            if (rowDifference >= 0) {
                for (int row = player.getKingRow() + 1; row < threateningPieceSpot.getRow(); row++) {
                    Spot testSpot = board.getBox(kingFile, row);
                    return testSpot.isAccessibleToOwn(board, player, testSpot);
                }
            }
            // if threatening piece below
            else {
                for (int row = player.getKingRow() - 1; row > threateningPieceSpot.getRow(); row--) {
                    Spot testSpot = board.getBox(kingFile, row);
                    return testSpot.isAccessibleToOwn(board, player, testSpot);
                }
            }
        }
        // not vertical
        else {
            // if threatening piece on right hand side
            if (fileDifference >= 0) {
                for (int file = player.getKingFile() + 1; file < threateningPieceSpot.getFile(); file++) {
                    Spot testSpot = board.getBox(file, kingRow);
                    return testSpot.isAccessibleToOwn(board, player, testSpot);
                }
            }
            // if threatening piece on left hand side
            else {
                for (int file = player.getKingFile() - 1; file > threateningPieceSpot.getFile(); file--) {
                    Spot testSpot = board.getBox(file, kingRow);
                    return testSpot.isAccessibleToOwn(board, player, testSpot);
                }
            }
        }
        return false;
    }

    // get the spot of the piece threatening king
    public Spot getThreateningPieceSpot(Board board) {
        King king = (King) board.getBox(getKingFile(), getKingRow()).getPiece();
        return king.getCheckedBy();
    }

    // check if can block in diagonals(upperLeft, upperRight, lowerLeft, lowerRight)
    public boolean tryToBlockDiagonals(Board board, Player player) {
        Spot threateningPieceSpot = player.getThreateningPieceSpot(board);

        int fileDiff = Math.abs(threateningPieceSpot.getFile() - player.getKingFile());
        int fileDifference = threateningPieceSpot.getFile() - player.getKingFile();
        int rowDifference = threateningPieceSpot.getRow() - player.getKingRow();

        // upper right or lower right
        if (fileDifference > 0) {
            // upper right
            if (rowDifference > 0) {
                for (int count = 1; count < fileDiff; count++) {
                    Spot testSpot = board.getBox(kingFile + count, kingRow + count);

                    if (testSpot.isAccessibleToOwn(board, player, testSpot))
                        return true;
                }
            }
            // lower right
            else {
                for (int count = 1; count < fileDiff; count++) {
                    Spot testSpot = board.getBox(kingFile + count, kingRow - count);

                    if (testSpot.isAccessibleToOwn(board, player, testSpot))
                        return true;
                }
            }
            return false;
        }
        // upper left or lower left
        else {
            // upper left
            if (rowDifference > 0) {
                for (int count = 1; count < fileDiff; count++) {
                    Spot testSpot = board.getBox(kingFile - count, kingRow + count);

                    if (testSpot.isAccessibleToOwn(board, player, testSpot))
                        return true;
                }
            }
            // upper right
            else {
                for (int count = 1; count < fileDiff; count++) {
                    Spot testSpot = board.getBox(kingFile - count, kingRow - count);

                    if (testSpot.isAccessibleToOwn(board, player, testSpot))
                        return true;
                }
            }
            return false;
        }
    }

    // check if piece captured opponent's piece
    public boolean captureOpponentPieces(Board board, Player player, Spot target) {
        if (target.getPiece().isWhite() != player.isWhite())
            return true;
        else
            return false;
    }
}
