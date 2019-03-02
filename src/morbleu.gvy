import midi.*
import static midi.MlzMidi.*
import static midi.GammaHelper.*
import midi.Engines
import static midi.Engines.rnd

import midi.BasicEngines

//  Engines will all need to be initialized here, 
//  as each is responsible for adding their closures to
//  Engines.map
BasicEngines.init()


def inp = loadGamma(args)

inheritors = [
    override: {k,v,g -> g[k]=v},
    passive: {k,v,g -> if (g[k]==null) g[k]=v },
    add: {k,v,g -> 
        if (g[k]==null) g[k]=v
        else g[k] += v
    }
]

def gamma
if (inp instanceof Map) {
    if (inp.gamma) {
        gamma = inp.gamma
    }
    else {
        println 'input map is missing a "gamma" key'
        System.exit(0)
    }
    if (inp.inherit) {
        for (def type : inp.inherit.keySet()) {
            //println type
            def toInherit = inp.inherit[type]
            for (def k : toInherit.keySet()) {
                //println "k: $k"
                gamma.each { g->
                    inheritors[type](k,toInherit[k],g)
                }
            }
        }
    }
}
else {
    gamma = inp
}
//println "gamma: $gamma\n"

// gamma should be able to contain or specify player

//player=new Player('gervil') // the microsoft GS synth
player=new Player('828')
player.open()

// the pause between the commencement of each track
rndPause=7000

threads=[]

Thread.start {
    gamma.each { g -> 
        if (! (g.patches instanceof List)) g.patches = [g.patches]
        g.pitches = (g.pitches instanceof String) ?  
                        toMidiNumList(g.pitches) :
                        g.pitches // should be ary of ints

        g.channel.each { c->
            if (Engines.stop) return
            threads << Thread.start { 
                Engines.map[g.engine](c, g, player) 
            }
            Thread.sleep(rnd.nextInt(rndPause))
        }
    }
    if (!Engines.stop) println '>> ALL THREADS STARTED. <<'
}

println 'press Enter to stop'
System.console().readLine()
println '>> STOP REQUESTED [wait for threads to close] <<'
//stop=true

Engines.stop=true  

threads.each { it.join() }

player.close()
