/*
 * Tested in XMLSwingApp project
 * 
 */

package siima.app.model.tree;

import java.util.List;
import java.util.Vector;

public class ElementNode {
	/*
	 * 
	 * 
	 */
	ElementNode parent;
	Vector<ElementNode> children;
	private String name;
	private String nodetype;
	private Object jaxbObject;

	public ElementNode(String name) {
		this.name = name;
		parent = null;
		nodetype = null;
		children = new Vector<ElementNode>();
	}

	public static void linkChildren(ElementNode pe, List<ElementNode> addchildren) {
		for (ElementNode kid : addchildren) {
			pe.children.addElement(kid);
			kid.parent = pe;
		}
	}

	/// getter methods required for the tree model ///

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getChildCount() {
		return children.size();
	}

	public ElementNode getChildAt(int i) {
		return children.elementAt(i);
	}

	public int getIndexOfChild(ElementNode kid) {
		return children.indexOf(kid);
	}

	/// Other getter and setter methods  ///
	public Object getJaxbObject() {
		return jaxbObject;
	}

	public void setJaxbObject(Object jaxbObject) {
		this.jaxbObject = jaxbObject;
	}

	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}
	
	public ElementNode getParent() {
		return parent;
	}
	
	/* parent is set by linkChildren() method
	public void setParent(ElementNode parent) {
		this.parent = parent;
	}
	*/
	

}
