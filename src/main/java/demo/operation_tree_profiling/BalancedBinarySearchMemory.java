package demo.operation_tree_profiling;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class BalancedBinarySearchMemory {

    public static void main(String[] args) {
        var tree = new BalancedBinarySearch();

        // Insert some nodes
        int[] keys = generateKeys(100000);
        for (int key : keys) {
            tree.root = tree.insert(tree.root, key);
        }

        // Memory layout of a single node
        System.out.println(ClassLayout.parseInstance(tree.root).toPrintable());

        // Total memory footprint of the tree
        System.out.println(GraphLayout.parseInstance(tree).toFootprint());
    }

    private static int[] generateKeys(int i) {
        int[] keys = new int[i];
        for (int j = 0; j < keys.length; j++) {
            keys[j] = j;
        }
        return keys;
    }

}
