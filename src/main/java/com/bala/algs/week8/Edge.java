package com.bala.algs.week8;

public class Edge implements Comparable<Edge> {

	private int vertexV;

	private int vertexW;

	private double weight;

	public Edge(int vertexV, int vertexW, double weight) {
		this.vertexV = vertexV;
		this.vertexW = vertexW;
		this.weight = weight;
	}

	public int either() {
		return vertexV;
	}

	public int other(int vertex) {
		if (this.vertexV == vertex) {
			return vertexW;
		} else
			return vertexV;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int compareTo(Edge that) {
		return Double.compare(this.weight, that.weight);
	}

}