#! /bin/sh
DECAC_HOME=$(cd "$(dirname "$0")"/../../../../ && pwd)

CP_FILE="$DECAC_HOME"/target/generated-sources/classpath.txt

CP="$DECAC_HOME"/target/generated-classes/cobertura:"$DECAC_HOME"/target/test-classes/:"$DECAC_HOME"/target/classes/:$(cat "$CP_FILE")

cd $DECAC_HOME

decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/assign.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/assign.deca
decac -a -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/assign.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare10.deca
decac -a -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst15.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int.deca
decac -v -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare11.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst17.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class7.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/while.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus9.deca
decac -a -r 5 -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst13.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool5.deca
decac -a -P -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float13.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign14.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus8.deca
decac -a -r 8 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus5.deca
decac -a -r 10 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/divide.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float13.deca
decac -a -d "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/unaryMinus.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class2.deca
decac -a -d -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float12.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst11.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool4.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare17.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare17.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/greater.deca
decac -a -P -n "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/divide.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus9.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus1.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare1.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/decac_test.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst3.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca
decac -a -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult7.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult2.deca
decac -a -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign9.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/assign.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/unaryMinus.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/voidMain.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/divide.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/divide.deca
decac -a -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus9.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/while.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool8.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/assign.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool10.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float12.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst10.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class6.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class7.deca
decac -P -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float7.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float9.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare11.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare13.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare14.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare15.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare16.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare1.deca
decac -a -P -r 6 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst13.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult9.deca
decac -a -P -r 7 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float12.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/decac_test.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolean.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool3.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult6.deca
decac -a -P -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus10.deca
decac -a -P -r 16 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst16.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult9.deca
decac -a -r 16 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst21.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int6.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus8.deca
decac -a -r 8 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool9.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool7.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/inst8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus5.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign4.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float7.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/float8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool7.deca
decac -a -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca
decac -a -r 5 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca
decac -a -r 6 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca
decac -a -r 6 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/class5.deca
decac -a -P -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc.deca
decac -a -n -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc.deca
decac -a -r 4 -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/abc.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus1.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus2.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/mult8.deca
decac -a -n -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/multiply.deca
decac -a -n -r 5 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/multiply.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus3.deca
decac -a -n -r 5 -P "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/multiply.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus10.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus3.deca
decac -a "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/ifThenElse.deca
decac -v "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/ifThenElse.deca
decac -p "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/ifThenElse.deca
decac -a -P -r 4 "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/ifThenElse.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/compare17.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/int8.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/bool13.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus3.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus4.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/plus5.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/unaryMinus.deca "$DECAC_HOME"/Projet_GL/src/test/deca/codegen/valid/created/boolassign4.deca
