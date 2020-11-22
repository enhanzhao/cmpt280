//Enhan Zhao enz889 11097118 cmpt280 a8


import lib280.graph.Edge280;
import lib280.graph.GraphAdjListRep280;
import lib280.graph.Vertex280;


public class UnionFind280 {
	GraphAdjListRep280<Vertex280, Edge280<Vertex280>> G;
	
	/**
	 * Create a new union-find structure.
	 * 
	 * @param numElements Number of elements (numbered 1 through numElements, inclusive) in the set.
	 * @postcond The structure is initialized such that each element is in its own subset.
	 */
	public UnionFind280(int numElements) {
		G = new GraphAdjListRep280<Vertex280, Edge280<Vertex280>>(numElements, true);
		G.ensureVertices(numElements);		
	}
	
	/**
	 * Return the representative element (equivalence class) of a given element.
	 * @param id The elements whose equivalence class we wish to find.
	 * @return The representative element (equivalence class) of the element 'id'.
	 */
	public int find(int id) {
		GraphAdjListRep280 temp = G;
		//move to id location
		temp.goIndex(id);
		//move edge cursor to id's first edge
		temp.eGoFirst(G.item());
		while (temp.eItemExists()) {
			temp.goVertex(temp.eItemAdjacentVertex());
			temp.eGoFirst(temp.item());
		}
		return temp.itemIndex();
	}
	
	/**
	 * Merge the subsets containing two items, id1 and id2, making them, and all of the other elemnets in both sets, "equivalent".
	 * @param id1 First element.
	 * @param id2 Second element.
	 */
	public void union(int id1, int id2) {
		//if not equal, add edge from root1 to root2 (union)
		if (find(id1) != find(id2))
			G.addEdge(find(id1), find(id2));
	}

	public static void main(String[] args) {

		//testing given example
//		UnionFind280 uf1 = new UnionFind280(8);
//		uf1.G.addEdge(1,2);
//		uf1.G.addEdge(3,2);
//		uf1.G.addEdge(7,3);
//		uf1.G.addEdge(4,6);
//		uf1.G.addEdge(6,5);
//		System.out.println("7: " + uf1.find(7));
//		System.out.println("4: " + uf1.find(4));
//		System.out.println("6: " + uf1.find(6));
//		System.out.println("1: " + uf1.find(1));
//		System.out.println("8: " + uf1.find(8));
	}
	
}
