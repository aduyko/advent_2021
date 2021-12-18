// Find cheapest path from top left to bottom right of input number grid moving orthogonally 
// I wound up googling to be reminded of Djikstra's algorithm because I got impatient and didn't want to think it through!!!!
inputFile = "inputs/15.txt"
input = []
pathQueue = [] as Queue
bestPaths = [:]

new File(inputFile).eachLine { line ->
  // This feels unintuitive but since strings are array indexable we can just do this
  input.add(line.collect { it as int })
}

pathQueue.offer([0,0])
bestPaths[[0,0]] = [[],0]
while (pathQueue.size() > 0) {
  node = pathQueue.poll()
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
          if (!pathQueue.contains(nextNode)) {
            pathQueue.offer(nextNode)
          }
        }
      } else {
        bestPaths[nextNode] = [
          newPath,
          newScore
        ]
        pathQueue.offer(nextNode)
      }
    }
  }
}

print bestPaths[input.size()-1,input.size()-1][1]
