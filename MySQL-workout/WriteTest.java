package com.sql.data;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class WriteTest {

	public static void main(String[] args) throws FileNotFoundException {
		writeRowData();
	}
	
	private static void writeRowData() {
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Sample.txt"))); FileInputStream fis = new FileInputStream("./src/main/resources/data.properties")) {
			Properties p = new Properties();
			p.load(fis);
			String header = p.getProperty("columns");
			String[] sizeString = p.getProperty("size").split(",");
			int[] size = new int[sizeString.length];
			int k =0;
			for (String s : sizeString) {
				size[k++] = Integer.parseInt(s);
			}
			bw.write(header);
			bw.newLine();
			String[] cols = header.split(",");
			String[] dataType = p.getProperty("dataType").split(",");
			if (cols.length != size.length || cols.length != dataType.length) { System.err.println("Column and its size length does not match col=" + cols.length + " sizes=" + size.length); }
			Integer ii = 1;
			String varchar = "Hi There!";
			for (int i = 0; i < 200000; i++) {
				String data = "";
				for (int j = 0; j < cols.length; j++) {
					if ("bigint,int".contains(dataType[j])) {
						//System.out.println("col name = " + cols[j] + " dataType=" + dataType[j] + " size = " + size[j]);
						ii++;
						if (ii.toString().length() >= size[j]) {
							data+= ii.toString().substring(0, size[j]) + ",";
						} else {
							data += ii + ",";
						}
					} else if ("varchar".equalsIgnoreCase(dataType[j])) {
						//System.out.println("col name = " + cols[j]);
						if (varchar.length() >= size[j]) {
							data += varchar.substring(0, size[j]) + ",";
						} else {
							data += varchar;
						}
					}
				}
				bw.write(data.substring(0, data.length() -1));
				bw.newLine();
			}
			System.out.println("End***********");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
