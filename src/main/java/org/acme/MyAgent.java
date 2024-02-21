package org.acme;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


public class MyAgent {
	

	
	public static void premain(
		String agentArgs, Instrumentation inst) {
		System.out.println("[Agent] In premain method");
	    dsiplayClasses(inst);
	}


	private static void dsiplayClasses(Instrumentation instrumentation) {	
	    Class<?>[] cLasses = instrumentation.getAllLoadedClasses();
	    for (Class<?> cls : cLasses) {
	        System.out.println("PreMainAgent get loaded class:" + cls.getName());
	    }
	    
	 // Register our transformer
	    instrumentation.addTransformer(new transformer()); 
	}
}
class transformer implements ClassFileTransformer
{
   // The transform method is called for each non-system class as they are being loaded  
   public byte[] transform(ClassLoader loader, String className, 
                           Class<?> classBeingRedefined, ProtectionDomain protectionDomain, 
                           byte[] classfileBuffer) throws IllegalClassFormatException
   {
     if (className != null)
     {
       // Skip all system classes
       if (!className.startsWith("java") && 
           !className.startsWith("sun") && 
           !className.startsWith("javax") && 
           !className.startsWith ("com") && 
           !className.startsWith("jdk"))
       {
         System.out.println("Dumping: " + className);

       }
     }
     // We are not modifying the bytecode in anyway, so return it as-is
     return classfileBuffer;
   }
 }

