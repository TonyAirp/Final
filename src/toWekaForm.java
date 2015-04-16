package antismell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.ldap.SortResponseControl;

import padl.creator.aolfile.javacup.internal_error;

import com.sun.tools.hat.internal.parser.Reader;

public class toWekaForm {
	final static String path = "F:/AntiPattern/antiSmell/Temp/Leiyh5/";
	final static String sortPath = "F:/AntiPattern/Temp/"
			+ ICPC2015.resultsPath;

	public static void main(String[] args) {
		toArffForm();
	}

	private static void toArffForm() {
		List<String> iniFiles = null;
		iniFiles = new util.io.FileFilter(path, ".ini").getFiles();

		System.out.println(iniFiles.size());

		// String tempString = "NDC|NAD_NMD:11.0|LCOM5:0.0|DataClassSize:0";
		// String[] resultString = tempString.split("\\|");
		// //System.out.println(resultString);

		Iterator<String> iterator = iniFiles.iterator();
		while (iterator.hasNext()) {
			String pathName = iterator.next();
			antiSingleton(pathName);
			blob(pathName);
			classDataShouldBePrivate(pathName);
			complexClass(pathName);
			lazyClass(pathName);
			longParameterList(pathName);
			messageChains(pathName);
			refusedParentBequest(pathName);
			speculativeGenerality(pathName);
			swissArmyKnife(pathName);
		}
	}

	private static void antiSingleton(String pathName) {
		if (!pathName.contains("AntiSingleton")) {
			return;
		}

		System.out.println("AntiSingleton");

	}

