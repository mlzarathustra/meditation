
def getSeed() {
    long t=new Date().getTime()
    def lo=t%1000
    t /= 1000
    def hi=t%1000
    lo**6 + 3*hi**2
}

[*1..10].collect { Thread.sleep(10); getSeed()  }


