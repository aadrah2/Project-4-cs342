JFLAGS = -g
JC = javac -Xlint:unchecked
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Tester.java \
	Game.java \
	Place.java \
	Direction.java \
	Artifact.java \
	Character.java \
	NPC.java \
	Player.java \
	FileHandle.java \
	Move.java \
	DecisionMaker.java \
	Currency.java \
	Battle.java \
	Money.java \
	Merchant.java \
	Match.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

