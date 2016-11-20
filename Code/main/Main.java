package main;

import menus.*;

public class Main {

	public static void main(String... args) {
		for (String string : args) {
			System.out.println(string + " ");
		}
		new MainMenu();
	}
}
