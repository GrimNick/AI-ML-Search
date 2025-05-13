import java.awt.*;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class graphPlotter extends JPanel {
    List<myNode> nodes;
    double minX,maxX,minY,maxY;
    int panelWidth = 1500;
    int panelHeight = 600;
    int margin =100;

    public graphPlotter(List<myNode> nodes) {
        this.nodes = nodes;
        computeBounds();
        setPreferredSize(new Dimension(panelWidth,panelHeight));
    }

    private void computeBounds(){
        minX = Double.MAX_VALUE; maxX = Double.MIN_VALUE;
        minY = Double.MAX_VALUE; maxY = Double.MIN_VALUE;
        for(myNode node:nodes){
            if(node.x <minX) minX = node.x;
            if(node.y <minY) minY = node.y;
            if(node.x >maxX) maxX = node.x;
            if(node.y >maxY) maxY = node.y;

        }
        double minRange = 1000;
        if(maxX-minX < minRange){
            double center = (maxX+ minX)/2.00;
            minX = center -minRange/2.0;
            maxX = center + minRange/2.0;
        }
        if (maxY - minY < minRange) {
            double center = (maxY + minY) / 2.0;
            minY = center - minRange / 2.0;
            maxY = center + minRange / 2.0;
        }

    }

    private int scaleX(double x){
        return (int) (((x - minX) / (maxX - minX)) * (panelWidth - 2 * margin)) + margin;
    }

    private int scaleY(double y) {
        return (int) ((1 - (y - minY) / (maxY - minY)) * (panelHeight - 2 * margin)) + margin;
    }
    static boolean edgeExists(myNode pointA,myNode pointB){
        for(edge e: pointA.child){
            if(e.target == pointB){
                return true;
            }
        }
        for (edge e : pointB.child) {
            if (e.target == pointA) {
                return true;
            }
        }

        return false;
    }


    public void drawNodes(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        Random rand = new Random();
        for (myNode node : nodes) {
            int x = scaleX(node.x);
            int y = scaleY(node.y);

            // Draw node
            g.setColor(Color.RED);
            g.fillOval(x - 5, y - 5, 10, 10);
            g.setColor(Color.BLACK);
            Font boldFont = new Font("Arial", Font.BOLD, 20);
            g.setFont(boldFont);

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f); // 60% opaque
            g.setComposite(ac);

            g.drawString(node.name, x + 5, y - 5);

            // Draw edges
            for (edge ed : node.child) {
                int tx = scaleX(ed.target.x);
                int ty = scaleY(ed.target.y);
                drawArrow(g, x, y, tx, ty);

                g.drawString(String.valueOf(ed.cost), ((x+tx)/2) + 5 , ((y+ty)/2) - 5 );

            }
        }
    }

    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.BLUE);
        g.drawLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 10;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // 60% opaque
        g.setComposite(ac);

        int xArrow1 = (int) (x2 - arrowLength * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (y2 - arrowLength * Math.sin(angle - Math.PI / 6));
        g.drawLine(x2, y2, xArrow1, yArrow1);

        int xArrow2 = (int) (x2 - arrowLength * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (y2 - arrowLength * Math.sin(angle + Math.PI / 6));
        g.drawLine(x2, y2, xArrow2, yArrow2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawNodes((Graphics2D) g);
    }
}
