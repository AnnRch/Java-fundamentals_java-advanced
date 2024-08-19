package com.epam.rd.autocode.floodfill;

public interface FloodFill {
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {

//        throw new UnsupportedOperationException();
        return new FloodFill() {

            private int calcRows(String s){
                int rows = 0;
                for(int i = 0; i < s.length(); i++){
                    if(s.charAt(i) == '\n' || i == s.length()-1)
                        rows++;
                }
                return rows;
            }

            private char[][] mapToChars(String s, int rows, int cols){
                char[][] chars = new char[rows][cols];
                int row = 0;
                int col = 0;
                for(int i = 0; i < s.length(); i++){
                    if(s.charAt(i) == '\n'){
                        row++;
                        col = 0;
                        continue;
                    }
                    chars[row][col] = s.charAt(i);
                    col++;
                }
                return chars;
            }

            private char[][] copyContent(char[][] chars){
                char[][] copy = new char[chars.length][chars[0].length];
                for(int i = 0; i < copy.length; i++)
                    for(int j = 0; j < copy[0].length; j++){
                        copy[i][j] = chars[i][j];
                    }
                return copy;
            }

            private int getWatered(char[][] chars){
                int watered = 0;
                for(int i = 0; i < chars.length; i++){
                    for(int j = 0; j < chars[0].length; j++){
                        watered += chars[i][j] == '░' ? 1 : 0;
                    }
                }
                return watered;
            }

            private String tableToStringMap(char[][] arr){
                StringBuilder sb = new StringBuilder();
                for(char[] row : arr){
                    for(char c : row){
                        sb.append(c);
                    }
                    sb.append("\n");
                }
                sb.delete(sb.length()-1, sb.length());
                return sb.toString();
            }

            @Override
            public void flood(String map, FloodLogger logger) {

                logger.log(map);

                if(map.contains("█")){

                    int rows = calcRows(map);
                    int cols = map.substring(0,map.indexOf('\n')).length();
                    char[][] chars = mapToChars(map, rows, cols);

                    char[][] flooded = copyContent(chars);
                    int watered = getWatered(chars);

                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            if(watered == 0) break;
                            if(chars[i][j] == '░'){
                                if(i > 0)
                                    flooded[i-1][j] =  '░' ;
                                if(j > 0)
                                    flooded[i][j-1] =  '░' ;
                                if(i < rows - 1)
                                    flooded[i+1][j] = '░';
                                if(j < cols - 1)
                                    flooded[i][j+1] =  '░';
                                watered--;
                            }
                        }
                        if(watered == 0) break;
                    }

                    flood(tableToStringMap(flooded),logger);
                }

            }
        };
    }

    char LAND = '█';
    char WATER = '░';
}