	private static void blob(String pathName) {
		if (!pathName.contains("Blob")) {
			return;
		}

		System.out.println("Blob");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length - 1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;

		ArrayList<String> sortResults = new ArrayList<String>();

		if (sortFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					if (tempString.length() > 0) {
						sortResults.add(tempString);
					}
					// System.out.println("+++" + tempString +
					// tempString.length());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NAD_NMD = new ArrayList<String>();
		ArrayList<String> LCOM5 = new ArrayList<String>();
		ArrayList<String> DataSize = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NAD_NMD.add(resultStr[1].split(":")[1]);
				LCOM5.add(resultStr[2].split(":")[1]);
				DataSize.add(resultStr[3].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 输出
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation Blob\n");
			fileWriter.write("\n@attribute NAD_NMD numeric\n");
//			fileWriter.write("@attribute LCOM5 REAL\n");
			fileWriter.write("@attribute DataSize numeric\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NAD_NMD.size(); i++) {
				int classResult = 0;
				// 判断结果
				for (int j = 0; j < sortResults.size(); j++) {
					if (names.get(i).equals(sortResults.get(j))) {
						System.out.println("!!!wacacaca");
						classResult = 1;
					}
				}
				if(classResult == 1){
//					fileWriter.write(NAD_NMD.get(i) + "," 
//							+ DataSize.get(i) + "," + classResult + "\n");
//
					fileWriter.write(NAD_NMD.get(i) + "," 
							+ DataSize.get(i) + "," + classResult  + ",{5}"+ "\n");
				}else{
					fileWriter.write(NAD_NMD.get(i) + "," 
							+ DataSize.get(i) + "," + classResult + "\n");
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void classDataShouldBePrivate(String pathName) {
		if (!pathName.contains("ClassDataShouldBePrivate")) {
			return;
		}

		System.out.println("ClassDataShouldBePrivate");

		// TODO 暂时没有该条
	}

	private static void complexClass(String pathName) {
		if (!pathName.contains("ComplexClass")) {
			return;
		}

		System.out.println("ComplexClass");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length - 1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;

		ArrayList<String> sortResults = new ArrayList<String>();

		if (sortFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					if (tempString.length() > 0) {
						sortResults.add(tempString);
					}
					// System.out.println("+++" + tempString +
					// tempString.length());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NAD_NMD = new ArrayList<String>();
		ArrayList<String> McCabe = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NAD_NMD.add(resultStr[1].split(":")[1]);
				McCabe.add(resultStr[2].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// output
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation ComplexClass\n");
			fileWriter.write("\n@attribute NAD_NMD REAL\n");
			fileWriter.write("@attribute McCabe REAL\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NAD_NMD.size(); i++) {
				int classResult = 0;
				for (int j = 0; j < sortResults.size(); j++) {
					if (names.get(i).equals(sortResults.get(j))) {
						System.out.println("!!!wacacaca2");
						classResult = 1;
					}
				}
				fileWriter.write(NAD_NMD.get(i) + "," + McCabe.get(i) + ","
						+ classResult + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void lazyClass(String pathName) {
		if (!pathName.contains("LazyClass")) {
			return;
		}

		System.out.println("LazyClass");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length - 1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;

		ArrayList<String> sortResults = new ArrayList<String>();

		if (sortFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					if (tempString.length() > 0) {
						sortResults.add(tempString);
					}
					// System.out.println("+++" + tempString +
					// tempString.length());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NAD_NMD = new ArrayList<String>();
		ArrayList<String> WMC = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NAD_NMD.add(resultStr[1].split(":")[1]);
				WMC.add(resultStr[2].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation LazyClass\n");
			fileWriter.write("\n@attribute NAD_NMD REAL\n");
			fileWriter.write("@attribute WMC REAL\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NAD_NMD.size(); i++) {
				int classResult = 0;
				for (int j = 0; j < sortResults.size(); j++) {
					if (names.get(i).equals(sortResults.get(j))) {
						System.out.println("!!!wacacaca3");
						classResult = 1;
					}
				}
				fileWriter.write(NAD_NMD.get(i) + "," + WMC.get(i) + ","
						+ classResult + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void longParameterList(String pathName) {
		if (!pathName.contains("LongParameterList")) {
			return;
		}

		System.out.println("LongParameterList");

		// TODO 没有该条
	}

	private static void messageChains(String pathName) {
		if (!pathName.contains("MessageChains")) {
			return;
		}

		System.out.println("MessageChains");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length - 1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;

		ArrayList<String> sortResults = new ArrayList<String>();

		if (sortFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					if (tempString.length() > 0) {
						sortResults.add(tempString);
					}
					// System.out.println("+++" + tempString +
					// tempString.length());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NOTI = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NOTI.add(resultStr[1].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// output
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation MessageChains\n");
			fileWriter.write("\n@attribute NOTI REAL\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NOTI.size(); i++) {
				int classResult = 0;
				for (int j = 0; j < sortResults.size(); j++) {
					if (names.get(i).equals(sortResults.get(j))) {
						System.out.println("!!!wacacaca4");
						classResult = 1;
					}
				}
				fileWriter.write(NOTI.get(i) + "," + classResult + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void refusedParentBequest(String pathName) {
		if (!pathName.contains("RefusedParentBequest")) {
			return;
		}

		System.out.println("RefusedParentBequest");

		// TODO 没有该条
	}

	private static void speculativeGenerality(String pathName) {
		if (!pathName.contains("SpeculativeGenerality")) {
			return;
		}

		System.out.println("SpeculativeGenerality");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length-1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;
		
		ArrayList<String> sortResults = new ArrayList<String>();
		
		if (sortFile.exists()){
			try{
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while((tempString = reader.readLine()) != null){
					if (tempString.length() > 0){
						sortResults.add(tempString);
					}
//					System.out.println("+++" + tempString + tempString.length());
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NOC = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NOC.add(resultStr[1].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// output
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation blob\n");
			fileWriter.write("\n@attribute NOC REAL\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NOC.size(); i++) {
				int classResult = 0;
				for (int j=0; j<sortResults.size(); j++){
					if (names.get(i).equals(sortResults.get(j))){
						System.out.println("!!!wacacaca5");
						classResult = 1;
					}
				}
				fileWriter.write(NOC.get(i) + "," +classResult +"\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void swissArmyKnife(String pathName) {
		if (!pathName.contains("SwissArmyKnife")) {
			return;
		}

		System.out.println("SwissArmyKnife");

		// <Begin>读入结果
		String[] patches = pathName.split("\\\\");
		String sortPathName = sortPath + patches[patches.length-1];
		System.out.println(sortPathName);
		File sortFile = new File(sortPathName);
		BufferedReader reader = null;
		
		ArrayList<String> sortResults = new ArrayList<String>();
		
		if (sortFile.exists()){
			try{
				reader = new BufferedReader(new FileReader(sortFile));
				String tempString = null;
				while((tempString = reader.readLine()) != null){
					if (tempString.length() > 0){
						sortResults.add(tempString);
					}
//					System.out.println("+++" + tempString + tempString.length());
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		// <End>

		String resultPath = pathName.replace(".ini", ".arff");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> NOI = new ArrayList<String>();

		File file = new File(pathName);
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] resultStr = tempString.split("\\|");
				names.add(resultStr[0]);
				NOI.add(resultStr[1].split(":")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// output
		File resultFile = new File(resultPath);
		if (!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(resultFile);
			fileWriter.write("@relation blob\n");
			fileWriter.write("\n@attribute NOI REAL\n");
			fileWriter.write("@attribute class {0,1}\n");
			fileWriter.write("\n@Data\n");
			for (int i = 0; i < NOI.size(); i++) {
				int classResult = 0;
				for (int j = 0; j < sortResults.size(); j++){
					if (names.get(i).equals(sortResults.get(j))){
						classResult = 1;
					}
				}
				fileWriter.write(NOI.get(i) + "," + classResult + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
