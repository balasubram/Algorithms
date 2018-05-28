package com.bala.algs.week7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Outcast {

	private final WordNet wordNet;

	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		if (wordnet == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.wordNet = wordnet;
	}

	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast
		String outcast = "";
		int outcastDistance = Integer.MIN_VALUE;
		for (int i = 0; i < nouns.length; i++) {
			String noun = nouns[i];
			List<String> tempList = new ArrayList<>(Arrays.asList(nouns));
			tempList.remove(i);
			int distance = 0;
			for (String anotherNoun : tempList) {
				distance += wordNet.distance(noun, anotherNoun);
			}
			if (outcastDistance < distance) {
				outcastDistance = distance;
				outcast = noun;
			}
		}
		return outcast;
	}

	public static void main(String[] args) {

		String synSets = "/Users/bj186016/EclipseWorkspace/Algorithms/bin/wordnet/synsets50000-subgraph.txt";

		String hypernyms = "/Users/bj186016/EclipseWorkspace/Algorithms/bin/wordnet/hypernyms50000-subgraph.txt";

		WordNet wordNet = new WordNet(synSets, hypernyms);
		
		Outcast outcast = new Outcast(wordNet);
		outcast.outcast(new String[] {"Turing", "von_Neumann", "Mickey_Mouse"});
	}

}