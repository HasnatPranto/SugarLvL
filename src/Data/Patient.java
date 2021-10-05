package Data;

import java.util.Arrays;

public class Patient {

    public static String[] symptoms = new String[17];

    public static void refresh(){
        Arrays.fill(symptoms,"");
    }
}
