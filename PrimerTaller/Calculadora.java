import java.io.*;


public class Calculadora{
    static BufferedReader br= new BufferedReader (new InputStreamReader (System.in));
    static BufferedWriter wr= new BufferedWriter (new OutputStreamWriter(System.out));
    static String op; 
    static String x;
    static String y;
    static int op2,s1,s2;
    
    public static int suma(int a,int b){
        int c = a + b;          //Makes the sum and the result is stored in the variable c
        return c;                        
           }
 
    public static int resta(int a,int b){
               int c = a - b;   //Makes the subtraction and the result is stored in the variable c
      return c;
           }
 
    public static int multi(int a,int b){         
      if (b==1){                           // if the variable b is 1 return the variable a
                return a;               
                }else{
                return a + multi(a, b-1);  //Recursion is to get to b equal to 1 and add up the all until getting the call of start
                }
           }
 
    public static int division(int a,int b){
        if (b<=0){
            return 0;
        }else{
            int c = a / b;               // Makes the division and the result is stored in the variable c
            return c;
        }
    }
  
    public static int potencia(int a,int b){
        if (b==0){
            return 1;
        }
        if (b==1){
            return a; 
        }else
            return  a*potencia(a,b-1); 
    }
  
    public static double raizQ(int a){
      double b = Math.sqrt(a);        // This function makes the square of each number
      return b;
    }
  
    public static int residuo(int a, int b){
      int c= a%b;                    //It shows the residue of a division
      return c;
    }
    
    public static String menu(){
       String menu = "\n\n  Calculadora " + "\n1. Suma" + "\n2. Resta" + "\n3. Multiplicación" + "\n4. División"+
                    "\n5. Potencia"+"\n6. Raiz cuadrada"+"\n7. Residuo de la division"+"\n8. Salir"+"\n\n Opcion:";
                      //this is the menu that to appear in the start
       return menu;
     }
  
    public static void x() throws IOException{
      wr.write("Digite el Primer Numero ");
      wr.flush();
      x = br.readLine();               //this is to read the value that the usuary chosee      
      s1 = Integer.parseInt(x);
    }
    public static void y() throws IOException {
        wr.write("Digite el Segundo Numero ");
        wr.flush();
        y = br.readLine();               //this is to read the value that the usuary chosee
        s2 = Integer.parseInt(y); 
    }

    public static void main(String[] args) {
        
        
       try{             
        do{
            wr.write(menu());//invoke the function of menu
            wr.flush();
            op=br.readLine();           //Read the opcion that the user want          
             op2= Integer.parseInt(op); // this is to the variable turning into one number integer
            switch(op2){
                case 1:
                    x();
                    y();
                    wr.write("El resultado es " + suma(s1, s2)); // invoke the function "suma"
                        break;
                case 2: 
                    x();
                    y();
                    wr.write("El resultado es " + resta(s1, s2)); //invoke the function "resta"
                        break;
                case 3:
                    x();
                    y();
                    wr.write("El resultado es " + multi(s1, s2)); //invoke the function "multi"
                    break;
                case 4:
                    x();
                    y();
                    wr.write("El resultado es " + division(s1, s2)); //invoke the function "division"
                    break;
                case 5:
                    x();
                    y();
                    wr.write("El resultado es " + potencia(s1, s2)); //invoke the function "potencia"
                    break;
                case 6:
                    x();
                    wr.write("El resultado es " + raizQ(s1)); //invoke the function "reizQ"
                    break;
                case 7:
                    x();
                    y();
                    wr.write("El resultado es " + residuo(s1, s2)); //invoke the function "residuo"
                    break;
                default:
                        if (op2 > 8)                       //this is for say that the option is incorrect
                            System.out.println("\n\n: Opción no valida");
            }
       wr.flush();
        }while(op2!=8);  //this is for exit the program
       }catch (Exception ex){   
       }  
    }         
}
