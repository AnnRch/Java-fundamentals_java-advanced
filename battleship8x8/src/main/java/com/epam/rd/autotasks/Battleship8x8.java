package com.epam.rd.autotasks;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {

        this.ships = ships;
    }

    public boolean shoot(String shot) {

//        throw new UnsupportedOperationException();
        if(!shot.matches("^[a-hA-H][1-8]$"))
            throw new IllegalArgumentException(shot);

        int row = Integer.parseInt(shot.substring(1));
        int col = shot.substring(0, 1).toLowerCase().equals("a") ? 1
                : shot.substring(0, 1).toLowerCase().equals("b") ? 2
                : shot.substring(0, 1).toLowerCase().equals("c") ? 3
                : shot.substring(0, 1).toLowerCase().equals("d") ? 4
                : shot.substring(0, 1).toLowerCase().equals("e") ? 5
                : shot.substring(0, 1).toLowerCase().equals("f") ? 6
                : shot.substring(0, 1).toLowerCase().equals("g") ? 7
                : 8;

        int index = (row - 1) * 8 + col - 1;

        StringBuilder shotsMap;
        if(shots == 0)
            shotsMap = new StringBuilder("0000000000000000000000000000000000000000000000000000000000000000");
        else
//
            shotsMap = new StringBuilder(Long.toBinaryString(shots));
        System.out.println(shotsMap);
        int diff = 64 - shotsMap.length();
        if(diff > 0)
            for(int i = 0; i < diff; i++)
                shotsMap.insert(0, "0");


        StringBuilder shootMap = shotsMap.replace(index,index + 1, "1");


        StringBuilder shipsMap = new StringBuilder(Long.toBinaryString(ships));
        diff = 64 - shipsMap.length();
        if(diff > 0)
            for(int i = 0; i < diff; i++)
                shipsMap.insert(0, "0");

        shots = Long.parseUnsignedLong(shootMap.toString(),2);


        if(shipsMap.charAt(index) == '1')
            return true;

        return false;
    }

    public String state() {

//        throw new UnsupportedOperationException();
        StringBuilder shootMap = new StringBuilder(Long.toBinaryString(shots));
        int diff = 64 - shootMap.length();
        if(diff > 0)
            for(int i = 0; i < diff; i++)
                shootMap.insert(0, "0");

        StringBuilder shipsMap = new StringBuilder(Long.toBinaryString(ships));
        diff = 64 - shipsMap.length();
        if(diff > 0)
            for(int i = 0; i < diff; i++)
                shipsMap.insert(0, "0");


        String[] stateMap = new String[8];

        for(int i = 0; i < 8; i++){
            stateMap[i] = "";
            for(int j = 0; j < 8; j++){
                int indexAt = i * 8 + j;
                System.out.println("index:"+indexAt);
                if(shipsMap.charAt(indexAt) == '1' && shootMap.charAt(indexAt) == '1')
                    stateMap[i] += "☒";
                else if(shipsMap.charAt(indexAt) == '1' && shootMap.charAt(indexAt) == '0')
                    stateMap[i] += "☐";
                else if(shipsMap.charAt(indexAt) == '0' && shootMap.charAt(indexAt) == '1')
                    stateMap[i] +="×";
                else
                    stateMap[i] += ".";
            }
            stateMap[i] += "\n";
        }

        StringBuilder resMap = new StringBuilder();
        for(String item: stateMap){
            resMap.append(item);
        }
        return resMap.toString();
    }
}
