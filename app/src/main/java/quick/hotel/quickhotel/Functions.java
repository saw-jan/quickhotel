package quick.hotel.quickhotel;

import android.text.TextUtils;

public class Functions {
    //check for empty text box
    public static boolean IsEmptyText(String input){
        if (TextUtils.isEmpty(input)) {
            return true;
        } else {
            return false;
        }
    }
    //checks, is numbers?
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    //first cap
    public static String capsFirst (String input)
    {
        String firstLetter = input.substring(0,1).toUpperCase();
        String restLetters = input.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }
    public static String removeWords(String word, String remove){
        return word.replace(remove,"");
    }
}
