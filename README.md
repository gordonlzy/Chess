# Chess
Chess game with implementation of checking, checkmating, castling, pawn promotion, capturing and movement for all pieces written in Java.

Overview of the classes created
1.0 Piece.java
Abstract class inherited by classes 1.1 - 1.6. 

Piece has an instance boolean variable which determines if the piece is white or black.

Abstract methods canMove(), getName() are implemented differently in the subclasses.
- canMove() -> check if a piece can move
- getName() -> get the name of a piece

Other methods have also been implemented:
- hasNotMoved() -> to be inherited and overwritten by king or rook to check if they have moved
- canPromote() -> to be inherited and overwritten by pawn to check if pawn can promote
- canCastle() -> to be inherited and overwritten by king to check if king can castle
- isKing() / isQueen() / isRook() / isBishop() / isKnight() / isPawn() -> to determine the identity of each piece when method is called
- isSafeFromOpponent() -> checking all directions to determine if a spot is safe to move to from opponent pieces
  - checkOpponentFile() -> check left and right
    - checkOpponentLeftFile() -> check left
    - checkOpponentRightFile() -> check right
  - checkOpponentRow() -> check above and below the target spot
    - checkOpponentLowerRow() -> check below
    - checkOpponentUpperRow() -> check above
  - checkOpponentUpperLeftDiagonal() -> check upper left diagonal
  - checkOpponentUpperRightDiagonal() -> check upper right diagonal
  - checkOpponentLowerLeftDiagonal() -> check lower left diagonal
  - checkOpponentLowerRightDiagonal() -> check lower right diagonal
  - checkOpponentKnight() -> for spots stored in the list see if there is an opponent knight that can threaten the target spot
    - listKnightLocations() -> add the possible spots that a knight can move to a list from target, provided that the spot is in the board
- canStillMove() -> to be inherited and overwritten to see if a piece can still move

  1.1 King.java
  Inherits from Piece.java
  King.java has instance variables white(inherited) and notMoved(to check if king has moved for castling purposes)
  
  Methods implemented are:
  -canMove() -> check if piece can move from start to end
  -canStillMove() -> check if king can still move
  -getName() -> return name of piece, WK - white king, BK - black king
  -isKing() -> check if piece is king, overwrite method in Piece.java
  -canCastle() -> check if king can castle, overwrite method in Piece.java
  -hasNotMoved() -> check if king has moved, overwrite method in Piece.java
  -checkOpponentKnight() -> check for possible opponent knight that can threaten king, overwrite method in Piece.java
  -checkOpponentLowerRow() -> check below, overwrite method in Piece.java
  -checkOpponentUpperRow() -> check above, overwrite method in Piece.java
  -checkOpponentLeftFile() -> check left, overwrite method in Piece.java
  -checkOpponentRightFile() -> check right, overwrite method in Piece.java
  -checkOpponentUpperLeftDiagonal() -> check upper left diagonal, overwrite method in Piece.java
  -checkOpponentUpperRightDiagonal() -> check upper right diagonal, overwrite method in Piece.java
  -checkOpponentLowerLeftDiagonal() -> check lower left diagonal, overwrite method in Piece.java
  -checkOpponentLowerRightDiagonal() -> check lower right diagonal, overwrite method in Piece.java
  -getCheckedBy() -> find the spot of the piece checking the king
  
  1.2 Queen.java
  Inherits from Piece.java
  
  Methods implemented are:
  -canMove() -> check if queen can move
  -getName() -> get piece name, WQ - white queen, BQ - black queen
  -isQueen() -> determine if piece is queen
  -isBlocked() -> check if the path of movement is blocked
  
  1.3 Rook.java
  Inherits from Piece.java
  Rook.java has instance variables white(inherited) and notMoved(to check if rook has moved for castling purposes)
  
  Methods implemented are:
  -canMove() -> check if rook can move
  -getName() -> return name of piece, WR - white rook, BR -black rook
  -isRook() -> determine if piece is rook
  -hasNotMoved() -> check if rook has moved
  -isBlocked() -> check if rook is blocked
  
  1.4 Bishop.java
  Inherits from Piece.java
  
  Methods implemented are:
  -canMove() -> check if bishop can move
  -getName() -> get piece name, WB - white bishop, BB - black bishop
  -isBishop() -> determine if piece is bishop
  -isBlocked() -> check if the path of movement is blocked
  
  1.5 Knight.java
  Inherits from Piece.java
  
  Methods implemented are:
  -canMove() -> check if knight can move
  -getName() -> get piece name, WN - white knight, BB - black knight
  -isKnight() -> determine if piece is knight
  
  1.6 Pawn.java
  Inherits from Piece.java
  Pawn.java has instance variables white(inherited) and moved(to check if pawn has moved so pawn can move 2 spaces in the first move)
  
  Methods implemented are:
  -canMove() -> check if pawn can move
  -getName() -> get piece name, WP - white pawn, BB - black pawn
  -isPawn() -> determine if piece is pawn
  -canPromote() -> check if pawn can be promoted
  
