package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

/**
 * @author hejiayuan
 * @create 2024-03-11-21:46
 */
public class BSTMapExtra<K extends Comparable<K>, V> implements Map61B<K, V> {

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
            } else if (cmp > 0) {
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
        root = putRecursive(root, key, value);
    }

    private Node putRecursive(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(node.k);
        if (cmp < 0) {
            node.left = putRecursive(node.left, key, value);
        } else if (cmp > 0) {
            node.right = putRecursive(node.right, key, value);
        }else {
            node.v = value;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }


    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        Iterator<K> iterator = iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        Node removed = new Node();
        root = removeRecursive(root, key, removed);
        return removed.v;
    }

    private Node removeRecursive(Node node, K key, Node removed) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.k);
        if (cmp < 0) {
            node.left = removeRecursive(node.left, key, removed);
        } else if (cmp > 0) {
            node.right = removeRecursive(node.right, key, removed);
        } else {
            removed.v = node.v;
            node.size = 1 + size(node.left) + size(node.right);
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.right = swapSmallest(node.right, node, removed);
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node swapSmallest(Node childRight, Node swap, Node removed) {
        if (childRight.left == null) {
            swap.v = childRight.v;
            swap.k = childRight.k;
            return childRight.right;
        } else {
            childRight.left = swapSmallest(childRight.left, swap, removed);
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
        } else if (value.equals(v)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<K> {
        BSTMapExtra.Node cur;

        public BSTIterator(BSTMapExtra.Node cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K key = null;
            while (cur != null) {
                if (cur.left == null) {
                    //visit 没有左节点，访问当前节点、记录下一个访问的节点后退出
                    key = (K) cur.k;
                    cur = cur.right;
                    break;
                }else {
                    //左节点不为空时，找到左节点的最右侧未连线的节点
                    BSTMapExtra.Node pre = cur.left;
                    while (pre.right != null && pre.right != cur) {
                        pre = pre.right;
                    }
                    //最右侧节点未连线 将他与当前节点连线，并将当前节点指向左节点
                    if (pre.right == null) {
                        pre.right = cur;
                        cur = cur.left;
                    }else {
                        //最右侧节点已连线 代表已经访问过pre节点 visit当前节点后 将下一个要访问的节点指向当前节点右侧节点
                        pre.right = null;
                        key = (K) cur.k;
                        cur = cur.right;
                        break;
                    }
                }
            }
            return key;
        }
        //用栈实现树的遍历
        //Stack<BSTMapExtra.Node> stack;
        //
        //public BSTIterator() {
        //    stack = new Stack<>();
        //    putLeft(stack,root);
        //}
        //
        //private void putLeft(Stack<BSTMapExtra.Node> stack, BSTMapExtra.Node node) {
        //    if (node == null) {
        //        return;
        //    }
        //    BSTMapExtra.Node cur = node;
        //    while (cur != null) {
        //        stack.push(cur);
        //        cur = cur.left;
        //    }
        //}
        //
        //@Override
        //public boolean hasNext() {
        //    return !stack.isEmpty();
        //}
        //
        //@Override
        //public K next() {
        //    if (stack.isEmpty()) {
        //        return null;
        //    }
        //    BSTMapExtra.Node cur = stack.pop();
        //    K key = (K) cur.k;
        //    putLeft(stack,cur.right);
        //
        //    return key;
        //}
    }

    private class Node {
        K k;
        V v;
        Node left;
        Node right;
        int size;

        public Node() {
        }

        public Node(K k, V v, int size) {
            this.k = k;
            this.v = v;
            this.size = size;
        }
    }
}
