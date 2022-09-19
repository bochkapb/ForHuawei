package p;

import java.util.*;

public class Task3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberCases = Integer.parseInt(in.nextLine());
        if(numberCases < 1 || numberCases > 10){
            throw new IllegalArgumentException("Number Test Cases must be between 1 and 10");
        }
        List<Integer> result = new ArrayList<>();
        for(int j = 0; j < numberCases; j++) {
            String secondLine = in.nextLine();
            String[] split = secondLine.split(" ");
            if (split.length != 3) {
                throw new IllegalArgumentException("You must input three divided by space");
            }
            int width = Integer.parseInt(split[0]);
            int height = Integer.parseInt(split[1]);
            int numberOfPieces = Integer.parseInt(split[2]);
            //TODO validation

            SortedSet<Rectangle> pieces = new TreeSet<>();
            for (int i = 0; i < numberOfPieces; i++) {
                String line = in.nextLine();
                split = line.split(" ");
                //TODO validation x1 < x2, y1 < y2,
                //TODO if x1 = x2 or y1 = y2 skip piece
                //TODO if x2 > width or y2 > height skip piece
                Rectangle piece = new Rectangle(
                        Integer.parseInt(split[0]),
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2]),
                        Integer.parseInt(split[3])
                );
                pieces.add(piece);
            }
            if (j != numberCases -1) {
                String line = in.nextLine();    //empty line
            }
            Rectangle map = new Rectangle(0, 0, width, height);
            result.add(count(map, pieces));
        }

        result.forEach(System.out::println);
    }

    private static int count(Rectangle map, SortedSet<Rectangle> pieces){
        if (pieces.isEmpty()){
            return -1;
        }

        Point lowerLeft = map.p1;
        Rectangle first = pieces.first();
        if (map.equals(first)){
            return 1;
        }

        if (!lowerLeft.equals(first.p1)){
            return -1;
        }

        SortedSet<Rectangle> candidates = pieces.headSet(first.getLarger());
        int result = Integer.MAX_VALUE;
        Point upperRight = map.p2;
        for (Rectangle candidate: candidates){
            /*
            ----------
            ----------
            ##########
            ##########
             */
            if (upperRight.x == candidate.p2.x){
                int countTop = countTop(lowerLeft, candidate, upperRight, pieces);
                if (countTop != -1){
                    result = Math.min(result, 1 + countTop);
                }
            /*
            #####-----
            #####-----
            #####-----
            #####-----
             */
            } else if (upperRight.y == candidate.p2.y) {
                int countLeft = countLeft(lowerLeft, candidate, upperRight, pieces);
                if (countLeft != -1){
                    result = Math.min(result, 1 + countLeft);
                }
            }else {
            /*
            &&&&&-----
            &&&&&-----
            #####-----
            #####-----
             */
                int count1 = Integer.MAX_VALUE;
                int countTopLeft = countTopLeft(lowerLeft, candidate, upperRight, pieces);
                int countLeft = countLeft(lowerLeft, candidate, upperRight, pieces);
                if (countTopLeft != -1 && countLeft != -1){
                    count1 = 1 + countTopLeft + countLeft;
                }
            /*
            ----------
            ----------
            #####&&&&&
            #####&&&&&
             */
                int count2 = Integer.MAX_VALUE;
                int countBottomRight = countBottomRight(lowerLeft, candidate, upperRight, pieces);
                int countTop = countTop(lowerLeft, candidate, upperRight, pieces);
                if (countBottomRight != -1 && countTop != -1){
                    count2 = 1 + countBottomRight + countTop;
                }

                if (count1 != Integer.MAX_VALUE || count2 != Integer.MAX_VALUE){
                    result = Math.min(result, Math.min(count1, count2));
                }
            }
        }
        return result != Integer.MAX_VALUE ? result : -1;
    }

    /*
    #####-----
    #####-----
    #####-----
    #####-----
     */
    private static int countLeft(Point lowerLeft, Rectangle rec, Point upperRight, SortedSet<Rectangle> pieces){
        Point newLowerLeft = new Point(rec.p2.x, lowerLeft.y);
        Rectangle newMap = new Rectangle(newLowerLeft, upperRight);
        return count(newMap, pieces.tailSet(newMap));
    }

    /*
    ----------
    ----------
    ##########
    ##########
     */
    private static int countTop(Point lowerLeft, Rectangle rec, Point upperRight, SortedSet<Rectangle> pieces){
        Point newLowerLeft = new Point(lowerLeft.x, rec.p2.y);
        Rectangle newMap = new Rectangle(newLowerLeft, upperRight);
        return count(newMap, pieces.tailSet(newMap));
    }

    /*
            &&&&&-----
            &&&&&-----
            #####-----
            #####-----
     */
    private static int countTopLeft(Point lowerLeft, Rectangle rec, Point upperRight, SortedSet<Rectangle> pieces){
        Point newLowerLeft = new Point(lowerLeft.x, rec.p2.y);
        Point newUpperRight = new Point(rec.p2.x, upperRight.y);
        Rectangle newMap = new Rectangle(newLowerLeft, newUpperRight);
        Rectangle newPoint2 = new Rectangle(newUpperRight, newUpperRight);
        SortedSet<Rectangle> newCandidates = pieces.subSet(newMap, newPoint2);
        return count(newMap, newCandidates);
    }

    /*
            ----------
            ----------
            #####&&&&&
            #####&&&&&
 */
    private static int countBottomRight(Point lowerLeft, Rectangle rec, Point upperRight, SortedSet<Rectangle> pieces){
        Point newLowerLeft = new Point(rec.p2.x, lowerLeft.y);
        Point newUpperRight = new Point(upperRight.x, rec.p2.y);
        Rectangle newMap = new Rectangle(newLowerLeft, newUpperRight);
        Rectangle newPoint2 = new Rectangle(newUpperRight, newUpperRight);
        SortedSet<Rectangle> newCandidates = pieces.subSet(newMap, newPoint2);
        return count(newMap, newCandidates);
    }

    private static class Rectangle implements Comparable<Rectangle>{
        Point p1;
        Point p2;

        Rectangle(int x1, int y1, int x2, int y2){
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
        }

        Rectangle (Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public int compareTo(Rectangle o) {
            if (p1.compareTo(o.p1) != 0){
                return p1.compareTo(o.p1);
            }else {
                return -p2.compareTo(o.p2);
            }
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Rectangle)){
                return false;
            }

            return p1.equals(((Rectangle) o).p1) && p2.equals(((Rectangle) o).p2);
        }

        @Override
        public int hashCode(){
            return p1.hashCode() * p2.hashCode();
        }

        public Rectangle getLarger(){
            return new Rectangle(p1.x, p1.y+1, p2.x, p2.y);
        }

        @Override
        public String toString(){
            return p1 + " " + p2;
        }
    }

    private static class Point implements Comparable<Point>{
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Point)){
                return false;
            }

            return x == ((Point) o).x && y == ((Point) o).y;
        }

        @Override
        public int hashCode(){
            return x * y;
        }

        @Override
        public int compareTo(Point o) {
            if (x > o.x){
                return 1;
            } else if (x < o.x) {
                return -1;
            } else if (y > o.y) {
                return 1;
            } else if (y < o.y) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString(){
            return x + " " + y;
        }
    }
}
