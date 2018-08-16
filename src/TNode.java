import java.util.ArrayList;
import java.util.List;

public class TNode {

    private PossibleMove move;
    private PlayingField model;
    private int minimaxValue; //minimax vrednost
    private TNode stars; //predhodnik
    private List<TNode> otroci; //otroci
    private boolean isLeaf = false;
    private int player;

    public TNode(PossibleMove m_move, PlayingField playingField, int m_player){
        this.move = m_move;
        this.model = playingField;
        this.player = m_player;
        otroci = new ArrayList<TNode>();
        this.minimaxValue = MINIMAXRATING.DEFAULMINIMAXVALUE; //Default value
    }

    //Get-Set

    public PlayingField getModel(){
        return this.model;
    }

    public int getCurrentPlayer(){
        return this.player;
    }

    public void setCurrentPlayer(int m_player){
        this.player = m_player;
    }

    public void setParent(TNode m_stars){
        this.stars = m_stars;
    }

    public TNode getParent(){
        return this.stars;
    }

    public void setLeaf(){
        this.isLeaf = true;
    }

    public boolean isIsLeaf(){
        return this.isLeaf;
    }

    public List<TNode> getChildren(){
        return this.otroci;
    }

    public PossibleMove getMove(){
        return this.move;
    }

    public List<TNode> setChildren(int player, boolean setList){
        if(!this.isIsLeaf()){
            for(int i = 0; i < this.model.returnNumOfColumns(); i++){
                PlayingField m_playingField = model.Copy();

                TNode otrok = new TNode(new PossibleMove(i), m_playingField, player);
                otrok.setParent(this);
                boolean niList = otrok.makeMove(this.player);

                if(!niList){
                    otrok.setLeaf();
                }

                if(setList){
                    otrok.setLeaf();
                }

                this.otroci.add(otrok);
            }
        }

        return this.otroci;
    }

    private boolean makeMove(int player){
        return model.setFieldStatus(this.getMove(), player);
    }

    public void setMinIMaxValue(int value){
        this.minimaxValue = value;
    }

    public int getMinIMaxValue(){
        return this.minimaxValue;
    }

    public void setMinValue(){
        if(this.getChildren() != null && this.getChildren().size() > 0){

            int min = MINIMAXRATING.WIN;

            for(TNode otrok : this.getChildren()){
                if(otrok.getMinIMaxValue() < min){
                    min = otrok.getMinIMaxValue();
                }
            }

            this.setMinIMaxValue(min);
        }
    }

    public void setMaxValue(){
        if(this.getChildren() != null && this.getChildren().size() > 0){

            int max = MINIMAXRATING.LOSE;

            for(TNode otrok : this.getChildren()){
                if(otrok.getMinIMaxValue() > max){
                    max = otrok.getMinIMaxValue();
                }
            }

            this.setMinIMaxValue(max);
        }
    }

