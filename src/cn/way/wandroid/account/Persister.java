package cn.way.wandroid.account;

import java.util.ArrayList;

public interface Persister <T>{
	boolean add(T obj);
	boolean delete(T obj);
	boolean update(T obj);
	T read(String objId);
	ArrayList<T> readAll();
}
