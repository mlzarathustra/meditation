
import common.Lib
println Lib.x
Eval.me('''
    import common.Lib
    Lib.x=3
''')
println Lib.x
