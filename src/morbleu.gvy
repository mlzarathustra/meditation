import engine.Breathe
import engine.Engine
import engine.Ocean
import midi.*
import static engine.Engine.*
import static engine.Morbleu.threads

import engine.BasicEngines

//  Engines all need to be initialized here,
//  as each is responsible for adding their closures to Engines.map
//
//  Currently there is only the one class (BasicEngines) but others
//  can be defined.
//
BasicEngines.init()
map['ocean'] = Ocean.class
map['breathe'] = Breathe.class
//
//

if (args.contains('-list')) {
    println "available engines:"
    map.keySet().sort().each { println "  $it" }
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
    if (inp.engine) {
        gamma = [ inp ]
        inp = [:]
    }
    else if (inp.gamma) {
        gamma = inp.gamma
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

// In the future, a gamma should be able to contain or specify player
player=new Player(settings.player)
if ( null == player.dev ) {
    println "MIDI Player $settings.player not found."
    System.exit(0)
}
player.open()
//

// the pause between the commencement of each track
def fixedPause=inp.fixedPause?:0
def rndPause = (fixedPause>0) ? 0 : (inp.rndPause?:7000)

//  basic sanity check
if (!gamma) {
    println "Nothing to play!"
    System.exit(0)
}

//          TODO - keep instantiated Thread classes in this step...
//                  problem: it isn't 1:1
//                  as each 'g' may contain multiple channels
//
threads = gamma.collect { g->assertValid(g) }.flatten()
//  or addAll() for each
//
//gamma.each { g -> assertValid(g) }

Thread.start {
    threads.each { t ->
        //println "Starting { Engine: $t.gamma.engine; Title: $t.gamma.title }"
//        println "t class: "+t.getClass()
//        println "t methods: "+t.getClass().getMethods().sort().join('\n')
        if (stop) return
        t.start()

        def delay=0
        if (fixedPause>0) delay=fixedPause
        else if (rndPause > 0) delay = rnd.nextInt(rndPause)
        sleep(delay)
    }
    if (!stop) println '>> ALL THREADS STARTED. <<'
}

println 'press <Enter> to stop, or "slow"<Enter> for slow stop.'
def line=System.console().readLine()

if (line.trim().toLowerCase().startsWith("s")) {
    // slow stop
    println '>> SLOW STOP REQUESTED [wait for threads to close] <<'
    Engine.stop=true
    threads.each { it.join() }
}
else {
    println ">> STOP! <<"
    player.allNotesOff()
}
player.close()
System.exit(0)