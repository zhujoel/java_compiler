#!/bin/sh

rm 100variables.deca
rm 1000variables.deca
rm 10000variables.deca


echo "compilation et execution d'un fichier de 100 variables"
touch 100variables.deca
echo "{" >> 100variables.deca
for i in {1..100}
do
    echo "  int a$i = 1;" >> 100variables.deca
done

echo "println(\"execution du programme de 100 variables sans erreur\");" >> 100variables.deca
echo "}" >> 100variables.deca

echo "compilation et execution d'un fichier de 1000 variables"
touch 1000variables.deca
echo "{" >> 1000variables.deca
for i in {1..1000}
do
    echo "  int a$i = 1;" >> 1000variables.deca
done

echo "println(\"execution du programme de 1000 variables sans erreur\");" >> 1000variables.deca
echo "}" >> 1000variables.deca

echo "compilation et execution d'un fichier de 10000 variables"
touch 10000variables.deca
echo "{" >> 10000variables.deca
for i in {1..10000}
do
    echo "  int a$i = 1;" >> 10000variables.deca
done

echo "println(\"execution du programme de 10000 variables sans erreur\");" >> 10000variables.deca
echo "}" >> 10000variables.deca

decac -d -P 100variables.deca 1000variables.deca 10000variables.deca
