package com.bala.algs.week8;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week4.MinPQ;

import edu.princeton.cs.algs4.In;

public class KruskalMST2 implements MinimumSpanTree {

	private Queue<Edge> tree;

	private double weight;

	public KruskalMST2(EdgeWeightedGraph graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.tree = new LinkedListQueue<Edge>();
		this.weight = 0.0d;
		MinPQ<Edge> edges = new MinPQ<>();
		for (Edge edge : graph.edges()) {
			edges.insert(edge);
		}
		while (!edges.isEmpty()) {
			Edge edge = edges.delMin();
			int v = edge.either();
			edge.other(v);
			if (!hasCycle(tree, edge, graph.noOfVertices())) {
				tree.enqueue(edge);
				weight += edge.getWeight();
			}
		}
	}

	private boolean hasCycle(Queue<Edge> tree, Edge newEdge, int noOfVertices) {
		EdgeWeightedGraph graph = new EdgeWeightedGraph(noOfVertices);
		boolean[] marked = new boolean[graph.noOfVertices()];
		int edgeTo[] = new int[graph.noOfVertices()];
		for (int i = 0; i < graph.noOfVertices(); i++) {
			edgeTo[i] = -1;
		}
		int v1 = newEdge.either();
		edgeTo[v1] = 0;
		for (Edge edge : tree) {
			graph.addEdge(edge);
		}
		graph.addEdge(newEdge);
		dfs(graph, marked, edgeTo, v1);
		return hasCycle(edgeTo, newEdge.other(v1));
	}

	private boolean hasCycle(int[] edgeTo, int vertex) {
		Queue<Integer> cycle = new LinkedListQueue<>();
		int v = vertex;
		for (; (edgeTo[v] != -1 && edgeTo[v] != v); v = edgeTo[v]) {
			cycle.enqueue(v);
		}

		cycle.enqueue(v);

		int first = -1, last = -1;
		for (int v1 : cycle) {
			if (first == -1) {
				first = v1;
			}
			last = v1;
		}
		if (first == last)
			return true;
		else
			return false;
	}

	private void dfs(EdgeWeightedGraph graph, boolean[] marked, int edgeTo[], int vertex) {
		marked[vertex] = true;
		for (Edge edge : graph.adj(vertex)) {
			int other = edge.other(vertex);
			if (!marked[other]) {
				edgeTo[other] = vertex;
				dfs(graph, marked, edgeTo, other);
			}
		}
	}

	@Override
	public Iterable<Edge> edges() {
		return tree;
	}

	@Override
	public double weight() {
		return weight;
	}

	public static void main(String[] args) {
		In in = new In("algs-data/tinyEWG.txt");
		String vertices = in.readLine();
		EdgeWeightedGraph graph = new EdgeWeightedGraph(Integer.parseInt(vertices));
		int noOfedges = Integer.parseInt(in.readLine());
		for (int i = 0; i < noOfedges; i++) {
			String line = in.readLine();
			String[] splits = line.split("\\s+");
			Edge edge = new Edge(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]), Double.parseDouble(splits[2]));
			graph.addEdge(edge);
		}
		KruskalMST2 mst = new KruskalMST2(graph);
		for (Edge edge : mst.edges()) {
			System.out.println(String.format("%d-%d-%1.2f", edge.either(), edge.other(edge.either()), edge.getWeight()));
		}
		System.out.println(mst.weight());

	}

}