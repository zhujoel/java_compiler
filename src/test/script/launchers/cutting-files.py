#! /usr/bin/env python3

import sys
import os

f = open(sys.argv[1], 'r')
lines = f.readlines()
f.close()

# On coupe le fichier quand on rencontre le caractère ':'
for i in range(len(lines)):
    lines[i] = lines[i].split(":",1)[0] + "\n"

f = open(sys.argv[1], 'w')
f.writelines(lines)
f.close()
