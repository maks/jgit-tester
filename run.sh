DIR=`dirname $0`
CLASSPATH=$DIR/bin:$DIR/lib/jsch-0.1.45.jar:$DIR/lib/org.eclipse.jgit-1.2.0.201112221803-r.jar:$DIR/lib/org.eclipse.jgit-1.2.0.201112221803-r-javadoc.jar

java -cp $CLASSPATH GitTester $@
