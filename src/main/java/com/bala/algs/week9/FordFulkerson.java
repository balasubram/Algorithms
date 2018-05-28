package com.bala.algs.week9;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

public class FordFulkerson {

	private int noOfVertices;

	private boolean[] marked;

	private FlowEdge[] edgeTo;

	private double maxFlowValue;

	public FordFulkerson(FlowNetwork flowNetwork, int source, int destination) {
		this.noOfVertices = flowNetwork.getNoOfVertices();
		validateVertex(source);
		validateVertex(destination);
		if (source == destination) {
			throw new IllegalArgumentException("Source and destination point to the same vertex");
		}

		maxFlowValue = findIfAvaliablePath(flowNetwork, destination);
		while (hasPathInFlowNetwork(flowNetwork, source, destination)) {
			double maxFlowTemp = Double.POSITIVE_INFINITY;
			for (int v = destination; v != source; v = edgeTo[destination].other(v)) {
				maxFlowTemp = Math.min(maxFlowTemp, edgeTo[v].residualCapacityTo(v));
			}

			for (int v = destination; v != source; v = edgeTo[destination].other(v)) {
				edgeTo[v].addResidualFlowTo(v, maxFlowTemp);
			}
			maxFlowValue += maxFlowTemp;
		}

	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= noOfVertices) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private double findIfAvaliablePath(FlowNetwork flowNetwork, int vertex) {
		double excess = 0.0;
		for (FlowEdge edge : flowNetwork.adj(vertex)) {
			if (vertex == edge.from()) {
				excess -= edge.getFlowValue();
			} else {
				excess += edge.getFlowValue();
			}
		}
		return excess;
	}

	private boolean hasPathInFlowNetwork(FlowNetwork flowNetwork, int source, int destination) {
		marked = new boolean[flowNetwork.getNoOfVertices()];
		edgeTo = new FlowEdge[flowNetwork.getNoOfVertices()];
		marked[source] = Boolean.TRUE;
		Queue<Integer> queue = new LinkedListQueue<Integer>();
		queue.enqueue(source);
		while (!queue.isEmpty()) {
			int v = queue.dequeue();
			for (FlowEdge edge : flowNetwork.adj(v)) {
				int w = edge.other(v);

				if (edge.residualCapacityTo(w) > 0.0) {
					if (!marked[w]) {
						queue.enqueue(w);
						edgeTo[w] = edge;
						marked[w] = Boolean.TRUE;
					}
				}
			}
		}
		return marked[destination];
	}

	public double getMaxFlowValue() {
		return this.maxFlowValue;
	}

	public boolean inCut(int vertex) {
		validateVertex(vertex);
		return marked[vertex];
	}

}