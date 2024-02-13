package engine

class GammaThread extends Thread {
    def channel, gamma, player, closure
    GammaThread(channel, gamma, player, closure) {
        this.channel=channel
        this.gamma=gamma
        this.player=player
        this.closure=closure
    }
    void run() {
        closure(channel,gamma,player)
    }

}