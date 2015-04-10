/* Least Common Ancestor */
import java.io.*;
import java.util.*;
import java.lang.*;
class Node{
	int val;
	Node left;
	Node right;
	
	public Node(int val){
		this.val = val;
		left = null;
		right = null;
	}
}
public class LCA {
    public static void main (String[] args) throws IOException {
    	Node head = null;
    	head = new Node(30);
    	head.left = new Node(8);
    	head.right = new Node(52);
    	head.left.left = new Node(3);
    	head.left.right = new Node(20);
    	head.left.right.left = new Node(10);
    	head.left.right.right = new Node(29);
        File file = new File(args[0]);
        //BufferedReader buffer = new BufferedReader(new FileReader(file));
        //String line = args[0];
       // while ((line = args[0]) != null) {
           // line = line.trim();
           // String[] str = line.split(" ");
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            System.out.println(lca(head,x,y).val);
       // }
    }
    
    private static Node lca(Node head, int x, int y) {
		
    	if(head == null){
    		return null;
    	}
    	
    	if(head.val == x || head.val == y){
    		return head;
    	}
    	
    	Node left = lca(head.left,x,y);
    	Node right = lca(head.right,x,y);
    	
    	if(left != null && right != null){
    		return head;
    	}
    	
    	if(left != null){
    		return left;
    	}else if(right != null){
    		return right;
    	} 
    	return null;
		
	}

	public static Node insert(Node head, int val){
    	if(head == null){
    		return new Node(val);
    	}
		
    	if(val <= head.val){
    		head.left =  insert(head.left,val);
    		
    	}else{
    		head.right =  insert(head.right,val);
    	}
    	
    	return head;
    }
}
