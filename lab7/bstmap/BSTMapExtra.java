package bstmap;


import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Set;

/**
 * @author hejiayuan
 * @create 2024-03-11-21:46
 */
public class BSTMapExtra <K extends Comparable<K>, V> implements Map61B<K,V>{

    private Node root;
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        Node iterate = getIterate(key);
        return iterate == null ? null : iterate.v;
    }

    private Node getIterate(K key) {
        Node node = root;
        while (node != null && !node.k.equals(key)) {
            int cmp = key.compareTo(node.k);
            if (cmp < 0) {
                node = node.left;
            }else if (cmp > 0) {
                node = node.right;
            }
        }

        return node;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        root = putRecursive(root, key,value);
    }

    private Node putRecursive(Node node, K key, V value) {
        if (node == null) {
            return new Node(key,value,1);
        }
        int cmp = key.compareTo(node.k);
        if (cmp < 0) {
            return node.left = putRecursive(node.left,key,value);
        }else if (cmp > 0) {
            return node.right = putRecursive(node.right,key,value);
        }
        node.v = value;
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }


    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        Node removed = new Node();
        root = removeRecursive(root,key,removed);
        return removed.v;
    }

    private Node removeRecursive(Node node, K key, Node removed) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.k);
        if (cmp < 0 ) {
            return node.left = removeRecursive(node.left,key,removed);
        }else if (cmp > 0) {
            return node.right = removeRecursive(node.right,key,removed);
        }else {
            if (node.left == null) {
                return node.right;
            }else if (node.right == null) {
                return node.left;
            }
            node.right = swapSmallest(node.right,node,removed);
            return node;
        }
    }

    private Node swapSmallest(Node childRight, Node swap, Node removed) {
        if (childRight.left == null) {
            removed.v =swap.v;
            swap.v = childRight.v;
            return childRight.right;
        }else {
            childRight.left = swapSmallest(childRight.left,swap,removed);
            return childRight;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key");
        }
        V v = get(key);
        if (value == null) {
            if (v == null) {
                return remove(key);
            }
        }else if (value.equals(v)){
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class Node {
        K k;
        V v;
        Node left;
        Node right;
        int size;

        public Node() {
        }

        public Node(K k, V v,int size) {
            this.k = k;
            this.v = v;
            this.size = size;
        }
    }
}
