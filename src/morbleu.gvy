import midi.*
import static midi.MlzMidi.*
import midi.Engines
import static midi.Engines.rnd

import midi.BasicEngines

//  Engines will all need to be initialized here, 
//  as each is responsible for adding their closures to
//  Engines.map
BasicEngines.init()

def gammaName='c-major' // Accept list? 
if (args) gammaName = args[0]

def gamma = Eval.me(new File("gamma/$gammaName"+'.gvy').text)

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
