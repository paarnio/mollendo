/* ElementModel.java
 * See: http://stackoverflow.com/questions/9761843/adding-a-swing-tree-selection-listener-with-custom-tree-model
 * 
 * See: https://docs.oracle.com/javase/7/docs/api/javax/swing/event/TreeModelListener.html
 * AND !!!
 * http://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html
 * 
 * Tested in XMLSwingApp project
 * (ElementModel See CH."Creating a Data Model" in oracle:tree tutorial (GenealogyExample.java))
 */
package siima.app.model.tree;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ElementModel implements TreeModel {

	private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();

	private ElementNode rootElementNode;

	private ElementNode lastSelectedNode;

	public ElementModel(ElementNode root) {

		rootElementNode = root;
	}

	/*
	 * from: GenealogyModel Used to toggle between show ancestors/show
	 * descendant and to change the root of the tree.
	 * 
	 * public void showAncestor(boolean b, Object newRoot) { showAncestors = b;
	 * Person oldRoot = rootPerson; if (newRoot != null) { rootPerson =
	 * (Person)newRoot; } fireTreeStructureChanged(oldRoot); }
	 */

	//////////////// Fire events //////////////////////////////////////////////

	/**
	 * GenealogyModel The only event raised by this model is
	 * TreeStructureChanged with the root as path, i.e. the whole tree has
	 * changed.
	 * 
	 * protected void fireTreeStructureChanged(ElementNode oldRoot) { int len =
	 * treeModelListeners.size(); TreeModelEvent e = new TreeModelEvent(this,
	 * new Object[] {oldRoot}); for (TreeModelListener tml : treeModelListeners)
	 * { tml.treeStructureChanged(e); } }
	 * 
	 */
	//////////////// TreeModel interface implementation ///////////////////////

	/**
	 * Adds a listener for the TreeModelEvent posted after the tree changes.
	 */
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.addElement(l);
	}

	/**
	 * Returns the child of parent at index index in the parent's child array.
	 */
	public Object getChild(Object parent, int index) {
		ElementNode p = (ElementNode) parent;

		return p.getChildAt(index);
	}

	/**
	 * Returns the number of children of parent.
	 */
	public int getChildCount(Object parent) {
		ElementNode p = (ElementNode) parent;

		return p.getChildCount();
	}

	/**
	 * Returns the index of child in parent.
	 */
	public int getIndexOfChild(Object parent, Object child) {
		ElementNode p = (ElementNode) parent;

		return p.getIndexOfChild((ElementNode) child);
	}

	/**
	 * Returns the root of the tree.
	 */
	public Object getRoot() {
		return rootElementNode;
	}

	/**
	 * Returns true if node is a leaf.
	 */
	public boolean isLeaf(Object node) {
		ElementNode p = (ElementNode) node;

		return p.getChildCount() == 0;
	}

	/**
	 * Removes a listener previously added with addTreeModelListener().
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.removeElement(l);
	}

	/**
	 * Messaged when the user has altered the value for the item identified by
	 * path to newValue. Not used by this model.
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
		System.out.println("*** valueForPathChanged : " + path + " --> " + newValue);
	}

	/*
	 * VPA: ADDED
	 * 
	 */
	public ElementNode getLastSelectedNode() {
		return lastSelectedNode;
	}

	public void setLastSelectedNode(ElementNode lastSelectedNode) {
		this.lastSelectedNode = lastSelectedNode;
		System.out.println("ElementModel:setLastSelectedNode nodetype: " + lastSelectedNode.getNodetype());
		
	}

}
