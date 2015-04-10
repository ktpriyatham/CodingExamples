/*
 * 
 * Author: Tejesh Priyatham Kalidindi
 * 
 * Email : ktpriyatham@gmail.com
 * 
 * Data Structure: Min-Max-Median Heap.
 * 
 * Problem Definition: Given a Stream of Integers, give the median for each 
 * entry in the most efficient time.
 * 
 * Solution: Maintain the Data provided in a Min-Max-Median Heap which stores 
 * all the elements before in a maxHeap so we can obtain the value before the median 
 * in constant time and the values after the median in a minHeap so we can obtain the 
 * value right after the median in constant time.
 * 
 * In case of even No. of elements we take the elements from the maxHeap and minHeap and 
 * find the mean of the two. 
 * In case of odd elements we take the first element from the 
 * stack with the larger size as it is the median. 
 * 
 * Algorithm:
 * - Insert each element in the minHeap if value greater than current median.
 *   and maxHeap if value lesser than current median.
 * - Balance the heaps so they can be of equal size or differ by no more than one by removing
 * 	 the respective min or max from the greater list and adding it to the other.
 * - Save the new median.
 *  
 * [------MAXHEAP - MEDIAN - MINHEAP------]
 * 
 * Time Complexity: N elements entered with logN insertion time for each element so O(NlogN)
 * 
 * The steps are commented with implementation details.
 * 
 * Please contact me at ktpriyatham@gmail.com if any discrepancies or questions. 
 *
 */

import java.util.*;

class Heap{
	//Declarations
	float median;
	ArrayList<Integer> minHeap;
	ArrayList<Integer> maxHeap;
	
	//Constructor
	public Heap(){
		minHeap = new ArrayList<Integer>();
		maxHeap = new ArrayList<Integer>();
	}
	
	/*
	 * if value passed is less than the median than we insert in the maxHeap
	 * else we insert in the minHeap
	 * 
	 * We maintain a boolean value so we know whether we are swimming/sinking in the maxHeap or minHeap.
	 * 
	 * We call the balance function.
	 */
	public void insert(int val){
		if(minHeap.size() ==0 && maxHeap.size() ==0){
			this.median = val;
			maxHeap.add(val);
			return;
		}
		boolean x;
		if(val <= median){
			x = true;
			maxHeap.add(val);
			swim(x);
		}else{
			x= false;
			minHeap.add(val);
			swim(x);
		}
		
		balance();
		
	}
	/*
	 * Remove elements from bigger heap and put it in the smaller heap 
	 * till the differnce in size is no more than one.
	 * 
	 * Call the new median function
	 */
	private void balance() {
		ArrayList<Integer> a1 = null;
		ArrayList<Integer> a2 = null;
		boolean x;
		if(minHeap.size() < maxHeap.size()){
			x = true;
			a1 = maxHeap;
			a2 = minHeap;
		}else if(maxHeap.size() < minHeap.size()){
			x = false;
			a1 = minHeap;
			a2 = maxHeap;
		}else{
			this.median =(minHeap.get(0) + maxHeap.get(0))/2;
			return;
		}
		
		while(!(a1.size() == a2.size())&&!(a1.size()-a2.size() == 1)) {
			int val = removeElement(a1,x);
			a2.add(val);
			swim(!x);
		}
		this.findMedian();		
	}
	
	/*
	 * Find the new Median 
	 */
	private void findMedian() {
		if(minHeap.size()==maxHeap.size()-1){
			this.median = maxHeap.get(0);
		}else if(maxHeap.size()==minHeap.size()-1){
			this.median = minHeap.get(0);
		}else{
			this.median = (float)(minHeap.get(0)+maxHeap.get(0))/2;
		}
		
	}
	
	/*--------- HELPER FUNCTIONS -------------*/
	
	private int removeElement(ArrayList<Integer> a1,boolean x) {
		int val = a1.get(0);
		a1.set(0,a1.get(a1.size()-1));
		a1.remove(a1.size()-1);
		sink(x);
		return val;
	}

	private void sink(boolean x) {
		ArrayList<Integer> a;
		
		if(x){
			a = maxHeap;
			int p = 0;
			int c = 2*p+1;
			while(c < a.size()){
				if(c < a.size()-1 && a.get(c)<a.get(c+1)){
					c++;
				}
				if(a.get(p)<a.get(c)){
					swap(a,p,c);
					p=c;
					c=2*p+1;
				}else{
					break;
				}
			}
		}else{
			a = minHeap;
			int p = 0;
			int c = 2*p+1;
			while(c < a.size()){
				if(c < a.size()-1 && a.get(c)>a.get(c+1)){
					c++;
				}
				if(a.get(p)>a.get(c)){
					swap(a,p,c);
					p=c;
					c=2*p+1;
				}else{
					break;
				}
			}
		}
	}
	
	private void swim(boolean x) {
		ArrayList<Integer> heap;
		if(x){
			heap = this.maxHeap;
			int len = heap.size()-1;
			while(len/2>=0){
				if(heap.get(len)>heap.get(len/2)){
					swap(heap,len,len/2);
					len = len/2;
				}else{
					break;
				}
			}
		}else{
			heap = this.minHeap;
			int len = heap.size()-1;
			while(len/2>=0){
				if(heap.get(len)<heap.get(len/2)){
					swap(heap,len,len/2);
					len = len/2;
				}else{
					break;
				}
			}
		}
	}

	private void swap(ArrayList<Integer> x,int len, int i) {
		int t = x.get(len);
		x.set(len,x.get(i));
		x.set(i,t);
	}
	
	public void print(){
		System.out.println("\nmaxHeap: \nSize = "+maxHeap.size()+" \nElements "
				+ "= "+maxHeap+"\n\nminHeap: \nSize = "+minHeap.size()+" \nElements "
						+ "= "+minHeap+"\n\nMedian: "+this.median);
	}
	
}
class MinMaxMed {
	public static void main(String[] args){
		//No. of elements to be entered - if argument is provided then it takes that value
		int n;
		if(args.length == 1){
			n = Integer.parseInt(args[0]);
		}else{
			n = (int)(Math.random()*25 +10);
		}
		
		//Inserts requested/random no.of elements. to a list and the heap.
		Heap h = new Heap();
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i=0;i<n;i++){
			int v = (int)(Math.random()*200);
			al.add(v);
			h.insert(v);
		}
		
		//Prints the sorted no. of elements, the minHeap, maxHeap & median for user verification.
		al.sort(null);
		System.out.println("Number of elements entered: "+al.size());
		System.out.println("Elements entered : "+al);
		h.print();
	}
}