2.0 Spot.java
A spot is one of the boxes in a 8 x 8 board.
Spot.java has instance variables piece(piece on spot), file(file of spot - left, right) and row(row of spot - up, down).

Methods implemented are:
-isAccessibleToOwn() -> check if a spot is accessible to pieces that belong to player
  -checkOwnFile() -> check the accessibility of the spot in the same file
    -checkLeft() -> check if target spot is accessible from left
    -checkRight() -> check if target spot is accessible from right
  -checkOwnRow() -> check the accessibility of the spot in the same row
    -checkBelow() -> check if target spot is accessible from below
    -checkAbove() -> check if target spot is accessible from above
  -checkUpperLeftDiagonal() -> check if target spot is accessible from upperLeft
  -checkUpperRightDiagonal() -> check if target spot is accessible from upperRight
  -checkLowerLeftDiagonal() -> check if target spot is accessible from lowerLeft
  -checkLowerRightDiagonal() -> check if target spot is accessible from lowerRight
  -checkOwnKnight() -> check if possible spot can access the target spot
    -listKnightLocations() -> put the possible spots of movement of knight into a list

3.0 Board.java
The board in which the game is played.
Board.java has instance variable boxes(which is a Spot[8][8])

Methods implemented are:
-resetBoard() -> the board is reset to the default state
-printBoard() -> print the board
-getBox() -> get the spot from file and row

4.0 Player.java
Player who can perform actions on the pieces that belong to them.
Player.java has instance variables white(check player's side), kingFile(locate king), kingRow(locate king), piecesLeft(check for stalemate)

Methods implemented are:
-notChecked() -> return if the king is being checked
-move() -> move piece from start(startFile, startRow) to end(endFile, endRow) and return if the move is done
-castle() -> perform castling
-promote() -> promote pawn
-isCheckmated() -> check if player is checkmated
  -canBlockThreateningPiece() -> check if can block piece threatening king
    -tryToBlockCross() -> check if can block in cross direction(up, down, left, right)
    -tryToBlockDiagonals() -> check if can block in diagonals(upperLeft, upperRight, lowerLeft, lowerRight)
  -canCaptureThreateningPiece() -> check if can capture piece threatening king
    -canKingCapture() -> check if king can capture threatening piece
-getThreateningPieceSpot() -> get the spot of the piece threatening king  
-captureOpponentPieces() -> check if piece captured opponent's piece

5.0 Game.java
Launch Game, contains main method.

Board is created, resetted and printed. 
Player 1(white) and player 2(black) are created.

Player 1's turn
While player 1 has not made a move, check if white king is safe.
-If white king is safe 
  -If both players only one piece left
    - Stalemate, terminate
  -Else, prompt player 1 to make move.
-If white king is not safe 
  -Check if player 1 is checkmated
    -If checkmated, terminate
    -If not checkmate, prompt player to make a move to keep white king safe

Player 2's turn
While player 2 has not made a move, check if black king is safe.
-If black king is safe 
  -If both players only one piece left
    - Stalemate, terminate
  -Else, prompt player 2 to make move.
-If black king is not safe 
  -Check if player 2 is checkmated
    -If checkmated, terminate
    -If not checkmate, prompt player to make a move to keep black king safe
    
Repeat the process until a player is checkmated or both players only have one piece left.    
