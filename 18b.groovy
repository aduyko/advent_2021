// Do snail homework, follow a bunch of steps on nested pairs of numbers. cool
inputFile = "inputs/18.txt"
input = []

new File(inputFile).eachLine { line ->
  input << line
}

class Node {
  def parent
  def left
  def right
  def depth
}

def parseInput(s, parentNode) {
  depth = 1
  if (parentNode) {
    depth = parentNode.depth + 1
  }
  def newNode = new Node(parent:parentNode, depth:depth)

  for (def i=0; i<s.size(); i++) {
    def nextValue = ""
    if (s[i] == "[") {
      parsedValues = parseInput(s[i+1..-1], newNode)
      nextValue = parsedValues[0]
      i += parsedValues[1]
    } else if (s[i] ==~ /\d/) {
      nextValue = s[i] as int
    } else if (s[i] == "]") {
      return [newNode, i+1]
    }
    if (nextValue != "") {
      if (newNode.left != null) {
        newNode.right = nextValue
      } else {
        newNode.left = nextValue
      }
    }
  }
  return newNode
}

def enqueue(node, queue) {
  if (!(node.left instanceof Node) && !(node.right instanceof Node)) {
    queue << node
  } else {
    if (node.left instanceof Node) {
      enqueue(node.left, queue)
    } else {
      queue << node
    }
    if (node.right instanceof Node) {
      enqueue(node.right, queue)
    } else {
      queue << node
    }
  }
}

def reduce(queue) {
  checkAgain = true
  while (checkAgain) {
    checkAgain = false
    newQueue = []
    queue.eachWithIndex { node,idx ->
      if (!checkAgain) {
        // Explode
        if (node.depth == 5) {
          checkAgain = true
          if (idx > 0) {
            firstLeft = idx-1
            if (queue[firstLeft].right instanceof Node) {
              queue[firstLeft].left += node.left
            } else {
              queue[firstLeft].right += node.left
            }
          }
          if (idx+1 < queue.size()) {
            firstRight = idx+1
            if (queue[firstRight].left instanceof Node) {
              queue[firstRight].right += node.right
            } else {
              queue[firstRight].left += node.right
            }
          }
          enqueueThisNode = false
          if (node.parent.left instanceof Node && node.parent.right instanceof Node) {
            enqueueThisNode = true
          }
          if (node.parent.left == node) {
            node.parent.left = 0
          } else {
            node.parent.right = 0
          }
          if (enqueueThisNode) {
            newQueue << node.parent
          }
        } else {
          newQueue << node
        }
      } else {
        newQueue << node
      }
    }

    queue = newQueue
    newQueue = []

    if (checkAgain) { continue }

    queue.eachWithIndex { node,idx ->
      if (!checkAgain) {
        if (!(node.left instanceof Node) && node.left >= 10) { //split
          checkAgain = true
          def newNode = new Node(
            parent: node,
            depth: node.depth + 1,
            left: Math.floor(node.left/2) as int,
            right: Math.ceil(node.left/2) as int
          )
          node.left = newNode
          newQueue << newNode
          if (!(node.right instanceof Node)) {
            newQueue << node
          }
        } else if (!(node.right instanceof Node) && node.right >= 10) { //split
          checkAgain = true
          def newNode = new Node(
            parent: node,
            depth: node.depth + 1,
            left: Math.floor(node.right/2) as int,
            right: Math.ceil(node.right/2) as int
          )
          node.right = newNode
          if (!(node.left instanceof Node)) {
            newQueue << node
          }
          newQueue << newNode
        } else {
          newQueue << node
        }
      } else {
        newQueue << node
      }
    }
    queue = newQueue
  }
  return queue
}

def increaseDepth(node) {
  node.depth += 1
  if (node.left instanceof Node) {
    increaseDepth(node.left)
  }
  if (node.right instanceof Node) {
    increaseDepth(node.right)
  }
}

def add(node1, node2) {
  increaseDepth(node1)
  increaseDepth(node2)
  newNode = new Node(
    depth: 1,
    left: node1,
    right: node2
  )
  node1.parent = newNode
  node2.parent = newNode
  def queue = enqueue(newNode, [])
  reduce(queue)
  return newNode
}

def magnitude(node) {
  sum = 0
  if (node.left instanceof Node) {
    sum += (3*magnitude(node.left))
  } else {
    sum += (3*node.left) 
  }
  if (node.right instanceof Node) {
    sum += (2*magnitude(node.right))
  } else {
    sum += (2*node.right)
  }
}

maxSum = 0
(0..<input.size()).each { idx1 ->
  (0..<input.size()).each { idx2 ->
    if (idx1 != idx2) {
      sum1 = parseInput(input[idx1][1..-2], null)
      sum2 = parseInput(input[idx2][1..-2], null)
      sum = magnitude(add(sum1, sum2))
      if (sum > maxSum) {
        maxSum = sum
      }
    }
  }
}

print maxSum
