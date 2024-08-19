package com.epam.rd.autocode.iterator;

import javax.swing.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Iterators {

    public static Iterator<Integer> intArrayTwoTimesIterator(int[] array){

//        throw new UnsupportedOperationException();
        return new Iterator<>() {
            private int index = 0;
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    int item = array[index];
                    counter++;
                    if(counter == 2){
                        counter = 0;
                        index++;
                    }
                    return item;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    public static Iterator<Integer> intArrayThreeTimesIterator(int[] array) {
//        throw new UnsupportedOperationException();
        return new Iterator<>() {

            private int index = 0;
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    int item = array[index];
                    counter++;
                    if(counter == 3){
                        counter = 0;
                        index++;
                    }
                    return item;
                }
               else {
                   throw new NoSuchElementException();
                }
            }
        };
    }

    public static Iterator<Integer> intArrayFiveTimesIterator(int[] array) {
//        throw new UnsupportedOperationException();
            return new Iterator<>() {

                private int index = 0;
                private int counter = 0;

                @Override
                public boolean hasNext() {
                    return index < array.length;
                }

                @Override
                public Integer next() {
                    if (hasNext()) {
                        int item = array[index];
                        counter++;
                        if(counter == 5){
                            counter = 0;
                            index++;
                        }
                        return item;
                    }
                   else{
                       throw new NoSuchElementException();
                    }
                }
            };
        }

    public static Iterable<String> table(String[] columns, int[] rows){

//        throw new UnsupportedOperationException();
        return () -> {

            return new Iterator<>() {

                int column = 0;
                int row = 0;

                @Override
                public boolean hasNext() {
                    return column < columns.length && row < rows.length;
                }

                @Override
                public String next() {
                    if(hasNext()){
                       String item = columns[column] + rows[row];
                        row++;
                        if(row == rows.length){
                            row = 0;
                            column++;
                        }
                       return item;
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                }
            };
        };
    }


}
