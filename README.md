This problem solves the classic "stable marriage" problem in which equal sized groups of men and women have ranked preferences of the opposite gender which they want to marry, and I pair them in the most "stable" way possible, 
by using a pattern in which a there is never a downgrade in preference. I use 2D Lists to store the preference for the men and women, a regular ArrayList for the names of men and women, a LinkedList to store the single men, 
which is what keeps the algorithm going, and a HashMap to store the engaged couples.
