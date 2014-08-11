package hu.farcsal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author zoli
 */
public class ArrayCollection<T> implements Collection<T>, Set<T>, List<T> {
    
    private final List<T> l;
    
    public ArrayCollection(T ... values) {
        l = new ArrayList<>();
        for (T val : values) {
            l.add(val);
        }
    }

    public ArrayCollection(List<T> list) {
        l = list;
    }

    public ArrayCollection() {
        l = new ArrayList<>();
    }
    
    // Collection and Set
    
    @Override
    public int size() {
        return l.size();
    }

    @Override
    public boolean isEmpty() {
        return l.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return l.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return l.iterator();
    }

    @Override
    public Object[] toArray() {
        return l.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return l.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return l.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return l.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return l.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return l.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return l.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return l.retainAll(c);
    }

    @Override
    public void clear() {
        l.clear();
    }

    // List
    
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return l.addAll(index, c);
    }

    @Override
    public T get(int index) {
        return l.get(index);
    }

    @Override
    public T set(int index, T element) {
        return l.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        l.add(index, element);
    }

    @Override
    public T remove(int index) {
        return l.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return l.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return l.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return l.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return l.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return l.subList(fromIndex, toIndex);
    }
    
    // Defaults
    @Override
    public void forEach(Consumer<? super T> action) {
        l.forEach(action);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return l.removeIf(filter);
    }

    @Override
    public boolean equals(Object obj) {
        return l.equals(obj);
    }

    @Override
    public int hashCode() {
        return l.hashCode();
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        l.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        l.sort(c);
    }

    @Override
    public Spliterator<T> spliterator() {
        return l.spliterator();
    }

    @Override
    public String toString() {
        return l.toString();
    }

}
