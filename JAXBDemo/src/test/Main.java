package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		FileReader fin = new FileReader(new File("234.txt"));
		BufferedReader bfin = new BufferedReader(fin,1024);
		StringBuilder sb = new StringBuilder();
		String a = null;
		while((a = bfin.readLine())!=null){
			sb.append(a);
		}
		String res = sb.toString();
		res = res.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&");
		System.out.println(res);
		bfin.close();
		fin.close();
	}

}
