// Do snail homework, follow a bunch of steps on nested pairs of numbers. cool
// This is a bad approach to this problem
inputFile = "inputs/18test.txt"
input = []

new File(inputFile).eachLine { line ->
  input << parseInput(line)
}

def parseInput(s) {
  def stack = []
  for (def i=0; i<s.size(); i++) {
    if (s[i] == "[") {
      parsedValues = parseInput(s[i+1..-1])
      stack << parsedValues[0]
      i += parsedValues[1]
    } else if (i+2 < s.size() && s[i..i+2] ==~ /\d,\d/) {
      stack << (s[i] as int)
      stack << (s[i+2] as int)
      i += 2
    } else if (s[i] ==~ /\d/) {
      stack << [s[i] as int]
    } else if (s[i] == "]") {
      return [stack, i+1]
    }
  }
  return stack
}

def getNext(list, level) {
  println("List: ${list}, level:${level}")
  if (list[0][0] instanceof Integer) {
    def item = list[0]
    list.removeAt(0)
    return item
  } else {
    return getNext(list[0], level+1)
  }
}

def cleanUp(list) {
  for (def i=list.size()-1;i>=0;i--) {
    if (list[i] instanceof List) {
      if (list[i].size() > 0) {
        cleanUp(list[i])
      }
      if (list[i].size() == 0) {
        list.removeAt(i)
      }
    }
  }

  return list
}


//abandoning ship at this point, I think it would be easier to write a tree, then queue up all the nodes than to do this insane deep copy of everything EXCEPT the leaves each time
def cloneList(list) {
  def newList = []
}

def doMath(list) {
  def done = false
  while (!done) {
    done = true
    popList = list.clone()
    popList.removeAt(0)
    println "List: ${list}"
    println "poplist: ${popList}"
    return
    queue = []
    while (popList.size() > 0) {
      queue << getNext(popList, 0)
      println (popList)
      cleanUp(popList)
    }
    println "List: ${list}"
    println "poplist: ${popList}"
    println "Queue: ${queue}"
  }
}

input.each { println it }
println "ok"

println input[0]
println doMath(input[0])
