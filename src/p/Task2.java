package p;

import java.util.*;

public class Task2 {
    private static final int MAX_COUNT = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String first = in.nextLine();
        String[] split = first.split(" ");
        if (split.length != 2){
            throw new IllegalArgumentException("You must input two divided by space");
        }
        int numberOfInteger = Integer.parseInt(split[0]);
        int upperBound = Integer.parseInt(split[1]);
        if(numberOfInteger < 1 || numberOfInteger > 1000){
            throw new IllegalArgumentException("Number Of Integer must be between 1 and 20");
        }

        if(upperBound < 1 || upperBound > 1000000000){
            throw new IllegalArgumentException("Upper Bound must be between 1 and 20");
        }

        SortedSet<Integer> list = new TreeSet<>();
        for (int i = 0; i < numberOfInteger; i++){
            int value = Integer.parseInt(in.nextLine());
            if (value < upperBound) {
                list.add(value);
            }
        }
        String end = in.nextLine();
        if (!"0 0".equals(end)){
            throw new IllegalArgumentException("The last line must contain two zeros");
        }

        if (list.isEmpty()) {
            System.out.println("Case 1: No GN");
            return;
        }

        int max = list.last() * MAX_COUNT;
        if (max < upperBound){
            System.out.println("Case 1: " + max * MAX_COUNT);
            return;
        }
        List<Integer> sortedList = new ArrayList<>(list);
        int maxOfTwo = maxOfTwo(sortedList, upperBound);
        int result = Math.max(maxOfTwo, list.last());
        int maxOfTree = maxOfThree(sortedList, upperBound);
        result = Math.max(result, maxOfTree);
        int maxOfFour = maxOfFour(sortedList, upperBound);
        result = Math.max(result, maxOfFour);
        System.out.println("Case 1: " + result);
    }

    private static int maxOfTwo(List<Integer> list, int bound){
        if (list.get(0) >= bound){
            return Integer.MIN_VALUE;
        }

        int max = list.get(list.size()-1);
        if (max * 2 < bound || list.size() == 1){
            return max * 2;
        }
        int index1 = 0;
        int index2 = list.size()-1;
        int result = list.get(0)*2;
        while (index1 <= index2){
            int v1 = list.get(index1);
            int v2 = list.get(index2);
           int sum = v1 + v2;
           if (sum == bound - 1){
               return sum;
           } else if (sum < bound) {
               index1++;
               if (result < sum) {
                   result = sum;
               }
           }else {
               index2--;
           }
        }
        return result;
    }

    private static int maxOfThree(List<Integer> list, int bound){
        if (list.get(0) >= bound){
            return Integer.MIN_VALUE;
        }
        int max = list.get(list.size()-1);
        if (max * 3 < bound || list.size() == 1){
            return max * 3;
        }
        int result = list.get(0)*3;
        for(int i = 0; i < list.size(); i++){
            int value = list.get(i);
            int newBound = bound-value;
            int maxOfTwo = maxOfTwo(list, newBound);
            int sum = maxOfTwo + value;
            if (sum > result){
                result = sum;
            }
        }

        return result;
    }

    private static int maxOfFour(List<Integer> list, int bound){
        if (list.get(0) >= bound){
            return Integer.MIN_VALUE;
        }
        int max = list.get(list.size()-1);
        if (max * 4 < bound || list.size() == 1){
            return max * 4;
        }
        int result = list.get(0)*4;
        for(int i = 0; i < list.size(); i++){
            int value = list.get(i);
            int newBound = bound-value;
            int maxOfTwo = maxOfThree(list, newBound);
            int sum = maxOfTwo + value;
            if (sum > result){
                result = sum;
            }
        }

        return result;
    }
}
