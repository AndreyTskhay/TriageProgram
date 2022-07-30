
import java.util.Iterator;
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> implements BinaryTree<E>{
	
	//Nested node class
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		//Constructs a node with the given element and neighbors.
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		//accessor methods
		public E getElement() { return element; }
		public Node<E> getParent() { return parent; }
		public Node<E> getLeft() { return left; }
		public Node<E> getRight() { return right; }
		//update methods
		public void setElement(E e) {element = e;}
		public void setParent(Node<E> parentNode) { parent = parentNode; }
		public void setLeft(Node<E> leftChild) { left = leftChild; }
		public void setRight (Node<E> rightChild) { right = rightChild; }
//		protected boolean isLeft() { return parent.getLeft().getElement()== element;}
//		protected void setRightNode(Node<E> rightNode) {
//			if(isLeft()) rightLeaf = parent.getRight();
//			else if(!isLeft()) rightLeaf = parent.parent;
	}
		//end of nested Node class
	//Factory function to create a new node storing element e.
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right){
		return new Node<E> (e, parent, left, right);
	}
	
	//LinkedBinaryTree instance variables
	protected Node<E> root = null;		//root of the tree
	private int size = 0;				//number of nodes in the tree
	public Node<E> lastNode = null;
	
	public LinkedBinaryTree() {}	//constructs an empty binary tree
	
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
		if(!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		Node<E> node = (Node<E>) p;		//safe cast
		if(node.getParent() == node)	//our convention for defunct node
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}

	//accessor methods (not already implemented in AbstractBinaryTree)
	//Returns the number of nodes in the tree.
	public int size() {
		return size;
	}
	
	//Returns the root Position of the tree (or null if tree is empty).
	public Position<E> root() {
		return root;
	}
	
	//Returns the Position of p's parent (or null if p is root).
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}
	
	//Returns the Position of p's left child (or null if no child exists)
	public Position<E> left (Position<E> p) throws IllegalArgumentException{
		Node<E> node = validate(p);
		return node.getLeft();
	}
	//Returns the Position of p's right child (or null if no child exists).
	public Position<E> right(Position<E> p) throws IllegalArgumentException{
		Node<E> node = validate(p);
		return node.getRight();
	}
	public Position<E> addRoot(E newRootElement){
		Node<E> newRoot= new Node<E>(newRootElement, null, null, null);
		root = newRoot;
		size++;
		return newRoot;
	}
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getRight() != null)
			throw new IllegalArgumentException("p already has a right child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
		Node<E> parent = validate(p);
		if(parent.getLeft() != null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	}
	//Replaces the element at Position p with e and returns the replaced element.
	public E set(Position<E> p, E e) throws IllegalArgumentException{
		 Node<E> node = validate(p);
		 E temp = node.getElement();
		 node.setElement(e);
		 return temp;
	}
	public E remove (Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(numChildren(p) == 2)
			throw new IllegalArgumentException("p has two children");
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		if (child != null)
			child.setParent(node.getParent());	//child's grandparent becomes its parent
		if (node == root)
			root = child;		//child  becomes root
		else {
			Node<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);
		return temp;
	}
	//Returns the given node as a Position
	private Position<E> position(Node<E> node){
		return node;
	}
	//Returns the first Position in the linked list (or null, if empty)
	public Position<E> first(){
		return position(root);
	}
	
	public Position<E> after (Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(node==root) //if the node is the root then give the position of the node's left child
			return position(root.getLeft());
		else if(node.getLeft()==null)	//if the node is the left child then return its right sibling...
			return node.getParent().getRight();
		else return node.getParent().getLeft();	// ...or vice versa
	}

	public String toString() {
		Iterator<E> kb = iterator();
		if (root==null) return "";
		StringBuilder output = new StringBuilder();
		for (Position<E> p : positions()) {
			int depth = depth(p);
			for(int i=0; i<depth; i++) {
				output.append("\t");
			}
			output.append(kb.next());
			output.append("\n");
			
		}
		return output.toString();
	}
}

