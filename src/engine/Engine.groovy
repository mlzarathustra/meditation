package engine

import midi.Player

import static midi.MlzMidi.toMidiNumList

class GammaThread extends Thread {
    def channel, gamma, player, closure
    GammaThread(channel, gamma, player, closure) {
        this.channel=channel
        this.gamma=gamma
        this.player=player
        this.closure=closure
    }
    void run() {
        closure(channel,gamma,player)
    }

}


//  Superclass for engines that are represented by classes
//  (as opposed to those which are plain Closures)
//
class Engine extends Thread {
    static boolean stop=false 
    static def map=[:]  // initialized in morbleu.gvy
    static Player player

    static def getSeed = {
        long t=new Date().getTime()
        def lo=t%1000
        t /= 1000
        def hi=t%1000
        lo**6 + 3*hi**2
    }
    static Random rnd=new Random(getSeed())
    static def rndInt(pair) {
        pair.min + pair.var ? rnd.nextInt(pair.var) : 0
    }

    //
    //
    //
    String errMsg=''

    // overload with validity check
    boolean isValid(g) {
        errMsg = ''
        return true
    }



    static final def settingsFilename = 'morbleu-settings.gvy'

    static def loadGamma = { args->
        File gammaDir = new File('gamma')

        String gammaName
        if (args) gammaName = args[0]
        else {
            println 'Specify a gamma from below:'
            def files = []; int i=1
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

        //  looks for <name> and <name>.gvy
        //  as given, and in the gamma directory.
        File gammaFile = new File(gammaName)
        if (!gammaFile.exists()) {
            gammaFile = new File(gammaDir, gammaName)
            if (!gammaFile.exists()) {
                gammaFile = new File(gammaName+'.gvy')
                if (!gammaFile.exists()) {
                    gammaFile = new File(gammaDir, gammaName+'.gvy')
                    if (!gammaFile.exists()) {
                        println "Cannot find $gammaName"
                        System.exit(0)
                    }
                }
            }
        }

        println "playing $gammaName"
        println gammaFile
        Eval.me(gammaFile.text)
    }


    static def getSettings = {
        def f = new File(settingsFilename)
        if (f.isFile()) return Eval.me(f.text)
        return [:]
    }

    static def gammaThreads(g) {
        def threads = []
        def engine = map[g.engine]
        //println " >> Engine class: "+engine.getClass()+" <<\n"

        g.channel.each { c->
            if (engine instanceof Class) {
                // println 'Class gamma'
                def t =engine.getConstructors()[0]
                        .newInstance( g, player )
                threads << t
            }
            else {
                // println 'Closure gamma'
                Thread t=new GammaThread(c,g,player,engine)
                // println "t class : "+t.getClass()
                threads << t
            }
        }
        return threads
    }


    //  may adjust g slightly.
    //
    //  For the classes, we'll instantiate the thread objects here
    //  to avoid Engine.isValid() being static, thus impossible to overload
    //
    //  TODO - return Thread object, or list of them, one for each channel;
    //           NOTE: we need the Player object to construct the engine.
    //           Keeping in mind we may want to let the engine decide which
    //           player it will use. Maybe: make player an element of Engine
    //
    static def assertValid = { g->
        def err = { ga, msg ->
            println "Gamma '$ga.title': $msg"
            System.exit(0)
        }
        def engine = Engine.map[g.engine]
        if (!engine) err(g, "Engine $g.engine not found.")

        if ( engine instanceof Class ) {
            def t = engine.getConstructors()[0].newInstance(g, player)
            if (!t.isValid(g)) {
                println "Gamma $g.title is invalid."
                System.exit(0)
            }
            return gammaThreads(g)
        }
        else {

            //  generic assert

            //  checking: pitch/pitches, patches, channel
            //
            if (!g.pitches) err(g, "missing 'pitches' property")
            g.pitches = (g.pitches instanceof String) ?
                    toMidiNumList(g.pitches) :
                    g.pitches
            if (g.pitches instanceof Integer) g.pitches = [g.pitches]
            if (!g.pitches.every { it instanceof Integer}) {
                err(g, "pitches must be an integer, string of note names, \n" +
                        "    or list of integers.")
            }

            if (g.patches == null && g.patch == null) {
                err(g, "cannot find 'patch' or 'patches' properties")
            }
            if (g.patch) g.patches = [g.patch]
            else if (! (g.patches instanceof List)) g.patches = [g.patches]
            if (!g.patches.every { it instanceof Integer}) {
                err(g, "patches must be either an integer or a list of integers.")
            }

            if (!g.channel) err(g, "'channel' property undefined")
            return gammaThreads(g)

        }


    }




}