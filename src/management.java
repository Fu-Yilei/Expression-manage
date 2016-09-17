/**
 * @author fuyilei96@outlook.com
 **/

import java.io.*;
import java.util.*;
import java.lang.*;
/*
x*x+2*3+5*y+2
! simplify x=2 y=3
 */





class data {
	public char o;
	public int n;
	public char v; 
	public int type;
	
	public void SetValue(char ope, char va, int numb, int typ) {
		o = ope;
		n = numb;
		v = va;
		type = typ;
	}
}




public class management {

	static int op = 0;
	static int num = 1;
	static int var = 2;
	static int MAXNUM = 5;
	
	public static boolean isnumber(char ch) {
		if(ch >= '0' && ch <= '9')
			return true;
		return false;
	}
	
	public static boolean isop(char ch) {
		if(ch == '+' || ch == '*') 
			return true;
		return false;
	}
	
	public static boolean isvar(char ch) {
		if((ch >= 'a' && ch <= 'z') || (ch >='A' && ch <= 'Z')) 
			return true;
		return false;
	}
	
	public static void printerror() {
		System.out.println("Input Error!");
	}
	
	public static data[] expression(String express) throws IOException {
		
		int count = 0;
		data[] 	st;
		st = new data[30];
		
		for(int i = 0; i < 30; i++) {
			st[i] = new data();
		}
		
		StringBuffer temp = new StringBuffer("");
		
		for(int i = 0; i < express.length(); i++) {
			if(isnumber(express.charAt(i))) {
				do {
					temp.append(express.charAt(i));
					if(i == express.length() - 1)
						break;
					i++;
				} while(isnumber(express.charAt(i)));
				
				String temps = new String(temp.toString());
				int num1 = Integer.parseInt(temps);
				
				if(i == express.length() - 1) {
					st[count].SetValue(' ', ' ', num1, num);
					temp.delete(0, temp.length());
					count++;	
				}
				
				else if(isop(express.charAt(i))) {
					st[count].SetValue(' ', ' ', num1, num);
					temp.delete(0, temp.length());
					count++;
					i--;			
				}
				else {
					printerror();
					return null;	
				}
			}
			else if(isop(express.charAt(i))) {
				if (isvar(express.charAt(i+1)) || isnumber(express.charAt(i+1))) {
					st[count].SetValue(express.charAt(i), ' ', 0, op);
					count++;
				}
				else {
					printerror();
					return null;
				}
			}
			else if(isvar(express.charAt(i))){
				if(i == express.length() - 1) {
					st[count].SetValue(' ', express.charAt(i), 0, var);
					count++;
				}
				else if(isop(express.charAt(i + 1))){
					//System.out.println(express.charAt(i));
					st[count].SetValue(' ', express.charAt(i), 0, var);
					count++;
				}
				else {
					printerror();
					return null;
				}
			}
			else {
				printerror();
				return null;
			}
		}
		return st;
	}
	
	
	public static data[] simplify(String tobesimpled, data[] simple, String express) throws IOException {
		System.out.println(express);

		for (int i = 0; i < express.length(); i++)
			System.out.println("datatype:" + simple[i].type + "operation:" + simple[i].o + "variable:" + simple[i].v +"number:" + simple[i].n);
		
		StringBuffer vart = new StringBuffer("");
		tobesimpled = tobesimpled.replace(" ","");
		tobesimpled = tobesimpled.replace("=", "");
		System.out.println(tobesimpled);
		
		for(int i = 0; i < express.length(); i++) {
			if(simple[i].type == 2) 
				vart.append(simple[i].v);
		}
		
		String varts = vart.toString();
		System.out.println(varts);
		for(int i = 0; i < tobesimpled.length(); i++) {
			if(isvar(tobesimpled.charAt(i))) {
				System.out.println(tobesimpled.charAt(i));
				if(varts.indexOf(tobesimpled.charAt(i)) == -1) {
					printerror();
					return null;
				}
				express.replace(tobesimpled.charAt(i), tobesimpled.charAt(i+1));
				simple = expression(express);
			}
			//System.out.println(Splited[i]);
		}
		
		for (int i = 0; i < express.length(); i++)
			System.out.println("datatype:" + simple[i].type + "operation:" + simple[i].o + "variable:" + simple[i].v +"number:" + simple[i].n);
		return simple;
	}
	
	
	public static String derivative(String derivate) throws IOException {

		return derivate;
		
	}
	
	public static void main(String[] args) throws IOException {
		data[] newexpression = new data[30];
		for(int i = 0; i < 30; i++) {
			newexpression[i] = new data();
		}		
		while (true) {
			String sentencein;
			String expressionstore = new String();
			BufferedReader buf;
			buf=new BufferedReader(new InputStreamReader(System.in));
			sentencein = buf.readLine();
			if(sentencein.charAt(0) != '!') {
				//expression
				newexpression  =  expression(sentencein);
				expressionstore = sentencein;
				for (int i = 0; i < sentencein.length(); i++)
					System.out.println("datatype:" + newexpression[i].type + "operation:" + newexpression[i].o + "variable:" + newexpression[i].v +"number:" + newexpression[i].n);
			}
			else {
				//commands
				if(sentencein.length() >= 11 && !(sentencein.substring(0, 10).equalsIgnoreCase( "! simplify "))) {
					//simplify
					System.out.println("Doing the simplify work...");
					simplify(sentencein.substring(10), newexpression, expressionstore);
				}
				else if(sentencein.length() >= 5 && !(sentencein.substring(0, 4).equalsIgnoreCase( "!d/d "))) {
					//derivative
					System.out.println("Doing the derivation work...");
					simplify(sentencein.substring(4), newexpression, expressionstore);
				}
				else {
					System.out.println("Wrong Input!");
				}
			}
		}
	}
}
