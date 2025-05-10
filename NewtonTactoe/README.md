# Tic-Tac-Toe AI Project

## Summary

This practical aimed to create a Tic-Tac-Toe game using Java and C, implementing both two-player mode and an AI that never loses.  
I developed the AI-supported game (bot vs. human using the minimax algorithm) in C, and implemented two-player functionality in both simple and custom-grid games in Java.  
The custom grid game continues until all grid spaces are filled, counting the number of rows with the same mark.  
I learned how to develop decision-making algorithms like **minimax** and how heuristics affect game-solving.

**Keywords** — TicTacToe, Java, Two-Player Game, Minimax Algorithm, C

---

## I. Problem Description

A simple turn-based Tic-Tac-Toe board game where two players can play using two classes.  
An AI algorithm ensures the bot never loses. The game is visualized properly for ease of user interaction.

---

## II. Background

### A. Tic-Tac-Toe  
A simple, globally recognized board game with easy-to-understand rules.  
It uses a 3×3 grid (2D array). The objective is to get 3 identical marks (X or O) in a row horizontally, vertically, or diagonally.  
Possible outcomes: win, loss, or draw.

### B. TicTacToe Bot  
Computers can play optimally using algorithms like hill climbing or minimax.  
We used **minimax** to maximize the AI's gain while minimizing the player’s.  
The bot simulates all possible game outcomes, functioning like an omniscient entity that knows the game's start and end states.

---

## III. Program Description

The engine first asks whether to play a standard Tic-Tac-Toe game (3×3).  
If yes, it initializes the board accordingly; otherwise, it creates an `n×n` custom board.  
In the custom game, the match continues until all moves are exhausted. The game alternates turns, reads moves, updates the board, and checks for winners.

![Flowchart](flowchart.png)

---

## IV. Implementation

### A. `TicTacToe` Class (Java)  
Handles board setup, win conditions, and game status.

- `public TicTacToe(int n)`: Initializes a custom `n×n` board.  
- `public int getXgot3()`: Returns the score of X (number of rows with 3 Xs).  
- `public boolean isCustomGridGame()`: Returns whether a custom grid is used.

### B. `TTTGame` Class (Java)  
Manages moves, turns, and interaction with the board.

- `public TTTGame()`: Initializes a standard game.  
- `public TTTGame(int n)`: Initializes a custom grid game.

### C. `TictactoeWithAi.c` (C)  
Implements minimax-based AI with a menu setup for Ubuntu, supporting two-player and AI modes.

- `int minimax(int isMaximizing)`: Recursively simulates all moves and scores them.  
- `void aiMove()`: Determines the best move using minimax.  
- `char checkWin()`: Checks board status — 1 (AI win), -1 (player win), `' '` (draw).

---

## V. Tests and Results

The AI was tested with random and optimal inputs — it never lost.  
Debug output showed how the AI evaluated all possibilities and avoided losing paths.  
Custom grid functionality was tested with various win conditions — horizontal, vertical, and diagonal.

---

## VI. Lessons Learnt

- Learned how to build AI using the **minimax algorithm**.
- Gained understanding of grid representation with matrices.
- Explored two-player turn logic and implemented custom rules in Tic-Tac-Toe.
- Implemented complex win-checking for custom `n×n` grids.

**Challenges**:  
Custom win logic introduced many logical errors and bugs during development. It was complex in planning and more so in execution and debugging.

**Future Improvements**:

- Add UI/UX enhancements (colors, animations, windows).  
- Support multiplayer over LAN or internet.  
- Add AI difficulty levels: Easy, Medium, Impossible.

---
