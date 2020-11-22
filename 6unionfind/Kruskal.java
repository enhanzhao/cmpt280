//Enhan Zhao enz889 11097118 cmpt280 a8


import lib280.graph.Vertex280;
import lib280.graph.WeightedEdge280;
import lib280.graph.WeightedGraphAdjListRep280;
import lib280.tree.ArrayedMinHeap280;

public class Kruskal {
	
	public static WeightedGraphAdjListRep280<Vertex280> minSpanningTree(WeightedGraphAdjListRep280<Vertex280> G) {
		WeightedGraphAdjListRep280 minTree = new WeightedGraphAdjListRep280(G.numVertices(), false);	// Undirected weighted graph that contains the same items as G, without edges
		//makes sure there are no copies
		minTree.ensureVertices(G.numVertices());
		UnionFind280 unionFind = new UnionFind280(minTree.numVertices());
		//heap container to hold all edges sorted
		ArrayedMinHeap280 edgesSorted = new ArrayedMinHeap280(G.numEdges() * 2);
		G.goFirst();
		while (G.itemExists()){
			G.eGoFirst(G.item());
			//traverse all edges
			while (G.eItemExists()){
				edgesSorted.insert(G.eItem());
				//increment edge
				G.eGoForth();
			}
			//increment vertex
			G.goForth();
		}
		//go through all edges, get first vertex index and index adjacent to it,
		while (!edgesSorted.isEmpty()) {
			WeightedEdge280 currentEdge = (WeightedEdge280) edgesSorted.item();
			int firstVertexIndex = ((Vertex280) currentEdge.firstItem()).index();
			int adjacentIndex = ((Vertex280) currentEdge.secondItem()).index();
			if (unionFind.find(firstVertexIndex) != unionFind.find(adjacentIndex)) {
				//add both to mstree
				minTree.addEdge(firstVertexIndex,adjacentIndex);
				//set weight
				minTree.setEdgeWeight(firstVertexIndex, adjacentIndex, G.getEdgeWeight(firstVertexIndex,adjacentIndex));
				unionFind.union(firstVertexIndex, adjacentIndex);

			}
			edgesSorted.deleteItem();
		}
		return minTree;
	}
	
	
	public static void main(String args[]) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<Vertex280>(1, false);
		// If you get a file not found error here and you're using eclipse just remove the 
		// 'Kruskal-template/' part from the path string.
		G.initGraphFromFile("/home/paul/IdeaProjects/Kruskal-Template/mst.graph");
		System.out.println(G);
		
		WeightedGraphAdjListRep280<Vertex280> minST = minSpanningTree(G);
		
		System.out.println(minST);
	}
}


