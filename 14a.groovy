// Follow steps to insert characters between sequences of 2 characters, repeat this process 10 times and print most frequent - least frequent characters

inputFile = "inputs/14.txt"
input = ""
rules = [:]
steps = 10
frequencies = [:]

new File(inputFile).eachLine { line ->
  if (line.contains('->')) {
    rule = line.split(' -> ')
    rules[rule[0]] = rule[1]
  } else if (line?.trim()) {
    input = line.trim()
  }
}

steps.times {
  newInput = []
  lastChar = ""
  input.each { c ->
    if (lastChar?.trim()) {
      sequence = lastChar + c
      if (rules.containsKey(sequence)) {
        insertion = rules[sequence]
        newInput.add(insertion)
      }
    }
    newInput.add(c)
    lastChar = c
  }
  input = newInput
}

input.each { c ->
  if (frequencies.containsKey(c)) {
    frequencies[c]++
  } else {
    frequencies[c] = 1
  }
}

print frequencies.max { it.value }.value - frequencies.min { it.value }.value
