// Follow a bunch of rules on a hex number converted to binary
inputFile = "inputs/16.txt"
input = []
packets = []

new File(inputFile).eachLine { line ->
  input.add(
    line.collect { Integer.parseInt(it, 16) }
    .collect { String.format("%4s", Integer.toBinaryString(it)).replace(' ','0') }
    .join()
  )
}

class Packet {
  int version
  int type
  BigInteger literalValue
  String opType
  int opValue
  def subPackets
}

def pruneStack(length, parseStack) {
  newParseStack = []
  parseStack.each { stack ->
    if (stack["type"] == "sizeSubPackets") {
      stack["value"] -= length
    }
    if (stack["value"] != 0) {
      newParseStack << stack
    }
  }
  return newParseStack
}

def parsePackets(binary, parseStack) {
  version = Integer.parseInt(binary[0..2],2)
  type = Integer.parseInt(binary[3..5],2)
  value = binary[6..-1]

  packet = new Packet(version: version, type: type, subPackets: [])
  packets << packet

  position = 0
  if (type == 4) {
    leadingBit = value[0] as int
    literal = value[1..4]
    position = 5
    while (leadingBit != 0) {
      leadingBit = value[position] as int
      literal += value[position+1..position+4]
      position += 5
    }
    packet.opType = "literalValue"
    packet.literalValue = new BigInteger(literal,2)
    if (parseStack.size() > 0) {
      stack = parseStack[-1]
      stack["packet"].subPackets << packet
      if (stack["type"] == "numSubPackets") {
        stack["value"]--
      }
      if (stack["value"] == 0) {
        parseStack.removeElement(stack)
      }
      if (parseStack.size() > 0) {
        parseStack = pruneStack(position + 6, parseStack)
      }
      if (parseStack.size() > 0) {
        parsePackets(value[position..-1], parseStack)
      }
    }
  } else {
    opType = value[0] as int
    opTypeHR = ""
    opLength = 0
    opValue = 0
    if (opType == 0) {
      opTypeHR = "sizeSubPackets"
      opLength = 16
      opValue = Integer.parseInt(value[1..15], 2)
    } else {
      opTypeHR = "numSubPackets"
      opLength = 12
      opValue = Integer.parseInt(value[1..11], 2)
    }
    packet.opType = opTypeHR
    packet.opValue = opValue
    if (parseStack.size() > 0) {
      stack = parseStack[-1]
      stack["packet"].subPackets << packet
      if (stack["type"] == "numSubPackets") {
        stack["value"]--
      }
      if (stack["value"] == 0) {
        parseStack.removeElement(stack)
      }
      if (parseStack.size() > 0) {
        parseStack = pruneStack(opLength + 6, parseStack)
      }
    }

    parseStack << [
      "type": opTypeHR,
      "value": opValue,
      "packet": packet
    ]
    parsePackets(value[opLength..-1], parseStack)
  }
}

def calculateValue(packet) {
  subPackets = packet.subPackets
  switch (packet.type) {
    case 0:
      sum = 0G
      subPackets.each {
        sum += calculateValue(it)
      }
      return sum
    case 1:
      product = 1G
      subPackets.each {
        product *= calculateValue(it)
      }
      return product
    case 2:
      values = subPackets.collect { calculateValue(it) }
      return Collections.min(values)
    case 3:
      values = subPackets.collect { calculateValue(it) }
      return Collections.max(values)
    case 4:
      return packet.literalValue
    case 5:
      values = subPackets.collect { calculateValue(it) }
      return values[0] > values [1] ? 1 : 0
    case 6:
      values = subPackets.collect { calculateValue(it) }
      return values[0] < values [1] ? 1 : 0
    case 7:
      values = subPackets.collect { calculateValue(it) }
      return values[0] == values [1] ? 1 : 0
  }
}

input.each {
  parsePackets(it,[])
  println calculateValue(packets[0])
  packets = []
}
