package PrimerTaller;
import java.io.*;
public class Recursividad {
    
    public static int pascal (int i,int j) {
	if(j == 0 || j == i) { //If j is 0 or j is equal to i, the function will be return 1
            return 1;
	}else {
            return pascal(i-1,j-1) + pascal(i-1,j);//Else the sum of the up num and back num sill be the actual num 
	}
    }
    
   public static int Fibonacci(int n){
        if (n==1){                                        //If the number is equal to 1 returns 0
            return 0;
        }else {
            if (n==2){                                    //if the number is equal to 2 returns 1
                return 1;  
            }else{                                        
                return Fibonacci(n-1)+Fibonacci(n-2);     //You add the first term minus one with the second minus two
            }
        }
    } 
 
    public static int Fac(int n){
        if(n>1){                     //if the numer is greater than 1 returns the number is multiplied by the function minus one
            return n*Fac(n-1);
        }else{
            return 1;
        }
    }
 
   public static String menu(){
        String menu = "\n\n1. Pascar" + "\n2. Fibonacci" + "\n3. Factorial" + "\n4. Salir" + "\n\n Opcion:";
                         //this is the menu that to appear in the start
        return menu;
   }
   
    public static void main(String[] args) {
        
        BufferedReader br= new BufferedReader (new InputStreamReader (System.in));
        BufferedWriter bw= new BufferedWriter (new OutputStreamWriter(System.out));
        
        int a=0;
        int n=0;
        String b;
        try{
        do{
            bw.write(menu());              //invoke the function of menu
            bw.flush();                    
            b=br.readLine();               //this is to read the option that the usuary chosee 
            a = Integer.parseInt(b);       //this is to the variable turning into one number integer
        switch(a){
            case 1:
            int pascal=Integer.parseInt(br.readLine());
		for(int i=0; i< pascal; i++){
                    for(int j=0; j <=i; j++) {
                        bw.write((pascal(i,j)+" "));
                        bw.flush();
                    }
                    bw.write("\n");
                    bw.flush();
                }
              break;
            case 2:
                bw.write("Digite el numero: ");
                bw.flush();
                b=br.readLine();                //this is to read the option that the usuary chosee 
                n = Integer.parseInt(b);        //this is to the variable turning into one number integer
                bw.write("\n" + Fibonacci(n));  // invoke the fuction of Fibonacci
              break;              
            case 3:
                bw.write("Digite :");
                bw.flush();
                b=br.readLine();                //this is to read the option that the usuary chosee 
                n = Integer.parseInt(b);        //this is to the variable turning into one number integer
                bw.write("\n" + Fac(n));        // invoke the fuction "Factorial"
              break;
              default:
                        if (a > 4)                       //this is for say that the option is incorrect
                            System.out.println("\n\n: Opción no valida");
    }
        bw.flush();
        }while(a!=4); //this is for exit the program
        }
        catch (Exception ex){
           
        }
}
    
}
