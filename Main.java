import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileOutputStream;
import java.io.FileReader;

import net.sourceforge.plantuml.FileFormat; 
import net.sourceforge.plantuml.FileFormatOption; 
import net.sourceforge.plantuml.SourceStringReader; 
import net.sourceforge.plantuml.syntax.SyntaxChecker; 
import net.sourceforge.plantuml.syntax.SyntaxResult;

public class Main {
	public static void main(String[] args) throws IOException, ParseException{
		
		//start JavaParse test
		
		FileInputStream in = new FileInputStream("src/Hello1.java");

        // parse it
        CompilationUnit cu = JavaParser.parse(in);

        // visit and print the methods names
        System.out.println(cu.toString());
        
        
        //start plantUML test
		
		StringBuilder plantUmlSource = new StringBuilder();
		
        plantUmlSource.append("@startuml\n");
        

		try {
		    File file = new File("src/Hello1.java");
		    Scanner scanner = new Scanner(file);
		    String lines;
		    while(scanner.hasNextLine()){
		    	lines = scanner.nextLine();
		    	//plantUmlSource.append(lines);
		    	System.out.println(lines);
		    }
		    scanner.close();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}

        
        plantUmlSource.append("skinparam classAttributeIconSize 0\n");
        
        plantUmlSource.append("class A {\n");

        plantUmlSource.append("-int x\n");
        
        plantUmlSource.append("-int y\n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("class B {\n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("A \"1\" -- \"many\" B\n");
        
        plantUmlSource.append("class C {\n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("A \"1\" -- \"1\" C\n");
        
        plantUmlSource.append("class D {\n");
        
        plantUmlSource.append("}\n");
        
        plantUmlSource.append("A \"1\" -- \"many\" D\n");

        plantUmlSource.append("@enduml");
        
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("Hello1.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
	}
}
