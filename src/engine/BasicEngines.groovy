package engine


import static midi.MlzMidi.*

class BasicEngines extends Engine {

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
        // so far, you can specify one bank only
        if (g.bankMSB != null) player.bankMSB(chan, g.bankMSB) 
        if (g.bankLSB != null) player.bankLSB(chan, g.bankLSB)
        Thread.sleep(200)
        
        for(;;) {
            def nxtPatch = patchSet[Engine.rnd.nextInt(patchSet.size())]
            print "$g.title [${c+1}] >> "

            note=pitchSet[Engine.rnd.nextInt(pitchSet.size())]
            print "{${midiNumToStr(note)}} "

            if (nxtPatch != patch) {
                patch=nxtPatch
                player.patch(chan,patch)
                Thread.sleep(100)
            }
            player.showInstrument(patch)

            player.noteOn(chan,note,(96-note/2) as int)
            t= Engine.rnd.nextInt(hold.var)+hold.min
            Thread.sleep(t)

            player.noteOff(chan,note,0)
            if (Engine.stop) return
            Thread.sleep(Engine.rnd.nextInt(pause.var)+pause.min)
        }
    }


    static def selectPatch = { c,g, player ->
        def patch = g.patch ?: g.patches[0]
        if (g.bankMSB != null) player.bankMSB(c, g.bankMSB) 
        if (g.bankLSB != null) player.bankLSB(c, g.bankLSB)
        player.patch(c, patch)
        Thread.sleep(100)
    }

    static def multiNote = { c,g, player -> 
        int chan = c
        int trans = g.transpose ?: 0 
        List pitchSet = g.pitches
        int noteCount = g.noteCount ?: 1
        int velocity = g.velocity ?: 64

        this.selectPatch(c,g, player)

        def playing = []

        for (;;) {
            def note = trans + pitchSet[Engine.rnd.nextInt(pitchSet.size())]

            if (!g.noRepeats || !(note in playing)) {
                while (playing.size() >= noteCount) {
                    player.noteOff(chan,playing.removeAt(0),0)
                }

                def var=g.timing.pause.var==0?0: Engine.rnd.nextInt(g.timing.pause.var)
                Thread.sleep(g.timing.pause.min + var)

                player.noteOn(chan,note,velocity)
                playing.add(note)

            }
            if (Engine.stop) break
            def var=g.timing.hold.var==0?0: Engine.rnd.nextInt(g.timing.hold.var)
            Thread.sleep(g.timing.hold.min + var)


            if (Engine.stop) break
        }
        playing.each { p->
            player.noteOff(chan, p, 0)
        }
    }

    static def intervals = { c,g, player ->
        int chan = c
        this.selectPatch(c,g,player)

        def playing = []

        int maxBottom = g.pitches.size()-(g.interval-1) // origin 0
        int idx = -1
        int sinceBreath = 0, nextBreath = 0


        for (;;) {
            def oldIdx=idx
            if (g.breathe) {
                if (++sinceBreath > nextBreath) {
                    Thread.sleep(rndInt(g.breathe.pause))
                    nextBreath = rndInt(g.breathe.freq)
                    sinceBreath=0
                }
            }
            if (idx < 0 || g.maxLeap == null) {
                idx= Engine.rnd.nextInt(maxBottom)
            }
            else {
                idx = idx + Engine.rnd.nextInt(g.maxLeap * 2 + 1) - g.maxLeap
                if (idx<0) idx=0
                else if (idx>=maxBottom) idx=maxBottom-1
            }
            def bottom = g.pitches[idx]
            playing.add(bottom)
            if (g.interval > 0) playing.add(g.pitches[idx + g.interval - 1]) 

            try { // debug
                if (!g.noRepeats || oldIdx!=idx) {
                    playing.each { p ->
                        player.noteOn(chan,p,g.velocity)
                    }
                }
            }
            catch (Exception ex) {
                println ex
                println "playing: $playing"
            }

            def var=g.timing.hold.var==0?0: Engine.rnd.nextInt(g.timing.hold.var)
            Thread.sleep(g.timing.hold.min + var)

            playing.each { p->
                player.noteOff(chan, p, 0)
            }
            playing.clear()
            if (Engine.stop) break

            var=g.timing.pause.var==0?0: Engine.rnd.nextInt(g.timing.pause.var)
            Thread.sleep(g.timing.pause.min + var)

        }
   
    }

    static def ocean = { c,g, player ->
        int chan = c

        player.patch(chan,122) // std midi = ocean
        Thread.sleep(200)

        def idx=0
        def note = toMidiNum('c-1')
        for (;;) {
            if (Engine.stop) break

            int off = (idx+g.spread-g.density)%g.spread
            //println "$idx on  $off off"
            player.noteOn(chan,note+idx,70+ Engine.rnd.nextInt(g.velVar))
            player.noteOff(chan,note+off,0)
            Thread.sleep(g.gapMin+ Engine.rnd.nextInt(g.gapVar))

            idx = ++idx % g.spread
        }
        // send note-OFF to all
        [*0..<g.spread].each { off ->
            player.noteOff(chan,note+off,0)
        }
    }

        
    static void init() {
        //println 'BasicEngines init()'
        //map.ocean = ocean // now its own class
        map.multiPatch = multiPatch
        map.multiNote = multiNote
        map.intervals = intervals
    }
  


}


