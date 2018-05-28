package com.bala.algs.week10;

interface TriesST<Value> {

	void put(String key, Value val);

	Value get(String key);

	void delete(String key);

	boolean contains(String key);

	boolean isEmpty();

	int size();

	Iterable<String> keys();
	
	Iterable<String> keysWithPrefix(String prefix);
	
	String longestPrefixOf(String query);
	
	Iterable<String> keysThatMatch(String pattern);

}