package midi

import midi.Engines

class GammaHelper {

    static final def settingsFilename = 'morbleu-settings.gvy'

    static def loadGamma = { args->
        File gammaDir = new File('gamma')

        String gammaName
        if (args) gammaName = args[0]
        else {
            println 'specify a file in the gamma directory'
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
        Eval.me(gammaFile.text)
    }


    static def getSettings = {
        def f = new File(settingsFilename)
        if (f.isFile()) return Eval.me(f.text)
        return [:]
    }

    static def assertValid = { g->
        def err = { ga, msg ->
            println "Gamma $ga.title $msg"
            System.exit(0)
        }
        def engine = Engines.map[g.engine]
        if (!engine) err(g, "Engine $g.engine not found.")

        //  KLUDGE !
        //  If the engine were its own type, rather than a closure,
        //  it could report on which items it needs.
        if (g.engine != 'ocean') {
            if (!g.pitches) err(g, "missing 'pitches' property")

            if (g.patches == null && g.patch == null) {
                err(g, "cannot find 'patch' or 'patches' properties")
            }
        }

        if (!g.channel) err(g, "'channel' property undefined")
    }




}