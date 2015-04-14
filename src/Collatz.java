import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
/* Collatz sequence solution */
class Collatz {
	public static int[]save;
	public static void main(String[] args) {
		try {
			final long startTime = System.currentTimeMillis();
			save = new int[1000000];
			Scanner scan = new Scanner(new FileInputStream("/Users/Priyatham/Dev/3*n+1/input.txt"));
			FileWriter fw =  new FileWriter("output.txt");
			while(scan.hasNext()){
				String str = scan.nextLine();
				String A[] = str.split(" ");
				int a,b;
				int len = 0;
				int from = Math.min(Integer.parseInt(A[0]),Integer.parseInt(A[1]));
				int to = Math.max(Integer.parseInt(A[0]),Integer.parseInt(A[1]));
				for (int i=from; i<to; i++) {
					len = Math.max(len,cycle(i));
				}
				System.out.println(str+" "+len);
				fw.write(str+" "+len+"\n");
				
			}
			fw.flush();
			fw.close();
			final long endTime = System.currentTimeMillis();
			System.out.println("time = "+(endTime-startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int next(int n){
		if(n%2==0){
			return n/2;
		}
		else{
			return (3*n +1);
		}
	}
	
	public static int cycle(int n){
		if(n==1){
			return 1;
		}
		if(n<1000000 && save[n]!=0){
			return save[n];
		}
		
		int len = 1+ cycle(next(n));
		if(n<1000000){
			save[n]=len;
		}
		return len;
	}
}