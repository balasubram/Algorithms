package com.bala.algs.week7;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

public class BreadthFirstSearch implements Search {

	boolean[] marked;

	int count;

	public BreadthFirstSearch(Graph graph, int vertex) {
		marked = new boolean[graph.noOfVertices()];
		validateVertex(vertex);
		bfs(graph, vertex);
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Edge is not in range");
		}
	}

	private void bfs(Graph graph, int vertex) {
		Queue<Integer> queue = new LinkedListQueue<Integer>();
		queue.enqueue(vertex);
		marked[vertex] = Boolean.TRUE;
		count++;
		while (!queue.isEmpty()) {
			int newVertex = queue.dequeue();
			for (Integer adj : graph.adj(newVertex)) {
				if (!marked[newVertex]) {
					queue.enqueue(adj);
					marked[vertex] = Boolean.TRUE;
					count++;
				}
			}
		}
	}

	@Override
	public boolean marked(int v) {
		return marked[v];
	}

	@Override
	public int count() {
		return count;
	}

}
