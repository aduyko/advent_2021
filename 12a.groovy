// Create a graph from the input, from start to end nodes, how many paths are there that visit large (capital letter) caves any number of times but small (lowercase letter) caves at most once?

inputFile = "inputs/12.txt"
// We can format this like { "ab" => ["cd", "fg"] }
nodes = [
  "start": [],
  "end": []
]

new File(inputFile).eachLine { line ->
  inputNodes = line.split("-")
  inputNodes.each {
    if (!nodes.containsKey(it)) {
      nodes[it] = []
    }
  }
  if (inputNodes[0] == "start") {
    nodes["start"].add(inputNodes[1])
  } else if (inputNodes[1] == "start") {
    nodes["start"].add(inputNodes[0])
  } else {
    if (inputNodes[0] != "end") {
      nodes[inputNodes[0]].add(inputNodes[1])
    }
    if (inputNodes[1] != "end") {
      nodes[inputNodes[1]].add(inputNodes[0])
    }
  }
}

println nodes

def traverse(node, path, fullPaths) {
  path.add(node)

  if (node == "end") {
    fullPaths.add(path.clone())
  } else {
    println nodes[node]
    nodes[node].each { vertex ->
      if (!(vertex.toLowerCase().equals(vertex) && path.contains(vertex))) {
        traverse(vertex,path.clone(),fullPaths)
      }
    }
  }
  return fullPaths
}

paths = traverse("start", [], [])
nodes.each {
  println it
}
paths.each {
  println it
}
println paths.size()
