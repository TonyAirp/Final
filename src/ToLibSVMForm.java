package antismell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import padl.kernel.IIdiomLevelModel;
import padl.util.ModelStatistics;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IEntity;
import padl.kernel.IClass;
import padl.kernel.IInterface;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.Blob.DataClassDetection;
import sad.codesmell.property.impl.ClassProperty;
import sad.kernel.impl.CodeSmell;
import sad.kernel.impl.CodeSmellComposite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.tools.javah.Util.Exit;

public class ToLibSVMForm{
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

		String resultPath = pathName.replace(".ini", ".libsvm");
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
			for (int i = 0; i < NAD_NMD.size(); i++) {
				int classResult = -1;
				// 判断结果
				for (int j = 0; j < sortResults.size(); j++) {
					if (names.get(i).contains(sortResults.get(j))) {
						System.out.println("!!!wacacaca");
						classResult = 1;
					}
				}
				if(classResult == 1){
//					for (int j=0; j<5; j++){
//						fileWriter.write("+" + classResult + " 1:" + NAD_NMD.get(i) + 
//							" 2:" + DataSize.get(i) + "\n");
//					}
					fileWriter.write("+" + classResult + " 1:" + NAD_NMD.get(i) + 
							" 2:" + DataSize.get(i) + "\n");

				}else{
					fileWriter.write(classResult + " 1:" + NAD_NMD.get(i) + 
							" 2:" + DataSize.get(i) + "\n");
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}

