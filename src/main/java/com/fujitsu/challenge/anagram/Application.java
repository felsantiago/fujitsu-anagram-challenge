package com.fujitsu.challenge.anagram;

import java.util.List;

public class Application {

  /**
   * Demonstrates usage of the AnagramGenerator and prints the sorted permutations
   *
   * @param args Command line arguments (not used)
   * @returns void
   */
  public static void main(final String[] args) {
    final AnagramGenerator anagramGenerator = new AnagramGenerator();
    final String input = "abc";
    final List<String> sortedPermutations = anagramGenerator.generateSortedPermutations(input.toCharArray());
    final String output = String.join(", ", sortedPermutations.subList(1, sortedPermutations.size()));
    System.out.println(output);
  }
}