    public int FunctionUse() {



        /*
         * WIN 4 in a row
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+3].returnPlayer()) {
                    return MINIMAXRATING.WIN;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-3; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+3][col].returnPlayer())
                    return MINIMAXRATING.WIN;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-3; row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row+3][col+3].returnPlayer())
                    return MINIMAXRATING.WIN;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=3; row--) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row-2][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row-3][col+3].returnPlayer())
                    return MINIMAXRATING.WIN;
            }
        }

        /*
         * 3 in a row for max
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+2].returnPlayer()) {
                    return MINIMAXRATING.ALMOSTWIN;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-2; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col].returnPlayer())
                    return MINIMAXRATING.ALMOSTWIN;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-2; row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col+2].returnPlayer())
                    return MINIMAXRATING.ALMOSTWIN;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=2; row--) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row-2][col+2].returnPlayer())
                    return MINIMAXRATING.ALMOSTWIN;
            }
        }

        /*
         * 3-X for max
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+2].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+3].returnPlayer() == Players.MINPLAYER) {
                    return MINIMAXRATING.BREAKMINTHREE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-3; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+2][col].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+3][col].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTHREE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-3; row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+2][col+2].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+3][col+3].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTHREE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=3; row--) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row-2][col+2].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row-3][col+3].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTHREE;
            }
        }

        /*
         * 2-X for max
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+2].returnPlayer() == Players.MINPLAYER) {
                    return MINIMAXRATING.BREAKMINTWO;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-2; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+2][col].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTWO;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-2; row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row+2][col+2].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTWO;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=2; row--) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MINPLAYER
                        && model.returnPlayingField()[row-2][col+2].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINTWO;
            }
        }

        /*
         * 1-X for max
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MINPLAYER) {
                    return MINIMAXRATING.BREAKMINONE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-1; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINONE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-1; row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINONE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=1; row--) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MINPLAYER)
                    return MINIMAXRATING.BREAKMINONE;
            }
        }

        /*
         * 2 in a row for max
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()) {
                    return MINIMAXRATING.GOODGAMEWIN;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-1; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer())
                    return MINIMAXRATING.GOODGAMEWIN;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-1; row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer())
                    return MINIMAXRATING.GOODGAMEWIN;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=1; row--) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MAXPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer())
                    return MINIMAXRATING.GOODGAMEWIN;
            }
        }

        /*
         * LOSE
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+3].returnPlayer()) {
                    return MINIMAXRATING.LOSE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-3; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+3][col].returnPlayer())
                    return MINIMAXRATING.LOSE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-3; row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row+3][col+3].returnPlayer())
                    return MINIMAXRATING.LOSE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=3; row--) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row-2][col+2].returnPlayer()
                        && curr == model.returnPlayingField()[row-3][col+3].returnPlayer())
                    return MINIMAXRATING.LOSE;
            }
        }

        /*
         * 3 in a row for min
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row][col+2].returnPlayer()) {
                    return MINIMAXRATING.ALMOSTLOSE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-2; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col].returnPlayer())
                    return MINIMAXRATING.ALMOSTLOSE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-2; row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row+2][col+2].returnPlayer())
                    return MINIMAXRATING.ALMOSTLOSE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=2; row--) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer()
                        && curr == model.returnPlayingField()[row-2][col+2].returnPlayer())
                    return MINIMAXRATING.ALMOSTLOSE;
            }
        }

        /*
         * 3-O for min
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row][col+2].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row][col+3].returnPlayer() == Players.MAXPLAYER) {
                    return MINIMAXRATING.BREAKMAXTHREE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-3; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+2][col].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+3][col].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTHREE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-3; row++) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+2][col+2].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+3][col+3].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTHREE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=3; row--) {
            for (int col=0; col<model.returnNumOfColumns()-3; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row-2][col+2].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row-3][col+3].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTHREE;
            }
        }

        /*
         * 2-O for min
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row][col+2].returnPlayer() == Players.MAXPLAYER) {
                    return MINIMAXRATING.BREAKMAXTWO;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-2; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+2][col].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTWO;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-2; row++) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row+2][col+2].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTWO;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=2; row--) {
            for (int col=0; col<model.returnNumOfColumns()-2; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MAXPLAYER
                        && model.returnPlayingField()[row-2][col+2].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXTWO;
            }
        }

        /*
         * 1-O for min
         */
        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row][col+1].returnPlayer() == Players.MAXPLAYER) {
                    return MINIMAXRATING.BREAKMAXONE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-1; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXONE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-1; row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row+1][col+1].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXONE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=1; row--) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && model.returnPlayingField()[row-1][col+1].returnPlayer() == Players.MAXPLAYER)
                    return MINIMAXRATING.BREAKMAXONE;
            }
        }

        /*
         * 2 in a row for min
         */

        // horizontal rows
        for (int row=0; row<model.returnNumOfRows(); row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row][col+1].returnPlayer()) {
                    return MINIMAXRATING.GOODGAMELOSE;
                }
            }
        }
        // vertical columns
        for (int col=0; col<model.returnNumOfColumns(); col++) {
            for (int row=0; row<model.returnNumOfRows()-1; row++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col].returnPlayer())
                    return MINIMAXRATING.GOODGAMELOSE;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<model.returnNumOfRows()-1; row++) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row+1][col+1].returnPlayer())
                    return MINIMAXRATING.GOODGAMELOSE;
            }
        }
        // diagonal upper left to lower right
        for (int row=model.returnNumOfRows()-1; row>=1; row--) {
            for (int col=0; col<model.returnNumOfColumns()-1; col++) {
                int curr = model.returnPlayingField()[row][col].returnPlayer();
                if (curr == Players.MINPLAYER
                        && curr == model.returnPlayingField()[row-1][col+1].returnPlayer())
                    return MINIMAXRATING.GOODGAMELOSE;
            }
        }

        /*
         * EQUAL
         */

        return MINIMAXRATING.EQUAL;
    }

}