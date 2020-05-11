import midi.*
import static midi.MlzMidi.*
import static midi.GammaHelper.*
import midi.Engines
import static midi.Engines.rnd

import midi.BasicEngines

//  Engines will all need to be initialized here, 
//  as each is responsible for adding their closures to Engines.map
//
//  Currently there is only the one class (BasicEngines) but others
//  can be defined.
//
BasicEngines.init()

if (args.contains('-list')) {
    println "available engines:"
    BasicEngines.map.keySet().sort().each { println "  $it" }
    System.exit(0)
}


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
    else if (inp.engine) {
        gamma = [ inp ]
        inp = [:]
    }
    else {
        println '''
            input map must contain either "gamma" or "engine" key
            See README.md and Gamma.md for details
        '''
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
    inp = [:]
}
//println "gamma: $gamma\n"

def settings = getSettings()
// default to the microsoft GS synth
if (!settings.player) settings.player = 'gervil'

// gamma should be able to contain or specify player

player=new Player(settings.player)
//player=new Player('828')
player.open()

// the pause between the commencement of each track
def fixedPause=inp.fixedPause?:0
def rndPause = (fixedPause>0) ? 0 : (inp.rndPause?:7000)

//  basic sanity check
if (!gamma) {
    println "Nothing to play!"
    System.exit(0)
}
gamma.each { g -> assertValid(g) }

threads=[]

Thread.start {
    gamma.each { g ->
        println "Starting { Engine: $g.engine; Title: $g.title }"
        if (Engines.stop) return
        if (! (g.patches instanceof List)) g.patches = [g.patches]
        g.pitches = (g.pitches instanceof String) ?  
                        toMidiNumList(g.pitches) :
                        g.pitches

        // if present, g.pitches should be ary of ints

        g.channel.each { c->
            if (Engines.stop) return
            threads << Thread.start {
                Engines.map[g.engine](c, g, player)
            }
            def delay=0
            if (fixedPause>0) delay=fixedPause
            else if (rndPause > 0) delay = rnd.nextInt(rndPause)
            Thread.sleep(delay)
        }
    }
    if (!Engines.stop) println '>> ALL THREADS STARTED. <<'
}

println 'press <Enter> to stop, or "slow"<Enter> for slow stop.'
def line=System.console().readLine()

if (line.trim().toLowerCase().startsWith("s")) {
    // slow stop
    println '>> SLOW STOP REQUESTED [wait for threads to close] <<'
    Engines.stop=true  
    threads.each { it.join() }
}
else {
    println ">> STOP! <<"
    player.allNotesOff()
}
player.close()
System.exit(0)