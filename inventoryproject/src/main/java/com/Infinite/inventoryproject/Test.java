package com.Infinite.inventoryproject;

public class Test {
	public static void main(String[] args) {
		
		StockDAO stock = new StockDAO();
		System.out.println(stock.searchitem("dsbfhb"));
	}
}
