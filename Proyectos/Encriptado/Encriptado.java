import java.io.*;

public class Encr {

   
    public static void main(String[] args) {
        // TODO code application logic here
                BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String modified;
		
		try {
			
			modified = br.readLine();//read the word
			
			bw.write("\nOriginal: " + modified);
			
			modified = modified.toLowerCase(); // Change the String in lower case
			
			modified = modified.replace("a","4");// Change the vowel by a number
			modified = modified.replace("e","3");// Change the vowel by a number
			modified = modified.replace("i","1");// Change the vowel by a number
			modified = modified.replace("o","0");// Change the vowel by a number
			modified = modified.replace("u","9");// Change the vowel by a number
			
			bw.write("\nModificado: " + modified); // show the word in the screen
			
                        char c[]= modified.toCharArray();//tomar cada letra del String
                        bw.write("\n\n El corrimiento: ");
                        bw.flush();
                        String n=br.readLine();

                        bw.flush();
                        int n1=Integer.parseInt(n);
                        
                        
                        
                        for (int i=0;i<modified.length();i++){
                            
                             c[i]=(char)(modified.charAt(i)+(char)n1);
                                if (c[i]>122) {
                                	c[i]= (char) (c[i]-(char)(26)); 
                                }else {
                                	if (c[i] >= 58 && c[i] <= 96) {
                                		c[i]= (char) (c[i]-(char)(10)); 

                                	}
                                }
                                
                        }
                        
                        String Enc= String.valueOf(c);
                        bw.write("El prro "+Enc); // show the word in the screen
                        
                        
                        
                        char d[]= Enc.toCharArray();
                        
                        for (int i=0;i<Enc.length();i++){
                            d[i]=(char)(Enc.charAt(i)+(char)-n1);
                            if (c[i]>122) {
                            	c[i]= (char) (c[i]+(char)(26)); 
                            }else {
                            	if (c[i] >= 58 && c[i] <= 96) {
                            		c[i]= (char) (c[i]+(char)(10)); 
                            	}
                            }
                            
                        }
                        String Des= String.valueOf(d);
                        bw.write("\n\n"+Des); // print the decrypt word with numbers
                        
                        
                        Des = Des.replace("4","a");// Change the number by a vowel
                        Des = Des.replace("3","e");// Change the number by a vowel
                        Des = Des.replace("0","o");// Change the number by a vowel
                        Des = Des.replace("1","i");// Change the number by a vowel
                        Des = Des.replace("9","u");// Change the number by a vowel
                        
                        bw.write("\nDecodificado: " + Des); // show the word in the screen
                        bw.flush();
               } 
                
                catch (Exception ex){
                    
                }
    
}
}
