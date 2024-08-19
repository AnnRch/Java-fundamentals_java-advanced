package com.epam.rd.autocode.concurrenttictactoe;

public interface TicTacToe {

    /**
     * Sets a mark in cell with specified coordinates.
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @param mark - mark to set.
     */
    void setMark(int x, int y, char mark);

    /**
     * Returns a COPY of current table with marks.
     * Note, edit of that copy should not affect the source TicTacToe object.
     * @return a copy of current table.
     */
    char[][] table();

    /**
     * Returns last mark that was set in a table.
     * @return last mark that was set in a table.
     */
    char lastMark();
    boolean isFull();
    boolean inRange(int x, int y);

    static TicTacToe buildGame() {

        return new TicTacToe() {

            private char lastMark = ' ';
            private char[][] table = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

            @Override
            public void setMark(int x, int y, char mark) {

                if(!inRange(x, y) || table[x][y] != ' ') {
                    throw new IllegalArgumentException();
                }

                    table[x][y] = mark;
                    lastMark = mark;

            }

            @Override
            public char[][] table() {
                char[][] tableCopy = new char[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        tableCopy[i][j] = table[i][j];
                    }
                }
                return tableCopy;
            }

            @Override
            public char lastMark() {
                return lastMark;
            }

            @Override
            public boolean isFull() {
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        if(table[i][j] == ' ')
                            return false;
                    }
                }
                return true;
            }

            @Override
            public boolean inRange(int x, int y) {
                return x >= 0 && x < 3 && y >= 0 && y < 3;
            }
        };

//        throw new UnsupportedOperationException();
    }
}
