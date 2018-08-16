import java.util.ArrayList;
import java.util.List;


public class Tree {

    //Public properties
    public int maxDepth = 0;

    //Private properties
    private TNode rootNode;
    private List<List<TNode>> levels;

    public Tree(int m_depth, Element[][] m_playingField){
        maxDepth = m_depth;

        levels = new ArrayList<List<TNode>>();
        for(int i = 0; i < maxDepth; i++){
            levels.add(new ArrayList<TNode>());
        }

        rootNode = new TNode(null, new PlayingField(m_playingField), Players.MAXPLAYER);
        getLevel(0).add(rootNode);

        createTree(maxDepth);
    }

    private void createTree(int depth) {

        //Zgradimo drevo moznosti
        for(int i = 0; i <= depth - 2; i++){
            List<TNode> currentLevel = getLevel(i);
            List<TNode> nextLevel = getLevel(i + 1);

            int player = Players.MAXPLAYER;

            if((i+1)%2 == 0){
                player = Players.MAXPLAYER;
            }
            else{
                player = Players.MINPLAYER;
            }

            if(i == depth - 2){
                for(TNode node : currentLevel){
                    nextLevel.addAll(node.setChildren(player, true)); //otroci so hkrati listi
                }
            }
            else{
                for(TNode node : currentLevel){
                    nextLevel.addAll(node.setChildren(player, false));
                }
            }
        }

        // Set minimax value to every tree node
        setMinMaxValue(this.rootNode);
    }

    // Method, sets minimax value to every node
    private int setMinMaxValue(TNode rootNode){
        if(rootNode.isIsLeaf()){
            rootNode.setMinIMaxValue(rootNode.FunctionUse());
            return rootNode.getMinIMaxValue();
        }
        else{

            if(rootNode.getCurrentPlayer() == Players.MAXPLAYER){
                int max = MINIMAXRATING.LOSE;
                for(TNode child : rootNode.getChildren()){
                    int maxR = setMinMaxValue(child);
                    if(maxR > max){
                        max = maxR;
                    }
                }
                rootNode.setMinIMaxValue(max);
                return max;
            }
            else{
                int min = MINIMAXRATING.WIN;
                for(TNode child : rootNode.getChildren()){
                    int minR = setMinMaxValue(child);
                    if(minR < min){
                        min = minR;
                    }
                }
                rootNode.setMinIMaxValue(min);
                return min;
            }
        }
    }

    public List<TNode> getLevel(int index){
        return levels.get(index);
    }

    public int makeBestMove(){
        boolean found = false;
        int bestColumn = 0;
        List<TNode> listOfChecked = new ArrayList<TNode>();

        while(found != true){
            int maxValue = MINIMAXRATING.LOSE;
            TNode maxNode = null;

            for(TNode node : rootNode.getChildren()){
                if(!listOfChecked.contains(node) && node.getMinIMaxValue() >= maxValue){
                    maxValue = node.getMinIMaxValue();
                    maxNode = node;
                }
            }

            bestColumn = maxNode.getParent().getChildren().indexOf(maxNode);

            if(ColumnIsAvailable(bestColumn, maxNode)){
                found = true;
            }
            else{
                listOfChecked.add(maxNode);
            }
        }

        return bestColumn;
    }

    private boolean ColumnIsAvailable(int bestColumn, TNode node) {

        boolean found = false;

        for(int i = node.getModel().returnPlayingField().length - 1; i >= 0; i--){
            if(node.getModel().returnPlayingField()[i][bestColumn].returnPlayer() == 0){
                found = true;
                break;
            }
        }

        return found;
    }
}