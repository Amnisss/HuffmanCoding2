/*  Student information for assignment:
 *
 *  On OUR honor, Rachel Yun and Amruta Soma,
 *  this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: rny82
 *  email address: rny82@utexas.edu
 *  Grader name: Hrutvik Palutla
 *  Section number: 54705
 *
 *  Student 2
 *  UTEID: as228483
 *  email address: as228483@my.utexas.edu
 *
 */

import java.util.ArrayList;
import java.lang.Comparable;
import java.util.Queue;
import java.util.NoSuchElementException;
import java.util.Collection;

public class PriorityQueue314<E extends Comparable<E>> {

    private ArrayList<E> con;

    // pre: none
    // post: creates an empty priority queue
    public PriorityQueue314() {
        con = new ArrayList(); 
    }

    // pre: value != null
    // post: queue size increased by 1 and contains value in correct position
    public boolean enqueue(E value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        // if queue is empty, add to end
        if (con.size() == 0) {
            con.add(value);
            return true;
        }

        // find index where this value should be inserted
        int indexToAdd = 0;
        // adds past all elements <= value
        while (indexToAdd < con.size() && value.compareTo(con.get(indexToAdd)) >= 0) {
            indexToAdd++;
        }

        con.add(indexToAdd, value);
        return true;
    }
    
    // pre: none
    // post: returns true if size = 0
    public boolean isEmpty() {
        return con.size() == 0;
    }

    // pre: none
    // post: returns smallest element and removes it from the queue
    public E dequeue() {
        return con.remove(0);
    }

    // pre: none
    // post: returns last element
    public E peek() {
        // if empty queue, returns null
        if (con.size() == 0) {
            return null;
        }
        return con.get(con.size()-1);
    }

    // pre: none
    // post: returns current number of elements
    public int size() {
        return con.size();
    }

}
