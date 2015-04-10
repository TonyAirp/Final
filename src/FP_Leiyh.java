package antismell;

import java.util.HashSet;
import java.util.Iterator;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.tools.javah.Util.Exit;

public class FP_Leiyh{
	final static String[] path = {
		"F:/target/xerces-2_11_0",
		"F:/target/xerces-2_10_0",
		"F:/target/apache-log4j-2.1",
		"F:/target/ArgoUML-0.24",
		"F:/target/ArgoUML-0.26",
		"F:/target/cocoon-all-3.0.0-alpha-3",
		"F:/target/xalan-j_2_6_0",
		"F:/target/xalan-j_2_7_0",
	};
	final static String resultPath = System.getProperty("user.dir") + "\\Temp\\Leiyh3\\";	//TODO please fill in
	
	public static void main(String[] args){
		getMetrics();
	}
	
	private static void getMetrics(){
		File dir = new File(resultPath);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		SmellDetection sd = new SmellDetection();
		
		final String[] version = new String[path.length];
		
		for (int i=0; i<path.length; i++){
			version[i] = sd.getFileName(path[i]);
			
			IIdiomLevelModel idiomLevelModel = null;
			final ModelStatistics statistics = new ModelStatistics();
			
			idiomLevelModel = sd.getModel(version[i], path[i], statistics, ".java");
			if (idiomLevelModel == null){
				System.err.println("model is null");
				return;
			}
			
			final Iterator iter = idiomLevelModel.getIteratorOnTopLevelEntities();
			while(iter.hasNext()){
				final IEntity entity = (IEntity) iter.next();
				if (entity instanceof IClass){
					final IClass aClass = (IClass) entity;
//					try{
//						System.out.printf("--%s--", aClass.getDisplayID());
//						double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(idiomLevelModel, aClass);
//						double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(idiomLevelModel, aClass);
//						double AID = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("AID")).compute(idiomLevelModel, aClass);
//						System.out.printf("!!!!!!The NMD is %f, and NAD is %f, and AID is %f\n", new Object[]{new Double(NMD), new Double(NAD), new Double(AID)});
//					}catch (Exception e){
//						System.out.println("Fail");
//					}
					BbMetrics(idiomLevelModel, aClass, version[i]);
					CCMetrics(idiomLevelModel, aClass, version[i]);
					LCMetrics(idiomLevelModel, aClass, version[i]);
					MCMetrics(idiomLevelModel, aClass, version[i]);
					PBMetrics(idiomLevelModel, aClass, version[i]);
					SGMetrics(idiomLevelModel, aClass, version[i]);
					SKMetrics(idiomLevelModel, aClass, version[i]);
				}
			}
		}
	}
	
	//Metrics for Blob Detection
	private static void BbMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_Blob.ini";
		
		final Set codeSmell = new HashSet();
		ClassProperty classProp = new ClassProperty(aClass);
		codeSmell.add(new CodeSmell("LargeClass", "",classProp));
		
		double LCOM5 = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5")).compute(anAbstractLevelModel, aClass);
		double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
		double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
		
		final ICodeSmellDetection csDataClass = new DataClassDetection();
		csDataClass.detect(anAbstractLevelModel);
		final Set setDataClass = ((DataClassDetection) csDataClass)
				.getCodeSmells();
		
		sad.util.Relationships relations = sad.util.Relationships.getInstance();
		final Set classContainDataClass = relations.checkAssociationOneToMany(1, codeSmell, setDataClass);
		Iterator iter = classContainDataClass.iterator();
		int dataClassSize = 0;
		while(iter.hasNext()){
			final CodeSmellComposite csc = (CodeSmellComposite) iter.next();
			dataClassSize = csc.getSetOfCodeSmellsOfGeneric().size();
		}
		
		//输出到文件
		File file = new File(outputPath);
	//	System.out.println(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID()  + "|NAD_NMD:" + (NAD+NMD) + "|LCOM5:" + LCOM5 + "|DataClassSize:" + dataClassSize + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//Metrics for Complex Class Detection
	private static void CCMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_ComplexClass.ini";
		
		double McCabe = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("McCabe")).compute(anAbstractLevelModel, aClass);
		double NAD = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
		double NMD = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
		//TODO 输出到文件
		
		File file = new File(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID() + "|NAD_NMD:" + (NAD+NMD) + "|McCabe:" + McCabe + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	
	}
	
	//Metrics for Lazy Class Detection
	private static void LCMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_LazyClass.ini";
		
		double NAD = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
		double NMD = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
		double WMC = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("WMC")).compute(anAbstractLevelModel, aClass);
		//TODO　输出到文件
		File file = new File(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID() + "|NAD_NMD:" + (NAD+NMD) + "|WMC:" + WMC + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//Metrics for Message Chains
	private static void MCMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_MessageChains.ini";
		
		double NOTI = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NOTI")).compute(anAbstractLevelModel, aClass);
		//TODO 输出到文件
		File file = new File(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID() + "|NOTI:" + NOTI + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//Metrics for Refused Parent Bequest(RB)
	private static void PBMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		//TODO 暂不理解
	}
	
	//Metrics for Speculative Generality(SG)
	private static void SGMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_SpeculativeGenerality.ini";
		
		double NOC = ((IUnaryMetric)MetricsRepository.getInstance().getMetric("NOC")).compute(anAbstractLevelModel, aClass);
		boolean isAbstract = aClass.isAbstract();
		//TODO 输出到文件
		File file = new File(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID() + "|NOC:" + NOC + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//Metrics for Swiss Army Knife
	private static void SKMetrics(final IAbstractLevelModel anAbstractLevelModel, final IClass aClass, final String aName){
		String outputPath = resultPath + aName + "_SwissArmyKnife.ini";
		
		final Iterator iter = aClass.getIteratorOnImplementedInterfaces();
		Set setOfInterfaces = new HashSet();
		while (iter.hasNext()){
			final Object obj = iter.next();
			if (obj instanceof IInterface){
				final IInterface iInterface = (IInterface) obj;
				setOfInterfaces.add(iInterface);
			}
		}
		int interfacesSize = setOfInterfaces.size();
		//TODO 输出到文件
		File file = new File(outputPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		try{
			FileWriter fileWriter = new FileWriter(file, true);
			String s = new String(aClass.getDisplayID() + "|Number Of Interfaces:" + interfacesSize + "\n");
			fileWriter.write(s);
			fileWriter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
}

