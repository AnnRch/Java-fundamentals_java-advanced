package com.efimchick.ifmo.collections;

import java.util.*;

class PairStringList implements Iterator<String>, List<String> {

    private List<String> pairList;
    private int currentIndex = 0;

    public PairStringList() {
        pairList = new ArrayList<>();
    }

    @Override
    public int size() {
        return pairList.size();
    }

    @Override
    public boolean isEmpty() {
        return pairList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return pairList.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return pairList.iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newObject = new Object[pairList.size()];
        for (int i = 0; i < newObject.length; i++) {
            newObject[i] = pairList.get(i);
        }
        return newObject;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] newArray = a;
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = (T) pairList.get(i);
        }
        return newArray;
    }

    public boolean add(String pair) {
        pairList.add(pair);
        pairList.add(pair);
        return true;
    }

    public void add(int index, String pair) {

        if(index < 0 || index > pairList.size()) {
            throw new IndexOutOfBoundsException();
        }
        else if(index == pairList.size()) {
            add(pair);
        }
        else {
            if(index == 0){
                    ArrayList<String> oldList = new ArrayList<>(pairList);
                    pairList = new ArrayList<>();
                    add(pair);
                    pairList.addAll(oldList);
            }
            else {
               int insertIndex = index % 2 == 1 ? index : index - 1;
                ArrayList<String> newList = new ArrayList<>(pairList.subList(0, insertIndex + 1));
                newList.add(pair);
                newList.add(pair);
                newList.addAll(pairList.subList(insertIndex + 1, pairList.size()));

                pairList = newList;
            }

        }
    }

    public String remove(int index){
        if(index > pairList.size()) {
            return null;
        }

        int actualIndex = index % 2 == 1 ? index - 1 : index;

       String prev = pairList.get(actualIndex);
       if(pairList.contains(pairList.get(actualIndex))) {
           pairList.remove(actualIndex);
           pairList.remove(actualIndex);
       }
       return prev;
    }

    @Override
    public int indexOf(Object o) {
        return pairList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return pairList.lastIndexOf(o);
    }

    @Override
    public ListIterator<String> listIterator() {
        return pairList.listIterator();
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        return pairList.listIterator(index);
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return pairList.subList(fromIndex, toIndex);
    }

    public boolean remove(Object obj){

        boolean changed = false;
        while(pairList.contains(obj.toString())){
            pairList.remove(obj.toString());
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c){
            if(!pairList.contains(o.toString())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        int curSize = pairList.size();
        for(String s : c){
                add(s);
        }
        return pairList.size() == curSize +  c.size();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        int curSize = pairList.size();
        for(String s : c){
            add(index, s);
            index += 2;
        }
        return pairList.size() - curSize == c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int curSize = pairList.size();
        int count = 0;
        for(Object o : c){
            if(pairList.contains(o.toString())){
                pairList.remove(o.toString());
                pairList.remove(o.toString());
                count+=2;
            }
        }
        return pairList.size() - curSize == count;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(String str : pairList){
            if(!c.contains(str)){
                remove(str);
            }
        }

        Set<?> pairSet = new HashSet<>(Arrays.asList(pairList));
        Set<?> cSet = new HashSet<>(Arrays.asList(c));

        return pairSet.equals(cSet);
    }

    @Override
    public void clear() {
        if(!pairList.isEmpty()) {
            pairList.clear();
        }
    }

    public String get(int index){
        return pairList.get(index);
    }

    public String set(int index, String pair){
        if(index >= pairList.size()) {
            return null;
        }
        String prev = pairList.get(index);
        pairList.set(index, pair);
        if(index % 2 == 0)
            pairList.set(index + 1, pair);
        else
            pairList.set(index - 1, pair);
        return prev;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < pairList.size();
    }

    @Override
    public String next() throws NoSuchElementException {
        if(currentIndex < pairList.size()) {
            String currentPair = pairList
                    .get(currentIndex)
                    .concat(pairList.get(currentIndex + 1));
            currentIndex += 2;
            return currentPair;
        }
        throw new NoSuchElementException();
    }
}
