package javaBackend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class RegionParser {
	public ArrayList<Region> regionParser() {
		File regionFile = new File("assets/regions.txt");
		ArrayList<Region> regions = new ArrayList<Region>();
		try {
			Scanner sc = new Scanner(regionFile);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				
				
				StringTokenizer splitLine = new StringTokenizer(line, "|");
				String regionName = splitLine.nextToken();
				
				
				ArrayList<String> provinces = new ArrayList<String>();
				while(splitLine.hasMoreTokens()) {
					provinces.add(splitLine.nextToken());
				}
				regions.add(new Region(provinces, regionName));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error", "Missing project file - regions.txt", JOptionPane.ERROR_MESSAGE);
		}
		return regions;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegionParser rp = new RegionParser();
		rp.regionParser();
	}

}
