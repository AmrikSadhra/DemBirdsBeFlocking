import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Flock {
    private ArrayList<Bird> birdArrayList = new ArrayList<Bird>();
    private ArrayList<ArrayList> localBirdList = new ArrayList<ArrayList>();
    private ArrayList<Integer> localBirdID = new ArrayList<Integer>();

    private ArrayList<Bird> addedBirdArrayList = new ArrayList<Bird>();
    private ArrayList<Bird> addedPredatorArrayList = new ArrayList<Bird>();

    /* Create GUI Panel for settings */
    private SliderPanel sliders = new SliderPanel();

    /* Objects used for cursor */
    private int cursorAngle;
    private CartesianDouble currentMousePosition = new CartesianDouble(0, 0);

    /* Coordinates used for drawing wind line */
    private Point pointStart = null;
    private Point pointEnd = null;

    /* Decide to draw cursor/ wind line */
    boolean cursorOnScreen = false;
    boolean linePresent = false;

    /* Used for adding birds/predators */
    private Point clickPos;
    private int cursor = 0;

    /* Helpers to draw lines */
    CanvasHelper mouseHelper;
    CanvasHelper windHelper;

    /* Fixes concurrency issues with array list */
    boolean birdAdded = false;
    boolean birdRemoved = false;
    boolean predatorAdded = false;
    boolean predatorRemoved = false;

    /* Store vector states and weights */
    Boolean v1Enabled, v2Enabled, v3Enabled, v4Enabled, v5Enabled;
    Double wt1, wt2, wt3, wt4, wt5;

    /* private reference to canvas */
    private Canvas currentCanvas;
    private int numBirds;
    private int numAddedBirds;
    private int numPredators;
    private int numAddedPredators;

    public Flock(Canvas canvas, int numBirds) {
        mouseHelper = new CanvasHelper(canvas);
        windHelper = new CanvasHelper(canvas);

        int i;
        for (i = 0; i <= numBirds; i++) {
            Bird Birdy = new Bird(canvas);
            birdArrayList.add(Birdy);
            localBirdID.add(0);
            localBirdList.add(localBirdID);
        }
        this.currentCanvas = canvas;
        this.numBirds = numBirds;

        /* MouseWheelListener Added */
        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation(); //Read number of notches scrolled
                if (Math.signum(notches) == 1) cursor++; //If notches scrolled = +ve, increment cursor
                else cursor--; //Else decrement

                /* Wrap around so that cursor is always 0, 1, 2*/
                if (cursor > 2) cursor = 0;
                if (cursor < 0) cursor = 2;
                if (cursor != 2) linePresent = false;
            }
        });

        /* MouseMotionListener Added */
        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (cursor == 2) {//If cursor dragged, and cursor in wind mode, get current position
                    pointEnd = e.getPoint();
                    linePresent = true;
                }
                currentMousePosition = new CartesianDouble(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentMousePosition = new CartesianDouble(e.getX(), e.getY());//If mouse moved, get current position
                pointEnd = e.getPoint();
            }
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Switch statement to handle what happens when cursor in different states */
                switch (cursor) {
                    case 0:
                        if (e.getButton() == MouseEvent.BUTTON3) {//If right click in bird cursor, set birdRemoved
                            birdRemoved = true;
                        } else {
                            clickPos = e.getPoint();
                            birdAdded = true;//If normal click, set birdAdded
                        }
                        break;

                    case 1:
                        if (e.getButton() == MouseEvent.BUTTON3) {//If right click in predator cursor, set predatorRemoved
                            predatorRemoved = true;
                        } else {
                            clickPos = e.getPoint();
                            predatorAdded = true; //If normal click, set predatorAdded
                        }
                        break;

                    case 2:
                        //Code for cursor 2 wind inside mousePressed event, Works better w/ drag.
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (cursor == 2) pointStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (cursor == 2) {
                    linePresent = false;
                    pointStart = null;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cursorOnScreen = true; //Boolean used to decide whether to draw cursor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cursorOnScreen = false;
            }
        });
    }


    /* Adds bird if birdAdded == true in main loop.
        Creates new bird object, adds it to array list
        Adds bird object to addedBirdArrayList (only for added birds)
        Sets current position to cursor coordinates
        Adds entry to localBirdID so that it can be detected by other birds
        Add the created localBirdID to the localBirdList
        Increment total number of birds and number of added birds
     */
    private void addBird() {
        Bird Birdy = new Bird(currentCanvas);
        birdArrayList.add(Birdy);
        addedBirdArrayList.add(Birdy);
        Birdy.currentPosition = new CartesianDouble(clickPos.getX(), clickPos.getY());
        localBirdID.add(0);
        localBirdList.add(localBirdID);
        numBirds++;
        numAddedBirds++;
    }

    /* Removes bird if birdRemoved == true in main loop
        Removes bird from birdArrayList, by specific ID taken from addedBirdArrayList
        Removes bird from localBirdList and ID using specific ID taken from addedBirdArrayList
        Removes bird from AddedBirdArray list
        Decrements total number of birds and number of added birds
     */
    public void removeBird() {
        if (numAddedBirds > 0) {
            birdArrayList.remove(addedBirdArrayList.get(addedBirdArrayList.size() - 1));
            localBirdList.remove(addedBirdArrayList.get(addedBirdArrayList.size() - 1));
            localBirdID.remove(addedBirdArrayList.get(addedBirdArrayList.size() - 1));
            addedBirdArrayList.remove(addedBirdArrayList.size() - 1);
            numBirds--;
            numAddedBirds--;
        }
    }

    private void addPredator() {
        numBirds++;
        Predator Attack = new Predator(currentCanvas);
        birdArrayList.add(Attack);
        addedPredatorArrayList.add(Attack);
        Attack.currentPosition = new CartesianDouble(clickPos.getX(), clickPos.getY());
        localBirdID.add(0);
        localBirdList.add(localBirdID);
        numPredators++;
        numAddedPredators++;
    }

    public void removePredator() {
        if (numAddedPredators > 0) {
            birdArrayList.remove(addedPredatorArrayList.get(addedPredatorArrayList.size() - 1));
            localBirdList.remove(addedPredatorArrayList.get(addedPredatorArrayList.size() - 1));
            localBirdID.remove(addedPredatorArrayList.get(addedPredatorArrayList.size() - 1));
            addedPredatorArrayList.remove(addedPredatorArrayList.size() - 1);
            numBirds--;
            numAddedPredators--;
        }
    }

    /* Master simulation method described in LaTeX report under "3.1 Simulation Loop" */
    public void Fly() {
        CartesianDouble vector1 = new CartesianDouble(0, 0);
        CartesianDouble vector2 = new CartesianDouble(0, 0);
        CartesianDouble vector3 = new CartesianDouble(0, 0);
        CartesianDouble vector4 = new CartesianDouble(0, 0);
        CartesianDouble vector5 = new CartesianDouble(0, 0);
        CartesianDouble vector6 = new CartesianDouble(0, 0);
        Integer turnAngle;

        while (true) {
            turnAngle = sliders.slider9.getValue();

            objectAdd();
            checkAndSetWeights();

            currentCanvas.clear();
            for (Bird currentBird : birdArrayList) {
                updateLocalBirds();

                if (cursorOnScreen) drawCursor(cursor);
                if (linePresent) drawWind();

                if (!(currentBird instanceof Predator)) {
                    if (v1Enabled) vector1 = centreOfMass(currentBird);
                    else vector1 = new CartesianDouble(0, 0);

                    if (v2Enabled) vector2 = avoidCollision(currentBird);
                    else vector2 = new CartesianDouble(0, 0);

                    if (!v3Enabled) vector3 = matchVelocity(currentBird);
                    else vector3 = new CartesianDouble(0, 0);

                    if (v4Enabled) vector4 = boundPosition(currentBird);
                    else vector4 = new CartesianDouble(0, 0);

                    if (v5Enabled) vector5 = followMouse(currentBird);
                    else vector5 = new CartesianDouble(0, 0);

                    if (linePresent) vector6 = applyWind(currentBird);
                    else vector6 = new CartesianDouble(0, 0);


                    currentBird.velocity = Vectors.add(currentBird.velocity, Vectors.scalarMultiply(vector1, wt1), Vectors.scalarMultiply(vector2, wt2), Vectors.scalarMultiply(vector3, wt3), Vectors.scalarMultiply(vector4, wt4), Vectors.scalarMultiply(vector5, wt5), vector6);
                    limitVelocity(currentBird);
                    currentBird.currentPosition = Vectors.add(currentBird.currentPosition, currentBird.velocity);
                    setBirdAngle(currentBird, turnAngle);
                } else {
                    Predator myPredator = (Predator) currentBird;
                    myPredator.move(3);
                    myPredator.turn(Utils.genRandom(-7, 7));
                    myPredator.wrapAround();
                }
                currentBird.drawBird();

            }
            Utils.pause(20);
        }
    }

    /* Main Vector functions described in LaTeX report.
    centreOfMass, matchVelocity, avoidCollision, boundPosition and limitVelocity
    are adapted from pseudocode supplied by Conrad Parker at http://www.kfish.org/boids/pseudocode.html
    Modified to take into account local birds.
   */
    private CartesianDouble centreOfMass(Bird activeBird) {
        int i;
        int neighbourBirdID, numLocalBirds;

        CartesianDouble runningTotal = new CartesianDouble(0, 0);
        CartesianDouble output = new CartesianDouble(0, 0);

        numLocalBirds = getNumLocalBirds(activeBird.birdNumber);

        //Retrieve angle from birdArrayList of id that corresponds to localBirdList
        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(activeBird.birdNumber).get(i).toString());//Holy hack
            if ((neighbourBirdID != -1) && (neighbourBirdID != activeBird.birdNumber)) {
                runningTotal = Vectors.add(runningTotal, birdArrayList.get(neighbourBirdID).currentPosition);
            }
        }
        if (numLocalBirds > 0) {
            output = Vectors.scalarDivide(Vectors.subtract(Vectors.scalarDivide(runningTotal, numLocalBirds), activeBird.currentPosition), 100);
        } else {
            output = new CartesianDouble(0, 0);
        }

        return output;
    }

    private CartesianDouble avoidCollision(Bird activeBird) {
        int i;
        int neighbourBirdID, numLocalBirds;
        CartesianDouble output = new CartesianDouble(0, 0);

        numLocalBirds = getNumLocalBirds(activeBird.birdNumber);

        //Retrieve angle from birdArrayList of id that corresponds to localBirdList
        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(activeBird.birdNumber).get(i).toString());//Holy hack
            if ((neighbourBirdID != -1) && (neighbourBirdID != activeBird.birdNumber)) {
                if ((Math.pow(birdArrayList.get(neighbourBirdID).currentPosition.getX() - activeBird.currentPosition.getX(), 2) + Math.pow(birdArrayList.get(neighbourBirdID).currentPosition.getY() - activeBird.currentPosition.getY(), 2)) <= Math.pow(sliders.slider7.getValue(), 2)) {//TO SLIDE
                    if (activeBird instanceof Predator)
                        output = Vectors.scalarMultiply(Vectors.subtract(output, Vectors.subtract(birdArrayList.get(neighbourBirdID).currentPosition, activeBird.currentPosition)), 2);
                    output = Vectors.subtract(output, Vectors.subtract(birdArrayList.get(neighbourBirdID).currentPosition, activeBird.currentPosition));
                }
            }
        }

        return output;
    }

    private CartesianDouble matchVelocity(Bird activeBird) {
        int i;
        int neighbourBirdID, numLocalBirds;

        CartesianDouble perceivedVelocity = new CartesianDouble(0, 0);

        numLocalBirds = getNumLocalBirds(activeBird.birdNumber);

        //Retrieve angle from birdArrayList of id that corresponds to localBirdList
        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(activeBird.birdNumber).get(i).toString());//Holy hack
            if ((neighbourBirdID != -1) && (neighbourBirdID != activeBird.birdNumber)) {
                perceivedVelocity = Vectors.add(perceivedVelocity, birdArrayList.get(neighbourBirdID).velocity);
            }
        }

        perceivedVelocity = Vectors.scalarDivide(perceivedVelocity, numLocalBirds);

        if (numLocalBirds > 0) {
            return Vectors.scalarDivide(Vectors.subtract(perceivedVelocity, activeBird.velocity), 4);
        } else {
            return new CartesianDouble(0, 0);
        }
    }

    private CartesianDouble boundPosition(Bird activeBird) {
        Integer bound = 50;
        Integer Xmin = bound, Xmax = currentCanvas.getWidth() - bound, Ymin = bound, Ymax = currentCanvas.getHeight() - bound;

        Double encourage = (double) sliders.slider8.getValue() / 10;
        Double outX = 0.0, outY = 0.0;

        if (activeBird.currentPosition.getX() < Xmin) outX = encourage;
        else if (activeBird.currentPosition.getX() > Xmax) outX = -encourage;

        if (activeBird.currentPosition.getY() < Ymin) outY = encourage;
        else if (activeBird.currentPosition.getY() > Ymax) outY = -encourage;

        return new CartesianDouble(outX, outY);
    }

    private CartesianDouble followMouse(Bird activeBird) {
        return Vectors.scalarDivide(Vectors.subtract(currentMousePosition, activeBird.currentPosition), 100);
    }

    private CartesianDouble applyWind(Bird activeBird) {
        if (linePresent) {
            return new CartesianDouble((pointEnd.getX() - pointStart.getX()) / 10, (pointEnd.getY() - pointStart.getY()) / 10);
        }
        return new CartesianDouble(0, 0);
    }

    /* Bird Steering method, turns bird amount turnAngle towards angle calculated from vector
    * for smooth turning motion
    * */
    private void setBirdAngle(Bird activeBird, Integer turnAngle) {
        int birdAngle;

        birdAngle = (int) Math.toDegrees(Math.atan2(activeBird.velocity.getY(), activeBird.velocity.getX()));

        if (activeBird.currentAngle < birdAngle) {
            activeBird.currentAngle += turnAngle;
        } else if (activeBird.currentAngle > birdAngle) {
            activeBird.currentAngle -= turnAngle;
        } else if (activeBird.currentAngle > birdAngle) {
            activeBird.currentAngle -= turnAngle;
        }
    }

    /* Reads in vector weights and enabled states from slider UI panel */
    private void checkAndSetWeights() {
        /* Obtain Vector weight values from SliderPanel UI */
        wt1 = (double) sliders.slider1.getValue() / 10;
        wt2 = (double) sliders.slider2.getValue() / 10;
        wt3 = (double) sliders.slider3.getValue() / 10;
        wt4 = (double) sliders.slider4.getValue() / 10;
        wt5 = (double) sliders.slider5.getValue() / 10;
        v1Enabled = sliders.v1Enable.isSelected();
        v2Enabled = sliders.v2Enable.isSelected();
        v3Enabled = sliders.v3Enable.isSelected();
        v4Enabled = sliders.v4Enable.isSelected();
        v5Enabled = sliders.v5Enable.isSelected();
    }

    private void limitVelocity(Bird activeBird) {
        int vlim = sliders.slider6.getValue();

        if ((Math.abs(activeBird.velocity.getX()) > vlim) || (Math.abs(activeBird.velocity.getY()) > vlim)) {
            activeBird.velocity.setX(activeBird.velocity.getX() / Math.abs(activeBird.velocity.getX()) * vlim);
            activeBird.velocity.setY(activeBird.velocity.getY() / Math.abs(activeBird.velocity.getY()) * vlim);
        }
    }

    /* Concurrency fix described in LaTeX report under section "3.2 Local Birds - ObjectAdd()"*/
    private void objectAdd() {
        if (birdAdded) addBird();
        birdAdded = false;//Hack to fix concurrency issues with for loop
        if (birdRemoved) removeBird();
        birdRemoved = false;//Hack to fix concurrency issues with for loop
        if (predatorAdded) addPredator();
        predatorAdded = false;//Hack to fix concurrency issues with for loop
        if (predatorRemoved) removePredator();
        predatorRemoved = false;//Hack to fix concurrency issues with for loop
    }

    /* Decrements numLocalBirds if -1 detected in localBirdList for birdNumber
    * Supplies the number of local birds around a specific bird*/
    private int getNumLocalBirds(int birdNumber) {
        int i, numLocalBirds, neighbourBirdID;

        numLocalBirds = numBirds;


        for (i = 0; i <= numBirds; i++) {
            neighbourBirdID = Integer.parseInt(localBirdList.get(birdNumber).get(i).toString());
            if (neighbourBirdID == -1) numLocalBirds--;
        }

        if (numLocalBirds < 0) numLocalBirds = 0;

        return numLocalBirds;
    }

    /* Complex function. Described in LaTeX report under section "3.2 Local Birds - Important Methods */
    private void updateLocalBirds() {
        int i, j, currentX, currentY, potentialNeighbourX, potentialNeighbourY;

        //Retrieve coords of every bird, but for each bird, retrieve list of birds nearby
        for (j = 0; j <= birdArrayList.size() - 1; j++) {
            currentX = (int) birdArrayList.get(j).currentPosition.getX();
            currentY = (int) birdArrayList.get(j).currentPosition.getY();

            //check nearby birds to see if they're within radius
            for (i = 0; i <= birdArrayList.size() - 1; i++) {
                potentialNeighbourX = (int) birdArrayList.get(i).currentPosition.getX();
                potentialNeighbourY = (int) birdArrayList.get(i).currentPosition.getY();
                if ((Math.pow((potentialNeighbourX - currentX), 2) + Math.pow((potentialNeighbourY - currentY), 2)) <= Math.pow(sliders.slider11.getValue(), 2)) {
                    localBirdID.set(i, i);
                } else {
                    localBirdID.set(i, -1);
                }
            }
            localBirdList.set(j, localBirdID);
        }
    }

    /* Draws cursor to screen when cursor is on screen. Changes colour depending on
        cursor value (modified by scrolling)

        Repurposes Chris Harte's drawBird method at half scale using mouseHelper object.
     */
    private void drawCursor(int cursorState) {
        String colour = "black";
        if (cursorState == 0) colour = "black";
        if (cursorState == 1) colour = "red";
        if (cursorState == 2) colour = "blue";

        int localBearing;
        CartesianDouble localPosition = new CartesianDouble(0, 0), nextPosition;

        // array holding angles and distances to draw a Bird
        int[][] polarArray = new int[][]{{120, -150, -120, -150},
                {5, 10, 10, 5}};

        // set local bearing to the same as the Bird
        localBearing = -90;

        // set local position reference to current position
        localPosition.setX(currentMousePosition.getX());
        localPosition.setY(currentMousePosition.getY());

        for (int i = 0; i < 4; i++) {
            // update local bearing
            localBearing = localBearing + polarArray[0][i];
            // calculate new position given local bearing and distance
            nextPosition = mouseHelper.calculateEndPoint(localPosition, polarArray[1][i], localBearing);
            // draw a line between local position and new position
            mouseHelper.drawLine(localPosition, nextPosition, colour);
            // update local position reference to point at new position
            localPosition = nextPosition;
        }
    }

    /* Draws wind line to screen when cursor in wind mode and mouse has been dragged */
    private void drawWind() {
        String colour = "blue";

        if (pointStart != null) {
            CartesianDouble localPosition = new CartesianDouble(pointStart.getX(), pointStart.getY()), nextPosition = new CartesianDouble(pointEnd.getX(), pointEnd.getY());

            // draw a line between local position and new position
            windHelper.drawLine(localPosition, nextPosition, colour);
        }
    }
}

