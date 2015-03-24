import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


public class Flock {
    private ArrayList<Bird> birdArrayList = new ArrayList<Bird>();
    private ArrayList<Integer> localBirdID = new ArrayList<Integer>();
    private ArrayList<Integer> localBirdDistances = new ArrayList<Integer>();
    public ArrayList<ArrayList> localBirdList = new ArrayList<ArrayList>();
    public ArrayList<Double> localBirdHeadings = new ArrayList<Double>();

    private int radius = 200;
    private int numBirds;


    public Flock(Canvas canvas, int numBirds) {
        int i;
        for (i = 0; i <= numBirds; i++) {
            Bird Birdy = new Bird(canvas);
            birdArrayList.add(Birdy);
            localBirdID.add(0);
            localBirdDistances.add(0);
            localBirdHeadings.add(0.0);
            localBirdList.add(localBirdID);
        }
        this.numBirds = numBirds;
    }

    public void Fly(Canvas canvas) {
        //currentBird is the currently selected bird instance from the arraylist
        while (1 == 1) {
            updateCanvas(canvas, false);
            for (Bird currentBird : birdArrayList) {
                currentBird.simulate(this);
            }
            updateCanvas(canvas, true);
            Utils.pause(20);
        }
    }

    public ArrayList localBirds() {
        int i, j, sectorStartAngle, sectorEndAngle, angleToNeighbour, currentX, currentY, potentialNeighbourX, potentialNeighbourY, currentHeading;

        //Retrieve coords of every bird, but for each bird, retrieve list of birds nearby
        for (j = 0; j <= birdArrayList.size() - 1; j++) {
            currentX = (int) birdArrayList.get(j).currentPosition.getX();
            currentY = (int) birdArrayList.get(j).currentPosition.getY();
            currentHeading = birdArrayList.get(j).getHeading();

            sectorStartAngle = currentHeading - 130;
            sectorEndAngle = currentHeading + 130;

            //check nearby birds to see if they're within radius
            for (i = 0; i <= birdArrayList.size() - 1; i++) {
                potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
                potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();
                angleToNeighbour = (int) Math.atan2(potentialNeighbourY - currentY, potentialNeighbourX - currentX);
                if ((Math.pow((potentialNeighbourX - currentX), 2) + Math.pow((potentialNeighbourY - currentY), 2)) <= Math.pow(radius, 2))
                {//&&(angleToNeighbour >= sectorStartAngle)&&(angleToNeighbour<=sectorEndAngle))
                    localBirdID.set(i, i);
                } else {

                    localBirdID.set(i, -1);
                }
            }
            localBirdList.set(j, localBirdID);
        }
        return localBirdList;
    }

    public double getAverageBirdHeading(int birdNumber) {
        int i, runningTotal = 0;
        int neighbourBirdID, numLocalBirds;
        double neighbourHeading, averageHeading, outAngle;

        numLocalBirds = getNumLocalBirds(birdNumber);

        //Retrieve angle from birdArrayList of id that corresponds to localBirdList
        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());//Holy hack
            if (neighbourBirdID != -1) runningTotal += birdArrayList.get(neighbourBirdID).getHeading();
        }
        if (numLocalBirds > 0 ){outAngle = runningTotal/numLocalBirds;}
        else outAngle = 0.0;

        //Limit range: wraparound
        if (outAngle <= 0) outAngle += 360;
        if (outAngle >= 360) outAngle -= 360;

        return outAngle;
    }

    public double getAverageDirectionToLocalFlock(int birdNumber) {
        int i, j, currentX, currentY, potentialNeighbourX, potentialNeighbourY, neighbourBirdID, numLocalBirds;
        double outAngle, angleToNeighbour, runningTotal = 0;

        numLocalBirds = getNumLocalBirds(birdNumber);

        //Retrieve coords of every bird, but for each bird, retrieve list of birds nearby
        currentX = (int) birdArrayList.get(birdNumber).currentPosition.getX();
        currentY = (int) birdArrayList.get(birdNumber).currentPosition.getY();

            //check nearby birds to see if they're within radius
            for (i = 0; i <= numBirds; i++) {
                neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());//Holy hack

                potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
                potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();

                angleToNeighbour = 180/Math.PI * Math.atan2(potentialNeighbourY - currentY, potentialNeighbourX - currentX);
                if (neighbourBirdID != -1) runningTotal += angleToNeighbour;
            }
        if (numLocalBirds > 0 )outAngle = runningTotal/numLocalBirds;
        else outAngle = 0.0;

        if (outAngle <= 0) outAngle += 360;
        if (outAngle >= 360) outAngle -= 360;

        return outAngle;
    }

    public int getDistanceToNearestBird(int birdNumber){
        int i, currentX, currentY, potentialNeighbourX, potentialNeighbourY, distance = 0, neighbourBirdID;

        currentX = (int) birdArrayList.get(birdNumber).currentPosition.getX();
        currentY = (int) birdArrayList.get(birdNumber).currentPosition.getY();

        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());//Holy hack

            potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
            potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();

            if (neighbourBirdID != -1) distance = (int) Math.sqrt((Math.pow(potentialNeighbourY - currentX, 2) + Math.pow(potentialNeighbourY - currentX, 2)));
            localBirdDistances.set(i, distance);
        }

        return localBirdDistances.get(minIndex(localBirdDistances));
    }

    public int getNumLocalBirds(int birdNumber){
        int i, numLocalBirds, neighbourBirdID;

        numLocalBirds = numBirds;


        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());
            if (neighbourBirdID == -1) numLocalBirds--;
        }

        if (numLocalBirds < 0) numLocalBirds = 0;
        return numLocalBirds;
    }

    private void updateCanvas(Canvas canvas, boolean ready) {
        if (!ready) {
            canvas.clear();
        } else {
            for (Bird currentBird : birdArrayList) {
                currentBird.drawBird();
            }
        }
    }

    public static int minIndex (ArrayList<Integer> list) {
        return list.indexOf (Collections.min(list)); }
}
