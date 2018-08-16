
public class PlayingField {

    private Element[][] playingField;

    public PlayingField(){
        playingField = new Element[6][7];
        InitializeIP();
    }

    public PlayingField(Element[][] m_playingField){
        playingField = new Element[6][7];
        InitializeIP(m_playingField);
    }

    private void InitializeIP(Element[][] m_playingField){
        for(int i = 0; i < this.playingField.length; i++){
            for(int j = 0; j < this.playingField[0].length; j++){
                this.playingField[i][j] = m_playingField[i][j];
            }
        }
    }

    private void InitializeIP() {
        for(int i = 0; i < this.playingField.length; i++){
            for(int j = 0; j < this.playingField[0].length; j++){
                this.playingField[i][j] = new Element();
            }
        }
    }

    public boolean setFieldStatus(PossibleMove move, int igralec) {

        boolean uspel = false;
        Element elt = new Element(igralec);

        for(int i = playingField.length - 1; i >= 0; i--){
            if(playingField[i][move.getMove()].returnPlayer() == 0){
                playingField[i][move.getMove()] = elt;
                uspel = true;
                break;
            }
        }

        return uspel;
    }

    public PlayingField Copy() {

        PlayingField copy = new PlayingField();

        for(int i = 0; i < playingField.length; i++){
            for(int j = 0; j < playingField[0].length; j++){
                copy.playingField[i][j] = this.playingField[i][j];
            }
        }

        return copy;
    }

    public int returnNumOfColumns(){
        return this.playingField[0].length;
    }

    public int returnNumOfRows(){
        return this.playingField.length;
    }

    public Element[][] returnPlayingField(){
        return this.playingField;
    }

}