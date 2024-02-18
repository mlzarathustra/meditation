package engine

import static engine.Morbleu.*

import midi.Player

class Breathe extends Engine {
    Player player
    def gamma  // could be single or list
    boolean stop = false

    def localThreads = []

    //  NB. when stopping threads, remove from threads list

    Breathe(g,p) { gamma=g; player=p }

    boolean isValid(g) {
        if (!g.gamma) {
            errMsg = 'missing "gamma" section'
            return false
        }
        if (!g.timing) {
            errMsg = 'missing "timing" section'
            return false
        }
        return true
    }

    void run() {
        if (gamma.gamma instanceof Map) gamma.gamma = [gamma.gamma]
        gamma.gamma.each { g-> assertValid(g) }
        inherit(gamma)

        //   TODO - encapsulate this pause logic somehow
        //          ...it also appears in morbleu.gvy
        def defaultPause = 750 // 3/4 of a second
        def fixedPause=gamma.fixedPause?:0
        def rndPause = (fixedPause>0) ? 0 : (gamma.rndPause?:0)

        //gamma.gamma.each { println it }

        for (;;) {
            // println 'Breathe loop top'
            localThreads = gamma.gamma.collect { g -> gammaThreads(g) }.flatten()
            localThreads.each { t->
                if (stop) return
                t.start()

                def delay=0
                if (fixedPause>0) delay=fixedPause
                else if (rndPause > 0) delay = rnd.nextInt(rndPause)
                else delay = defaultPause
                sleep(delay)
            }
            // println "Breathe threads started"

            if (stop) break
            def var= gamma.timing.hold.var == 0 ? 0 :
                    rnd.nextInt(gamma.timing.hold.var)

            // println "Breathe sleeping for ${gamma.timing.hold.min + var}"
            sleep(gamma.timing.hold.min + var)
            if (stop) break

            gamma.gamma.each { g-> g.stop=true }
            // println "before join"
            localThreads.each { it.join() }
            // println "after join"
            localThreads.clear()

            // println "Breathe - all stop set to true"


            var=gamma.timing.pause.var == 0 ? 0 :
                rnd.nextInt(gamma.timing.pause.var)

            // println "Breathe sleeping for ${gamma.timing.pause.min + var}"
            sleep(gamma.timing.pause.min + var)

            gamma.gamma.each { g-> g.stop=false }
        }
        gamma.gamma.each { g-> g.stop=true }
        localThreads.each { it.join() }


    }


}
