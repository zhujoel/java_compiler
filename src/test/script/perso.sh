cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"
test_synt src/test/deca/syntax/invalid/provided/simple_lex.deca
