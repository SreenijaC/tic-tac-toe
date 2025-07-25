
---

# Tic-Tac-Toe (Java) — Portfolio 1: Human vs. Human Game

This repository contains the implementation of a **Human vs. Human Tic-Tac-Toe** game for **CSC-214 Portfolio 1**.

You can find the instructions for this portfolio project [here](https://morethanequations.com/Computer-Science/Portfolio-Projects/Tic-Tac-Toe).

---

## Overview

This version of the Tic-Tac-Toe game supports:

* 3x3 game board
* Two human players: Player X and Player O
* Turn-based gameplay via console
* Input validation for invalid moves and replay prompts
* Clear console output showing the current board after each move
* End-game detection (win or draw)
* Option to play again

---

## Project Structure

This app consists of multiple modular classes:

* `App` – the main entry point
* `Game` – handles game logic and flow
* `Board` – represents and manages the 3x3 board
* `Player` – represents player information (symbol only)
* `UserInput` – manages and validates console input
* `Computer Player` - implements computer-controlled player logic
---

## Build Instructions

We use [Gradle](https://gradle.org/) to automate common development tasks.

### 🔧 Building the App

```bash
./gradlew build
```

---

## Testing the App

This project includes unit tests for each class using JUnit 5.
You can run the automated test suite with:

```bash
./gradlew test
```

Test coverage includes:

* Game flow (win/draw)
* Board state handling
* Input validation
* Player object rules

---

## Running the App

To run the game from the terminal:

```bash
./gradlew run --quiet --console=plain
```

The flags above suppress Gradle’s extra logs so only the game console output is shown. The flags can be removed for full Gradle logs while debugging.

---
