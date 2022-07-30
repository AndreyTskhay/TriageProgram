import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTree<E> implements Tree<E> {


	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p)>0;
	}

	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0;
	}

	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p==root();
	}
	
	@Override
	public boolean isEmpty() {
		return size()==0;
	}
	public int height (Position<E> p) {
		int h = 0;
		for(Position<E> c : children(p))
			h = Math.max(h, 1 + height(c));
		return h;
	}
	public int depth(Position<E> p) {
		if(isRoot(p))
			return 0;
		else return 1 + depth(parent(p));
	}

	//-------preorder--------
	//adds positions of the subtree rooted at Position p to the given snapshot.
	private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		snapshot.add(p);
		for(Position<E> c: children(p))
			preorderSubtree(c, snapshot);
	}
	//returns an iterable collection of positions of the tree, reported in preorder.
	public Iterable<Position<E>> preorder(){
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty())
			preorderSubtree(root(), snapshot);	//fills the snapshot recursively
		return snapshot;
	}
	//-------postorder--------
	//adds positions of the subtree rooted at Position p to the given snapshot.
	private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		for (Position<E> c:children(p))
			postorderSubtree(c, snapshot);
		snapshot.add(p);	//adds position p after exploring subtrees
	}
	//returns an iterable collection of positions of the tree, reported in postorder.
	public Iterable<Position<E>> postorder(){
		List<Position<E>> snapshot=new ArrayList<>();
		if(!isEmpty())
			postorderSubtree(root(), snapshot); 	//fills the snapshot recursively
		return snapshot;
	}
	//-------breadth-first-------- 
	//returns an iterable collection of positions of the tree in breadth-first order
	public Iterable<Position<E>> breadthfirst(){
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()) {
			ArrayQueue<Position<E>> fringe = new ArrayQueue<>();
			fringe.enqueue(root());	//start with the root
			while(!fringe.isEmpty()) {
				Position<E> p = fringe.dequeue();	//remove from front of the queue
				snapshot.add(p);	//report this position
				for(Position<E> c : children(p))
					fringe.enqueue(c);	//add children to back of queue
			}
		}
		return snapshot;
	}
	
	//----nested ElementIterator class----
	//This class adapts the iteration produced by positionts() to return element.
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> posIterator = positions().iterator();	
		
		public boolean hasNext() {
			return posIterator.hasNext();
		}
		public E next() {
			return  posIterator.next().getElement();
		}
	}
	public Iterable<Position<E>> positions() {return breadthfirst();}
	//returns an iterator of the elements stored in the list
	public Iterator<E> iterator() {
		return new ElementIterator(); }
	
}
