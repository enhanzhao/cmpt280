//Enhan Zhao enz889 11097118 cmpt 280 ass4 q 1

package lib280.dispenser;

import lib280.base.Dispenser280;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.tree.ArrayedBinaryTree280;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class ArrayedHeap280<I extends Comparable<I>> extends ArrayedBinaryTree280<I> implements Dispenser280<I>{
    /**
     * Constructor for class ArrayedHeap280.
     *
     * @param cap Maximum number of elements that can be in the lib280.tree.
     */
    public ArrayedHeap280(int cap) {
        super(cap);
        this.items = (I[]) new Comparable[cap+1];
    }

    @Override
    public void insert(I x) throws ContainerFull280Exception {
        if( this.isFull() ) throw new ContainerFull280Exception("Can't insert on full heap.");
        else {
            this.count++; //increment count
            this.items[count] = x; //add to the end
            int last = count;
            I temp;
            //while added item is larger than its parent, swap it and its parent
            while (items[last] != items[1] && (items[last].compareTo(items[findParent(last)]) > 0)) {
                temp = items[findParent(last)]; //temp is the parent of the added
                items[findParent(last)] = items[last]; //make parent the added
                items[last] = temp; //make the last item the old parent of the added
                last = findParent(last); //make last the current parent of the last
            }
            this.currentNode = 1; //set cursor to root.
        }
    }

    
    public void deleteItem() throws ContainerEmpty280Exception {
        if(!itemExists() ) throw new ContainerEmpty280Exception("Can't delete from empty heap");
//        items[1] = items[count];
//        items[count] = null;
//        count--;
        if( this.currentNode < this.count)
            this.items[this.currentNode] = this.items[this.count];

        // Either way, erase the bottom-right-most node
        this.items[this.count] = null;
        this.count--;

        // If we deleted the last item, move the cursor back one.
        if(this.currentNode > this.count) {
            this.currentNode = this.count;
        }
        int last = 1;
        I temp;
        while (maxChild(last) != last && items[last].compareTo(items[maxChild(last)]) < 0){
            int i = maxChild(last);
            temp = items[i];
            items[i] = items[last];
            items[last] = temp;
            last = i;
        }
    }

    /**
     * return the larger of a node's 2 children in an arrayed heap.
     * @param i the index of the node in the arrayed bintree.
     * @return the index child that is larger.
     */
    public int maxChild(int i){
        //only left children: return left
        if (i*2 <= this.count && i*2+1 > this.count)
            return i*2;
        // if left and right both exist, return max
        else if (i*2 <= this.count && i*2+1 <= this.count && i*2 >= 0 && i*2+1 >= 0){
            if (items[(i * 2)].compareTo(items[(i * 2 + 1)]) > 0) //left child is larger
                return i*2;
            else            //right child is larger
                return i*2+1;
        }
        //if has no children, return node
        return i;
    }

    /**
     * Helper for the regression test.  Verifies the heap property for all nodes.
     */
    private boolean hasHeapProperty() {
        for(int i=1; i <= count; i++) {
            if( findRightChild(i) <= count ) {  // if i Has two children...
                // ... and i is smaller than either of them, , then the heap property is violated.
                if( items[i].compareTo(items[findRightChild(i)]) < 0 ) return false;
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else if( findLeftChild(i) <= count ) {  // if n has one child...
                // ... and i is smaller than it, then the heap property is violated.
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else break;  // Neither child exists.  So we're done.
        }
        return true;
    }


    public static void main(String[] args) {

        ArrayedHeap280<Integer> H = new ArrayedHeap280<Integer>(10);

        // Empty heap should have the heap property.
        if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");

        // Insert items 1 through 10, checking after each insertion that
        // the heap property is retained, and that the top of the heap is correctly i.
        for(int i = 1; i <= 10; i++) {
            H.insert(i);
            if(H.item() != i) System.out.println("Expected current item to be " + i + ", got " + H.item());
            if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
        }

        // Remove the elements 10 through 1 from the heap, chekcing
        // after each deletion that the heap property is retained and that
        // the correct item is at the top of the heap.
        for(int i = 10; i >= 1; i--) {
            // Remove the element i.
            H.deleteItem();
            // If we've removed item 1, the heap should be empty.
            if(i==1) {
                if( !H.isEmpty() ) System.out.println("Expected the heap to be empty, but it wasn't.");
            }
            else {
                // Otherwise, the item left at the top of the heap should be equal to i-1.
                if(H.item() != i-1) System.out.println("Expected current item to be " + i + ", got " + H.item());
                if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
            }
        }
        System.out.println("Regression Test Complete.");
    }
}
