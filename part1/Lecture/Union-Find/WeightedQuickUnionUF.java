// public class newQuickUnionUF {
//     private int[] id;
//     private int[] size;

//     public newQuickUnionUF(int N) {
//         id = new int[N];
//         for (int i = 0; i < N; i++) {
//             id[i] = i;
//             size[i] = 1;
//         }
//     }

//     private int root(int i) {
//         while (i != id[i]) i = id[i];
//         return i;
//     }

//     public boolean connected(int p, int q) {
//         return root(p) == root(q);
//     }

//     public void union(int p, int q) {
//         int i = root(p);
//         int j = root(q);
//         if (i == j) return;
//         if(size[i] < size[j]) {
//             id[i] = j;
//             size[j] += size[i];
//         } else {
//             id[j] = i;
//             size[i] += size[j];
//         }
//     }
// }
public class WeightedQuickUnionUF {
    // parent[i] 表示节点 i 的父节点
    private final int[] parent;

    // size[i] 表示以 i 为根的树包含多少个节点
    // 只有当 i 是根节点时，size[i] 的值才有意义
    private final int[] size;

    public WeightedQuickUnionUF(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // 每个节点一开始都是自己的根
            size[i] = 1;   // 每棵树一开始只有一个节点
        }
    }

    // 找到节点 p 所在树的根节点
    private int root(int p) {
        while (p != parent[p]) {
            // p指向祖父
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    // 判断 p 和 q 是否属于同一个连通分量
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // 合并 p 和 q 所在的两棵树
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);

        // 已经连通，无需再次合并
        if (rootP == rootQ) {
            return;
        }

        // 小树连接到大树
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}