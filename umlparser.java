import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import net.sourceforge.plantuml.*;

public class umlparser {


	private static final Object PUBLIC = null;
	private static final Object STATIC = null;

	public static void main(String[] args) throws IOException{

		if (args.length==2){
		    String inputCmd = args[0];
		    String outputCmd = args[1];
			runParser(inputCmd,outputCmd);
		} else {
			System.out.println("Input error. Correct example:");
			System.out.println("umlparser test4 ouput4.png");
		}
	}
	
	public static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private String result;
        private String classDependency = "";
        private boolean depFlag = false;
        private ArrayList<String> allClass = new ArrayList();
        private String currentClassName;
        private String currentClassType;
        public String getParseResult(){
        	String returnVal = this.result;
        	if(returnVal != null && !returnVal.isEmpty()){
        		return this.result;
        	} else {
        		return "";
        	}
        	
        }
        public void setAllClasses(ArrayList<String> allClasses){
        	int length = allClasses.size();
        	for(int i=0; i<length; i++){
        		this.allClass.add(allClasses.get(i));
        	}
        }
        public String getClassDependency(){
        	String returnVal = this.classDependency;
        	if(returnVal != null && !returnVal.isEmpty()){
        		return this.classDependency;
        	} else {
        		return "";
        	}
        }
        public void setFlag(){
        	this.depFlag = true;
        }
        public void setCurrentClass(String[] CCN){
        	this.currentClassName = CCN[0];
        	this.currentClassType = CCN[1];
        }
        private void setParseResult(String value){this.result+=value+"\n";}
        private void setClassDependency(String value){this.classDependency+=value+"\n";}
        @Override
        public void visit(MethodDeclaration n, Void arg) {
        	if(this.depFlag == false){
        		String modifier = n.getModifiers().toString();
            	if(modifier.equals("[PUBLIC]")){
            		
            		NodeList paras = n.getParameters();
    	        	int length = paras.size();
    	        	String resultPara;
    	        	resultPara = "";
    	        	for(int i=0;i<length;i++){
    	        		String tempPara = paras.get(i).toString();
    	        		String[] splitPara = tempPara.split(" ");
    	        		resultPara += splitPara[1] + " : " + splitPara[0] + ",";
    	        	}
    	        	if (resultPara != null && resultPara.length() > 0) {
    	        		resultPara = resultPara.substring(0, resultPara.length()-1);
            	    }
            		this.setParseResult( "+" + n.getName().toString() + "(" + resultPara + "): " + n.getType().toString());
            	}
        	} else {
        		String modifier = n.getModifiers().toString();
        		String temp1 = "";
            	if(modifier.equals("[PUBLIC]")){
            		NodeList paras = n.getParameters();
    	        	int length = paras.size();
    	        	String resultPara;
    	        	resultPara = "";
    	        	for(int i=0;i<length;i++){
    	        		String tempPara = paras.get(i).toString();
    	        		String[] splitPara = tempPara.split(" ");
    	        		resultPara += splitPara[1] + " : " + splitPara[0] + ",";
    	        		int length1 = this.allClass.size();
    	        		for(int j=0; j<length1; j++){
    	        			if(allClass.get(j).equals(splitPara[0]) && currentClassType.equals("false")){
    	        				
    	        				temp1 = this.currentClassName + " ..> " + splitPara[0]+"\n";
    	        				if(this.getClassDependency().equals("")){
    	        					this.setClassDependency(temp1);
    	        				}else if(!this.getClassDependency().contains(temp1)){
    	        					this.setClassDependency(this.getClassDependency()+temp1);
    	        				}
    	        			}
    	        		}
    	        	}
    	        	//System.out.println(temp1);
    	        	if (resultPara != null && resultPara.length() > 0) {
    	        		resultPara = resultPara.substring(0, resultPara.length()-1);
            	    }
            	}
        	}
        	
            //super.visit(n, arg);

        }
        
    }
	
	static boolean runParser(String inputCmd, String outputCmd) throws IOException{
		//String sourcePath = "src/test4";
		//String classDiagramPng = "Test4.png";
		String sourcePath = "src/" + inputCmd;
		String classDiagramPng = outputCmd;
		File folder = new File(sourcePath);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> allClasses = new ArrayList<String>();
		String valClass = "@startuml\n";
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	String fileName = listOfFiles[i].getName();
	    	if(!fileName.contains(".java")){
	    		continue;
	    	}
	        FileInputStream in = new FileInputStream(sourcePath+"/"+fileName);
	        CompilationUnit cu = JavaParser.parse(in);

	        
	        String ValDeclaration = getCuDeclaration(cu);
	        valClass += ValDeclaration;
	        
	        String ValConstructor = getCuConstructorDecl(cu);
	        if(ValConstructor != null && !ValConstructor.isEmpty()){
	        	valClass += ValConstructor;
	        }
	        
	        MethodVisitor visitor = new MethodVisitor();
	        visitor.visit(cu, null);
	        String temp = "";
	        if(visitor.getParseResult().contains("null"))
	        	temp = visitor.getParseResult().replace("null","");
	        valClass += temp + "}\n";
	        allClasses.add(cu.getTypes().get(0).getNameAsString());
	      }
	    }
	    
	    //System.out.println(valClass);
	    String valClassRelation = "";
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		String fileName = listOfFiles[i].getName();
	    		if(!fileName.contains(".java")){
		    		continue;
		    	}
		        //System.out.println("File " + fileName);
		        FileInputStream in = new FileInputStream(sourcePath+"/"+fileName);
		        CompilationUnit cu = JavaParser.parse(in);
		        valClassRelation += getClassRelation(cu,allClasses);
		        MethodVisitor visitor = new MethodVisitor();
		        visitor.setAllClasses(allClasses);
		        visitor.setFlag();
		        String currentClassName = cu.getTypes().get(0).getNameAsString();
		        String[] CNN = {
		        	currentClassName,
		        	isInterface(cu,currentClassName).toString()
		        };
		        visitor.setCurrentClass(CNN);
		        visitor.visit(cu, null);
		        valClassRelation += visitor.getClassDependency();
	    	}
	    }
	    valClass += valClassRelation;
	    valClass += "@enduml";
	    valClass = cleanSourceCode(valClass);
	    //System.out.println(valClass);
		StringBuilder plantUmlSource = new StringBuilder();
        plantUmlSource.append(valClass + " \n");
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File(classDiagramPng));
        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		return true;

	}
	
	private static String getCuConstructorDecl(CompilationUnit compilationUnit) {
	    ConstructorDeclaration[] ctorDecls = compilationUnit.getTypes().get(0).getMembers().stream()
	            .filter(bd -> bd instanceof ConstructorDeclaration)
	            .map(bd -> (ConstructorDeclaration) bd).toArray(ConstructorDeclaration[]::new);

	    if (ctorDecls.length == 0) {
	        return null;
	    } else {
	        for (ConstructorDeclaration ctorDecl : ctorDecls) {
	        	NodeList paras = ctorDecl.getParameters();
	        	int length = paras.size();
	        	
	        	for(int i=0;i<length;i++){
	        		String tempPara = paras.get(i).toString();
	        		String[] splitPara = tempPara.split(" ");
	        		String returnValue = "+" + ctorDecl.getName() + "(" + splitPara[1] + " : " + splitPara[0] + ")\n";
	        		return returnValue;
	        	}
	        }
	        return null;
	    }
	}
	
	private static String getCuDeclaration(CompilationUnit compilationUnit) {
		String className = compilationUnit.getTypes().get(0).getNameAsString();
		String returnValue;
		if(!isInterface(compilationUnit,className)){
			returnValue = "class " + className + "{\n";
		} else {
			returnValue = "interface " + className + "{\n";
		}
		return returnValue;
	}
	
	private static Boolean isInterface(CompilationUnit compilationUnit, String className){
		
		return compilationUnit.toString().contains("interface "+className);
	}
	
	private static String getClassRelation(CompilationUnit cu, ArrayList<String> allClasses){
		
		String className = cu.getTypes().get(0).getNameAsString();
		String cutDeclaration = cutDeclaration(cu,className);
		String cutField = cutField(cu,className,allClasses);
		return cutDeclaration + cutField;
	}
	
	private static String cutField(CompilationUnit cu, String className, ArrayList<String> allClasses){
		ArrayList<String> allFieldRelation = new ArrayList<String>();
		List Fields = cu.getTypes().get(0).getFields();
		String returnValue = "";
		for(int i=0; i<Fields.size(); i++){
			String temp1 = Fields.get(i).toString();
			if(temp1.contains("=")){
				String[] temp2 = temp1.split("=");
				for(int j=0; j<allClasses.size(); j++){
					if((temp2[0].contains(" "+ allClasses.get(j) +" ") || temp2[0].contains("<"+ allClasses.get(j) +">"))
							&&
						(temp2[1].contains(" "+ allClasses.get(j) +" ") || temp2[1].contains("<"+ allClasses.get(j) +">"))){
						// x to multiple
						
						allFieldRelation.add(className + " \"1\" -- \"many\" " + allClasses.get(j));
					}
				}
			} else if(temp1.contains("[]")){
				for(int j=0; j<allClasses.size(); j++){
					if(temp1.contains(" "+ allClasses.get(j) +" ") || temp1.contains(" "+ allClasses.get(j) +"[]")){
						// x to multiple
						
						allFieldRelation.add(className + " \"1\" -- \"many\" " + allClasses.get(j));
					}
				}
			} else {
				for(int j=0; j<allClasses.size(); j++){
					if(temp1.contains(" "+ allClasses.get(j) +" ")){
						// x to multiple
						
						allFieldRelation.add(className + " -- " + allClasses.get(j));
					}
				}
			}
			//System.out.println(Fields.get(i));
			for(int k=0; k<allFieldRelation.size(); k++){
				//System.out.println(allFieldRelation.get(k));
				returnValue += allFieldRelation.get(k) + "\n";
			}
		}
		return returnValue;
	}
	
	private static String cutDeclaration(CompilationUnit cu, String className){
		String returnValue = "";
		//
		if(cu.toString().contains(" "+className+" ")){

			String[] temp1 = cu.toString().split("}");
			String[] temp2 = temp1[0].split(" "+className+" ");
			//System.out.println(temp2[1]);
			if(containsIgnoreCase(temp2[1],"extends")){
				String[] temp3 = temp1[0].split(" extends ");
				if(temp3[1].contains(" ")){
					String[] temp4 = temp3[1].split(" ");
					returnValue += className + " --|> " + temp4[0] + "\n";
				} else {
					returnValue += className + " --|> " + temp3[0] + "\n";
				}
			} else if (containsIgnoreCase(temp2[1],"implements")){
				String[] temp3 = temp1[0].split(" implements ");
				if(temp3[1].contains(" ")){
					String[] temp4 = temp3[1].split(" ");
					returnValue += className + " ..|> " + temp4[0] + "\n";
				} else {
					returnValue += className + " ..|> " + temp3[0] + "\n";
				}
			}
		} else {
			return "";
		}
			
		return returnValue;
		
	}
	
	public static boolean containsIgnoreCase(String str, String searchStr)     {
	    if(str == null || searchStr == null) return false;

	    final int length = searchStr.length();
	    if (length == 0)
	        return true;

	    for (int i = str.length() - length; i >= 0; i--) {
	        if (str.regionMatches(true, i, searchStr, 0, length))
	            return true;
	    }
	    return false;
	}
	
	public static String cleanSourceCode(String javaSource){
		String[] stringLn = javaSource.split("\n");
		String returnString = javaSource;
		for(int i=0; i<stringLn.length; i++){
			if(stringLn[i].contains(" ..|> ")){
				String[] temp1 = stringLn[i].split(Pattern.quote(" ..|> "));
				//System.out.println(temp1[0] + " " +temp1[1]);
				String className = "class " + temp1[0] ;
				String interfaceName = "interface " + temp1[1] ;
				String classMethodsStringOld = javaSource.split(className)[1].split("}")[0];
				String classMethodsStringNew = classMethodsStringOld;
				//System.out.println(classMethodsStringOld);
				String[] classMethods = classMethodsStringOld.split("\n");
				String[] interfaceMethods = javaSource.split(interfaceName)[1].split("\n");
				for(int j=1; j<interfaceMethods.length;j++){
					if(interfaceMethods[j].equals("}")){
						break;
					} else {
						for(int k=1; k<classMethods.length; k++){
							if(classMethods[k].equals("}")){
								break;
							}else if(interfaceMethods[j].equals(classMethods[k])){
								//System.out.println(k + "duplicate: " + interfaceMethods[j]);
								//System.out.println(k + j);
								classMethodsStringNew = classMethodsStringNew.replace(classMethods[k]+"\n","");
							}
						}
					}
					
				}
				//System.out.println(classMethodsStringNew);
				returnString = returnString.replace(classMethodsStringOld, classMethodsStringNew);
			}
		}
		
		return returnString;
	}
}
