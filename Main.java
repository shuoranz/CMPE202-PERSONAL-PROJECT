import java.awt.image.BufferedImage;
import java.io.*;
//import java.util.Optional;
import java.util.Scanner;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sourceforge.plantuml.*;
public class Main {


	private static final Object PUBLIC = null;
	private static final Object STATIC = null;

	public static void main(String[] args) throws IOException{
		
		
		//TestHello();
		TestCase1();
		
		/*
		try {
		    File file = new File("src/Hello1.java");
		    Scanner scanner = new Scanner(file);
		    String lines;
		    while(scanner.hasNextLine()){
		    	lines = scanner.nextLine();
		    	//plantUmlSource.append(lines);
		    	//System.out.println(lines);
		    }
		    scanner.close();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		*/
        
	}

	public static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private String result;
        public String getParseResult(){ return this.result;}
        private void setParseResult(String value){this.result+=value+"\n";}
        @Override
        public void visit(MethodDeclaration n, Void arg) {

        	//System.out.print(n.getType() + " " + n.getModifiers());
        	//System.out.println(n.toString());
        	String modifier = n.getModifiers().toString();
        	if(modifier.equals("[PUBLIC]")){
        		
        		this.setParseResult(n.getType().toString() + " " + n.getName().toString() + "()");
        	}
        	
            //super.visit(n, arg);
            
        }
        
    }
	
	static boolean TestHello() throws IOException{
		//start JavaParse test
		FileInputStream in = new FileInputStream("src/Hello1.java");
        // parse it
        CompilationUnit cu = JavaParser.parse(in);
        
        
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);
        
        //System.out.println(visitor.getParseResult());
        
        //System.out.println(visitor.getParseResult().replace("null",""));
        // visit and print the methods names
        //System.out.println(cu.toString());
        
        

        //start plantUML test
		
		StringBuilder plantUmlSource = new StringBuilder();
		
        plantUmlSource.append("@startuml\n");
        
        
        //plantUmlSource.append("skinparam classAttributeIconSize 0\n");
        
        plantUmlSource.append("class Hello1 {\n");
        
        String temp = visitor.getParseResult().replace("null","");
        
        System.out.println(temp);
        
        plantUmlSource.append(temp + " \n");
        
        //plantUmlSource.append("String getStr() \n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("@enduml");
        
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("Hello1.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
        
		return true;
	}

	static boolean TestCase1() throws IOException{
		
		//start JavaParse test
		FileInputStream in = new FileInputStream("src/A.java");
		

        // parse it
        CompilationUnit cu = JavaParser.parse(in);
        
        
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);
        
        //start plantUML test

		StringBuilder plantUmlSource = new StringBuilder();
		
        plantUmlSource.append("@startuml\n");
        
        
        //plantUmlSource.append("skinparam classAttributeIconSize 0\n");
        
        plantUmlSource.append("class A {\n");

        String temp = visitor.getParseResult().replace("null","");
		/*
        System.out.println(temp);
        
        plantUmlSource.append(temp + " \n");
        
        //plantUmlSource.append("String getStr() \n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("@enduml");

        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("A.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		*/
		return true;
	}
	
	static boolean TestCase2() throws IOException{
		return true;
	}
	
	static boolean TestCase3() throws IOException{
		return true;
	}
	
	static boolean TestCase4() throws IOException{
		return true;
	}
	
	static boolean TestCase5() throws IOException{
		return true;
	}
	
	static boolean TestCase6(){
		return true;
	}
}
