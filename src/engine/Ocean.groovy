package engine

import static midi.MlzMidi.toMidiNum
import static engine.Morbleu.*

class Ocean extends Engine {
    int chan

    private boolean paused

    Ocean(g, p) {
        super(g,p)
        chan = gamma.channel
    }

    void run() {
        player.patch(chan,122) // std midi = ocean
        sleep(200)
        def idx=0
        def note = toMidiNum('c-1')
        for (;;) {
            if (stop) break

            int off = (idx+gamma.spread-gamma.density)%gamma.spread
            //println "$idx on  $off off"
            player.noteOn(chan,note+idx,70+ rnd.nextInt(gamma.velVar))
            player.noteOff(chan,note+off,0)
            sleep(gamma.gapMin+ rnd.nextInt(gamma.gapVar))

            idx = ++idx % gamma.spread
        }
        // send note-OFF to all
        [*0..<gamma.spread].each { off ->
            player.noteOff(chan,note+off,0)
        }
    }
}
