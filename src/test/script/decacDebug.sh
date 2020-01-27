#! /bin/sh

echo Test 1
decac -p -d ../syntax/valid/created/while.deca
echo Test 2
decac -p -d -d ../syntax/valid/created/while.deca
echo Test 3
decac -p -d -d -d ../syntax/valid/created/while.deca
echo Test 4
decac -p -d -d -d -d ../syntax/valid/created/while.deca
echo Test 5
decac -p -d -r 5 ../syntax/valid/created/while.deca
echo Test 6
decac -a -d ../syntax/valid/created/divide.deca
