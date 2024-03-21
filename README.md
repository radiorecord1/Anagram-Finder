# Anagram-Finder
Data Structures final project
Paul Arevalo
pa2658

Capstone Project "Anagram Finder"
These are the results for how long the program takes to produce the answer
for each data structure(the average of 5 executions):
0.625s for HashMap
1.101s for AVL Tree
2.336s for Binary Tree

The results match my expectations. I expected the HashMap to be the fastest because
it is the fastest in finding the target key. I expected the slowest one to be the
Binary Tree because as the program builds the tree it will likely become pretty
unbalanced leading to slower search times as opposed to the AVL. Because the AVL tree
retains a balanced condition it yields faster search times than the Binary Tree. It is
still slower than the Hashmap, it needs to traverse various entries to get to the target,
meanwhile the HashMap goes directly to where the target key is found.
