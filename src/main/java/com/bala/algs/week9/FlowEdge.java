package com.bala.algs.week9;

public class FlowEdge {

	private int vertexV;

	private int vertexW;

	private double capacity;

	private double flow;

	public FlowEdge(int vertexV, int vertexW, double capacity) {
		if (vertexV < 0)
			throw new IllegalArgumentException("vertex index must be a non-negative integer");
		if (vertexW < 0)
			throw new IllegalArgumentException("vertex index must be a non-negative integer");
		if (capacity < 0.0)
			throw new IllegalArgumentException("Edge capacity must be non-negative");
		this.vertexV = vertexV;
		this.vertexW = vertexW;
		this.capacity = capacity;
		this.flow = 0;

	}

	public FlowEdge(int vertexV, int vertexW, double capacity, double flow) {
		if (vertexV < 0)
			throw new IllegalArgumentException("vertex index must be a non-negative integer");
		if (vertexW < 0)
			throw new IllegalArgumentException("vertex index must be a non-negative integer");
		if (capacity < 0.0)
			throw new IllegalArgumentException("Edge capacity must be non-negative");
		if (flow < 0.0)
			throw new IllegalArgumentException("flow value must be non-negative");
		this.vertexV = vertexV;
		this.vertexW = vertexW;
		this.capacity = capacity;
		this.flow = flow;

	}

	public int to() {
		return vertexV;
	}

	public int from() {
		return vertexV;
	}

	public double getCapcity() {
		return this.capacity;
	}

	public double getFlowValue() {
		return this.flow;
	}

	public int other(int vertex) {
		if (vertex == vertexV)
			return vertexW;
		else if (vertex == vertexW)
			return vertexV;
		else
			throw new IllegalArgumentException("Invalid vertex value");
	}

	public double residualCapacityTo(int vertex) {
		if (vertex == vertexV)
			return capacity - flow;
		else if (vertex == vertexW)
			return flow;
		else
			throw new IllegalArgumentException("Invalid vertex value");
	}

	public void addResidualFlowTo(int vertex, double delta) {
		if (vertex == vertexV)
			flow -= delta;
		else if (vertex == vertexW)
			flow += delta;
		else
			throw new IllegalArgumentException("Invalid vertex value");

		if (Math.abs(flow) <= 0.0001)
			flow = 0.0d;

		if (Math.abs(capacity - flow) <= 0.0001)
			flow = capacity;

		if (flow < 0.0)
			throw new IllegalArgumentException("Flow cannot be negative");
		if (flow > capacity)
			throw new IllegalArgumentException("Flow cannot be greater than capacoty");

	}

}