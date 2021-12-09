import stanford.karel.SuperKarel;
/**
 * This Game consists of two parts:

 * Part 1: assume you have a map with beepers that are randomly distributed, your mission now is to collect
 all these beepers which known as (cleaning the map), and then divide the map.

 * Dividing the map: any given map with any size will be divided into four equal chambers using beepers.

 * Special cases: some maps are impossible to divide into four equal chambers, so they are divided into two equal chambers. Whereas, other maps have an odd number of vertical and horizontal
 lines and equal, ex: 5*5, 7*7, they are divided using diagonal lines also.

 * Last mission in part 1: is returning the Robot "Karel" to his origin point after achieving the task.

 * Part 2: Assume that the map is totally clean from beepers, so do the same as part 1, but without cleaning
 the map.
                                                      Good Luck!
 */
public class Homework extends SuperKarel {
    public void run() {
        setBeepersInBag(400);
        part1();
//        part2();
    }
    private void part1(){
        cleanMap();
        doTask();
        System.out.println("Number of Karel's moves = "+moves);
        System.out.println("Number of dropped beepers: "+droppedBeepers());
        System.out.println("******************************");
        moves= 0;
        dropped=0;
        picked=0;
    }
    private void part2(){
        doTask();
        System.out.println("Number of Karel's moves = "+moves);
        System.out.println("Number of dropped beepers: "+droppedBeepers());
        moves= 0;
        dropped=0;
        picked=0;
    }
    //Cleaning the map.......
    private void cleanMap(){
        System.out.println("Cleaning the map started.....");
        cleanRow();
        while (leftIsClear()) {
            turnFaceToWest();
            cleanRow();
            if (rightIsClear()) {
                turnFaceToEast();
                cleanRow();
            } else
                turnAround();
        }
        System.out.println("Cleaning the map finished.....");
        backHome();
        System.out.println("mission one done...");
        System.out.println("******************************");
    }
    private void cleanRow() {
        checkBeepers();
        keepPicking();
    }
    private void turnFaceToWest() {
        turnLeft();
        countMoves();
        turnLeft();
    }
    private void turnFaceToEast() {
        turnRight();
        countMoves();
        turnRight();
    }
    private void doTask() {
        System.out.println("Getting the world dimensions.....");
        int[] world_dimensions = get_world_dimensions();
        System.out.println("Dimensions of the world = "+world_dimensions[0]+","+world_dimensions[1]);
        backHome();
        if (frontIsBlocked()) {
            turnLeft();
        }
        System.out.println("******************************");
        System.out.println("Dividing the map.....");
        divide_map(world_dimensions);
        System.out.println("Part 1 done...");
    }
    private int[] get_world_dimensions() {
        int[] dimensions = new int[2];
        int steps =0;
        while(frontIsClear())
        {
            countMoves();
            steps++;
        }
        dimensions[0]= steps+1;
        steps=0;
        turnLeft();
        while(frontIsClear())
        {
            countMoves();
            steps++;
        }
        dimensions[1]=steps+1;
        return dimensions;
    }
    private void backHome() {
        System.out.println("Returning home.....");
        while(true) {
            if (facingWest()) {
                keepMoving();
                turnLeft();
                keepMoving();
                turnLeft();
                break;
            } else {
                turnLeft();
            }
        }
    }
    private void divide_map(int[] dimensions) {
        int x_axis = dimensions[0];
        int y_axis = dimensions[1];

        if(x_axis==1 & y_axis %2!=0)
        {   int moveTo=(y_axis/2);
            stepsToMove(moveTo);
            putBeeper();
            backHome();
            turnRight();
        }
        else if(x_axis<=2)
            divide_special_x(x_axis);
        else if(x_axis%2==0)
            divide_even_x(x_axis);
        else
            divide_odd_x(x_axis);
        if(y_axis<=2)
            divide_special_y(y_axis);
        else if(y_axis%2==0)
            divide_even_y(y_axis);
        else
            divide_odd_y(y_axis);
        if(((x_axis & y_axis) %2 !=0)& (x_axis == y_axis) &( x_axis!=3))
            divide_diagonal(x_axis);
    }

    private void divide_special_x(int x_axis)
    {
        if(x_axis==2){
            turnAround();
            turnLeft();
        }
        else {

            turnAround();
        }
    }
    private void divide_special_y(int y_axis){
        if (y_axis == 2) {
            backHome();
        }
        turnLeft();
        backHome();
    }
    private void divide_even_x(int x_axis){
        int moveTo = (x_axis/2);
        stepsToMove(moveTo);
        putLeftLine();
        turnLeft();
        countMoves();
        putLeftLine();
    }
    private void divide_even_y(int y_axis){
        int moveTo = (y_axis/2);
        if(facingSouth())
        {
            turnAround();
            stepsToMove(moveTo-1);
            turnLeft();
            keepPutting();
            turnRight();
            putRightLine();
            turnRight();
            putRightLine();
            turnLeft();
            keepMoving();
            turnLeft();
        }
        else
        {
            turnAround();
            stepsToMove(moveTo);
            turnRight();
            keepPutting();
            turnRight();
            putRightLine();
            turnRight();
            putRightLine();
            backHome();
        }
    }
    private void divide_odd_x(int x_axis){
        int moveTo = (x_axis/2);
        stepsToMove(moveTo);
        putLeftLine();
    }
    private void divide_odd_y(int y_axis){
        int moveTo = (y_axis/2);
        if(facingSouth())
        {
            turnAround();
            stepsToMove(moveTo);
            turnRight();
            keepPutting();
            turnAround();
            keepPutting();
            turnLeft();
            keepMoving();
            turnLeft();
        }
        else
        {
            turnAround();
            stepsToMove(moveTo);
            turnRight();
            keepPutting();
            turnAround();
            keepPutting();
            backHome();
        }
    }
    private void putLeftLine () {
        countBeepers();
        turnLeft();
        keepPutting();
    }
    private void putRightLine () {
        countMoves();
        countBeepers();
        turnRight();
        keepPutting();
    }
    private void divide_diagonal(int x_axis) {
        turnLeft();
        keepMoving();
        countBeepers();
        turnAround();
        for (int i = 1; i < x_axis; i++) {
            rowDownLeft();
            countBeepers();
            turnRight();
        }
        turnAround();
        keepMoving();
        turnAround();
        countBeepers();
        for (int i = 1; i < x_axis; i++) {
            rowDownRight();
            countBeepers();
            turnLeft();
        }
        turnLeft();
    }
    private void rowDownLeft(){
        countMoves();
        turnLeft();
        countMoves();
    }
    private void rowDownRight(){
        countMoves();
        turnRight();
        countMoves();
    }
    private void stepsToMove(int steps)
    {
        for(int i=0;i<steps;i++)
            countMoves();
    }
    private int droppedBeepers()
    {
        if(dropped>picked)
            return dropped-picked;
        return picked-dropped;
    }
    int dropped=0,picked =0,moves =0;
    //All the redundant methods
    private void countMoves(){
        move();
        moves++;
    }
    private void keepMoving() {
        while(frontIsClear())
        {
            countMoves();
        }
    }
    private void keepPicking() {
        while(frontIsClear()) {
            countMoves();
            checkBeepers();
        }
    }
    private void keepPutting() {

        while(frontIsClear())
        {
            countMoves();
            countBeepers();
        }
    }
    private void checkBeepers() {
        if(beepersPresent())
        {
            pickBeeper();
            picked++;
        }
    }
    private void countBeepers(){
        if(noBeepersPresent())
        {
            putBeeper();
            dropped++;
        }
    }
}
