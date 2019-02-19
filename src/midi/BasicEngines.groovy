package midi
import static midi.MlzMidi.*

class BasicEngines {
   
    static Random rnd=new Random(new Date().getTime())

    static boolean stop=false 

    static def multiPatch = { c, g, player ->
        int chan=c
        List pitchSet = (g.pitches instanceof String) ? 
                toMidiNumList(g.pitches) :
                g.pitches

        List patchSet = g.patches
        def hold = g.timing.hold
        def pause = g.timing.pause

        int note=64
        long t=1000
        Thread.sleep(200)
        
        for(;;) {
            def patch = patchSet[rnd.nextInt(patchSet.size())]
            print "$g.title [${c+1}] >> "

            note=pitchSet[rnd.nextInt(pitchSet.size())]
            print "{${midiNumToStr(note)}} "
            player.showInstrument(patch)

            player.patch(chan,patch)
            Thread.sleep(10)

            player.noteOn(chan,note,(96-note/2) as int)
            t=rnd.nextInt(hold.var)+hold.min
            Thread.sleep(t)

            player.noteOff(chan,note,0)
            if (stop) return
            Thread.sleep(rnd.nextInt(pause.var)+pause.min)
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

}


