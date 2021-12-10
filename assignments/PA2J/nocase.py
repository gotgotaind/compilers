kw=['true','false','class', 'else', 'fi', 'if', 'in', 'inherits', 'isvoid', 'let', 'loop', 'pool', 'then', 'while', 'case', 'esac', 'new', 'of', 'not']
for w in kw:
    for c in w:
        print(f'[{c}{c.upper()}]',end='')
    print()