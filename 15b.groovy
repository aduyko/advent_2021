// Find cheapest path from top left to bottom right of input number grid moving orthogonally, if the grid is 5x the size and every repeating 5x5 block increases by 1 and rolls over to 1 from 9
// I wound up googling to be reminded of Djikstra's algorithm because I got impatient and didn't want to think it through!!!!
// This one doesn't work with a naive Djikstra it gets too complex hard
// I googled a bit and found that we could use a priority queue to optimize a little and that seemed to solve the trick, along with adding an exit condition if we hit the last node
inputFile = "inputs/15.txt"
input = []
pathQueue = []
bestPaths = [:]
bestScore = 0

new File(inputFile).eachLine { line ->
  // This feels unintuitive but since strings are array indexable we can just do this
  input.add(line.collect { it as int })
}

numRows = input.size()
4.times { iteration ->
  numRows.times { rowIdx ->
    input << input[rowIdx].collect { it ->
      it += iteration + 1
      if (it > 9) {
        it -= 9
      }
      return it
    }
  }
}
numRows = input.size()
originalInput = input.clone()
4.times { iteration ->
  numRows.times { rowIdx ->
    input[rowIdx] += originalInput[rowIdx].collect { it ->
      it += iteration + 1
      if (it > 9) {
        it -= 9
      }
      return it
    }
  }
}

// get some seed values for bounds
(1..<input.size()).each {
  bestScore += input[0][it]
}
(1..<input.size()).each {
  bestScore += input[it][input.size()-1]
}

lastNode = [input.size()-1, input.size()-1]

pathQueue << [[0,0], 0]
bestPaths[[0,0]] = [[],0]
while (pathQueue.size() > 0) {
  node = pathQueue.min { it[1] }
  pathQueue.remove(node)

  node = node[0]
  path = bestPaths[node][0]
  score = bestPaths[node][1]

  nodesToCheck = []
  if (node[0] > 0 && !path.contains([node[0]-1,node[1]])) {
    nodesToCheck << [node[0]-1,node[1]]
  }
  if (node[1] > 0 && !path.contains([node[0],node[1]-1])) {
    nodesToCheck << [node[0],node[1]-1]
  }
  if (node[0] < input.size() - 1 && !path.contains([node[0]+1,node[1]])) {
    nodesToCheck << [node[0]+1,node[1]]
  }
  if (node[1] < input[0].size() - 1 && !path.contains([node[0],node[1]+1])) {
    nodesToCheck << [node[0],node[1]+1]
  }

  nodesToCheck.each { nextNode ->
    if (!path.contains(nextNode)) {
      newScore = score + input[nextNode[0]][nextNode[1]]
      newPath = path.clone()
      newPath << nextNode
      if (bestPaths.containsKey(nextNode)) {
        if (bestPaths[nextNode][1] > newScore) {
          bestPaths[nextNode] = [
            newPath,
            newScore
          ]
          if (!pathQueue.contains(nextNode) && newScore <= bestScore && nextNode != lastNode) {
              pathQueue << [nextNode, newScore]
          }
        }
      } else {
        bestPaths[nextNode] = [
          newPath,
          newScore
        ]
        if (newScore <= bestScore && nextNode != lastNode) {
          pathQueue << [nextNode, newScore]
        }
      }
    }
  }
}

print bestPaths[input.size()-1,input.size()-1][1]
