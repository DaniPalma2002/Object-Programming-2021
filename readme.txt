export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/ggc-core/ggc-core.jar:$(pwd)/ggc-app/ggc-app.jar
java -Dui="swing" ggc.app.App
