package engine

import midi.Player

class Engine extends Thread {
    def gamma
    Player player

    String errMsg=''

    Engine() {}
    Engine(g,p) { gamma=g; player=p }

    // overload the below with a specific validity check
    boolean isValid(g) {
        errMsg = ''
        return true
    }

}
