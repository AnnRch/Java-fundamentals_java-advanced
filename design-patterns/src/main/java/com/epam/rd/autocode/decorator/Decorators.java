package com.epam.rd.autocode.decorator;

import javax.swing.*;
import java.util.*;

public class Decorators {
    public static List<String> evenIndexElementsSubList(List<String> sourceList) {

        return new List<String>() {
            List<String> evenIndexElements = new ArrayList<>();
            int index = 0;
            private List<String> evenList(){
                List<String> evenList = new ArrayList<>();
                for(int i = 0; i < sourceList.size(); i++){
                    if(i % 2 == 0){
                        evenList.add(sourceList.get(i));
                    }
                }
                evenIndexElements.addAll(evenList);
                return evenList;
            };

            @Override
            public int size() {
                return sourceList.size() / 2 + sourceList.size() % 2;
            }

            @Override
            public boolean isEmpty() {
                return evenList().isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return evenList().contains(o);
            }

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    @Override
                    public boolean hasNext() {
                        return index < evenList().size();
                    }

                    @Override
                    public String next() {
                        if(hasNext()){
                            return evenList().get(index++);
                        }
                        return "";
                    }
                };
            }

            @Override
            public Object[] toArray() {
                Object[] result = new Object[evenList().size()];
               for(int i = 0; i < size(); i++){
                   result[i] = evenList().get(i);
               }
                return result;
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int index) {
                return evenList().get(index);
            }

            @Override
            public String set(int index, String element) {
                return evenList().set(index, element);
            }

            @Override
            public void add(int index, String element) {

            }

            @Override
            public String remove(int index) {
                return "";
            }

            @Override
            public int indexOf(Object o) {
                return evenList().indexOf(o);
            }

            @Override
            public int lastIndexOf(Object o) {
                return evenList().lastIndexOf(o);
            }

            @Override
            public ListIterator<String> listIterator() {
                return new ListIterator<String>() {
                    @Override
                    public boolean hasNext() {
                        return index < evenList().size();
                    }

                    @Override
                    public String next() {
                        if(hasNext()){
                            return evenList().get(index++);
                        }
                        return "";
                    }

                    @Override
                    public boolean hasPrevious() {
                        return index - 1 >= 0;
                    }

                    @Override
                    public String previous() {
                        return evenList().get(--index);
                    }

                    @Override
                    public int nextIndex() {
                        return index + 1;
                    }

                    @Override
                    public int previousIndex() {
                        return index - 1;
                    }

                    @Override
                    public void remove() {
                        evenList().remove(index);
                    }

                    @Override
                    public void set(String s) {
                        evenList().set(index, s);
                    }

                    @Override
                    public void add(String s) {
                        evenList().add(s);
                    }
                };
            }

            @Override
            public ListIterator<String> listIterator(int index) {
                return null;
            }

            @Override
            public List<String> subList(int fromIndex, int toIndex) {
                return List.of();
            }
        };
    }


}
