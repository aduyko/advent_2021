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
  int literalValue
  String opType
  int opValue
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

  packet = new Packet(version: version, type: type)
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
      opValue = new BigInteger(value[1..15], 2)
    } else {
      opTypeHR = "numSubPackets"
      opLength = 12
      opValue = new BigInteger(value[1..11], 2)
    }
    packet.opType = opTypeHR
    packet.opValue = opValue
    if (parseStack.size() > 0) {
      stack = parseStack[-1]
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
      "value": opValue
    ]
    parsePackets(value[opLength..-1], parseStack)
  }
}

input.each {
  parsePackets(it,[])
  println packets.sum { it.version }
  packets = []
}
