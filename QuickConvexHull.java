/*
 * Matthew Buchanan
 * CSC 482 Spring 2022
 * Homework 3 Convex Hull with QuickHull()
 */

package quickconvexhull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuickConvexHull {

    public static void main(String[] args) throws FileNotFoundException {
        File sampleInput = new File("SampleInput.txt");
        Scanner sampleInputReader = new Scanner(sampleInput);
        
        int numberOfPoints = 0;
        ArrayList<Point> tempArr = new ArrayList<Point>();
        
        String[] xy;
        while(sampleInputReader.hasNextLine()) {
            xy = sampleInputReader.nextLine().split(" ");
            Point newPoint = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            tempArr.add(newPoint);
            numberOfPoints++;
        }
        
        ConvexHull testHull = new ConvexHull(numberOfPoints, tempArr);
        testHull.quickHull();
        testHull.printHull();
    }
    
}
