// Follow steps to insert characters between sequences of 2 characters, repeat this process 10 times and print most frequent - least frequent characters

inputFile = "inputs/14.txt"
rules = [:]
steps = 40
frequencies = [:]
pairs = [:]

new File(inputFile).eachLine { line ->
  if (line.contains('->')) {
    rule = line.split(' -> ')
    rules[rule[0]] = [rule[0][0] + rule[1], rule[1] + rule[0][1]]
  } else if (line?.trim()) {
    startingInput = line.trim()
    lastChar = ""
    startingInput.each { c ->
      if (frequencies.containsKey(c)) {
        frequencies[c]++
      } else {
        frequencies[c] = 1G
      }
      if (lastChar?.trim()) {
        sequence = lastChar + c
        if (pairs.containsKey(sequence)) {
          pairs[sequence]++
        } else {
          pairs[sequence] = 1G
        }
      }
      lastChar = c
    }
  }
}

steps.times { time ->
  newPairs = [:]
  pairs.each { pair, pairCount ->
    if (rules.containsKey(pair)) {
      rules[pair].each { newSequence ->
        if (newPairs.containsKey(newSequence)) {
          newPairs[newSequence] += pairCount
        } else {
          newPairs[newSequence] = pairCount
        }
      }
      newChar = rules[pair][0][1]
      if (frequencies.containsKey(newChar)) {
        frequencies[newChar] += pairCount
      } else {
        frequencies[newChar] = 1G
      }
    } else {
      if (newPairs.containsKey(pair)) {
        newPairs[pair] += pairCount
      } else {
        newPairs[pair] = pairCount
      }
    }
  }
  pairs = newPairs
}

print frequencies.max { it.value }.value - frequencies.min { it.value }.value
