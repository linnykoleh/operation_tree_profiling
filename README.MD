# Operation Tree Profiling

## Implement Balanced Binary Search Tree class and operations of insert/delete/search.

[BalancedBinarySearch.java](src/main/java/demo/operation_tree_profiling/BalancedBinarySearch.java)

## Profile space usage ( Confirm that you see O (n) )

### Java code for benchmarking

```java
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
```

### Benchmark results

```bash
/proc/sys/kernel/yama/ptrace_scope
demo.operation_tree_profiling.BalancedBinarySearch$Node object internals:
OFF  SZ                                                      TYPE DESCRIPTION               VALUE
  0   8                                                           (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   4                                                           (object header: class)    0x00c01458
 12   4                                                       int Node.key                  65535
 16   4                                                       int Node.height               17
 20   4   demo.operation_tree_profiling.BalancedBinarySearch.Node Node.left                 (object)
 24   4   demo.operation_tree_profiling.BalancedBinarySearch.Node Node.right                (object)
 28   4        demo.operation_tree_profiling.BalancedBinarySearch Node.this$0               (object)
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

demo.operation_tree_profiling.BalancedBinarySearch@1f7030a6d footprint:
     COUNT       AVG       SUM   DESCRIPTION
         1        16        16   demo.operation_tree_profiling.BalancedBinarySearch
    100000        32   3200000   demo.operation_tree_profiling.BalancedBinarySearch$Node
    100001             3200016   (total)
```

> Each node in the BalancedBinarySearch tree takes up 32 bytes in memory.
> The entire tree with 100,000 nodes uses around 3.2 MB of memory.
> The memory layout of the Node object consists of a header (object metadata) and fields like the key, height, and pointers to child nodes.

## Profile time consumption ( Confirm that you see O (log n))

### Java code for benchmarking

```java
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
```

### Benchmark results

