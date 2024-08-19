package com.efimchick.ifmo.collections;

import java.util.*;

class SortedByAbsoluteValueIntegerSet implements Set<Integer> {

    private Set<Integer> set;
    Iterator<Integer> iterator;

    public SortedByAbsoluteValueIntegerSet() {
        set = new TreeSet<>(Comparator.comparingInt(Math::abs));
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        return set.iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[set.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = set.iterator().next();
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] result = Arrays.copyOf(a, set.size());
        for (int i = 0; i < result.length; i++) {
            result[i] = (T) set.iterator().next();
        }
        return result;
    }

    @Override
    public boolean add(Integer integer) {
      return set.add(integer);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!set.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        boolean changed = false;
        for (Integer integer : c) {
            changed |= set.add(integer);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for(Integer i : set){
            if(!c.contains(i)){
                changed |= set.remove(i);
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for(Object o : c){
            if(set.contains(o)){
                changed |= set.remove(o);
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        set.clear();
    }
}
