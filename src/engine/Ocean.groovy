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
        if (gamma.bankMSB != null) player.bankMSB(chan, gamma.bankMSB)
        else player.bankMSB(chan, 4) // alesis :^)

        sleep(200)
        def idx=0
        def note = toMidiNum('c-1')
        for (;;) {
            if (stop) break

            int offset = (idx + gamma.spread - gamma.density) % gamma.spread
            //println "$idx on  $off off"
            player.noteOn(chan, note+idx, gamma.velMin + rnd.nextInt(gamma.velVar))
            player.noteOff(chan,note+offset,0)

//            print "ocean: note-on -  note + idx = ${note + idx} "
//            println " note-off - note + offset=${ note + offset}"

            sleep(gamma.gapMin + rnd.nextInt(gamma.gapVar))

            idx = ++idx % gamma.spread
        }

        // send note-OFF to all (playing or not)
        [*0..<gamma.spread].each { offset ->
            player.noteOff(chan, note + offset, 0)
        }
    }
}
