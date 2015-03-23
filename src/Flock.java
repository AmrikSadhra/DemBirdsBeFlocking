import java.util.ArrayList;

public class Flock {
    private ArrayList<Bird> birdArrayList = new ArrayList<Bird>();
    private ArrayList<Integer> localBirdID = new ArrayList<Integer>();
    public ArrayList<ArrayList> localBirdList = new ArrayList<ArrayList>();
    public ArrayList<Double> localBirdHeadings = new ArrayList<Double>();

    private int i;
    private int radius = 300;
    private int numBirds;


    public Flock(Canvas canvas, int numBirds) {
        for (i = 0; i <= numBirds; i++) {
            Bird Birdy = new Bird(canvas);
            birdArrayList.add(Birdy);
            localBirdID.add(0);
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
                currentBird.setSpeed(1);
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

            sectorStartAngle = currentHeading - 180;
            sectorEndAngle = currentHeading + 180;

            //check nearby birds to see if they're within radius TODO add PACMAN
            for (i = 0; i <= birdArrayList.size() - 1; i++) {
                potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
                potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();
                angleToNeighbour = (int) Math.atan2(potentialNeighbourY - currentY, potentialNeighbourX - currentX);
                if ((Math.pow((potentialNeighbourX - currentX), 2) + Math.pow((potentialNeighbourY - currentY), 2)) <= Math.pow(radius, 2))
                {
                    //)&&(angleToNeighbour >= sectorStartAngle)&&(angleToNeighbour<=sectorEndAngle)
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
        if (numLocalBirds != 0 ){outAngle = runningTotal/numLocalBirds;}
        else outAngle = 0.0;

        //Limit range: wraparound
        if (outAngle <= 0) outAngle += 360;
        if (outAngle >= 360) outAngle -= 360;

        System.out.println("Avg Bird heading of " + birdNumber + " is " + outAngle);

        return outAngle;
    }

    public int getNumLocalBirds(int birdNumber){
        int i, numLocalBirds, neighbourBirdID;

        numLocalBirds = numBirds;

        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());
            if (neighbourBirdID == -1) numLocalBirds--;
        }
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
}
