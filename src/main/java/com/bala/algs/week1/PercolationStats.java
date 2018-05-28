package com.bala.algs.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private final int noOfTrials;
	
	private final double[] noOfOpenSites ;
	
	private double mean;
	
	private double stddev;
	
	public PercolationStats(int gridSize, int noOfTrials) {
		if( gridSize <= 0 || noOfTrials <= 0) {
			throw new IllegalArgumentException("Illegal Value");
		}
		
		this.noOfTrials = noOfTrials;
		this.noOfOpenSites = new double[noOfTrials];
		
		for (int i = 0; i < noOfTrials; i++) {
			int noOfCounts = 0;
			Percolation perc = new Percolation(gridSize);
			int row = 0, col = 0;
			do {
				do {
					row = StdRandom.uniform(1, gridSize+1);
					col = StdRandom.uniform(1, gridSize+1);
				} while(perc.isOpen(row, col));
				perc.open(row, col);
				noOfCounts++;
			} while(!perc.percolates());
			noOfOpenSites[i] = ((double) noOfCounts/(gridSize * gridSize));
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		this.mean = StdStats.mean(noOfOpenSites);
		return mean;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		stddev =  StdStats.stddev(noOfOpenSites);
		return stddev;
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return (mean - (stddev * 1.96))/ Math.sqrt(noOfTrials);
	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return (mean + (stddev * 1.96))/ Math.sqrt(noOfTrials);
	}

	public static void main(String[] args) {
		if(args.length != 2) {
			throw new IllegalArgumentException("Number of arguments is not equal to 2");
		}
		
		int gridSize = Integer.parseInt(args[0]);
		
		int noOfTrials = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(gridSize, noOfTrials);
		
		System.out.println(String.format("mean                          =  %f", stats.mean()));
		System.out.println(String.format("stddev                        =  %f", stats.stddev()));
		System.out.println(String.format("95 confidence interval       =  [%f, %f]", stats.confidenceLo(), stats.confidenceHi()));
		
	}
	
}