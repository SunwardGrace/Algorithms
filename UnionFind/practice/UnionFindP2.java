/* *****************************************************************************
Union-find with specific canonical element.
Add a method find() to the union-find data type so that
find(i) returns the largest element in the connected component containing i. The operations,
union(), connected(), and find() should all take logarithmic time or better.
For example, if one of the connected components is
{1,2,6,9}, then the find() method should return
9 for each of the four elements in the connected components.
 **************************************************************************** */

public class UnionFindP2 {
    public static void main(String[] args) {
        int[][] logs = { { 1, 2 }, { 2, 6 }, { 6, 9 } };
        UnionFind uf = new UnionFind(12);
        for (int[] log : logs) {
            uf.union(log[0], log[1]);
        }
        System.out.println(uf.find(9));
    }

    static class UnionFind {
        private int[] parent;
        private int[] sz;
        private int[] maxEle;

        public UnionFind(int n) {
            parent = new int[n];
            sz = new int[n];
            maxEle = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                sz[i] = 1;
                maxEle[i] = i;
            }
        }

        public int root(int i) {
            while (parent[i] != i) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }

        public void union(int i, int j) {
            int root1 = root(i);
            int root2 = root(j);
            if (root1 == root2) return;
            if (sz[root1] < sz[root2]) {
                int tmp = root1;
                root1 = root2;
                root2 = tmp;
            }
            parent[root2] = root1;
            maxEle[root1] = maxEle[root1] < maxEle[root2] ? maxEle[root2] : maxEle[root1];
            sz[root1] += sz[root2];
        }

        public boolean connected(int i, int j) {
            return root(i) == root(j);
        }

        public int find(int i) {
            return maxEle[root(i)];
        }
    }
}
