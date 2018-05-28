package com.bala.algs.week1;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PathCompressedWeightedQuickUnion implements UnionFind {

	int[] unionArray;

	int count;

	int[] size;

	public PathCompressedWeightedQuickUnion(int size) {
		this.unionArray = new int[size];
		this.size = new int[size];
		for (int i = 0; i < size; i++) {
			this.unionArray[i] = i;
			this.size[i] = 1;
		}
		this.count = size;
	}

	private void validate(int n) {
		if (n < 0 || n >= unionArray.length) {
			throw new IllegalArgumentException("Illegal value of index is passed");
		}
	}

	public void union(int p, int q) {
		validate(p);
		validate(q);
		int pid = find(p);
		int qid = find(q);
		if (pid == qid)
			return;
		if (size[pid] > size[qid]) {
			unionArray[qid] = pid;
			size[pid] += size[qid];
		} else {
			unionArray[pid] = qid;
			size[qid] += size[pid];
		}
		count--;
	}

	public int find(int n) {
		validate(n);
		while (n != unionArray[n]) {
			unionArray[n] = unionArray[unionArray[n]];
			n = unionArray[n];
		}
		return n;
	}

	public boolean connected(int p, int q) {
		validate(p);
		validate(q);
		return find(p) == find(q);
	}

	public int count() {
		return count;
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickFindUF uf = new QuickFindUF(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q))
				continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components");
	}

}