#Defines where source files are.
SRCS := $(wildcard *.java)

default: sentinel

clean:
	$(RM) *.class
	$(RM) *.huff
	$(RM) *.zipper

sentinel: $(SRCS)
	javac $(SRCS)
