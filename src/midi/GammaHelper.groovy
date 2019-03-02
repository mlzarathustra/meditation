package midi

class GammaHelper {

    static def loadGamma = { args->
        File gammaDir = new File('gamma')

        def gammaName
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
        if (!gammaName.endsWith('.gvy')) gammaName += '.gvy'
        File gammaFile = new File(gammaDir, gammaName)
        if (!gammaFile.exists()) {
            println "$gammaFile cannot be found."
            System.exit(-1)
        }
        println "playing $gammaName"
        Eval.me(gammaFile.text)
    }



}