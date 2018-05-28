package com.bala.algs.week8;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Edge;

public class TestClient {

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
			System.out.println(String.format("%d-%d-%1.2f", edge.either(), edge.other(edge.either()), edge.weight()));
		}
		System.out.println(mst.weight());

	}

}