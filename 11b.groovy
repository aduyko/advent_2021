// For each step, increase each octopus value by 1. If it is 9, set it to 0 and increase all adjacent numbers by 1. Any number increased past 9 this way is set to 0. Do this until all numbers are 0 and report how many iterations that took.

inputFile = "inputs/11.txt"
input = []

new File(inputFile).eachLine { line ->
  input.add(line.collect { [it as int, false] })
}

def flash(row, col) {
  directions = [
    [-1,-1],
    [-1,0],
    [-1,1],
    [0,-1],
    [0,0],
    [0,1],
    [1,-1],
    [1,0],
    [1,1]
  ]
  if (row == 0) {
    directions -= [
      [-1,-1],
      [-1,0],
      [-1,1]
    ]
  }
  if (col == 0) {
    directions -= [
      [-1,-1],
      [0,-1],
      [1,-1]
    ]
  }
  if (row == input.size()-1) {
    directions -= [
      [1,-1],
      [1,0],
      [1,1]
    ]
  }
  if (col == input[row].size()-1) {
    directions -= [
      [-1,1],
      [0,1],
      [1,1]
    ]
  }
  directions.each { direction ->
    newRow = row + direction[0]
    newCol = col + direction[1]
    if (!input[newRow][newCol][1]) {
      input[newRow][newCol][0]++
      if (input[newRow][newCol][0] == 10) {
        input[newRow][newCol][0] = 0
        input[newRow][newCol][1] = true
        flash(newRow,newCol)
      }
    }
  }
}

steps = 0
flashes = 0
while (flashes != input.size() * input[0].size()) {
  steps++
  flashes = 0
  (0..<input.size()).each { row ->
    (0..<input[row].size()).each { col ->
      // Only increase if this cell has not flashed
      if (!input[row][col][1]) {
        input[row][col][0]++
        if (input[row][col][0] == 10) {
          input[row][col][0] = 0
          input[row][col][1] = true
          flash(row,col)
        }
      }
    }
  }
  (0..<input.size()).each { row ->
    (0..<input[row].size()).each { col ->
      if (input[row][col][1]) {
        flashes++
        input[row][col][1] = false
      }
    }
  }
}

print steps
