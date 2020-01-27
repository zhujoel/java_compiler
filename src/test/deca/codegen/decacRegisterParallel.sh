#! /bin/sh

decac -r 4 -P -a ../syntax/valid/created/assign.deca ../syntax/valid/created/assign_assign_plus.deca
decac -r 5 -P -a ../syntax/valid/created/assign_assign_plus.deca ../syntax/valid/created/assign_string.deca
decac -r 6 -P -a ../syntax/valid/created/assign_string.deca ../syntax/valid/created/cast.deca
decac -r 7 -P ../syntax/valid/created/cast.deca ../syntax/valid/created/cast_multiple.deca
decac -r 8 -P ../syntax/valid/created/cast_multiple.deca ../syntax/valid/created/class_appel.deca
decac -r 9 -P ../syntax/valid/created/class_appel.deca ../syntax/valid/created/print.deca
decac -r 10 -P ../syntax/valid/created/class_appel_multiple.deca ../syntax/valid/created/minus.deca
decac -r 11 -P ../syntax/valid/created/class_attribut.deca ../syntax/valid/created/just-an-int.deca
decac -r 12 -P ../syntax/valid/created/class_attribute_init.deca ../syntax/valid/created/plus_plus.deca
decac -r 13 -P ../syntax/valid/created/class_attribute_method.deca ../syntax/valid/created/print_lots_of_argument.deca
decac -r 14 -P ../syntax/valid/created/class_attribut_visibility.deca ../syntax/valid/created/decac_test.deca
decac -r 15 -P ../syntax/valid/created/class_complex.deca ../syntax/valid/created/ifthenelse++.deca
decac -r 16 -P ../syntax/valid/created/class_extends.deca ../syntax/valid/created/class_simple.deca ../syntax/valid/created/lowerorequals.deca
