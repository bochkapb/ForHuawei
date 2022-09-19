package p;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberTestCases = Integer.parseInt(in.nextLine());
        if(numberTestCases < 1 || numberTestCases > 20){
            throw new IllegalArgumentException("Number Test Cases must be between 1 and 20");
        }

        List<BigInteger> firstNumber = new ArrayList<>(numberTestCases);
        List<BigInteger> secondNumber = new ArrayList<>(numberTestCases);
        List<BigInteger> result = new ArrayList<>(numberTestCases);
        for (int i = 0; i < numberTestCases; i++){
            String[] split = in.nextLine().split(" ");
            if (split.length == 2) {
                BigInteger v1 = new BigInteger(split[0]);
                firstNumber.add(v1);
                BigInteger v2 = new BigInteger(split[1]);
                secondNumber.add(v2);
                result.add(v1.add(v2));
            }else {
                throw new IllegalArgumentException("You must input two divided by space");
            }
        }
        // 1 + 2 = 3
        for (int i = 0; i < numberTestCases; i++){
            System.out.println("Case " + (i+1)+":");
            System.out.println(firstNumber.get(i) + " + " + secondNumber.get(i) + " = " + result.get(i));
            if(i != numberTestCases -1) {
                System.out.println();
            }
        }
    }
}
