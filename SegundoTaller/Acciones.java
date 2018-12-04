import java.io.*;

public class Acciones{
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
	public static int [] value (String value) {
		String [] values = value.split(" ");        //Split the String by spaces
		int[] values_Int = new int [values.length]; //Create a int array with the same size of the values String
		for (int i = 0; i < values.length; i++) {   //Go through the arrays 
			values_Int [i] = Integer.parseInt(values [i]);//Convert to int value
		}
		return values_Int;//Return int array
	}
	
	public static int find_purchase (int [] values) {
		int lower = values [0];//Create a variable that takes the position 0 of the values array
		int index = 0;//Create a variable that will be save the index of lower price
		for (int i = 0; i < values.length - 2; i++) {//Go trhough the array, except the last position
			if (lower > values[i+1]) {
				lower = values[i+1];//Save the value of the lower price
				index = (i + 1);//Save the index of the lower price
			}
		}
		return index;
	}
	
	public static int find_Sale(int [] values, int day) {
		int higher = values [day];//Create a variable that takes the position "day" of the values array
		
		int index = 0;//Create a variable that will be save the index of higher price
		for (int i = day; i < values.length - 1; i++) {//Go trhough the array, except the last position
			if (higher < values[i+1]) {
				higher = values[i+1];//Save the value of the higher price
				index = (i + 1);//Save the index of the higher price
			}else {
	
				index = (i + 1);//Save the index of the lower price
			}
		}
		return index;
	}	
	
	public static void main (String [] args) {
		
		try {			
                    bw.write("Numero de semanas: \n");
                    bw.flush();
                    int weeks = Integer.parseInt(br.readLine()); // Read the number of the weeks
			for (int i = 1; i <= weeks; i++){ // This loop go through the weeks one by one 
                            bw.write("En la semana " + i + "\nEntre el valor de las acciones separados por un espacio: \n");
                            bw.flush();
                            String value = br.readLine(); //Read the values
                            int [] values_Int = value(value); // Convert the String values to int array values
				
                            int purchase = find_purchase(values_Int);//Calls the find_pruchase function and saves the index
                            int sale = find_Sale(values_Int, purchase);//Calls the find_Sale function and saves the index
                            bw.write("Comprelo en el dia # " + (purchase + 1) + " y vendalo en el dia # " + (sale + 1));
                            bw.flush();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
        }
}

