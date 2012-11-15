package data;

public class BinaryTree {
	
	private Node root;
	/*
	 * Wrapper method
	 */
	public void add(String str){
		insert(root, str);
	}
	/*
	 * Insert a String in the binary tree. checks the string in every node.
	 * If the has a higher alphabetical value, it will go to the right
	 * If it has a low it will go left. the structure is shown in comments below.
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
