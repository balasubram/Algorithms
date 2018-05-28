package com.bala.algs.week2;

public interface Queue<Item> extends Iterable<Item> {

	public void enqueue(Item item) ;

	public Item dequeue() ;

	boolean isEmpty() ;

	public int size() ;
	
	public Item peek();

}