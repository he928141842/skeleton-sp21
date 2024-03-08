package bstmap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hejiayuan
 * @create 2024-03-07-21:46
 */
public class BSTMap<K extends Comparable, V> implements Map61B<K,V> {
    
    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();    }

    private Entry root;

    private int size;


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {

        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        Entry(K k, V v, Entry l,Entry r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }

        Entry(K k, V v) {
            key = k;
            val = v;
        }


        /** Stores the key of the key-value pair of this node in the list. */
        K key;
        /** Stores the value of the key-value pair of this node in the list. */
        V val;
        /** Stores the next Entry in the linked list. */
        Entry left;
        Entry right;
    }
}
