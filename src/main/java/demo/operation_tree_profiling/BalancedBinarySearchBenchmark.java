package demo.operation_tree_profiling;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;

@State(Scope.Benchmark)
public class BalancedBinarySearchBenchmark {

    BalancedBinarySearch tree;

    @Setup
    public void setUp() {
        tree = new BalancedBinarySearch();
    }

    @Benchmark
    public void benchmarkInsert() {
        int[] keys = {10, 20, 30, 40, 50, 25};
        for (int key : keys) {
            tree.root = tree.insert(tree.root, key);
        }
    }

    @Benchmark
    public void benchmarkSearch() {
        int[] searchKeys = {10, 25, 50};
        for (int key : searchKeys) {
            tree.find(tree.root, key);
        }
    }

    @Benchmark
    public void benchmarkDelete() {
        int[] deleteKeys = {25, 10};
        for (int key : deleteKeys) {
            tree.root = tree.delete(tree.root, key);
        }
    }
}
