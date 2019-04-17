package kdtree;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree2D {
    private class Node implements Comparable<Node> {
        private Point2D point;
        private Node left;
        private Node right;
        private boolean flag;
        private RectHV rect;
        public Node(Point2D point, boolean flag,RectHV rect){
            this.point = point;
            this.flag = flag;
            this.rect = rect;
        }
        public int compareTo(Node that){
            if(flag){
                return Double.compare(this.point.x(), that.point.x());
            }else{
                return Double.compare(this.point.y(), that.point.y());
            }

        }

    }
    private Node root;
    private int size = 0;

    public KdTree2D(){
        // construct an empty set of points
        root = null;
        size = 0;
    }
    public boolean isEmpty(){
        // is the set empty?
        return size == 0;
    }
    public int size(){
        // number of points in the set
        return size;
    }
    public void insert(Point2D p){
        // add the point to the set (if it is not already in the set)
        if(this.size==0) {
            this.root = new Node(p,true,new RectHV(0,0,1,1));
            this.size ++;
        }else if(put (root, p)) this.size ++;
    }
    private boolean put(Node root, Point2D p){
        Node tmp = new Node(p,!root.flag,null);
        if(root.point.x() == p.x() && root.point.y() == p.y()) return false;
        int cmp = root.compareTo(tmp);
        RectHV newrect = null;
        if(cmp > 0){
            if(root.left == null){
                if(root.flag){
                    newrect = new RectHV(root.rect.xmin(), root.rect.ymin(), root.point.x(), root.rect.ymax());
                }else{
                    newrect = new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), root.point.y());
                }
                tmp.rect = newrect;
                root.left = tmp;
                return true;
            }
            return put(root.left, p);
        }else{
            if(root.right == null){
                if(root.flag){
                    newrect = new RectHV(root.point.x(), root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
                }else{
                    newrect = new RectHV(root.rect.xmin(),  root.point.y(), root.rect.xmax(), root.rect.ymax());
                }
                tmp.rect = newrect;
                root.right = tmp;
                return true;
            }
            return put(root.right, p);
        }
    }
    public boolean contains(Point2D p){
        // does the set contain point p?
        if(isEmpty()) return false;
        Node tmp = new Node(p,!root.flag,null);
        Node x = root;
        while(x !=null){
            int cmp = x.compareTo(tmp);
            if(cmp > 0) x = x.left;
            else if((x.point.x() == p.x() && x.point.y() == p.y())) return true;
            else x = x.right;
        }
        return false;
    }
    public void draw(){
        // draw all points to standard draw
        if (isEmpty()) return;
        drawRec(root, 0, 1, 0, 1);

    }
    public Iterable<Point2D> range(RectHV rect){
        // all points that are inside the rectangle
        Set<Point2D> pointList = new TreeSet<Point2D>();
        rangeRec(root, rect, pointList);
        return pointList;

    }
    public Iterable<Point2D> range(Point2D point1, Point2D point2){
        double xmin=Math.min(point1.x(),point2.x());
        double ymin=Math.min(point1.y(),point2.y());
        double xmax=Math.max(point1.x(),point2.x());
        double ymax=Math.max(point1.y(),point2.y());
        return range(new RectHV(xmin,ymin,xmax,ymax));
    }
    private void rangeRec(Node root, RectHV rect, Set<Point2D> set) {
        if (root == null) return;
        if (root.rect.intersects(rect)) {
            if (rect.contains(root.point)) set.add(root.point);
            rangeRec(root.left, rect, set);
            rangeRec(root.right, rect, set);
        }
    }
    public Point2D nearest(Point2D p){
        // a nearest neighbor in the set to point p; null if the set is empty
        if(isEmpty()) return null;
        return nearest(root, root.point, p);
    }
    private Point2D nearest(Node root, Point2D champion, Point2D aim){
        //Point2D closest = champion;
        if(root == null || root.rect.distanceSquaredTo(aim) > champion.distanceSquaredTo(aim)) return champion;
        double dist = root.point.distanceSquaredTo(aim);
        if (dist < champion.distanceSquaredTo(aim)){
            champion = root.point;
        }

        Node near, far;
        if((root.flag && (aim.x() < root.point.x())) || (!root.flag && (aim.y() < root.point.y()))){
            near = root.left;
            far = root.right;
        }
        else {
            near = root.right;
            far = root.left;
        }
        champion = nearest(near, champion,aim);

        champion = nearest(far, champion,aim);

        return champion;
    }

	   /* public Point2D nearest(Point2D p) {
	        if (root == null) return null;
	        Point2D retp = null;
	        double mindis = Double.MAX_VALUE;
	        Queue<Node> queue = new Queue<Node>();
	        queue.enqueue(root);
	        while (!queue.isEmpty()) {
	            Node x = queue.dequeue();
	            double dis = p.distanceSquaredTo(x.point);
	            if (dis < mindis) {
	                retp = x.point;
	                mindis = dis;
	            }
	            if (x.left != null && x.left.rect.distanceSquaredTo(p) < mindis)
	                queue.enqueue(x.left);
	            if (x.right != null && x.right.rect.distanceSquaredTo(p) < mindis)
	                queue.enqueue(x.right);
	        }
	        return retp;
	    }*/



    private void drawRec(Node root, double minX, double maxX, double minY, double maxY) {
        if (root == null) return;

        StdDraw.setPenRadius(.002);
        if (root.flag) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.point.x(), minY, root.point.x(), maxY);
            drawRec(root.left, minX, root.point.x(), minY, maxY);
            drawRec(root.right, root.point.x(), maxX, minY, maxY);
        } else {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.line(minX, root.point.y(), maxX, root.point.y());
            drawRec(root.left, minX, maxX, minY, root.point.y());
            drawRec(root.right, minX, maxX, root.point.y(), maxY);
        }
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.setPenRadius(.006);
        root.point.draw();
    }
    public static void main(String[] args){
        // unit testing of the methods (optional)
        KdTree2D tree = new KdTree2D();
        Random random = new Random();
        for(int i=0;i<10000000;i++){
            tree.insert(new Point2D(random.nextDouble()*1,random.nextDouble()*1));
        }
//        tree.draw();

        Point2D point1=new Point2D(random.nextDouble(),random.nextDouble());
        Point2D point2=new Point2D(random.nextDouble(),random.nextDouble());
        double xmin=Math.min(point1.x(),point2.x());
        double ymin=Math.min(point1.y(),point2.y());
        double xmax=Math.max(point1.x(),point2.x());
        double ymax=Math.max(point1.y(),point2.y());

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.003);
        RectHV rectangle=new RectHV(xmin,ymin,xmax,ymax);
//        rectangle.draw();

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.008);

        long begin =System.currentTimeMillis();
        Iterable<Point2D> iterable =tree.range(rectangle);
        int count =0;
        for (Point2D p:iterable){
            count++;
//            p.draw();
        }
        System.out.println(System.currentTimeMillis()-begin);
        System.out.println(count);
        begin = System.currentTimeMillis();
        Point2D targetP=new Point2D(random.nextDouble(),random.nextDouble());
        Point2D nearestP=tree.nearest(targetP);
        long end = System.currentTimeMillis();
        System.out.println(end-begin);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        targetP.draw();
        nearestP.draw();
    }

}


