import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

public class NickelAndDime implements IState {

    private char[] state;
    private static final char NICKEL = 'N';
    private static final char DIME = 'D';
    private static final char EMPTY = '_';

    // Estado inicial: 2 nickels à esquerda, 1 espaço livre no meio, 2 dimes à direita
    public NickelAndDime() {
        this.state = new char[]{'N', 'N', '_', 'D', 'D'};
    }

    public NickelAndDime(char[] state) {
        this.state = state.clone();
    }

    @Override
    public boolean goal() {
        return new String(state).equals("DD_NN");
    }


    @Override
    public ArrayList<NickelAndDime> suc() {
        ArrayList<NickelAndDime> suc = new ArrayList<>();

        for (int i = 0; i < state.length; i++) {
            if (state[i] == NICKEL) {

                // move nickel to the right
                if (i < state.length - 1 && state[i + 1] == EMPTY) {
                    char[] newState = state.clone();
                    newState[i] = EMPTY;
                    newState[i + 1] = NICKEL;
                    suc.add(new NickelAndDime(newState));
                }

                // nikel jumps over dime to the right
                if (i < state.length - 2 && state[i + 1] == DIME && state[i + 2] == EMPTY) {
                    char[] newState = state.clone();
                    newState[i] = EMPTY;
                    newState[i + 2] = NICKEL;
                    suc.add(new NickelAndDime(newState));
                }
            } else if (state[i] == DIME) {

                // dime moves to the left
                if (i > 0 && state[i - 1] == EMPTY) {
                    char[] newState = state.clone();
                    newState[i] = EMPTY;
                    newState[i - 1] = DIME;
                    suc.add(new NickelAndDime(newState));
                }

                // dime jumps over nickel to the left
                if (i > 1 && state[i - 1] == NICKEL && state[i - 2] == EMPTY) {
                    char[] newState = state.clone();
                    newState[i] = EMPTY;
                    newState[i - 2] = DIME;
                    suc.add(new NickelAndDime(newState));
                }
            }
        }

        return suc;
    }

    public static IState getInitialState() {
        return new NickelAndDime(new char[] {NICKEL, NICKEL, EMPTY, DIME , DIME});
    }

    @Override
    public String toString() {
        return new String(state);
    }

    public static void main(String[] args) {
        IState initialState = NickelAndDime.getInitialState();
        System.out.println("Initial state: " + initialState);

        if (initialState.goal()) {
            System.out.println("Initial state is already the goal state!");
        } else {
            ArrayList<NickelAndDime> successors = initialState.suc();
            System.out.println("Successors of initial state:");
            for (NickelAndDime suc : successors) {
                System.out.println(suc);
            }
        }
    }
}
