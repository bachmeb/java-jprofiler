import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestApp me = new TestApp();
		me.go();
	}
	
	public void go(){
		System.out.println("How many strings go into the box?");
		
		//for(int i=0; i<987654321;i++){
		//	System.out.println("Number: "+i);	
		//}
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = bufferedReader.readLine();
			System.out.println(line);
			int num = Integer.parseInt(line);
			Box box = new Box(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	class Box {
		ArrayList<String> strings;
		
		Box(){
			strings = new ArrayList<String>();
		}
		Box(int number){
			strings = new ArrayList<String>();
			for(int i=0;i<number;i++){
				strings.add(String.valueOf(i));
				System.out.println("There are "+ i +" strings in the box");
			}
		}
		
	}

}
