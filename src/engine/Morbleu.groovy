package engine

import midi.Player

import static midi.MlzMidi.toMidiNumList

//  Superclass for engines that are represented by classes
//  (as opposed to those which are plain Closures)
//
class Morbleu {
    static boolean stop=false
    static def map=[:]  // initialized in morbleu.gvy
    static Player player  // rename to defaultPlayer and create an instance
                            //  variable, to support multiple players.

    static threads=[]

    //   inheritance

    static inheritors = [
        override: {k,v,g -> g[k]=v},
        passive: {k,v,g -> if (g[k]==null) g[k]=v },
        add: {k,v,g ->
            if (g[k]==null) g[k]=v
            else g[k] += v
        }
    ]

    static inherit = { inp ->
        if (inp.inherit) {
            for (def type : inp.inherit.keySet()) {
                //println type
                def toInherit = inp.inherit[type]
                for (def k : toInherit.keySet()) {
                    //println "k: $k"
                    inp.gamma.each { g->
                        inheritors[type](k,toInherit[k],g)
                    }
                }
            }
        }
    }
    //
    //

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

        if (engine instanceof Class) {
            // the 'class' engines will need to manage their own channels
            def t = engine.getConstructors()[0]
                    .newInstance( g, player )
            threads << t
        }
        else {
            g.channel.each { c->
                // it's a closure: create a thread for each channel
                Thread t=new GammaThread(c,g,player,engine)
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
    static def assertValid = { g->
        def err = { ga, msg ->
            println "Gamma '$ga.title': $msg"
            System.exit(0)
        }
        def engine = map[g.engine]
        if (!engine) err(g, "Engine $g.engine not found.")

        if ( engine instanceof Class ) {
            def t = engine.getConstructors()[0].newInstance(g, player)
            if (!t.isValid(g)) {
                println "Gamma '$g.title' is invalid: $t.errMsg"
                System.exit(0)
            }
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
            if (!g.channel.every { it instanceof Integer &&
                    it >= 1 && it <= 16}) {
                err(g, "channel out of range (1 <= channel <= 16)")
            }
        }


    }




}