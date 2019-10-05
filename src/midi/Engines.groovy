package midi

//  Superclass for all collections of engines
//
class Engines {
    static boolean stop=false 
    static def map=[:]

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

}