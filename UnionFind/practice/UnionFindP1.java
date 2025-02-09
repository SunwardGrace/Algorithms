/* *****************************************************************************
Given a social network containing n members and a log file containing
m timestamps at which times pairs of members formed friendships,
design an algorithm to determine the earliest time at which all members are connected
(i.e., every member is a friend of a friend of a friend ... of a friend).
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
The running time of your algorithm should be mlogn or better and use extra space proportional to n
 **************************************************************************** */

public class UnionFindP1 {
    public static void main(String[] args) {
        int n = 4;
        int[][] logs = { { 1, 0, 1 }, { 2, 1, 2 }, { 3, 2, 3 }, { 4, 0, 3 } };
        System.out.println(getTimeStamp(n, logs));
    }

    public static int getTimeStamp(int n, int[][] logs) {
        UnionFind uf = new UnionFind(n);
        for (int[] log : logs) {
            uf.union(log[1], log[2]);
            if (uf.getMaxSize() == n) {
                return log[0];
            }
        }
        return -1;
    }

    static class UnionFind {
        private int[] parent;
        private int[] sz;
        private int maxSize;

        public UnionFind(int n) {
            parent = new int[n];
            sz = new int[n];
            maxSize = 1;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                sz[i] = 1;
            }
        }

        private int root(int i) {
            while (parent[i] != i) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }

        public void union(int i, int j) {
            int rootI = root(i);
            int rootJ = root(j);
            if (rootI == rootJ) return;
            if (sz[rootI] < sz[rootJ]) {
                parent[rootI] = rootJ;
                sz[rootJ] += sz[rootI];
                maxSize = maxSize < sz[rootJ] ? sz[rootJ] : maxSize;
            }
            else {
                parent[rootJ] = rootI;
                sz[rootI] += sz[rootJ];
                maxSize = maxSize < sz[rootI] ? sz[rootI] : maxSize;
            }
        }

        public int getMaxSize() {
            return maxSize;
        }
    }
}
