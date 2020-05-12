package engine

import midi.Player

import static midi.MlzMidi.toMidiNum

class Ocean extends Engine {
    int chan
    def gamma
    Player player

    private boolean paused

    Ocean(g, p) { gamma = g; chan = gamma.channel; player = p }

    //   TODO - figure out pause logic
    void pause() { paused = true }
    void unPause() { paused = false }

    void play() {
        def idx=0
        def note = toMidiNum('c-1')
        for (;;) {
            if (Engine.stop) break

            int off = (idx+gamma.spread-gamma.density)%gamma.spread
            //println "$idx on  $off off"
            player.noteOn(chan,note+idx,70+ Engine.rnd.nextInt(gamma.velVar))
            player.noteOff(chan,note+off,0)
            sleep(gamma.gapMin+ Engine.rnd.nextInt(gamma.gapVar))

            idx = ++idx % gamma.spread
        }
        // send note-OFF to all
        [*0..<gamma.spread].each { off ->
            player.noteOff(chan,note+off,0)
        }
    }

    void run() {
        player.patch(chan,122) // std midi = ocean
        sleep(200)
        play()
    }
}
