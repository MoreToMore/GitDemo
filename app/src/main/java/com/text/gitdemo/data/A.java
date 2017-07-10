package com.text.gitdemo.data;

/**
 * Created by mengyuanyuan on 2017/7/9.
 */

public class A {
	private String nameA;
	public A(Builder builder ) {
		nameA = builder.name;
	}

	private static class Builder {
		private String name;
	}
}
