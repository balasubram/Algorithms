package com.bala.algs.week9;

import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private final int wins[];

	private final int losses[];

	private final int remaining[];

	private final Map<String, Integer> teamsToIntMap;

	private final int[][] remainingMatches;

	private final boolean[] eliminated;

	private final int noOfTeams;

	public BaseballElimination(String filename) {
		// create a baseball division from given filename in format specified below

		if (filename == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		In in = new In(filename);
		this.noOfTeams = in.readInt();
		wins = new int[noOfTeams];
		losses = new int[noOfTeams];
		remaining = new int[noOfTeams];
		teamsToIntMap = new HashMap<>();
		remainingMatches = new int[noOfTeams][noOfTeams];
		eliminated = new boolean[noOfTeams];
		for (int i = 0; i < noOfTeams; i++) {
			String teamName = in.readString();
			teamsToIntMap.put(teamName, i);
			wins[i] = in.readInt();
			losses[i] = in.readInt();
			remaining[i] = in.readInt();
			for (int j = 0; j < noOfTeams; j++) {
				int rem = in.readInt();
				if (i == j && rem != 0) {
					throw new IllegalArgumentException("A team cannot compete against itself");
				}
				remainingMatches[i][j] = rem;
			}

		}

		findTrivialElimination();
	}

	private void findTrivialElimination() {
		for (int index = 0; index < noOfTeams; index++) {
			int maxWins = wins[index] + remaining[index];
			for (int i = 0; i < noOfTeams; i++) {
				if (index == i)
					continue;

				if (maxWins < wins[i]) {
					eliminated[index] = Boolean.TRUE;
					break;
				}
			}
		}
	}

	private FlowNetwork constructNetwork(int teamValue) {

		int noOfVertices = noOfTeams + (noOfTeams * (noOfTeams - 1)) / 2 + 2;
		int sourceVertex = noOfVertices - 2;
		int destVertex = noOfVertices - 1;

		FlowNetwork flowNetwork = new FlowNetwork(noOfVertices);
		int numMatches = 0;
		for (int index = 0; index < noOfTeams; index++) {
			for (int i = index + 1; i < noOfTeams; i++) {
				int indexToI = noOfTeams + numMatches++;
				flowNetwork.addEdge(new FlowEdge(sourceVertex, indexToI, this.remainingMatches[index][i]));
				flowNetwork.addEdge(new FlowEdge(indexToI, index, Double.POSITIVE_INFINITY));
				flowNetwork.addEdge(new FlowEdge(indexToI, i, Double.POSITIVE_INFINITY));
			}
			flowNetwork.addEdge(new FlowEdge(index, destVertex, Math.max(0, wins[teamValue] + remaining[teamValue] - wins[index])));
		}

		return flowNetwork;

	}

	public int numberOfTeams() {
		// number of teams
		return this.noOfTeams;
	}

	public Iterable<String> teams() {
		// all teams
		return teamsToIntMap.keySet();
	}

	public int wins(String team) {
		// number of wins for given team
		if (team == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (teamsToIntMap.containsKey(team)) {
			return wins[teamsToIntMap.get(team)];
		}
		throw new IllegalArgumentException("Team is not in the list");
	}

	public int losses(String team) {
		// number of losses for given team
		if (team == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (teamsToIntMap.containsKey(team)) {
			return losses[teamsToIntMap.get(team)];
		}
		throw new IllegalArgumentException("Team is not in the list");
	}

	public int remaining(String team) {
		// number of remaining games for given team
		if (team == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (teamsToIntMap.containsKey(team)) {
			return remaining[teamsToIntMap.get(team)];
		}
		throw new IllegalArgumentException("Team is not in the list");

	}

	public int against(String team1, String team2) {
		// number of remaining games between team1 and team2
		if (team1 == null || team2 == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (teamsToIntMap.containsKey(team1) && teamsToIntMap.containsKey(team2)) {
			return remainingMatches[teamsToIntMap.get(team1)][teamsToIntMap.get(team2)];
		}

		throw new IllegalArgumentException("Either of the teams are not in the list");
	}

	public boolean isEliminated(String team) {
		// is given team eliminated?
		if (team == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		int teamIndex = teamsToIntMap.get(team);

		if (!teamsToIntMap.containsKey(team)) {
			throw new IllegalArgumentException("Team is not in the list");
		}

		if (eliminated[teamIndex]) {
			return Boolean.TRUE;
		}

		FlowNetwork flowNetwork = constructNetwork(teamIndex);
		new FordFulkerson(flowNetwork, flowNetwork.V() - 2, flowNetwork.V() - 1);
		for (FlowEdge e : flowNetwork.adj(flowNetwork.V() - 2)) {
			if (e.flow() < e.capacity()) {
				eliminated[teamIndex] = Boolean.TRUE;
				break;
			}
		}
		return eliminated[teamIndex];
	}

	public Iterable<String> certificateOfElimination(String team) {
		// subset R of teams that eliminates given team; null if not eliminated
		if (team == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (!teamsToIntMap.containsKey(team)) {
			throw new IllegalArgumentException("Team is not in the list");
		}

		Queue<String> listOfTeams = new Queue<>();
		int teamIndex = teamsToIntMap.get(team);
		if (!eliminated[teamIndex]) {
			return listOfTeams;
		}

		FlowNetwork flowNetwork = constructNetwork(teamIndex);
		FordFulkerson ff = new FordFulkerson(flowNetwork, flowNetwork.V() - 2, flowNetwork.V() - 1);
		for (Map.Entry<String, Integer> teams : teamsToIntMap.entrySet()) {
			if (!teams.getKey().equals(team) && ff.inCut(teams.getValue()))
				listOfTeams.enqueue(teams.getKey());
		}

		return listOfTeams;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(
				"/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/baseball/teams4.txt");
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}