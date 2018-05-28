package com.bala.algs.week8;

import com.bala.algs.week1.QuickFind;
import com.bala.algs.week1.UnionFind;
import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week4.MinPQ;

import edu.princeton.cs.algs4.In;

public class KruskalMST implements MinimumSpanTree {

	private Queue<Edge> tree;

	private double weight;

	public KruskalMST(EdgeWeightedGraph graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.tree = new LinkedListQueue<Edge>();
		this.weight = 0.0d;
		MinPQ<Edge> edges = new MinPQ<>();
		for (Edge edge : graph.edges()) {
			edges.insert(edge);
		}
		UnionFind uf = new QuickFind(graph.noOfVertices());
		while (!edges.isEmpty() && tree.size() < graph.noOfVertices() - 1) {
			Edge edge = edges.delMin();
			int v = edge.either();
			int w = edge.other(v);
			if (!uf.connected(v, w)) {
				uf.union(v, w);
				tree.enqueue(edge);
				weight += edge.getWeight();
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
		KruskalMST mst = new KruskalMST(graph);
		for (Edge edge : mst.edges()) {
			System.out.println(String.format("%d-%d-%1.2f", edge.either(), edge.other(edge.either()), edge.getWeight()));
		}
		System.out.println(mst.weight());

	}

}