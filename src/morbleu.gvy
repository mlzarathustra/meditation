import engine.Breathe
import engine.Ocean
import midi.*
import static engine.Morbleu.*
import static midi.MlzMidi.save

import engine.BasicEngines

//  Engines all need to be initialized here,
//  as each is responsible for adding their
//  closure or class to engines.Morbleu.map.
//
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

File midiOutFile = null
nonOptArgs = []
for (int i=0; i<args.length; ++i) {
    if (args[i] == '-midi-out-file') {
        ++i
        try {
            midiOutFile = new File(args[i])
            if (midiOutFile.exists()) {
                println "$midiOutFile already exists. Please specify a new file."
                System.exit(-1)
            }
            midiOutFile.createNewFile()
        }
        catch (ex) {
            println "Couldn't create MIDI file:\n$ex"
            System.exit(-1)
        }
    }
    else {
        nonOptArgs << args[i]
    }
}


// /// // /// // /// // /// // /// // /// // /// // /// // /// // /// // /// // ///
// /// // /// // /// // /// // /// // /// // /// // /// // /// // /// // /// // ///

def gamma

def inp = loadGamma(nonOptArgs) // it only loads the 1st one
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

    inherit(inp)
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
if (midiOutFile) player.record()
//


//  basic sanity check
if (!gamma) {
    println "Nothing to play!"
    System.exit(0)
}

gamma.each { g -> assertValid(g) }

//  Create a thread for each channel specified
//println "gamma: $gamma"
threads = gamma.collect { g->gammaThreads(g) }.flatten()

//println "threads: $threads"
// the pause between the commencement of each track
def defaultPause = 750 // 3/4 of a second
def fixedPause=inp.fixedPause?:0
def rndPause = (fixedPause>0) ? 0 : (inp.rndPause?:0)

Thread.start {
    threads.each { t ->
        println "Starting { Engine: $t.gamma.engine; Title: $t.gamma.title }"
        if (stop) return
        t.start()

        def delay=0
        if (fixedPause>0) delay=fixedPause
        else if (rndPause > 0) delay = rnd.nextInt(rndPause)
        else delay = defaultPause
        sleep(delay)
    }
    if (!stop) println '>> ALL THREADS STARTED. <<'
}

println 'press <Enter> to stop, or "slow"<Enter> for slow stop.'
def line=System.console().readLine()

if (line.trim().toLowerCase().startsWith("s")) {
    // slow stop
    println '>> SLOW STOP REQUESTED [wait for threads to close] <<'
    stop=true
    threads.each { it.join() }
}
else {
    println ">> STOP! <<"
    player.allNotesOff()
}
player.close()

if (midiOutFile) {
    save(player.sequence, midiOutFile)
}

System.exit(0)