/*
 * Matthew Buchanan
 * CSC 482 Spring 2022
 * Homework 3 Convex Hull with QuickHull()
 */

package quickconvexhull;
import java.util.ArrayList;


/* This class finds the convex hull of a set of points using the quick hull algorithm */
public class ConvexHull {
    public ArrayList<Point> points; // array to store the raw list of points from input
    public ArrayList<Point> hull; // array to store the final convex hull
    
    
    public ConvexHull(int numberOfPoints, ArrayList<Point> p) {
        this.hull = new ArrayList<Point>(); // hull starts empty
        this.points = new ArrayList<Point>(p); // points is a raw copy of the input
    }
    
    /* Function to perform the first part of the quickhull algorithm */
    public void quickHull() {
        if(this.points.size() < 3) {
            return;
        }
        
        /* Find leftmost and rightmost points and add to hull to start */
        Point leftMost = points.get(0);
        Point rightMost = points.get(0);
        
        for(int i = 0; i < points.size(); i++) {
            if(leftMost.x > points.get(i).x) {
                leftMost = points.get(i);
            }
            if(rightMost.x < points.get(i).x) {
                rightMost = points.get(i);
            }
        }
        hull.add(leftMost);
        hull.add(rightMost);
        
        /* Generate initial right and left point sets for recursion */
        ArrayList<Point> set1 = new ArrayList<Point>();
        ArrayList<Point> set2 = new ArrayList<Point>();
        for(int i = 0; i < points.size(); i++) {
            if(findDistanceFromLine(leftMost, rightMost, points.get(i)) > 0) {
                set1.add(points.get(i));
            }
            else if(findDistanceFromLine(leftMost, rightMost, points.get(i)) < 0) {
                set2.add(points.get(i));
            }
        }
        
        /* Recurse on each half */
        divideAndConquer(set1, leftMost, rightMost);
        divideAndConquer(set2, rightMost, leftMost);
    }
    
    
    /* Recursive portion of quick hull */
    public void divideAndConquer(ArrayList<Point> workingSet, Point p, Point q) {
        // check for empty set    
        if(workingSet.isEmpty()) {
            return;
        }
        
        /* Find point furthest from line and add to hull */
        Point furthestPoint = workingSet.get(0);
        for(int i = 0; i < workingSet.size(); i++) {
            if(Math.abs(findDistanceFromLine(p, q, furthestPoint)) < Math.abs(findDistanceFromLine(p, q, workingSet.get(i)))) {
                furthestPoint = workingSet.get(i);            }
        }
        hull.add(furthestPoint);
        
        /* Find points outside the triangle and add them to the correct set */
        ArrayList<Point> newSet1 = new ArrayList<Point>();
        ArrayList<Point> newSet2 = new ArrayList<Point>();
        for(int i = 0; i < workingSet.size(); i++) {
            if(!isPointInTriangle(p, q, furthestPoint, workingSet.get(i))) {
                if(findDistanceFromLine(p, furthestPoint, workingSet.get(i)) > 0) {
                    newSet1.add(workingSet.get(i));
                }
                else if(findDistanceFromLine(p, furthestPoint, workingSet.get(i)) < 0) {
                    newSet2.add(workingSet.get(i));
                }
            }
        }
    
        //Recurse
        divideAndConquer(newSet1, p, furthestPoint);
        divideAndConquer(newSet2, furthestPoint, q);
    }
    
    
    /* Find distance from line a<-->b to point c */
    public int findDistanceFromLine(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }
    
    
    /* Test whether point p is contained within triangle abc */
    public boolean isPointInTriangle(Point a, Point b, Point c, Point p) {
        int tmp1 = (((p.x - a.x) * (b.y - a.y)) - ((p.y - a.y) * (b.x - a.x)));
        int tmp2 = (((p.x - b.x) * (c.y - b.y)) - ((p.y - b.y) * (c.x - b.x)));
        int tmp3 = (((p.x - c.x) * (a.y - c.y)) - ((p.y - c.y) * (a.x - c.x)));
        
        if((tmp1 < 0 && tmp2 < 0 && tmp3 < 0) || (tmp1 > 0 && tmp2 > 0 && tmp3 > 0)) { // if all the same sign
            return true;
        }
        
        return false;
    }
    
    
    /* Display current state of hull */
    public void printHull() {
        System.out.println("Hull Size: " + hull.size());
        for(int i = 0; i < hull.size(); i++) {
            System.out.println(hull.get(i).x + " " + hull.get(i).y );
        }
    }
}
