// Create a chart from the input, fold "up" for y, "left" for x, find how many coordinates are visible after the first fold, then look at the output on a grid and determine what the letters are

inputFile = "inputs/13.txt"
// Use a map here to guarantee uniqueness
input = [:]
// folds are [x,1] [y,13] etc
folds = []

new File(inputFile).eachLine { line ->
  if (line.startsWith('fold along')) {
    fold = line.split('=')
    folds.add([fold[0][-1], fold[1] as int])
  } else if (line?.trim()) {
    coord = line.split(",").collect { it as int }
    input[coord] = true
  }
}

folds.each { fold ->
  folded = [:]
  input.keySet().each {
    foldCoord = fold[1]
    currentX = it[0]
    currentY = it[1]
    if (fold[0] == 'x') {
      if (currentX > foldCoord) {
        newX = 2*foldCoord - currentX
        folded[[newX,currentY]] = true
      } else {
        folded[[currentX,currentY]] = true
      }
    } else {
      if (currentY > foldCoord) {
        newY = 2*foldCoord - currentY
        folded[[currentX,newY]] = true
      } else {
        folded[[currentX,currentY]] = true
      }
    }
  }
  println folded.keySet().size()
  input = folded
}

coords = []

// Transpose our coordinates because they are (x,y) and arrays are [y,x]
input.keySet().each {
  coords.add([it[1],it[0]])
}

// These array lengths were determined by looking at the output. Could have iterated through all the coordinates and found the maxes too
grid = new String[6][40]

// Fill 2d array and then print it so we can see the letters the coordinates represent. ** and .. are used for better visibility than * and .
grid.eachWithIndex { row, rowIdx ->
  row.eachWithIndex { val, colIdx ->
    if (coords.contains([rowIdx,colIdx])) {
      grid[rowIdx][colIdx] = "**"
    } else {
      grid[rowIdx][colIdx] = ".."
    }
  }
}

// while adding comments it occurs to me that I don't need a grid value and can just loop 40x6 times and print the values from the folded map values
grid.each { row ->
  row.each {
    print it
  }
  println ""
}
