JFLAGS = -g
JC = javac -encoding UTF-8
JAVA = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Main.java Model.java View.java Tools.java ColourPalette.java Thickness.java Canvas.java LineComponent.java Line.java ColorChooser.java FileSaver.java FileOpener.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run: default
	$(JAVA) -cp . Main
