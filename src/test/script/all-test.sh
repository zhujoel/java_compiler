#! /bin/sh
DECAC_HOME=$(cd "$(dirname "$0")"/../../../ && pwd)

"$DECAC_HOME"/src/test/script/basic-lex.sh
"$DECAC_HOME"/src/test/script/basic-context.sh
"$DECAC_HOME"/src/test/script/launchers/validor_synt
"$DECAC_HOME"/src/test/script/basic-decac.sh
"$DECAC_HOME"/src/test/script/launchers/chain_test_context "$DECAC_HOME"/src/test/deca/context/{valid,invalid}/created/*
"$DECAC_HOME"/src/test/script/launchers/validor_lex
"$DECAC_HOME"/src/test/script/launchers/invalidor_lex
"$DECAC_HOME"/src/test/script/launchers/invalidor_synt
"$DECAC_HOME"/src/test/script/launchers/validor_synt_objet
"$DECAC_HOME"/src/test/script/launchers/validor_cod
#"$DECAC_HOME"/src/test/script/basic-gencode.sh
#"$DECAC_HOME"/src/test/script/basic-synt
#common-tests
#"$DECAC_HOME"/src/test/script/perso.sh
