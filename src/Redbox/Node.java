
package Redbox;


public class Node<T extends Comparable<T>>
{
    //left pointers and right poniters. T stores the data
    T data;
    Node left, right;
    
    
    Node() 
    {
        left = null;
        right = null;
    }
    
    Node(T d)
    {
        data = d;
    }
    
    @Override
    public String toString()  
    { 
        return data.toString();
    }
   
    
    public void setData(T d) { data = d;    }
    public T getData()       { return data; }
    
    public int compareTo(Node<T> comp) 
    {
        //compares the payload by calling the payloads compareTo function
        return this.data.compareTo(comp.data);
    } 
}
