package org.sjsu.edu.model;

public class SudokuBoard {
	
	public SudokuBoard(char[][] board) {
		super();
		this.board = board;
	}
	public SudokuBoard() {
		
	}
	public char[][] getBoard() {
		return board;
	}
	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	private char[][] board;
}
