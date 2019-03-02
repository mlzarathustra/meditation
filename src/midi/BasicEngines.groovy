package midi
import static midi.MlzMidi.*

class BasicEngines extends Engines {

    static def multiPatch = { c, g, player ->
        int chan=c
                        // this pitch/pitchset logic could go in morbleu
        List pitchSet = g.pitches
        List patchSet = g.patches
        def hold = g.timing.hold
        def pause = g.timing.pause

        def patch = -1

        int note=64
        long t=1000
        Thread.sleep(200)
        
        for(;;) {
            def nxtPatch = patchSet[rnd.nextInt(patchSet.size())]
            print "$g.title [${c+1}] >> "

            note=pitchSet[rnd.nextInt(pitchSet.size())]
            print "{${midiNumToStr(note)}} "

            if (nxtPatch != patch) {
                patch=nxtPatch
                player.patch(chan,patch)
                Thread.sleep(100)
            }
            player.showInstrument(patch)

            player.noteOn(chan,note,(96-note/2) as int)
            t=rnd.nextInt(hold.var)+hold.min
            Thread.sleep(t)

            player.noteOff(chan,note,0)
            if (stop) return
            Thread.sleep(rnd.nextInt(pause.var)+pause.min)
        }
    }

    static def multiNote = { c,g, player -> 
        int chan = c
        List pitchSet = g.pitches

        def patch = g.patch ?: g.patches[0]
        def noteCount = g.noteCount ?: 1

        player.bankMSB(chan, g.bankMSB)
        player.patch(chan, patch)
        Thread.sleep(100)

        def playing = []

        for (;;) {

            while (playing.size() >= g.noteCount) {
                player.noteOff(chan,playing.removeAt(0),0)
            }

            Thread.sleep(g.timing.pause.min + rnd.nextInt(g.timing.pause.var))

            def note = pitchSet[rnd.nextInt(pitchSet.size())]
            player.noteOn(chan,note,g.velocity)
            playing.add(note)

            Thread.sleep(g.timing.hold.min + rnd.nextInt(g.timing.hold.var))


            if (stop) break
        }
        playing.each { p->
            player.noteOff(chan, p, 0)
        }
    }

    static def ocean = { c,g, player -> 
        int chan = c 

        player.patch(chan,122) // std midi = ocean
        Thread.sleep(200)

        def idx=0
        def note = toMidiNum('c-1')
        for (;;) {
            if (stop) break

            int off = (idx+g.spread-g.density)%g.spread
            //println "$idx on  $off off"
            player.noteOn(chan,note+idx,70+rnd.nextInt(g.velVar))
            player.noteOff(chan,note+off,0)
            Thread.sleep(g.gapMin+rnd.nextInt(g.gapVar))

            idx = ++idx % g.spread
        }
        // send note-OFF to all
        [*0..<g.spread].each { off ->
            player.noteOff(chan,note+off,0)
        }
    }

        
    static void init() {
        //println 'BasicEngines init()'
        map.ocean = ocean
        map.multiPatch = multiPatch
        map.multiNote = multiNote
    }
  


}


