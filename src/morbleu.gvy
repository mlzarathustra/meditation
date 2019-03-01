import midi.*
import static midi.MlzMidi.*
import midi.Engines
import static midi.Engines.rnd

import midi.BasicEngines

//  Engines will all need to be initialized here, 
//  as each is responsible for adding their closures to
//  Engines.map
BasicEngines.init()

File gammaDir = new File('gamma')

def gammaName='c-major' // Accept list? 
if (args) gammaName = args[0]
else {
    println 'specify a file in the gamma directory'
    files = []; i=1
    gammaDir.eachFile {
        files << it.name
        println "$i. ${it.name.replaceFirst(/.gvy$/,'')}"
        ++i
    }
    print 'Enter a number or <Enter> to quit: '
    def inp=System.console().readLine()
    if (!inp) System.exit(0)
    int idx=inp as int
    if (idx>0 && idx<=files.size()) {
        gammaName = files[idx-1]
    }
    else {
        println 'Invalid selection.'
        System.exit(-1)
    }
}
if (!gammaName.endsWith('.gvy')) gammaName += '.gvy'
println "playing $gammaName"

def gamma = Eval.me(new File(gammaDir, gammaName).text)

//  TODO - if (gamma instanceof Map) look for inherit, gamma properties


inheritors = [
    override: {k,v,g -> g[k]=v},
    passive: {k,v,g -> if (g[k]==null) g[k]=v},
    add: {k,v,g -> 
        if (g[k]==null) g[k]=v
        else g[k] += v
    }
]



// gamma should be able to contain or specify player

player=new Player('gervil') // the microsoft GS synth
//player=new Player('828')
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
