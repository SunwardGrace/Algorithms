/* *****************************************************************************
Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form:
Remove x from S.
Find the successor of x: the smallest y in S such that y≥x.
design a data type so that all operations (except construction)
take logarithmic time or better in the worst case.
 **************************************************************************** */

public class UnionFindP3 {
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        uf.remove(5);
        uf.remove(6);
        uf.remove(7);
        uf.remove(1);
        uf.remove(3);
        uf.remove(2);
        System.out.println(uf.findSuc(5));
        System.out.println(uf.findSuc(2));
    }

    static class UnionFind {
        private int[] parent;
        private int[] size;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int root(int i) {
            while (parent[i] != i) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }

        public void union(int p, int q) {
            int rootP = root(p);
            int rootQ = root(q);
            if (rootP == rootQ) return;
            if (size[rootP] > size[rootQ]) {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            else {
                parent[rootP] = rootQ;      // 根节点选择很重要
                size[rootQ] += size[rootP];
            }
        }

        public void remove(int x) {
            if (root(x) != x) {
                union(x, x + 1);
            }
            else {
                parent[x] = x + 1;
            }
        }

        public int findSuc(int x) {
            if (parent[x] == parent.length) return -1;
            return root(x);
        }
    }
}
