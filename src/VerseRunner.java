import java.io.*;
import java.util.*;
import static java.lang.System.*;

public class VerseRunner
{
	public TreeMap<String,String> map;
	//could be optimized to output a data file
	public void run()throws Exception
	{
		Scanner file = new Scanner(new File("bin//NCT15.dat"));
		map = new TreeMap<>();
		while(file.hasNextLine()){
			String[] info = file.nextLine().split("&");
			String ref = info[0].toLowerCase().replaceAll("\\s+","");
			String verse = info[1].replaceAll("\\p{Punct}","").toLowerCase();
			map.put(ref,verse);
		}
		Scanner input = new Scanner(System.in);
		int num;
		while(true){
			out.println("To specify verse references press 0, to take the Random Verse Exam press any other number: ");
			String in = input.nextLine();
			try{
				num=Integer.parseInt(in);
				break;
			}
			catch(Exception e){
				out.println("This is not a number, try again.");
			}
		}
		if(num==0)
			manual(input);
		else
			rand(input);
		file.close();
	}
	
	public void manual(Scanner input){
		while(true){
			System.out.print("Please enter the verse reference that you wish to test on: ");
			String ref = input.nextLine().replaceAll("\\s+","").toLowerCase().trim();
			while(map.get(ref)==null){
				System.out.print("Please enter a memory verse that is on the verse sheet: ");
				ref = input.nextLine().replaceAll("\\s+}","").toLowerCase().trim();
			}
			System.out.print("Enter the verse here, punctuation and capitalization will not matter: ");
			String a = input.nextLine().toLowerCase().replaceAll("\\p{Punct}","").trim();
			if(map.get(ref).equals(a))
				out.println("Correct!");
			else
				out.println("Wrong, you suck.");
		}
	}
	
	public void rand(Scanner input){
		String[] ar = new String[map.size()];
		int ct = 0;
		for(String a:map.keySet()){
			ar[ct++]=a;
		}
		while(true){
			String randRef = ar[(int)(Math.random()*ar.length)];
			out.print("What is "+randRef+": ");
			String a = input.nextLine().toLowerCase().replaceAll("\\p{Punct}","").trim();
			if(map.get(randRef).equals(a))
				out.println("Correct!");
			else{ // should output discrepancy
				String key = map.get(randRef);
				int iter = Math.min(key.length(), a.length());
				for(int i=0;i<iter;i++){
					if(key.charAt(i) != a.charAt(i)){
						out.println(key.substring(i));
						return;
					}
				}
				if(key.length()>a.length())
					out.println("Your input was right but you were missing: "+key.substring(iter));
				else
					out.println("You were right but you added this at the end: " + a.substring(iter));
			}
		}
		
	}
	
	public static void main(String[] args)throws Exception
	{
		new VerseRunner().run();
	}
}