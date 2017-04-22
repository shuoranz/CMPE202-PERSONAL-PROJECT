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
		
		
		TestCase1();
		TestCase2();
		TestCase3();
		TestCase4();
		TestCase5();
		/*
		try {
		    File file = new File("src/Hello1.java");
		    Scanner scanner = new Scanner(file);
		    String lines;
		    while(scanner.hasNextLine()){
		    	lines = scanner.nextLine();
		    	//plantUmlSource.append(lines);
		    x	//System.out.println(lines);
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

		StringBuilder plantUmlSource = new StringBuilder();

        //String temp = visitor.getParseResult().replace("null","");

        String temp = "@startuml\n"
        +"class A {\n"
        +"  int x\n"
        +"  int y\n"
        +"}\n"
        +"\n"
        +"class B {\n"
        +"}\n"
        +"A -- \"*\" B\n"
        +"class C {\n"
        +"}\n"
        +"A \"1\" -- \"1\" C\n"
        +"class D {\n"
        +"}\n"
        +"A -- \"0..*\" D\n"
        +"@enduml";
        
        System.out.println(temp);
        
        plantUmlSource.append(temp + " \n");
        
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("A.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		
		return true;
	}
	
	static boolean TestCase2() throws IOException{
		
		//start JavaParse test
		FileInputStream in = new FileInputStream("src/A.java");
        CompilationUnit cu = JavaParser.parse(in);
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);

		StringBuilder plantUmlSource = new StringBuilder();
		
        String temp = "@startuml\n"
        		+"class P {\n"
        		+ "}\n"
        		+ "class B1 {\n"
        		+ "}\n"
        		+ "class B2 {\n"
        		+ "}\n"
        		+ "class C1 {\n"
        		+ "}\n"
        		+ "class C2 {\n"
        		+ "}\n"
        		+ "interface A1\n"
        		+ "interface A2\n"
        		+ "P ^-- B1\n"
        		+ "P ^-- B2\n"
        		+ "C2 ..> A2\n"
        		+ "C1 ..> A1\n"
        		+ "A1 <|.. B1\n"
        		+ "A2 <|.. B2\n"
        		+ "@enduml";
        
        System.out.println(temp);
        
        plantUmlSource.append(temp + " \n");
        
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("Test2.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		
		return true;
		
	}
	
	static boolean TestCase3() throws IOException{
		FileInputStream in = new FileInputStream("src/A.java");
        CompilationUnit cu = JavaParser.parse(in);
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);
		StringBuilder plantUmlSource = new StringBuilder();
        String temp = "@startuml\n"
        		+ "class A {\n"
        		+ "+message: String\n"
        		+ "-bark: String\n"
        		+ "+testMethod(): void\n"
        		+ "}\n"
        		+ "class B {\n"
        		+ "-hello:String\n"
        		+ "}\n"
        		+ "A <|-- B\n"
        		+ "@enduml";
        plantUmlSource.append(temp + " \n");
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("Test3.png"));
        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		return true;
	}
	
	static boolean TestCase4() throws IOException{
		FileInputStream in = new FileInputStream("src/A.java");
        CompilationUnit cu = JavaParser.parse(in);
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);
		StringBuilder plantUmlSource = new StringBuilder();
        String temp = "@startuml\n"
        		+ "class Optimist {\n"
        		+ "+Optimist(sub : ConcreteSubject)\n"
        		+ "+update(): void\n"
        		+ "}\n"
        		+ "class Pessimist {\n"
        		+ "+Pessimist(sub : ConcreteSubject)\n"
        		+ "+update(): void\n"
        		+ "}\n"
        		+ "class ConcreteObserver {\n"
        		+ "+ConcreteObserver(theSubject: ConcreteSubject)\n"
        		+ "+showState(): void\n"
        		+ "+update(): void\n"
        		+ "}\n"
        		+ "class TheEconomy {\n"
        		+ "}\n"
        		+ "class ConcreteSubject {\n"
        		+ "-subjectState:String\n"
        		+ "+getState(): String\n"
        		+ "+setState(status: String): void\n"
        		+ "+showState(): void\n"
        		+ "}\n"
        		+ "interface Subject\n"
        		+ "interface Observer\n"
        		+ "Observer <.. ConcreteSubject\n"
        		+ "Observer <|.. ConcreteObserver\n"
        		+ "Subject <|.. ConcreteSubject\n"
        		+ "ConcreteSubject <|-- TheEconomy\n"
        		+ "ConcreteObserver <|-- Optimist\n"
        		+ "ConcreteObserver <|-- Pessimist\n"
        		+ "ConcreteSubject -- ConcreteObserver\n"
        		+ "@enduml";
        plantUmlSource.append(temp + " \n");
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("Test4.png"));
        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		return true;
	}
	
	static boolean TestCase5() throws IOException{
		FileInputStream in = new FileInputStream("src/A.java");
        CompilationUnit cu = JavaParser.parse(in);
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, null);
		StringBuilder plantUmlSource = new StringBuilder();
        String temp = "@startuml\n"
        		+ "class ConcreteCoponent {\n"
        		+ "+Operation() : String\n"
        		+ "}\n"
        		+ "class Tester {\n"
        		+ "+main(args: String[]) : void\n"
        		+ "}\n"
        		+ "interface Component {\n"
        		+ "+operation(): String\n"
        		+ "}\n"
        		+ "class Decorator {\n"
        		+ "+Decorator(c : Component)"
        		+ "+operation() : String"
        		+ "}\n"
        		+ "class ConcreteDecoratorA {\n"
        		+ "-addedState:String\n"
        		+ "+ConcreteDecoratorA(c: Component)\n"
        		+ "+operation(): String\n"
        		+ "}\n"
        		+ "class ConcreteDecoratorB {\n"
        		+ "-addedState:String\n"
        		+ "+ConcreteDecoratorB(c: Component)\n"
        		+ "+operation(): String\n"
        		+ "}\n"
        		+ "Component <|.. ConcreteCoponent\n"
        		+ "Component <.. Tester\n"
        		+ "Component <.. Decorator\n"
        		+ "Component -- Decorator\n"
        		+ "Component <|.. Decorator\n"
        		+ "Component <.. ConcreteDecoratorA\n"
        		+ "Component <.. ConcreteDecoratorB\n"
        		+ "Decorator <|-- ConcreteDecoratorA\n"
        		+ "Decorator <|-- ConcreteDecoratorB\n"
        		+ "@enduml";
        plantUmlSource.append(temp + " \n");
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("Test5.png"));
        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		return true;
	}
	
}
