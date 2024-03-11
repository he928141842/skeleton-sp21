package bstmap;

import javax.sound.midi.Soundbank;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.spi.ToolProvider;

/**
 * @author hejiayuan
 * @create 2024-03-07-21:46
 */
public class BSTMap<K extends Comparable, V> implements Map61B<K,V> {

    private Entry root;

    private int size;

    public static void main(String[] args) {
        BSTMap<String, Integer> map = new BSTMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);

    }

    public void printInOrder() {
        printInOrderRecursive(root);
    }

    public void printInOrderRecursive(Entry entry) {
        if (entry == null) {
            return;
        }
        if (entry.left != null) {
            printInOrderRecursive(entry.left);
        }
        System.out.println("entry value is : "+entry.val);

        if (entry.right != null) {
            printInOrderRecursive(entry.right);
        }

    }


    @Override
    public void clear() {
        root = null;
        size=0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        Entry entry = root.get(key);
        return entry == null ? null : entry.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Entry(key,value);
            size++;
            return;
        }
        root.insert(key,value);
        size++;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
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

        public void insert(K k, V v) {
            insertRecursive(this,k,v);
        }

        private Entry insertRecursive(Entry node, K k, V v) {
            if (node == null) {
                return new Entry(k,v);
            }else if (node.key.compareTo(k) > 0) {
                return node.left = insertRecursive(node.left,k,v);
            }else if (node.key.compareTo(k) < 0){
                return node.right = insertRecursive(node.right,k,v);
            }
            node.val = v;
            return node;
        }

        public Entry get(K key) {
            return getRecursive(this,key);
        }

        private Entry getRecursive(Entry entry, K key) {
            if (entry == null) {
                return null;
            }
            int compared = entry.key.compareTo(key);
            if (compared > 0) {
                return getRecursive(entry.left,key);
            }else if (compared < 0) {
                return getRecursive(entry.right,key);
            }
            return entry;
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
