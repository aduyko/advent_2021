// Find how many different starting velocities will eventually cross through the input area
// There's definitely a math way to do this but I'm not figuring that out
inputFile = "inputs/17.txt"
input = ""

input = new File(inputFile).text.trim()[13..-1].split(", ").collect { it.split("=")[1].split("\\.\\.").collect { it as int } }

xMin = input[0][0]
xMax = input[0][1]
yMin = input[1][1]
yMax = input[1][0]

totalFound = 0

(1..xMax).each { dx ->
  odx = dx
  (yMax..200).each { dy ->
    ody = dy
    dx = odx
    xPos = 0
    yPos = 0  
    currentFoundMax = 0
    found = false
    while (xPos <= xMax && yPos >= yMax) {
      xPos += dx
      yPos += dy
      if (dx > 0) {
        dx -= 1
      } else if (dx < 0) {
        dx += 1
      }
      dy -= 1
      
      if (yPos > currentFoundMax) {
        currentFoundMax = yPos
      }
      if (xMin <= xPos && xPos <= xMax && yMin >= yPos && yPos >= yMax) {
        found = true
      }
    }
    if (found) {
      totalFound++
    }
  }
}

println totalFound
