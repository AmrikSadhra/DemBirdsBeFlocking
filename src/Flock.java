import java.util.ArrayList;

public class Flock {
    private ArrayList<Bird> birdArrayList = new ArrayList<Bird>();
    private ArrayList<Integer> localBirdID = new ArrayList<Integer>();
    public ArrayList<ArrayList> localBirdList = new ArrayList<ArrayList>();
    public ArrayList<Double> localBirdHeadings = new ArrayList<Double>();

    private int i;
    private int radius = 20;


    public Flock(Canvas canvas, int numBirds) {
        for (i = 0; i <= numBirds; i++) {
            Bird Birdy = new Bird(canvas);
            birdArrayList.add(Birdy);
            localBirdID.add(0);
            localBirdHeadings.add(0.0);
            localBirdList.add(localBirdID);
        }
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
        int i, j, currentX, currentY, potentialNeighbourX, potentialNeighbourY;

        //Retrieve coords of every bird, but for each bird, retrieve list of birds nearby
        for (j = 0; j <= birdArrayList.size() - 1; j++) {
            currentX = (int) birdArrayList.get(j).currentPosition.getX();
            currentY = (int) birdArrayList.get(j).currentPosition.getY();
            //check nearby birds to see if they're within radius ADD PACMAN SHAPE BEHAVIOUR
            for (i = 0; i <= birdArrayList.size() - 1; i++) {
                potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
                potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();
                if ((Math.pow((potentialNeighbourX - currentX), 2) + Math.pow((potentialNeighbourY - currentY), 2)) <= Math.pow(radius, 2)) {
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
        int neighbourBirdID, numBirds, numLocalBirds;
        double neighbourHeading, averageHeading;

        numBirds = localBirdList.size() -1;
        numLocalBirds = numBirds;

        //Retrieve angle from birdArrayList of id that corresponds to localBirdList
        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());//Holy hack
            if (neighbourBirdID != -1) runningTotal += birdArrayList.get(neighbourBirdID).getHeading();
            else numLocalBirds--;
        }
        if (numLocalBirds != 0 ){return runningTotal/numLocalBirds;}
        else return 0.0;
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
