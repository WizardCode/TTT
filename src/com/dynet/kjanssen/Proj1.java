// File: Proj1.java
// Author: Dr. Watts
// Contents: This file contains an application program to play Tic Tac Toe
// 	     using and ASCII interface.

package com.dynet.kjanssen;

// import java.io.*;
import java.util.*;

class Proj1
{
	private TicTacToe ttt;
	private int player;
	private int winner;
	private boolean done;

	Proj1 ()
	{
		ttt = new TicTacToe ();
		player = 1;
		winner = 0;
		done = false;
	
		Scanner in = new Scanner(System.in);
		System.out.println (ttt);
		while (!done && winner == 0)
		{
			String rowStr, colStr;
			int row = 0, column = 0;
			do
			{
				System.out.println ( "Player " + player + ", enter the row for your play: " );
				rowStr = in.next();
				//cin >> rowStr;
				if (rowStr.charAt(0) == 'q' || rowStr.charAt(0) == 'Q')
					done = true;
				else
				try
				{
					row = Integer.parseInt (rowStr);
				}
				catch (RuntimeException e)
				{
					row = 0;
				}
			} while (!done && (row < 1 || row > 3));
			if (done)
				break;
			do
			{
				System.out.println ( "Player " + player + ", enter the column for your play: " );
				colStr = in.next();
				//cin >> colStr;
				if (colStr.charAt(0) == 'q' || colStr.charAt(0) == 'Q')
					done = true;
				else
				try
				{
					column = Integer.parseInt (colStr);
				}
				catch (RuntimeException e)
				{
					column = 0;
				}
			} while (!done && (column < 1 || column > 3));
			if (done)
				break;
			if (ttt.Play (row, column, player))
			{
				System.out.println (ttt);
				winner = ttt.Test();
				player = player == 1 ? 2 : 1;
			}
			else
				System.out.println ( "Invalid move - try again.\n" );
		}
		if (winner == 1)
			System.out.println ( "Player 1!! You win!!\n" );
		else if (winner == 2)
			System.out.println ( "Player 2!! You win!!\n" );
		else if (winner == 3)
			System.out.println ( "Sorry, the cat wins!\n" );
		else
			System.out.println ( "Sorry, there is no winner :(\n" );
	
	}

	public static void main (String [] args)
	{
		Proj1 proj1 = new Proj1 ();
	}
}
