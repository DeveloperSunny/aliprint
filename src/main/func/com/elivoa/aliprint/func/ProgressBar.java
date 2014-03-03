package com.elivoa.aliprint.func;

import java.util.ArrayList;
import java.util.List;

public class ProgressBar {
	static int barlength = 50;
	static char char0 = '.', char1 = '$';

	static List<String> animation1, animation2;

	static int barindex;
	static double percentage;

	static {
		animation1 = new ArrayList<String>();
		animation2 = new ArrayList<String>();

		animation1.add("      $^o^nnmn$$");
		animation1.add("      $^o^nMn $$");
		animation1.add("     $^o^nnmn $$");
		animation1.add("     $^o^nMn  $$");
		animation1.add("    $^o^nnmn  $$");
		animation1.add("    $^o^nMn   $$");
		animation1.add("   $^o^nnmn   $$");
		animation1.add("   $^o^nMn    $$");
		animation1.add("  $^o^nnmn    $$");
		animation1.add("  $^o^nMn     $$");
		animation1.add(" $^o^nnmn     $$");
		animation1.add(" $^o^nMn      $$");
		animation1.add("$^o^nnmn      $$");
		animation1.add("^o^nnmn      $$$");
		animation1.add("'o'nnmn      $$$");
		animation1.add("^o^nnmn      $$$");
		animation1.add("nmnn^o^      $$$");
		animation1.add(" nMn^o^      $$$");
		animation1.add(" nmnn^o^     $$$");
		animation1.add("  nMn^o^     $$$");
		animation1.add("  nmnn^o^    $$$");
		animation1.add("   nMn^o^    $$$");
		animation1.add("   nmnn^o^   $$$");
		animation1.add("    nMn^o^   $$$");
		animation1.add("    nmnn^o^  $$$");
		animation1.add("     nMn^o^  $$$");
		animation1.add("     nmnn^o^ $$$");
		animation1.add("      nMn^o^ $$$");
		animation1.add("      nmnn^o^$$$");

		animation2.add(" |");
		animation2.add(" /");
		animation2.add(" -");
		animation2.add(" \\");

	}

	public ProgressBar() {
	}

	private static long last_time = 0;
	private static int index = 0;

	public static void printProgressBar(String dialog, String title, long _l, long _totalRecords) {

		int l = new Long(_l).intValue();
		int totalRecords = new Long(_totalRecords).intValue();
		if (totalRecords == 0) {
			totalRecords = 1;
		}
		StringBuilder progressbar = new StringBuilder();
		barindex = l * barlength / totalRecords;
		// System.out.println("Progress of " + title + ":");
		percentage = l * 100.0 / totalRecords;
		progressbar.append(title + "[");
		progressbar.append(String.format("%6.2f%%", percentage));
		for (int i = 0; i < barindex; i++)
			progressbar.append(char1);
		for (int i = barindex; i < barlength; i++)
			progressbar.append(char0);
		// Progress of download 56/100
		// [100.00%||||........]
		progressbar.append("]");

		boolean move = System.currentTimeMillis() - last_time > 500;
		if (move) {
			index++;
			last_time = System.currentTimeMillis();
		}

		if (animation1 != null)
			progressbar.append(animation1.get(index % animation1.size()));
		if (animation2 != null)
			progressbar.append(animation2.get(index % animation2.size()));

		if (Strings.isNotEmpty(dialog)) {
			System.out.print(dialog);
			int spaces = progressbar.length() - dialog.length();
			for (int i = 0; i < spaces; i++) {
				System.out.print(" ");
			}
			System.out.println("");
		}
		progressbar.append("\r");
		System.out.print(progressbar.toString());
	}

	public static void main(String args[]) throws InterruptedException {
		for (int i = 1; i <= 989; i++) {
			Thread.sleep(100);
			printProgressBar("~", "test", i, 989);
		}
	}
}
