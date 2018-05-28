package com.bala.algs.week7;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

	private Set<String> nouns = new HashSet<>();

	private Map<String, Bag<Integer>> nounToVertexMapping = new HashMap<>();

	private Map<Integer, String> vertexToNounMapping = new HashMap<>();

	private final SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) {
			throw new IllegalArgumentException("Argumet is null");
		}
		int noOfVertex = processSynsets(synsets);
		Digraph graph = new Digraph(noOfVertex);
		processHypernyms(hypernyms, graph);
		DirectedCycle cycle = new DirectedCycle(graph);
		if (cycle.hasCycle()) {
			throw new IllegalArgumentException("Graph has directed cycles");
		}
		sap = new SAP(graph);
	}

	private void processHypernyms(String hypernyms, Digraph graph) {
		In in = new In(hypernyms);
		String line = null;
		while ((line = in.readLine()) != null) {
			String[] splits = line.split(",");
			int vertex = Integer.parseInt(splits[0]);
			for (int i = 1; i < splits.length; i++) {
				int w = Integer.parseInt(splits[i]);
				graph.addEdge(vertex, w);
			}
		}
	}

	private int processSynsets(String synsets) {
		In in = new In(synsets);
		String line = null;
		int noOfLines = 0;
		while ((line = in.readLine()) != null) {
			String[] splits = line.split(",");
			String[] nouns = splits[1].split("\\s+");
			vertexToNounMapping.put(Integer.parseInt(splits[0]), splits[1]);
			for (String noun : nouns) {
				this.nouns.add(noun);
				Bag<Integer> integerBag = this.nounToVertexMapping.get(noun);
				if (integerBag == null) {
					integerBag = new Bag<>();
					this.nounToVertexMapping.put(noun, integerBag);
				}
				integerBag.add(Integer.parseInt(splits[0]));
			}
			noOfLines++;
		}
		return noOfLines;
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return Collections.unmodifiableSet(nouns);
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null) {
			throw new IllegalArgumentException("Argument is mull");
		}
		return nouns.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null) {
			throw new IllegalArgumentException("Argument is mull");
		}
		if (!nouns.contains(nounA) || !nouns.contains(nounB)) {
			throw new IllegalArgumentException("Argument does not belong to noun set");
		}

		return sap.length(nounToVertexMapping.get(nounA), nounToVertexMapping.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB in a shortest ancestral path (defined
	// below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null) {
			throw new IllegalArgumentException("Argument is mull");
		}
		if (!nouns.contains(nounA) || !nouns.contains(nounB)) {
			throw new IllegalArgumentException("Argument does not belong to noun set");
		}

		int ancestor = sap.ancestor(nounToVertexMapping.get(nounA), nounToVertexMapping.get(nounB));

		return vertexToNounMapping.containsKey(ancestor) ? vertexToNounMapping.get(ancestor) : null;
	}

	// do unit testing of this class
	public static void main(String[] args) {

		String synSets = "/Users/bj186016/EclipseWorkspace/Algorithms/bin/wordnet/synsets6.txt";

		String hypernyms = "/Users/bj186016/EclipseWorkspace/Algorithms/bin/wordnet/hypernyms6InvalidCycle.txt";

		WordNet wordNet = new WordNet(synSets, hypernyms);
		System.out.println(wordNet.distance("bank_statement", "floating-point_operation"));
		System.out.println(wordNet.sap("bank_statement", "floating-point_operation"));

	}

}