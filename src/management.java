/**
 * @author fuyilei96@outlook.com
 **/

import java.io.*;
import java.util.*;
import java.lang.*;
/*
x*x+2*3+5*y+2
! simplify y=3
!d/d z
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
		
		for (int i = 0; i < express.length(); i++) {
			switch (st[i].type) {
				case 1 :
					System.out.print(st[i].n);
					break;
				case 2:
					System.out.print(st[i].v);
					break;
				case 0:
					System.out.print(st[i].o);
					break;
			}
		}
		return st;
	}

	public static int calculate(data[] tobecaled, String express) {
		int datalength;
		datalength = express.length();
		for (int i = 0; i < datalength; i++) {
			if(tobecaled[i].type == op && tobecaled[i].o == '*' && tobecaled[i - 1].type == num && tobecaled[i + 1].type == num) {
				tobecaled[i - 1].n = tobecaled[i - 1].n * tobecaled[i + 1].n;
				for(int j = i; j < datalength - 2; j++) 
					tobecaled[j] = tobecaled[j + 2];
				datalength -= 2;
				i -= 2;
			}
		}
		for (int i = 0; i < datalength; i++) {
			//System.out.println(datalength);
			if(tobecaled[i + 1].type == num && tobecaled[i - 1].type == num && i == 1 && tobecaled[i + 2].o == '+') {
				tobecaled[i - 1].n = tobecaled[i - 1].n + tobecaled[i + 1].n;
				for (int j = i; j < datalength - 2; j++) {
					tobecaled[j] = tobecaled[j + 2];
				}
				datalength -= 2;
				i -= 2;
			}
			else if (tobecaled[i + 1].type == num && tobecaled[i - 1].type == num && i == datalength - 2 && tobecaled[i - 2].o == '+') {
				//System.out.println(" ");
				tobecaled[i - 1].n = tobecaled[i - 1].n + tobecaled[i + 1].n;
				for (int j = i; j < datalength - 2; j++) {
					tobecaled[j] = tobecaled[j + 2];
				}
				datalength -= 2;
				i -= 2;
			}
			else if (tobecaled[i + 1].type == num && tobecaled[i - 1].type == num && i > 1 && tobecaled[i + 2].o == '+' && tobecaled[i - 2].o == '+') {
				tobecaled[i - 1].n = tobecaled[i - 1].n + tobecaled[i + 1].n;
				for (int j = i; j < datalength - 2; j++) {
					tobecaled[j] = tobecaled[j + 2];
				}
				datalength -= 2;
				i -= 2;
			}
			
		}
		for (int i = 0; i < datalength; i++) {
			switch (tobecaled[i].type) {
				case 1 :
					System.out.print(tobecaled[i].n);
					break;
				case 2:
					System.out.print(tobecaled[i].v);
					break;
				case 0:
					System.out.print(tobecaled[i].o);
					break;
			}
		}
		System.out.println();
//			System.out.println("datatype:" + tobecaled[i].type + "operation:" + tobecaled[i].o + "variable:" + tobecaled[i].v +"number:" + tobecaled[i].n);		
		return datalength;
	}
	
	public static data[] simplify(String tobesimpled, data[] simple, String express) throws IOException {
//		System.out.println(express);
//		for (int i = 0; i < express.length(); i++)
//			System.out.println("datatype:" + simple[i].type + "operation:" + simple[i].o + "variable:" + simple[i].v +"number:" + simple[i].n);
		
		StringBuffer vart = new StringBuffer("");
//		System.out.println(tobesimpled);
		tobesimpled = tobesimpled.replace(" ","");
		tobesimpled = tobesimpled.replace("=", "");
//		System.out.println(tobesimpled);
			
		for(int i = 0; i < express.length(); i++) {
			if(simple[i].type == 2) 
				vart.append(simple[i].v);
		}
		String varts = vart.toString();
//		System.out.println(varts);
		for(int i = 0; i < tobesimpled.length(); i++) {
			if(isvar(tobesimpled.charAt(i))) {
				System.out.println(tobesimpled.charAt(i));
				if(varts.indexOf(tobesimpled.charAt(i)) == -1) {
					System.out.println("Not found!!!!!");
//					printerror();
					return null;
				}
				express = express.replace(tobesimpled.charAt(i), tobesimpled.charAt(i+1));
//				System.out.println(express);
				simple = expression(express);
			}
		}
		
//		for (int i = 0; i < express.length(); i++)
//			System.out.println("datatype:" + simple[i].type + "operation:" + simple[i].o + "variable:" + simple[i].v +"number:" + simple[i].n);
		
		System.out.println();
		calculate(simple, express);
		return simple;
	}
	
	
	public static String derivative(String tobeded, data[] deriv, String expression) throws IOException {
		System.out.println(expression);
		char devar;
		devar = ' ';
		for (int i = 0; i < tobeded.length(); i++) {
//			System.out.println(tobeded.charAt(i));
			if (isvar(tobeded.charAt(i)) && (expression.indexOf(tobeded.charAt(i)) == -1)) {
				System.out.println("Not Found!!!!");
//				devar = 'x';
				return null;
			} 
			else if(isvar(tobeded.charAt(i))) {
//				System.out.println(tobeded.charAt(i));
				devar = tobeded.charAt(i);
				break;
			}
		}
		String[] Splited = expression.split("\\+");
		StringBuffer expstore = new StringBuffer("");
		for(int i = 0; i < Splited.length; i++) {
			int countvar = 0;
			for (int j = 0; j < Splited[i].length(); j++) {
				if (Splited[i].charAt(j) == devar) {
					countvar++;
				}
			}
			if (countvar == 0) {
				Splited[i] = "";
			}
			else if(countvar == 1) {
				Splited[i] = Splited[i].replaceAll( String.valueOf(devar), "1");
//				System.out.println(Splited[i] + "      1");
			}
			else if(countvar == 2) {
				Splited[i] = Splited[i].replaceFirst( String.valueOf(devar), "2");
//				System.out.println(Splited[i] + "      2");
			}
			else if(countvar >= 3) {
				Splited[i] = Splited[i].replaceFirst( String.valueOf(devar), String.valueOf(countvar));
				countvar--;
				while (countvar > 1) {
					Splited[i] = Splited[i].replaceFirst(String.valueOf(devar), "1");
					countvar--;
				}
//				System.out.println(Splited[i] + "      3");
			}
			if (!("".equals(Splited[i]))) {
				expstore.append(Splited[i]);
				expstore.append("+");
			}
		}
		expstore.deleteCharAt(expstore.length() - 1);
		System.out.println(expstore);
		return tobeded;
	}
	 
	public static void main(String[] args) throws IOException {
		try {
			data[] newexpression = new data[30];
			for(int i = 0; i < 30; i++) {
				newexpression[i] = new data();
			}		
			String expressionstore = new String();
			while (true) {
			String sentencein;
			BufferedReader buf;
			buf=new BufferedReader(new InputStreamReader(System.in));
			
				sentencein = buf.readLine();
				sentencein = sentencein.replace(" ","");
//				System.out.println(sentencein);
			
				if(isnumber(sentencein.charAt(0)) || isvar(sentencein.charAt(0))) {
					//expression
					System.out.println();
					newexpression = expression(sentencein);
					expressionstore = sentencein;
	//				for (int i = 0; i < sentencein.length(); i++)
	//					System.out.println("datatype:" + newexpression[i].type + "operation:" + newexpression[i].o + "variable:" + newexpression[i].v +"number:" + newexpression[i].n);
				}
				else if(sentencein.charAt(0) == '!'){
					//commands
					if(sentencein.length() >= 11 && !(sentencein.substring(0, 10).equalsIgnoreCase( "! simplify "))) {
						//simplify
						System.out.println("Doing the simplify work...");
						System.out.println(sentencein.substring(9));
						simplify(sentencein.substring(9), newexpression, expressionstore);
					}
					else if(sentencein.length() >= 5 && !(sentencein.substring(0, 4).equalsIgnoreCase( "!d/d "))) {
						//derivative
						System.out.println("Doing the derivation work...");
						derivative(sentencein.substring(4), newexpression, expressionstore);
					}
					else {
						System.out.println("Wrong Command!");
					}
				}
				else {
					System.out.println("Wrong Command!");
					break;
				}
			}
		}
		catch(Exception e) {
			System.out.println("Nothing Inputed, exiting...");
			return;
		}
	}
	
}
