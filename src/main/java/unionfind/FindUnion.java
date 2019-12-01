package unionfind;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FindUnion implements UnionFind {

    private final int[] ids;
    private final int[] sz;

    public FindUnion(int count) {
        this.ids = IntStream.range(0, count).toArray();
        this.sz = new int[count];
        Arrays.fill(sz, 1);
    }

    @Override
    public void union(int p, int q) {
        final int i = root(p);
        final int j = root(q);
        if (i == j) return;

        if (sz[i] < sz[j]) {
            ids[i] = j;
            sz[j] += sz[i];
        } else {
            ids[j] = i;
            sz[i] += sz[j];
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (ids[i] != i) {
            ids[i] = ids[ids[i]];
            i = ids[i];
        }

        return i;
    }
}
