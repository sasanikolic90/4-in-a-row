public class RandomPlayer{

    public RandomPlayer(){}

    public int returnColumn(int[][] theArray) {
        // TODO Auto-generated method stub

        boolean found = false;
        int column = 1;

        while(!found){
            column = (int)(Math.random()*(theArray[0].length)) + 1;

            for(int i = theArray.length - 1; i >= 0; i--){
                if(theArray[i][column - 1] == 0){
                    found = true;
                    break;
                }
            }
        }

        return column;
    }

}