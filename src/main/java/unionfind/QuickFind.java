package unionfind;

import java.util.stream.IntStream;

public class QuickFind implements UnionFind {

    private int[] ids;

    public QuickFind(int count) {
        ids = IntStream.range(0, count).toArray();
    }

    @Override
    public void union(int p, int q) {
        if (connected(p, q)) {
            return;
        }

        int pid = ids[p];
        int qid = ids[q];

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == pid) {
                ids[i] = qid;
            }
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return ids[p] == ids[q];
    }
}
