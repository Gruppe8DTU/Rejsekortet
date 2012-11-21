package data;
/*
 * Class BinaryTree
 * 
 * Stores Strings as a binary tree
 * 
 * By: Jacob Espersen
 */
public class BinaryTree {
	
	private Node root;
	/*
	 * Wrapper method for adding a string
	 */
	public void add(String str){
		insert(root, str);
	}
	
	/*
	 * Wrapper method for checking if the binary tree contains a string
	 */
	public boolean contains(String str){
		if(root != null){
			if(findNode(root, str) != null)
				return true;
		}
		return false;
		
	}
	/*
	 * This is a recursive method for finding the node with the string we are searching for
	 * The basecase is that if the current node contains the string we are searching for
	 * it will return the node, if it equals null it will return null
	 * If not any of these cases are true, it will check if the String has a higher or lower value than the one in the node
	 * if the value is bigger, it will recurse on the node to the left
	 * if the value is smaller, it will recurse on the node to the right.
	 */
	private Node findNode(Node curNode, String str){
		if(curNode == null)
			return null;
		else if(str.equals(curNode.str))
			return curNode;
		else if(str.compareToIgnoreCase(curNode.str) < 0)
			return findNode(curNode.left, str);
		else
			return findNode(curNode.right, str);
	}
	
	/*
	 * Insert a String in the binary tree. checks the string in every node.
	 * If the has a higher alphabetical value, it will go to the right
	 * If it has a lower it will go left. the structure is shown in comments below.
	 */
	/* 					*
	 * 				  /   \
	 *				 *     *
	 *			   /   \  /  \
	 */
	private void insert(Node currentNode, String str){
		Node prev = root;
		while(currentNode != null){
			if(str.equals(currentNode.str))
				break;
			if(str.compareToIgnoreCase(currentNode.str) < 0)
				currentNode = currentNode.left;
			else
				currentNode = currentNode.right;
		}
		
		Node newNode = new Node(str);
		if(currentNode==root){
			this.root = newNode;
		}else if(str.compareToIgnoreCase(prev.str) < 0){
			prev.left = newNode;
		}else
			prev.right = newNode;				
	}

	/*
	 * Structure of each node in the binary tree
	 */
	class Node{
		public String str;
		public Node left, right;
		
		public Node(String str){
			this.str = str;
			left = null;
			right = null;
		}
	}
}
