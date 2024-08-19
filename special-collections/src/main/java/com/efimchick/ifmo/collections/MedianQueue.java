package com.efimchick.ifmo.collections;

import java.util.*;

class MedianQueue implements Queue<Integer> {

    private Queue<Integer> q;
    private int size = 0;
    private Iterator<Integer> it;


    public MedianQueue() {
        q = new LinkedList<>();
    }

    public int size(){
        return q.size();
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return q.contains(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        it = q.iterator();
        while(it.hasNext())
            it.next();
        return it;
    }

    @Override
    public Object[] toArray() {
        Object[] a = new Object[q.size()];
        int i = 0;
        for (Integer integer : q) {
            a[i++] = integer;
        }
        return a;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] b = a;
        int i = 0;
        for (Integer integer : q) {
            b[i++] = (T) integer;
        }
        return b;
    }

    @Override
    public Integer peek() {
        it = q.iterator();
        return element();
    }

    @Override
    public boolean add(Integer integer) {
        it = q.iterator();
        size++;
        return q.add(integer);
    }

    @Override
    public boolean remove(Object o) {
        return q.remove(o);
    }

    @Override
    public Integer poll(){
       if(q.isEmpty())
           return null;
        Integer first = element();
        it = q.iterator();
        q.poll();
        size--;
        return first;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!q.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        int curSize = q.size();
        for(Integer integer : c) {
            q.offer(integer);
        }
        return q.size() == curSize + c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int count = 0;
        int curSize = q.size();
        for(Object o : c) {
           if(q.contains(o)){
               poll();
               count++;
           }
        }
        return q.size() - curSize == count;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(Integer integer : q) {
            if(!c.contains(integer) && !q.remove(integer)) {
                    return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        q.clear();
    }

    @Override
    public boolean offer(Integer integer) {
        q.offer(integer);
        it = q.iterator();
        size++;
        return true;
    }

    @Override
    public Integer remove() {
        return q.remove();
    }

    @Override
    public Integer element() {
        if(q.isEmpty()){
            return null;
        }

        int[] arr = new int[size];
        int i = 0;
        it = q.iterator();
        while(it.hasNext()){
            arr[i++] = it.next();
        }

        Arrays.sort(arr);

        int median;

        if(size % 2 == 1){
            median = arr[size / 2];
        }
        else
            median = arr[size / 2 - 1];

        Queue<Integer> newList = new LinkedList<>();
        newList.offer(median);

        for(Integer val : arr)
            if(val != median)
                newList.offer(val);

        q = new LinkedList<>(newList);
        return median;
    }
}
