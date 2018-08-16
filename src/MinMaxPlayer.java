public class MinMaxPlayer{

    //Private fields
    private Tree minimaxTree;
    private Element[][] playingField;

    //Konstruktor
    public MinMaxPlayer(int m_depth, int[][] m_playingField){
        initializePlayingField(m_playingField);
        minimaxTree = new Tree(m_depth, playingField);
    }

    private void initializePlayingField(int[][] m_playingField){
        playingField = new Element[m_playingField.length][m_playingField[0].length];
        for(int i = 0; i < playingField.length; i++){
            for(int j = 0; j < playingField[0].length; j++){
                playingField[i][j] = new Element(m_playingField[i][j]);
            }
        }
    }

    public int returnColumn() {
        return minimaxTree.makeBestMove() + 1;
    }
}