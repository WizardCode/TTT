// File: TicTacToe.java
// Author: Dr. Watts
// Contents: This file contains the TicTacToe game class

package com.dynet.kjanssen;

//import java.io.*;
//import java.util.*;

import java.util.Random;

class TicTacToe
{
	private	char [][] positions;

	TicTacToe ()
	{
		positions = new char [5] [5];
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				positions[i][j] = ' ';
	}

    public char GetPosition (int R, int C)
    {
        return positions[R][C];
    }
	
	public String toString ()
	{
		String S = new String ();;
		S += "   ";
		for (int j = 0; j < 3; j++)
			S += "+-------";
		S += "+\n";
		for (int i = 1; i <= 3; i++)
		{
			S += "   ";
			for (int j = 0; j < 3; j++)
				S += "|       ";
			S += "|\n";
			S += " " + i + " ";
			for (int j = 1; j <= 3; j++)
				S += "|" + "   " + positions[i][j] + "   ";
			S += "|\n";
			S += "   ";
			for (int j = 0; j < 3; j++)
				S += "|       ";
			S += "|\n";
			S += "   ";
			for (int j = 0; j < 3; j++)
				S += "+-------";
			S += "+\n";
		}
		S += "   ";
		for (int j = 0; j < 3; j++)
			S += "    " + (j+1) + "   ";
		S += "\n";
		return S;
	}
	
	public boolean Play (int row, int col, int who)
	{
		if (positions [row][col] == ' ')
		{
			positions[row][col] = (char) ('0' + who);
			return true;
		}
		return false;
	}
	
	public int Test ()
	{
		// look for winning row
		for (int r = 1; r <= 3; r++)
			if (positions[r][1] != ' ')
			{
				int count = 1;
				for (int c = 2; c <= 3; c++)
					if (positions[r][c] == positions[r][1])
						count++; 
				if (count == 3)
					return positions[r][1] - '0'; 
			}
		// look for winning column
		for (int c = 1; c <= 3; c++)
			if (positions[1][c] != ' ')
			{
				int count = 1;
				for (int r = 2; r <= 3; r++)
					if (positions[r][c] == positions[1][c])
						count++; 
				if (count == 3)
					return positions[1][c] - '0'; 
			}
		// look for winning diagonal
		if (positions[1][1] != ' ')
			if (positions[2][2] == positions[1][1] && positions[3][3] == positions[1][1])
				return positions[1][1] - '0'; 
		if (positions[1][3] != ' ')
			if (positions[2][2] == positions[1][3] && positions[3][1] == positions[1][3])
				return positions[1][3] - '0'; 
		for (int r = 1; r <= 3; r++)
			for (int c = 1; c <= 3; c++)
				if (positions[r][c] == ' ')
					return 0;

        // if cat wins
		return 3;
	}

    public boolean MakeBestPlay (int who) {

        // the number of open tiles
        int openCount = 0;

        // look for winning move
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++)
                if (positions[i][j] == ' ') {
                    openCount++;

                    Play (i, j, who);
                    int test = Test();

                    if (test == who)
                        return true;

                    positions[i][j] = ' ';
                }

        // if no moves, return false
        if (openCount == 0)
            return false;

        // look for blocking move
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++)
                if (positions[i][j] == ' ') {
                    Play(i, j, who == 1 ? 2 : 1);
                    int test = Test();

                    if (test != 0 && test != 3) {
                        positions[i][j] = ' ';
                        Play(i, j, who);
                        return true;
                    }

                    positions[i][j] = ' ';
                }

        // if no winning or blocking move, pick random move
        Random random = new Random();
        int move = random.nextInt(openCount);
        openCount = 0;

        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++)
                if (positions[i][j] == ' ' && openCount++ == move) {
                    Play (i, j, who);
                    return true;
                }

        // all else fails, return false
        return false;
    }
}
