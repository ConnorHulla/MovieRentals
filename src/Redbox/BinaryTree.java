//Name: Connor Hulla
//NetID: CJH170230
package Redbox;

public class BinaryTree<T extends Comparable<T>> 
{
    Node<T> root;
    
    //helper function
    public void insert(T newData)
    {
        Node<T> head = root; //set head = to root
        Node<T> in = new Node(newData); //put our data into node form
                //Node<T> hold = in;
        if(root == null)       //if the root is null, then make in our root
            root = in;
        else                   //otherwise, start the recursive insert
            insert(in, head);
    }
    

    
    private void insert(Node<T> in, Node<T> comp)
    {
        
        //if the in is less than comp, go left
        if(in.compareTo(comp) < 0)
        {
            //if left is null, insert
            if(comp.left == null)
                comp.left = in;
            else //else, go left
                insert(in, comp.left);
        }
        //if in is greater than comp, go right
        else if(in.compareTo(comp) > 0)
        {
            //if the right is null, insert
            if(comp.right == null)
                comp.right = in;
            //else, go right
            else
                insert(in, comp.right);
        }
        
    }
    
    //helper function
    @Override
    public String toString()
    {
        String tree = "";
        if(root != null)
        {
            Node<T> hold = root;
            tree += toString(hold); //start at the root
        }
        return tree;
    }
    
    //in order traversal of the tree
    private String toString(Node<T> p)
    {
        //we wil keep traversing the tree until you reach the null
        String tree = "";
        if(p == null) //return
            return "";
        //go to the left
        tree += toString(p.left);
        tree += p.toString(); //add the data of this node to the string
        tree += toString(p.right);  //go to the right
        return tree; //return the string
    }
    
    //helper function for the search
    public T search(T f)
    {
        Node<T> find = new Node<>(f);
        //if the tree is null, returnnull
        if(root == null || find == null)
            return null;
        //if the node we want to find is less than the root
        if(find.compareTo(root) < 0)
            return (T)search(find, root.left); //go left
        //if the node we want to find is greater than the root
        else if(find.compareTo(root) > 0)
            return (T)search(find, root.right); //go right
        //if its equal to the root, return the root
        else if(find.compareTo(root) == 0)
            return root.data;
        return null;
    }
    
    private T search(Node<T> find, Node<T> findone)
    {
        //If we get to a null pointer, the item wasn't found in our binary
        //tree so return
        if(findone == null)
            return null;
        //otherwise, if what we are looking for is less than what the node we 
        //are on, go left
        if(find.compareTo(findone) < 0)
            return (T)search(find, findone.left);
        //if what we are looing for is greater than the node we are on, go right
        else if(find.compareTo(findone) > 0)
            return (T)search(find, findone.right);
        //if its equal to what we are looking for, return
        else if(find.compareTo(findone) == 0)
            return findone.data;
        return null;
    }
    
    public T delete(T del)
    {
        Node<T> parent = null;
        Node<T> cur = root;
        
        //travel to the node we want to delete
        while(cur != null && cur.data.compareTo(del) != 0)
        {
            parent = cur;
            if(del.compareTo(cur.data) < 0)
            {
                cur = cur.left;
            }
            else
                cur = cur.right;
        }
        //cur will hold the node we want to delete
        //3 cases.
        //case 1: no children
        //case 2: 1 child
        //case 3: 2 children
        if(cur != null)
        {
            if(cur.left == null && cur.right == null)
            {
                //if we are deleting the root
                if(parent == null)
                    root = null;
                //else, if curent is less than parent delete left
                else if (cur.compareTo(parent) < 0) 
                    parent.left = null; 
                else //delete right otherwise
                    parent.right = null;
            }
            //if only one child is null
            else if(cur.left == null ^ cur.right == null)
            {
                //if the left is null, we need to connect the parent node
                //to the current node's right pointer. we need to see if rightis
                //less than parent or greater than parent. if its less than
                //parent, it goes on the left. otherwise, it goes on the right
                if(cur.left == null)
                {
                    //if the parent is null, set root = to the right of cur 
                    //The greater value keeps our binary tree in order
                    //this is the root case
                    if(parent == null)
                        root = cur.right;
                    //if the current is less than the parent, the right parent
                    //connects to the right of our current
                    else if(cur.compareTo(parent) < 0)
                        parent.right = cur.right;
                    //otherwise, the left parent needs to connect to the right
                    //of cur 
                    else
                        parent.left = cur.right;
                }
                //if the right is the non-null one
                else
                {
                    //if parent is null, set root to left
                    if(parent == null)
                        root = cur.left;
                    //otherwise, if curleft is less than parent, the parent
                    //needs to connect the left of cur since the greater value
                    //goes right
                    else if(cur.compareTo(parent) > 0)
                        parent.right = cur.left;
                    //otherwise, the left of parent connects to the left of cur
                    else
                        parent.left = cur.left;
                }
            }
            else
            {
                //We will find the maximum of the left subtree, delete it,
                //and set that value equal to parents data
                parent = cur;
                cur = cur.left;

                //find the maximum value of the left subtree
                while(cur.right != null)
                    cur = cur.right;


                parent.data = delete(cur.data);  //calls the delete function
            }
        }

        return cur.data; //return the data we delete
    }
  
}
