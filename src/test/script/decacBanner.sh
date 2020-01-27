#! /bin/sh

echo Test 1
decac -b

echo Test 2
decac -b -a

echo Test 3
decac -b -p

echo Test 4
decac -b -v

echo Test 5
decac -b -r 5

echo Test 6
decac -b -P

echo Test 7
decac -b ../syntax/valid/created/readint.deca

echo Test 8
decac -a -b ../syntax/valid/created/readint.deca

echo Test 9
decac -p -b

echo Test 10
decac -v -b

echo Test 11
decac -r 5 -b

echo Test 12
decac -P -b

echo Test 13
decac -d -b

echo Test 14
decac -d -d -d -b

echo Test 15
decac -d -r 8 -P -b ../syntax/valid/created/assign.deca

echo Test 16
decac -a -b ../syntax/valid/created/divide.deca

echo Test 17
decac -p -b ../syntax/valid/created/while.deca

echo Test 18
decac -a -d -P -b ../syntax/valid/created/ifelse.deca

echo Test 19
decac -b -a -d -P ../syntax/valid/created/readint.deca

echo Test 20
decac ../syntax/valid/created/readint.deca -b 
