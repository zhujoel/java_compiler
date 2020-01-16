#! /bin/sh
DECAC_HOME=$(cd "$(dirname "$0")"/../../../../ && pwd)

basic-lex.sh
basic-context.sh
./launchers/validor_synt
basic-decac.sh
./launchers/chain_test_context "$DECAC_HOME"/src/test/deca/context/{valid, invalid}/created/*
./launchers/validor_lex
./launchers/invalidor_lex
basic-gencode.sh
basic-synt
common-tests
perso.sh
