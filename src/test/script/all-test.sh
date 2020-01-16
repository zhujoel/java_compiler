#! /bin/sh
DECAC_HOME=$(cd "$(dirname "$0")"/../../../ && pwd)

basic-lex.sh
basic-context.sh
"$DECAC_HOME"/src/test/script/launchers/validor_synt
basic-decac.sh
"$DECAC_HOME"/src/test/script/launchers/chain_test_context "$DECAC_HOME"/src/test/deca/context/{valid,invalid}/created/*
"$DECAC_HOME"/src/test/script/launchers/validor_lex
"$DECAC_HOME"/src/test/script/launchers/invalidor_lex
#basic-gencode.sh
#basic-synt
#common-tests
#perso.sh
