#!/bin/sh

rm ands.deca
rm ands.deca
rm ands.deca


echo "compilation et execution d'un fichier de 100 variables"
touch ands.deca
echo "{" >> ands.deca
for i in {1..10000}
do
    echo "  true && true;" >> ands.deca
done

echo "println(\"execution du programme de 100 variables sans erreur\");" >> ands.deca
echo "}" >> ands.deca
echo "fichier généré."
