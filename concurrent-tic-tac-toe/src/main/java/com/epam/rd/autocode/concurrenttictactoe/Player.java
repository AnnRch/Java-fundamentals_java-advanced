package com.epam.rd.autocode.concurrenttictactoe;

public interface Player extends Runnable{

    String isFinished();

    static Player createPlayer(final TicTacToe ticTacToe, final char mark, PlayerStrategy strategy) {

        return new Player() {

            @Override
            public String isFinished() {
                StringBuilder winner = new StringBuilder();
                int size = ticTacToe.table().length;

                //by row
                int count = 0;

                for (int i = 0; i < 3;i++) {
                    for(int j = 0; j < 2;j++){
                        if(ticTacToe.table()[i][j] != ' ' && ticTacToe.table()[i][j] == ticTacToe.table()[i][j+1])
                            count++;
                    }
                    if(count == 2){
                        winner.delete(0,winner.length());
                        winner.append(ticTacToe.table()[i][2]);
                        return winner.toString().repeat(3);
                    }
                    count = 0;
                }

                //by column
                for(int i = 0; i < 3;i++){
                    for(int j = 0; j < 2;j++){
                        if(ticTacToe.table()[j][i] != ' ' && ticTacToe.table()[j][i] == ticTacToe.table()[j+1][i])
                            count++;
                    }
                    if(count == 2){
                        winner.delete(0,winner.length());
                        winner.append(ticTacToe.table()[2][i]);
                        return winner.toString().repeat(3);
                    }
                    count = 0;
                }

                //by main diagonal
                for(int i = 0; i < 2;i++){
                    if(ticTacToe.table()[i][i] != ' ' && ticTacToe.table()[i][i] == ticTacToe.table()[i+1][i+1])
                        count++;
                }
                if(count == 2){
                    winner.delete(0,winner.length());
                    winner.append(ticTacToe.table()[0][0]);
                    return winner.toString().repeat(3);
                }


                //by anti-diagonal
                count = 0;
                for(int i = 0; i < 2;i++){
                    if(ticTacToe.table()[i][size - i - 1] != ' ' && ticTacToe.table()[i][size - i - 1] == ticTacToe.table()[i + 1][size - i - 2])
                        count++;
                }
                if(count == 2){
                    winner.delete(0,winner.length());
                    winner.append(ticTacToe.table()[0][2]);
                    return winner.toString().repeat(3);
                }
                return "";
            }

            @Override
            public void run() {

                     synchronized (ticTacToe){
                         while(!ticTacToe.isFull() && !isFinished().equals("XXX") && !isFinished().equals("OOO")) {
                            while(mark == ticTacToe.lastMark()){
                                try{
                                    ticTacToe.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            if(mark != ticTacToe.lastMark() &&
                                !isFinished().equals("XXX") && !isFinished().equals("OOO")){
                                Move move = strategy.computeMove(mark, ticTacToe);
                                ticTacToe.setMark(move.row, move.column, mark);
                            }

                            ticTacToe.notifyAll();

                        }
                }
            }
        };
    }
}
