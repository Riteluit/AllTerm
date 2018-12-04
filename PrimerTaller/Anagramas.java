import java.io.*;

public class Anagrama{
    static BufferedReader br = new BufferedReader (new InputStreamReader (System.in));       //creating the Reading Buffer
    static BufferedWriter bw = new BufferedWriter (new OutputStreamWriter (System.out));     //creating the Writing Buffer
    static int counter=0;

    public static void combinaciones(String first,String string) throws IOException{
    	if(string.length()==2){
            counter=counter+2;
            bw.write(first+string.charAt(1)+""+string.charAt(0));
            bw.write(first+string.charAt(0)+""+string.charAt(1));
            bw.flush();
    	}else{
            for (int i=0;i<string.length();i++){
               combinaciones(first+string.charAt(i),removechar(string,i));
            }
    	}
    }
   
    public static String removechar(String string,int i){
    	if(i==0){
            return string.substring(i+1,string.length());
    	}else{
            if(i==string.length()){
                return string.substring(0,string.length()-1);
            }else{
                return string.substring(0,i)+string.substring(i+1,string.length());
            }
        }
    }
   
    public static void main(String args[]) throws IOException 
    {
    	String string = br.readLine();
    	bw.write("Palabra: "+string);
    	bw.newLine();
    	combinaciones("\n"+"",string);
    	bw.newLine();
    	bw.write("total de palabras:"+counter);
    	bw.newLine();
    	bw.flush();
    }
}
