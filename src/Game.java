import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        board.resetBoard();
        board.printBoard();
        int turn = 1;

        Player player1 = new Player(true, 4, 0, 16);
        Player player2 = new Player(false, 4, 7, 16);
        
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTurn " + turn);
            boolean player1Finished = false;
            boolean player2Finished = false;

            // while player 1 has not made a move
            while (!player1Finished) {
                boolean whiteKingIsSafe = player1.notChecked(board);

                // if not checked
                if (whiteKingIsSafe) {
                    // if player 1 and player 2 both only have one piece left, then call a stalemate
                    if ((player1.getPiecesLeft() == 1) && (player2.getPiecesLeft() == 1)) {
                        System.out.println("Stalemate");
                        System.exit(0);
                    }
                    else {
                        // prompt player 1 to choose a piece to move
                        System.out.println("\nPlayer one to move, enter the file and row of the piece you want to move");
                        int startFile = scanner.nextInt();
                        int startRow = scanner.nextInt();
                        // make sure that player only moves his own pieces
                        while (board.getBox(startFile, startRow).getPiece().isWhite() != player1.isWhite()) {
                            System.out.println("Choose a white piece.");
                            System.out.println("Player one to move, enter the file and row of the piece you want to move");
                            startFile = scanner.nextInt();
                            startRow = scanner.nextInt();
                        }

                        System.out.println("Enter the file and row of the destination");
                        int endFile = scanner.nextInt();
                        int endRow = scanner.nextInt();

                        player1Finished = player1.move(board, startFile, startRow, endFile, endRow);

                        // if player 1 captured player 2 's piece, update the player 2 's number of pieces left
                        if (player1.captureOpponentPieces(board, player1, board.getBox(endFile, endRow))) {
                            player2.setPiecesLeft(player2.getPiecesLeft() - 1);
                        }
                    }
                }
                // checked
                else {
                    // check if player 1 is checkmated
                    if (player1.isCheckmated(board, player1) == true) {
                        // if checkmated, terminate the game
                        System.out.println("Checkmate");
                        System.exit(0);
                    }
                    // if not checkmated
                    else {
                        System.out.println("\nWhite Checked");
                        // while white king is not safe, repeat the loop until player secures safety for his king
                        while (!whiteKingIsSafe) {
                            System.out.println("\nPlayer one to move, enter the file and row of the piece you want to move");
                            int startFile = scanner.nextInt();
                            int startRow = scanner.nextInt();

                            while (board.getBox(startFile, startRow).getPiece().isWhite() != player1.isWhite()) {
                                System.out.println("Choose a white piece.");
                                System.out.println("Player one to move, enter the file and row of the piece you want to move");
                                startFile = scanner.nextInt();
                                startRow = scanner.nextInt();
                            }

                            System.out.println("Enter the file and row of the destination");
                            int endFile = scanner.nextInt();
                            int endRow = scanner.nextInt();

                            player1Finished = player1.move(board, startFile, startRow, endFile, endRow);

                            if (player1.captureOpponentPieces(board, player1, board.getBox(endFile, endRow))) {
                                player2.setPiecesLeft(player2.getPiecesLeft() - 1);
                            }

                            whiteKingIsSafe = player1.notChecked(board);
                        }
                    }
                }
            }
            board.printBoard();

            while (!player2Finished) {
                boolean blackKingIsSafe = player2.notChecked(board);

                // if not checked
                if (blackKingIsSafe) {
                    if ((player1.getPiecesLeft() == 1) && (player2.getPiecesLeft() == 1)) {
                        System.out.println("Stalemate");
                        System.exit(0);
                    }
                    else {
                        // prompt player 2 to choose a piece to move
                        System.out.printf("%nPlayer two to move, enter the file and row of the piece you want to move%n");
                        int startFile = scanner.nextInt();
                        int startRow = scanner.nextInt();
                        // if piece chosen does not belong to player 2, prompt player 2 to choose another piece
                        while (board.getBox(startFile, startRow).getPiece().isWhite() != player2.isWhite()) {
                            System.out.println("Choose a black piece.");
                            System.out.println("Player two to move, enter the file and row of the piece you want to move");
                            startFile = scanner.nextInt();
                            startRow = scanner.nextInt();
                        }
                        // prompt player 2 to choose the destination of the piece chosen
                        System.out.println("Enter the file and row of the destination");
                        int endFile = scanner.nextInt();
                        int endRow = scanner.nextInt();
                        // move the piece and end the turn by changing player2Finished to true
                        player2Finished = player2.move(board, startFile, startRow, endFile, endRow);

                        if (player2.captureOpponentPieces(board, player2, board.getBox(endFile, endRow))) {
                            player1.setPiecesLeft(player1.getPiecesLeft() - 1);
                        }
                    }
                }

                // checked
                else {
                    // check if player 2 is checkmated
                    if (player2.isCheckmated(board, player2)) {
                        // if checkmated, terminate
                        System.out.println("Checkmate");
                        System.exit(0);
                    }
                    else {
                        System.out.println("\nBlack Checked");
                        // continue loop if black king is not safe
                        while (!blackKingIsSafe) {
                            System.out.printf("%nPlayer two to move, enter the file and row of the piece you want to move%n");
                            int startFile = scanner.nextInt();
                            int startRow = scanner.nextInt();

                            while (board.getBox(startFile, startRow).getPiece().isWhite() != player2.isWhite()) {
                                System.out.println("Choose a black piece.");
                                System.out.println("Player two to move, enter the file and row of the piece you want to move");
                                startFile = scanner.nextInt();
                                startRow = scanner.nextInt();
                            }

                            System.out.println("Enter the file and row of the destination");
                            int endFile = scanner.nextInt();
                            int endRow = scanner.nextInt();

                            player2Finished = player2.move(board, startFile, startRow, endFile, endRow);

                            if (player2.captureOpponentPieces(board, player2, board.getBox(endFile, endRow))) {
                                player1.setPiecesLeft(player1.getPiecesLeft() - 1);
                            }

                            blackKingIsSafe = player2.notChecked(board);
                        }
                    }
                }
            }
            board.printBoard();
            turn++;
        }
    }
}