```bash
# JMH version: 1.36
# VM version: JDK 17.0.2, OpenJDK 64-Bit Server VM, 17.0.2+8-86
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home/bin/java
# VM options: -javaagent:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/lib/idea_rt.jar=54710:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkDelete

# Run progress: 0.00% complete, ETA 00:25:00
# Fork: 1 of 5
# Warmup Iteration   1: 567504942.020 ops/s
# Warmup Iteration   2: 1085326485.171 ops/s
# Warmup Iteration   3: 635488659.123 ops/s
# Warmup Iteration   4: 583686350.173 ops/s
# Warmup Iteration   5: 610567710.491 ops/s
Iteration   1: 657420677.945 ops/s
Iteration   2: 655820394.572 ops/s
Iteration   3: 639683231.468 ops/s
Iteration   4: 647828657.057 ops/s
Iteration   5: 657249385.270 ops/s

# Run progress: 6.67% complete, ETA 00:23:33
# Fork: 2 of 5
# Warmup Iteration   1: 1095840261.188 ops/s
# Warmup Iteration   2: 1075281508.226 ops/s
# Warmup Iteration   3: 1079413810.926 ops/s
# Warmup Iteration   4: 1097668209.556 ops/s
# Warmup Iteration   5: 1095931219.561 ops/s
Iteration   1: 1096291562.096 ops/s
Iteration   2: 1091043147.301 ops/s
Iteration   3: 1088691288.818 ops/s
Iteration   4: 1082736879.266 ops/s
Iteration   5: 1107167488.671 ops/s

# Run progress: 13.33% complete, ETA 00:21:52
# Fork: 3 of 5
# Warmup Iteration   1: 1108092793.456 ops/s
# Warmup Iteration   2: 1097092419.388 ops/s
# Warmup Iteration   3: 1096263610.176 ops/s
# Warmup Iteration   4: 1090694338.824 ops/s
# Warmup Iteration   5: 1105036388.339 ops/s
Iteration   1: 1094836551.490 ops/s
Iteration   2: 1072591316.504 ops/s
Iteration   3: 1097865455.996 ops/s
Iteration   4: 1096663592.964 ops/s
Iteration   5: 941173103.819 ops/s

# Run progress: 20.00% complete, ETA 00:20:12
# Fork: 4 of 5
# Warmup Iteration   1: 1046639954.186 ops/s
# Warmup Iteration   2: 1019046868.825 ops/s
# Warmup Iteration   3: 1099671511.180 ops/s
# Warmup Iteration   4: 1105321447.810 ops/s
# Warmup Iteration   5: 1105645362.282 ops/s
Iteration   1: 1099158442.425 ops/s
Iteration   2: 1096262901.662 ops/s
Iteration   3: 1093023856.593 ops/s
Iteration   4: 1098273602.740 ops/s
Iteration   5: 1103927880.744 ops/s

# Run progress: 26.67% complete, ETA 00:18:31
# Fork: 5 of 5
# Warmup Iteration   1: 1019121344.936 ops/s
# Warmup Iteration   2: 1076349068.114 ops/s
# Warmup Iteration   3: 496068144.547 ops/s
# Warmup Iteration   4: 493744790.423 ops/s
# Warmup Iteration   5: 493165427.039 ops/s
Iteration   1: 493064200.557 ops/s
Iteration   2: 485568820.652 ops/s
Iteration   3: 490369715.183 ops/s
Iteration   4: 493995802.856 ops/s
Iteration   5: 494120092.194 ops/s


Result "demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkDelete":
  878993121.954 ±(99.9%) 197182549.202 ops/s [Average]
  (min, avg, max) = (485568820.652, 878993121.954, 1107167488.671), stdev = 263233061.744
  CI (99.9%): [681810572.752, 1076175671.155] (assumes normal distribution)


# JMH version: 1.36
# VM version: JDK 17.0.2, OpenJDK 64-Bit Server VM, 17.0.2+8-86
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home/bin/java
# VM options: -javaagent:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/lib/idea_rt.jar=54710:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkInsert

# Run progress: 33.33% complete, ETA 00:16:50
# Fork: 1 of 5
# Warmup Iteration   1: 18909014.449 ops/s
# Warmup Iteration   2: 18279845.715 ops/s
# Warmup Iteration   3: 21515103.786 ops/s
# Warmup Iteration   4: 21096633.905 ops/s
# Warmup Iteration   5: 20854449.632 ops/s
Iteration   1: 21271570.439 ops/s
Iteration   2: 20995880.183 ops/s
Iteration   3: 20888936.175 ops/s
Iteration   4: 20402233.211 ops/s
Iteration   5: 21379364.273 ops/s

# Run progress: 40.00% complete, ETA 00:15:09
# Fork: 2 of 5
# Warmup Iteration   1: 18624457.456 ops/s
# Warmup Iteration   2: 19543113.897 ops/s
# Warmup Iteration   3: 21148365.975 ops/s
# Warmup Iteration   4: 21094271.199 ops/s
# Warmup Iteration   5: 21206297.601 ops/s
Iteration   1: 21430678.206 ops/s
Iteration   2: 21618484.972 ops/s
Iteration   3: 21464554.581 ops/s
Iteration   4: 21284732.584 ops/s
Iteration   5: 20819632.309 ops/s

# Run progress: 46.67% complete, ETA 00:13:28
# Fork: 3 of 5
# Warmup Iteration   1: 19230845.677 ops/s
# Warmup Iteration   2: 19837548.256 ops/s
# Warmup Iteration   3: 20226257.221 ops/s
# Warmup Iteration   4: 20297393.207 ops/s
# Warmup Iteration   5: 19840253.673 ops/s
Iteration   1: 19407970.495 ops/s
Iteration   2: 19314545.371 ops/s
Iteration   3: 18765065.633 ops/s
Iteration   4: 19462628.827 ops/s
Iteration   5: 19349678.987 ops/s

# Run progress: 53.33% complete, ETA 00:11:47
# Fork: 4 of 5
# Warmup Iteration   1: 17732424.558 ops/s
# Warmup Iteration   2: 17614854.786 ops/s
# Warmup Iteration   3: 18529264.080 ops/s
# Warmup Iteration   4: 19962804.210 ops/s
# Warmup Iteration   5: 19964842.207 ops/s
Iteration   1: 19391293.749 ops/s
Iteration   2: 19399648.561 ops/s
Iteration   3: 19799673.968 ops/s
Iteration   4: 19918939.781 ops/s
Iteration   5: 18750246.151 ops/s

# Run progress: 60.00% complete, ETA 00:10:06
# Fork: 5 of 5
# Warmup Iteration   1: 17779368.749 ops/s
# Warmup Iteration   2: 19321051.445 ops/s
# Warmup Iteration   3: 20767303.704 ops/s
# Warmup Iteration   4: 20591295.614 ops/s
# Warmup Iteration   5: 20841850.947 ops/s
Iteration   1: 20574753.498 ops/s
Iteration   2: 20643628.842 ops/s
Iteration   3: 20670214.341 ops/s
Iteration   4: 20791715.470 ops/s
Iteration   5: 20643666.759 ops/s


Result "demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkInsert":
  20337589.495 ±(99.9%) 673117.866 ops/s [Average]
  (min, avg, max) = (18750246.151, 20337589.495, 21618484.972), stdev = 898593.093
  CI (99.9%): [19664471.629, 21010707.360] (assumes normal distribution)


# JMH version: 1.36
# VM version: JDK 17.0.2, OpenJDK 64-Bit Server VM, 17.0.2+8-86
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home/bin/java
# VM options: -javaagent:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/lib/idea_rt.jar=54710:/Users/linnyk/Applications/IntelliJ IDEA Ultimate.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkSearch

# Run progress: 66.67% complete, ETA 00:08:25
# Fork: 1 of 5
# Warmup Iteration   1: 1308019519.179 ops/s
# Warmup Iteration   2: 1309955063.640 ops/s
# Warmup Iteration   3: 1296785953.413 ops/s
# Warmup Iteration   4: 1295346376.128 ops/s
# Warmup Iteration   5: 1298633030.634 ops/s
Iteration   1: 1289871710.612 ops/s
Iteration   2: 1261311155.637 ops/s
Iteration   3: 1296360282.218 ops/s
Iteration   4: 1274355252.921 ops/s
Iteration   5: 1280645717.689 ops/s

# Run progress: 73.33% complete, ETA 00:06:43
# Fork: 2 of 5
# Warmup Iteration   1: 1278281893.114 ops/s
# Warmup Iteration   2: 1234314348.335 ops/s
# Warmup Iteration   3: 1276308437.495 ops/s
# Warmup Iteration   4: 1280592676.899 ops/s
# Warmup Iteration   5: 1291605329.436 ops/s
Iteration   1: 1300595023.256 ops/s
Iteration   2: 1286813148.449 ops/s
Iteration   3: 1276343763.950 ops/s
Iteration   4: 1300757665.343 ops/s
Iteration   5: 1305754676.868 ops/s

# Run progress: 80.00% complete, ETA 00:05:02
# Fork: 3 of 5
# Warmup Iteration   1: 1298856459.739 ops/s
# Warmup Iteration   2: 1305136960.647 ops/s
# Warmup Iteration   3: 1306782516.499 ops/s
# Warmup Iteration   4: 1296878544.901 ops/s
# Warmup Iteration   5: 1297322986.941 ops/s
Iteration   1: 1291374257.537 ops/s
Iteration   2: 1202889040.510 ops/s
Iteration   3: 1271379325.901 ops/s
Iteration   4: 1305545938.211 ops/s
Iteration   5: 1307920475.826 ops/s

# Run progress: 86.67% complete, ETA 00:03:21
# Fork: 4 of 5
# Warmup Iteration   1: 1280380066.927 ops/s
# Warmup Iteration   2: 1301616693.667 ops/s
# Warmup Iteration   3: 1286011535.999 ops/s
# Warmup Iteration   4: 1307051476.936 ops/s
# Warmup Iteration   5: 1308940754.263 ops/s
Iteration   1: 1307942659.955 ops/s
Iteration   2: 1304251083.352 ops/s
Iteration   3: 1286166739.744 ops/s
Iteration   4: 1306670642.659 ops/s
Iteration   5: 1299065314.399 ops/s

# Run progress: 93.33% complete, ETA 00:01:40
# Fork: 5 of 5
# Warmup Iteration   1: 1287330862.622 ops/s
# Warmup Iteration   2: 1306773604.877 ops/s
# Warmup Iteration   3: 1295293113.196 ops/s
# Warmup Iteration   4: 1286078661.080 ops/s
# Warmup Iteration   5: 1303086929.939 ops/s
Iteration   1: 1297606224.510 ops/s
Iteration   2: 1308800398.872 ops/s
Iteration   3: 1310447647.573 ops/s
Iteration   4: 1302279720.723 ops/s
Iteration   5: 1290245466.783 ops/s


Result "demo.operation_tree_profiling.BalancedBinarySearchBenchmark.benchmarkSearch":
  1290615733.340 ±(99.9%) 16915899.666 ops/s [Average]
  (min, avg, max) = (1202889040.510, 1290615733.340, 1310447647.573), stdev = 22582242.086
  CI (99.9%): [1273699833.674, 1307531633.006] (assumes normal distribution)


# Run complete. Total time: 00:25:14

Benchmark                                       Mode  Cnt           Score           Error  Units
BalancedBinarySearchBenchmark.benchmarkDelete  thrpt   25   878993121.954 ± 197182549.202  ops/s
BalancedBinarySearchBenchmark.benchmarkInsert  thrpt   25    20337589.495 ±    673117.866  ops/s
BalancedBinarySearchBenchmark.benchmarkSearch  thrpt   25  1290615733.340 ±  16915899.666  ops/s
```

