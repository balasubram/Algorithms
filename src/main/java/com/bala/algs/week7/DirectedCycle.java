package com.bala.algs.week7;

import com.bala.algs.week2.ResizingArrayStack;
import com.bala.algs.week2.Stack;

public class DirectedCycle {

	private boolean marked[];

	private boolean onStack[];

	private int[] edgeTo;

	private Stack<Integer> cycle;

	public DirectedCycle(DirectedGraph graph) {
		this.marked = new boolean[graph.noOfVertices()];
		this.onStack = new boolean[graph.noOfVertices()];
		this.edgeTo = new int[graph.noOfVertices()];
		this.cycle = new ResizingArrayStack<Integer>(graph.noOfVertices());
		for (int vertex = 0; vertex < graph.noOfVertices(); vertex++) {
			if (!marked[vertex] && cycle == null)
				dfs(graph, vertex);
		}
	}

	private void dfs(DirectedGraph graph, int vertex) {
		marked[vertex] = Boolean.TRUE;
		onStack[vertex] = Boolean.TRUE;
		for (int adj : graph.adj(vertex)) {

			if (cycle != null)
				return;

			if (!marked[adj]) {
				edgeTo[adj] = vertex;
				dfs(graph, adj);
			}

			if (onStack[adj]) {
				this.cycle = new ResizingArrayStack<Integer>(graph.noOfVertices());
				for (int v = vertex; v != adj; v = edgeTo[v]) {
					cycle.push(v);
				}
				cycle.push(adj);
				cycle.push(vertex);
				assert check();
			}
		}
		onStack[vertex] = Boolean.FALSE;
	}

	private boolean check() {
		if (hasCycle()) {
			// verify cycle
			int first = -1, last = -1;
			for (int v : cycle()) {
				if (first == -1)
					first = v;
				last = v;
			}
			if (first != last) {
				System.err.printf("cycle begins with %d and ends with %d\n", first, last);
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {

		return cycle;
	}

}